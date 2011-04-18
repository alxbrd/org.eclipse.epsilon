/*******************************************************************************
 * Copyright (c) 2008-2011 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - test listeners, parametric testing
 ******************************************************************************/
package org.eclipse.epsilon.eol.eunit;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.epsilon.commons.parse.AST;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.EolOperation;
import org.eclipse.epsilon.eol.annotations.EolAnnotationsUtil;
import org.eclipse.epsilon.eol.exceptions.EolAssertionException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolSequence;

public class EUnitModule extends EolModule {
	
	private static final String MODEL_BINDING_ANNOTATION_NAME = "with";
	/** Default package name for the JUnit reports. */
	public static final String DEFAULT_PACKAGE = "default";
	private String packageName = DEFAULT_PACKAGE;

	private static ThreadMXBean THREAD_MXBEAN;
	private List<EUnitTestListener> testListeners = new ArrayList<EUnitTestListener>();
	private EUnitTest suiteRoot;

	// Destination directory for the JUnit XML report
	private File reportDirectory = new File(".");

	@SuppressWarnings("rawtypes")
	private List selectedOperations;

	static {
		THREAD_MXBEAN = ManagementFactory.getThreadMXBean();
		THREAD_MXBEAN.setThreadCpuTimeEnabled(true);
	}

	public ArrayList<EolOperation> getTests() {
		return getOperationsAnnotatedWith("test");
	}

	public ArrayList<EolOperation> getInlineModelOperations() {
		return getOperationsAnnotatedWith("model");
	}

	public ArrayList<EolOperation> getSetups() {
		return getOperationsAnnotatedWith("setup");
	}
	
	public ArrayList<EolOperation> getTeardowns() {
		return getOperationsAnnotatedWith("teardown");
	}

	public Map<EolOperation, String> getDataVariableNames() {
		final Map<EolOperation, String> results = new LinkedHashMap<EolOperation, String>();
	    for (EolOperation op : getOperations()) {
			try {
				String variableName = (String)EolAnnotationsUtil.getAnnotationValue(op.getAst(), "data", getContext());
				if (variableName != null) {
					results.put(op, variableName);
				}
			} catch (EolRuntimeException e) {}
		}
	    return results;
	}

	public boolean isAnnotatedAs(EolOperation operation, String annotation) {
		try {
			return EolAnnotationsUtil.getBooleanAnnotationValue(operation.getAst(), annotation, context, false, true);
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public Object execute() throws EolRuntimeException {
		// We're stricter when running EUnit than with the other E*L languages:
		// we will abort test execution if the EUnit module had any parse problems
		if (!getParseProblems().isEmpty()) {
			throw new EUnitParseException(this.getParseProblems());
		}

		prepare();
		try {
			runSuite(getSuiteRoot());
		} finally {
			writeReport();
		}
		return null;
	}

	public EUnitTest getSuiteRoot() throws EolRuntimeException {
		if (suiteRoot != null) {
			return suiteRoot;
		}

		Map<EolOperation, String> dataVariables = getDataVariableNames();
		List<Map.Entry<EolOperation, String>> pairs
			= new ArrayList<Map.Entry<EolOperation, String>>(dataVariables.entrySet());
		suiteRoot = new EUnitTest();
		populateSuiteTree(suiteRoot, pairs.listIterator());

		return suiteRoot;
	}

	public void runSuite(EUnitTest node) throws EolRuntimeException {
		try {
			// Make sure any exception while running or preparing the test
			// case does not crash the test suite, and is properly reported
			runSuiteInternal(node);
		}
		catch (EolAssertionException asex) {
			setResultWithFailureTrace(node, asex, EUnitTestResultType.FAILURE);
		}
		catch (Exception ex) {
			setResultWithFailureTrace(node, ex, EUnitTestResultType.ERROR);
		}
		finally {
			// Wipe any EOL operation caches
			wipeCaches();
			// Save the time required to run the test
			node.setEndCpuTime(THREAD_MXBEAN.getCurrentThreadCpuTime());
			node.setEndWallclockTime(System.currentTimeMillis());
			// Notify all users that the test is done
			fireAfterCase(node);

			if (node.getOperation() != null) {
				getContext().getFrameStack().leave(node.getOperation().getAst());
			}
		}
	}

	protected List<Object> getModelBindings(EolOperation opTest)
			throws EolRuntimeException {
		return EolAnnotationsUtil.getAnnotationsValues(opTest.getAst(), MODEL_BINDING_ANNOTATION_NAME, getContext());
	}

	protected boolean hasModelBindings(EolOperation opTest) {
		return EolAnnotationsUtil.hasAnnotation(opTest.getAst(), MODEL_BINDING_ANNOTATION_NAME);
	}

	private void populateSuiteTree(EUnitTest parent, ListIterator<Map.Entry<EolOperation,String>> dataIterator) throws EolRuntimeException {
		if (dataIterator.hasNext()) {
			populateSuiteTreeDataOperation(parent, dataIterator);
		} else {
			populateSuiteTreeTestOperation(parent);
		}
	}

	private void populateSuiteTreeTestOperation(EUnitTest parent)
			throws EolRuntimeException {
		for (EolOperation opTest : this.getTests()) {
			EUnitTest test = new EUnitTest();
			test.setParent(parent);
			test.setOperation(opTest);
			parent.addChildren(test);

			try {
				if (hasModelBindings(opTest)) {
					final List<Object> annotationsValues = getModelBindings(opTest);

					if (annotationsValues.size() == 1) {
						// Do not create an inner node if there is only one
						// model binding
						test.setModelBindings((EolSequence) annotationsValues.get(0));
					} else {
						for (Object annotation : annotationsValues) {
							EUnitTest child = new EUnitTest();
							child.setParent(test);
							child.setOperation(opTest);
							child.setModelBindings((EolSequence) annotation);
							test.addChildren(child);
						}
					}
				}
			} catch (Exception ex) {
				this.setResultWithFailureTrace(test, ex, EUnitTestResultType.ERROR);
			}
		}
	}

	private void populateSuiteTreeDataOperation(EUnitTest parent,
			ListIterator<Map.Entry<EolOperation, String>> dataIterator)
			throws EolRuntimeException {
		final Map.Entry<EolOperation, String> entry = dataIterator.next();

		try {
			final EolSequence values
				= (EolSequence) entry.getKey().execute(null, Collections.EMPTY_LIST, context, true);
			final String variableName = entry.getValue();
			for (Object value : values) {
				EUnitTest child = new EUnitTest();
				child.setParent(parent);
				child.setDataVariableName(variableName);
				child.setDataValue(value);
				child.setOperation(entry.getKey());
				parent.addChildren(child);

				// If the node has a data binding, use it while populating this
				// node's subtree: it may be used in a $with annotation.
				final FrameStack frameStack = getContext().getFrameStack();
				final AST operationAST = child.getOperation().getAst();
				frameStack.enter(FrameType.UNPROTECTED, operationAST);
				applyDataBinding(child);
				populateSuiteTree(child, dataIterator);
				frameStack.leave(operationAST);
			}
		}
		catch (Exception ex) {
			setResultWithFailureTrace(parent, ex, EUnitTestResultType.ERROR);
		}
		finally {
			if (dataIterator.hasPrevious()) {
				dataIterator.previous();
			}
		}
	}

	private void setResultWithFailureTrace(
		EUnitTest node, Exception asex, final EUnitTestResultType resultType)
	{
		node.setResult(resultType);
		node.setException(asex);
		node.setFrameStack(getContext().getFrameStack().clone());
	}

	private void runSuiteInternal(EUnitTest node)
			throws EolModelNotFoundException, EolRuntimeException {
		if (node.getResult().equals(EUnitTestResultType.SKIPPED)) {
			// The test case is to be skipped
			return;
		}

		// We need separate stack frames to ensure everything is clean after
		// each test case
		if (node.getOperation() != null) {
			getContext().getFrameStack().enter(FrameType.UNPROTECTED, node.getOperation().getAst());
		}

		// Implement data bindings
		if (node.getDataVariableName() != null) {
			// This node has a variable binding: use it
			applyDataBinding(node);
		}

		// We need to set test start time *before* firing the beforeCase notification
		// so the time taken by the nested tasks in the setup part is included.
		node.setStartCpuTime(THREAD_MXBEAN.getCurrentThreadCpuTime());
		node.setStartWallclockTime(System.currentTimeMillis());
		if (node.getResult().equals(EUnitTestResultType.NOT_RUN_YET)) {
			// Do not overwrite the result for tests which failed during tree population,
			// but still act as if we were running them, to avoid confusing the EUnit view
			node.setResult(EUnitTestResultType.RUNNING);
		}
		fireBeforeCase(node);

		if (node.isRootTest()) {
			// We need to wrap the original streams to capture all their output, for the JUnit-like reports.
			// We can't do it in #initialize(), as the EUnit Ant task changes stdout/stderr to point to the
			// Epsilon console in the beforeCase EUnitTestListener handler.
			getContext().setOutputStream(new ByteBufferTeePrintStream(getContext().getOutputStream()));
			getContext().setErrorStream(new ByteBufferTeePrintStream(getContext().getErrorStream()));
		}

		// Load models from the inline model operations, if any
		for (EolOperation inlineModelOp : getInlineModelOperations()) {
			inlineModelOp.execute(null, Collections.EMPTY_LIST, context, false);
		}

		// Implement model bindings (needs to be after fireBeforeCase, so
		// the EUnit Ant task can load the models)
		if (node.getModelBindings() != null) {
			applyModelBindings(node);
		}

		if (node.getResult().equals(EUnitTestResultType.RUNNING)) {
			if (node.getChildren().isEmpty()) {
				// Leaf test case: simply run it
				runLeafTestCase(node.getOperation(), node);
			} else {
				runInnerTestCase(node);
			}
		}
	}

	private void runInnerTestCase(EUnitTest node) throws EolRuntimeException {
		// Inner node: run its children. The result of this
		// node is as follows:
		//
		// * ERROR if at least one child had an ERROR result.
		// * Otherwise, FAILURE if at least one child had a FAILURE result.
		// * Otherwise, SUCCESS.
		for (EUnitTest child : node.getChildren()) {
			runSuite(child);
			switch (child.getResult()) {
			case ERROR: node.setResult(EUnitTestResultType.ERROR);
			case FAILURE:
				if (node.getResult() != EUnitTestResultType.ERROR) {
					node.setResult(EUnitTestResultType.FAILURE);
				}
			}
		}
		if (node.getResult() != EUnitTestResultType.ERROR && node.getResult() != EUnitTestResultType.FAILURE) {
			node.setResult(EUnitTestResultType.SUCCESS);
		}
	}

	private void runLeafTestCase(EolOperation opTest, EUnitTest node) throws EolRuntimeException {
		/*
		 * NOTE: the @setup, @test and @teardown operations are all called within
		 * the same unprotected stack frame, so they can reuse the same variables
		 * and access the variables bound by the @data operations.
		 */

		// EXECUTION
		try {
			// Call the @setup operations
			for (EolOperation opSetup : this.getSetups()) {
				opSetup.execute(null, Collections.EMPTY_LIST, context, false);
			}

			opTest.execute(null, Collections.EMPTY_LIST, context, false);
			node.setResult(EUnitTestResultType.SUCCESS);
		}
		finally {
			// Call the @teardown operations
			for (EolOperation opTeardown : this.getTeardowns()) {
				opTeardown.execute(null, Collections.EMPTY_LIST, context, false);
			}
		}
	}

	private void applyDataBinding(EUnitTest node) {
		Variable dataVariable = new Variable(node.getDataVariableName(), node.getDataValue(), EolAnyType.Instance, true);
		getContext().getFrameStack().put(dataVariable);
	}

	/**
	 * This method applies the model bindings set in <code>node</code>. The bindings
	 * rename the models in the model repository as indicated by the user: for instance,
	 * assume the model repository had models A and B. After applying the bindings from
	 * <code>$with Sequence {"", "A", "C", "B"}</code>, the default model is now A
	 * (keeping its name) and model B is renamed to C. The rest of the models are kept
	 * as is, though the order in the model repository may vary.
	 */
	private void applyModelBindings(EUnitTest node) throws EolModelNotFoundException {
		// Store the model to be used as default model (usable with no prefix,
		// must be first in the list of models in the model repository).
		final ModelRepository modelRepository = getContext().getModelRepository();
		final Map<String, String> bindings = new HashMap<String, String>(node.getModelBindings());
		IModel defaultModel = modelRepository.getModelByName("");
		if (bindings.containsKey("")) {
			defaultModel = modelRepository.getModelByName(bindings.get(""));
			bindings.remove("");
		}
		modelRepository.removeModel(defaultModel);

		// Store the models to be renamed
		final List<IModel> renamedModels = new ArrayList<IModel>();
		for (Map.Entry<String, String> entry : bindings.entrySet()) {
			if (defaultModel != null && entry.getValue().equals(defaultModel.getName())) {
				// Check if the module to be renamed is the default model
				defaultModel.setName(entry.getKey());
				continue;
			}

			final IModel renamedModel = modelRepository.getModelByName(entry.getValue());
			renamedModel.setName(entry.getKey());
			renamedModels.add(renamedModel);
			modelRepository.removeModel(renamedModel);
		}

		// Store the rest of the models, and remove them
		final List<IModel> otherModels = new ArrayList<IModel>(modelRepository.getModels());
		for (IModel model : otherModels) {
			modelRepository.removeModel(model);
		}

		// Add the models back: first the default, then the renamed models, and then the rest
		assert modelRepository.getModels().isEmpty();
		modelRepository.addModel(defaultModel);
		for (IModel model : renamedModels) {
			modelRepository.addModel(model);
		}
		for (IModel model : otherModels) {
			modelRepository.addModel(model);
		}
	}

	private void wipeCaches() {
		for (EolOperation op : getOperations()) {
			op.clearCache();
		}
		getContext().getExtendedProperties().clear();
	}

	private ArrayList<EolOperation> getOperationsAnnotatedWith(
			String annotationName) {
		ArrayList<EolOperation> results = new ArrayList<EolOperation>();
		for (EolOperation operation : getOperations()) {
			if (isAnnotatedAs(operation, annotationName)){
				results.add(operation);
			}
		}
		return results;
	}

	/* EVENT NOTIFICATION METHODS */

	public void addTestListener(EUnitTestListener listener) {
		testListeners.add(listener);
	}

	private void fireAfterCase(EUnitTest test) {
		for (EUnitTestListener listener : testListeners) {
			listener.afterCase(this, test);
		}
	}

	private void fireBeforeCase(EUnitTest test) {
		for (EUnitTestListener listener : testListeners) {
			listener.beforeCase(this, test);
		}
	}

	/* OPERATION FILTERING */

	@SuppressWarnings("rawtypes")
	public List getSelectedOperations() {
		return selectedOperations;
	}

	@SuppressWarnings("rawtypes")
	public void setSelectedOperations(List attribute) throws EolRuntimeException {
		this.selectedOperations = attribute;

		// Scan the test tree and mark entries as skipped as necessary
		markSkippedEntries(getSuiteRoot());
	}

	/**
	 * Returns <code>true</code> if this node was skipped, <code>false</code> otherwise.
	 * Inner nodes whose children are all skipped will be skipped as well.
	 */
	private boolean markSkippedEntries(EUnitTest node) {
		// The node is explicitly listed: skip it
		if (!node.isSelected(selectedOperations)) {
			node.setResult(EUnitTestResultType.SKIPPED);
			return true;
		}

		// Not listed: reset skipped status to "not run yet"
		if (node.getResult().equals(EUnitTestResultType.SKIPPED)) {
			node.setResult(EUnitTestResultType.NOT_RUN_YET);
		}

		if (node.isLeafTest()) {
			// If this is a leaf test, we know we won't skip it
			return false;
		}
		else {
			// This inner node will be skipped if all its children are skipped
			boolean bAllChildrenSkipped = true;
			for (EUnitTest child : node.getChildren()) {
				// We do *not* want the short-circuit evaluation of && here: & is *not* a typo
				bAllChildrenSkipped = bAllChildrenSkipped & markSkippedEntries(child);
			}
			if (bAllChildrenSkipped) {
				node.setResult(EUnitTestResultType.SKIPPED);
			}
			return bAllChildrenSkipped;
		}
	}

	/* JUNIT-LIKE REPORTS */

	/**
	 * Changes the destination file for the JUnit-style XML report.
	 * By default, it is {@link EUnitModule#DEFAULT_REPORT_FILE}. If <code>null</code>,
	 * no report will be written.
	 */
	public void setReportDirectory(File reportFile) {
		this.reportDirectory = reportFile;
	}

	/**
	 * Returns the destination file for the JUnit-style XML report.
	 */
	public File getReportDirectory() {
		return reportDirectory;
	}

	private void writeReport() throws EolRuntimeException {
		if (reportDirectory != null) {
			EUnitXMLFormatter formatter = new EUnitXMLFormatter(this);
			formatter.generate(reportDirectory);
		}
	}

	/**
	 * Returns the "class name" to be used for this module in JUnit-style reports.
	 * It is the basename of the .eunit file, without the extension.
	 */
	public String getClassName() {
		final String filename = getAst().getFile().getName();
		final int lastDot = filename.lastIndexOf('.');
		if (lastDot != -1) {
			return filename.substring(0, lastDot);
		}
		else {
			return filename;
		}
	}

	/**
	 * Returns the package name to use in the reports. By default, it is {@link #DEFAULT_PACKAGE}.
	 */
	public String getPackage() {
		return packageName;
	}

	/**
	 * Changes the package name to use in the reports. By default, it is {@link #DEFAULT_PACKAGE}.
	 */
	public void setPackage(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Returns the logical name of this module as if it was a Java class, for the JUnit-style reports.
	 */
	public String getQualifiedName() {
		return getPackage() + "." + getClassName();
	}

}
