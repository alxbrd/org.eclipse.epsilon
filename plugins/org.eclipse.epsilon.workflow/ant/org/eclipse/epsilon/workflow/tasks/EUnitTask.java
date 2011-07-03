/*******************************************************************************
 * Copyright (c) 2008-2011 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - rewrite with nested Ant task support
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.common.dt.extensions.ClassBasedExtension;
import org.eclipse.epsilon.emc.hutn.HutnModel;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.dt.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EclipseContextManager;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolClasspathNativeTypeDelegate;
import org.eclipse.epsilon.eol.userinput.JavaConsoleUserInput;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestListener;
import org.eclipse.epsilon.eunit.EUnitTestResultType;

/**
 * Ant task for running EUnit test suites.
 *
 * @author Antonio Garcia-Dominguez
 * @version 1.0
 */
public class EUnitTask extends ExecutableModuleTask implements EUnitTestListener {

	private static final String EUNIT_TEST_LISTENER_EXTENSION_POINT_ID = "org.eclipse.epsilon.workflow.eunit.listener";

	/**
	 * Class for a nested element which simply contains tasks.
	 */
	public class TaskCollection implements TaskContainer {
		private List<Task> tasks = new ArrayList<Task>();

		public void addTask(Task task) {
			tasks.add(task);
			if (task instanceof ExecutableModuleTask) {
				ExecutableModuleTask moduleTask = (ExecutableModuleTask)task;

				// The gui attribute of the EUnit Ant task is inherited by all nested tasks
				moduleTask.setGUI(isGUI());
			}
		}

		public void run() {
			// We trick tasks into using the EUnit model repository instead of the project's
			for (Task task : tasks) {
				task.perform();
			}
		}
	}

	public class RunTargetOperationContributor extends OperationContributor {
		@Override
		public boolean contributesTo(Object target) {
			return true;
		}

		/**
		 * Operation which can call a series of Ant tasks described inside a
		 * "script" nested element of this Ant task.
		 */
		public void runTarget(String targetName) throws EolRuntimeException {
			// Check that the name of the target is not null
			if (targetName == null) {
				throw new EolRuntimeException("The name of the target to be run cannot be null");
			}

			// Run tasks, ensuring they manipulate our model repository instead of the project's
			getProject().executeTarget(targetName);
		}

		/**
		 * EUnit-specific operation which is equivalent to the "exports" nested element.
		 */
		public void exportVariable(String varName) {
			EUnitTask.this.exportVariable(varName, varName, false);
		}

		/**
		 * EUnit-specific operation which is equivalent to the "imports" nested element.
		 */
		public void useVariable(String varName) {
			EUnitTask.this.useVariable(varName, varName, false);
		}

		/**
		 * EUnit-specific operation for loading models inside the .eunit file from HUTN fragments.
		 */
		public void loadHutn(String modelName, String hutnContent) throws EolModelLoadingException {
			final HutnModel hutnModel = new HutnModel(modelName, hutnContent);
			hutnModel.load();
			ModelRepository modelRepository = module.getContext().getModelRepository();
			modelRepository.addModel(hutnModel);
		}
	}

	private File fReportDirectory;
	private String fPackage = EUnitModule.DEFAULT_PACKAGE;
	private boolean fGenerateReport = true;
	private TaskCollection modelLoadingTasks;
	private ModelRepository oldProjectRepository;

	public EUnitTask() {
		// By default, the EUnit Ant task disables JFace-based user input,
		// which hinders automated testing
		setGUI(false);
	}

	@Override
	protected void initialize() throws Exception {
		final EUnitModule eunitModule = (EUnitModule)module;
		eunitModule.addTestListener(this);
		eunitModule.setPackage(getPackage());
		if (getToDir() != null) {
			eunitModule.setReportDirectory(getToDir());
		}
		else if (isReport()) {
			eunitModule.setReportDirectory(getProject().getBaseDir());
		}
		else {
			eunitModule.setReportDirectory(null);
		}

		for (EUnitTestListener extraListener : ClassBasedExtension.getImplementations(EUNIT_TEST_LISTENER_EXTENSION_POINT_ID, EUnitTestListener.class)) {
			eunitModule.addTestListener(extraListener);
		}
	}

	@Override
	protected void examine() throws Exception {
		final EUnitTest test = ((EUnitModule)createModule()).getSuiteRoot();
		final PrintStream out = module.getContext().getOutputStream();

		out.println("Global result: " + test.getResult());
		if (test.getResult() == EUnitTestResultType.FAILURE || test.getResult() == EUnitTestResultType.ERROR) {
			fail("At least one test case had a failure or an error", test.getException());
		}
	}

	@Override
	protected EolDebugger createDebugger() {
		return new EolDebugger();
	}

	@Override
	public IEolExecutableModule createModule() {
		// We store the created module, so the EUnit view can call this,
		// register itself as a listener, and then let EUnitTask configure
		// it as usual
		if (module == null) {
			module = new EUnitModule();
			final IEolContext context = module.getContext();
			context.getOperationContributorRegistry().add(new RunTargetOperationContributor());
			context.getFrameStack().put(
					new Variable("antProject", getProject(), new EolAnyType(), true));

			// Replace the default native type delegate (which uses the Eclipse class loader) with
			// one which uses the Ant classpath, as customized by the user
			final ClassLoader classLoaderAnt = getProject().createClassLoader(org.apache.tools.ant.types.Path.systemClasspath);
			context.getNativeTypeDelegates().clear();
			context.getNativeTypeDelegates().add(new EolClasspathNativeTypeDelegate(classLoaderAnt));
		}
		return module;
	}

	// TEST LISTENER METHODS

	public void beforeCase(EUnitModule module, EUnitTest test) {
		if (test.isRootTest() && Platform.getExtensionRegistry() != null) {
			EclipseContextManager.setup(module.getContext());

			// Disable notification through dialogs: it's bad for automated test cases.
			// Use the console instead.
			module.getContext().setUserInput(new JavaConsoleUserInput());
		}

		if (test.isLeafTest()) {
			try {
				// Dispose all models in this module's model repository, and reload them from the <model> references
				populateModelRepository(true);
			} catch (Exception e) {
				fail("Exception while repopulating the model repository", e);
			}

			// We need to trick the other Ant tasks into loading models into this module's repository.
			// These may be run from <modelTasks> or from the @model operations
			oldProjectRepository = getProjectRepository();
			setProjectRepository(module.getContext().getModelRepository());

			// Run the <modelTasks>
			if (modelLoadingTasks != null) {
				modelLoadingTasks.run();
			}
		}
	}

	public void afterCase(EUnitModule module, EUnitTest test) {
		// Restore the original model repository for the project after running the test
		if (test.isLeafTest()) {
			setProjectRepository(oldProjectRepository);
			module.getContext().getModelRepository().dispose();
		}

		final PrintStream out = module.getContext().getOutputStream();
		final PrintStream err = module.getContext().getErrorStream();
		final String sMillis = String.format(" [cpu: %d ms, wallclock: %d ms]", test.getCpuTimeMillis(), test.getWallclockTimeMillis());

		final String testDescription = "Test " + test.getMethodName() + " {" + test.explainAllBindings() + "}";
		if (test.getResult() == EUnitTestResultType.SUCCESS) {
			out.println(testDescription + " passed" + sMillis);
		} else if (test.getResult() == EUnitTestResultType.SKIPPED){
			out.println(testDescription + " skipped" + sMillis);
		} else {
			err.print(testDescription + " failed with status " + test.getResult());
			final Exception testException = test.getException();
			if (testException != null) {
				err.println(": " + testException.getMessage());
			}
			else {
				err.println();
			}
		}
	}

	// NESTED ELEMENTS

	public TaskCollection createModelTasks() {
		if (modelLoadingTasks == null) {
			modelLoadingTasks = new TaskCollection();
		}
		return modelLoadingTasks;
	}

	// TEST REPORT METHODS

	/**
	 * Returns the destination directory for the JUnit-like report. By default,
	 * it is the base directory of the Ant project.
	 */
	public File getToDir() {
		return fReportDirectory;
	}

	/**
	 * Changes the destination directory for the JUnit-like report. See {@link #getToDir()} for the default value.
	 */
	public void setToDir(File f) {
		fReportDirectory = f;
	}

	/**
	 * Returns the package in which all tests will be contained. By default, it
	 * is set to {@link EUnitModule#DEFAULT_PACKAGE}.
	 */
	public String getPackage() {
		return fPackage ;
	}

	/**
	 * Changes the package in which all tests will be contained. Empty or null arguments are <b>ignored</b>.
	 */
	public void setPackage(String packageName) {
		fPackage = packageName;
	}

	/**
	 * Returns <code>true</code> if a XML report compatible with the &lt;junit&gt; Ant task should be generated.
	 */
	public boolean isReport() {
		return fGenerateReport;
	}

	/**
	 * Changes whether an XML report compatible with the &lt;junit&gt; Ant task should be generated. By default,
	 * it will be generated.
	 * @param generate If <code>true</code>, the XML report will be generated. Otherwise, it will not be generated.
	 */
	public void setReport(boolean generate) {
		this.fGenerateReport = generate;
	}

}
