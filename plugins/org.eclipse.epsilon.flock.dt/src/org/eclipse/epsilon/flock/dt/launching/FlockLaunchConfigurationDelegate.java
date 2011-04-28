/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.dt.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.IFlockContext;
import org.eclipse.epsilon.flock.dt.FlockDebugger;
import org.eclipse.epsilon.flock.execution.exceptions.FlockUnsupportedModelException;

public class FlockLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	@Override
	public IEolExecutableModule createModule() {
		return new FlockModule();
	}
	
	@Override
	protected EolDebugger createDebugger() {
		return new FlockDebugger();
	}
	
	@Override
	protected void preExecute(IEolExecutableModule module) throws CoreException, EolRuntimeException {
		super.preExecute(module);
		IFlockContext context = (IFlockContext) module.getContext();
		try {
			context.setOriginalModel(configuration.getAttribute(FlockLaunchConfigurationAttributes.ORIGINAL_MODEL, -1));
			context.setMigratedModel(configuration.getAttribute(FlockLaunchConfigurationAttributes.MIGRATED_MODEL, -1));
		}
		catch (FlockUnsupportedModelException ex) {
			throw new EolInternalException(ex);
		}
	}
	
	/*
	private ILaunchConfiguration launchConfig;
	private ILaunch launch;
	private IProgressMonitor monitor;

	private IFlockContext context;
	private IFlockModule module; 
	
	
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor) throws CoreException {
		this.launchConfig = configuration;
		this.launch       = launch;
		this.monitor      = progressMonitor;
		
		try {
			EpsilonConsole.getInstance().clear();
			
			loadModels();

			if (parseSource()) {
				executeMigration();
				
			} else {
				printParseProblems();
			}
		
		} catch (FlockUnsupportedModelException e) {
			printErrorMessage(e.getLocalizedMessage());
			
		} catch (FlockRuntimeException e) {
			reportRuntimeException(e);
			
		} catch (Exception e) {
			reportException(e);
		
		} finally {
			teardownContext(progressMonitor);
		}
	}

	private void teardownContext(IProgressMonitor progressMonitor) {
		EclipseContextManager.teardown(context, progressMonitor);
	}
	
	private void reportRuntimeException(FlockRuntimeException ex) {
		ex.printStackTrace();
		
		if (ex.getCause() == null)
			printErrorMessage(ex.toString());
		else
			printErrorMessage(ex.getCause().toString());
		
		monitor.setCanceled(true);
	}

	private void reportException(Exception ex) {
		printErrorMessage(ex.getLocalizedMessage());
		
		for (StackTraceElement element : ex.getStackTrace()) {
			printErrorMessage("\t" + element);
		}
		
		monitor.setCanceled(true);
	}

	private void printParseProblems() {
		for (ParseProblem problem : module.getParseProblems()) {
			printErrorMessage(problem.toString());
		}
	}
	
	private void printErrorMessage(String message) {
		EpsilonConsole.getInstance().getErrorStream().println(message);
	}

	private void loadModels() throws EolRuntimeException, CoreException, FlockUnsupportedModelException {
		startingTask("Loading models");
		
		context = new FlockContext();
		
		EclipseContextManager.setup(context, launchConfig, monitor, launch);
		
		context.setOriginalModel(launchConfig.getAttribute(FlockLaunchConfigurationAttributes.ORIGINAL_MODEL, -1));
		context.setMigratedModel(launchConfig.getAttribute(FlockLaunchConfigurationAttributes.MIGRATED_MODEL, -1));
		
		finishedCurrentTask();
	}
	
	private boolean parseSource() throws Exception {
		module = new FlockModule();
		
		// CHANGED: The following works even if the Flock file is not in the workspace
		//final String fileName = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString() + launchConfig.getAttribute(EolLaunchConfigurationAttributes.SOURCE, "");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(launchConfig.getAttribute(EolLaunchConfigurationAttributes.SOURCE, "")));
		String fileName = file.getRawLocation().toOSString();
		
		startingTask("Parsing " + fileName);
		
		boolean parsed = module.parse(new File(fileName)) && module.getParseProblems().isEmpty();
				
		finishedCurrentTask();
		
		return parsed;
	}
	
	private void executeMigration() throws FlockRuntimeException {
		startingTask("Migrating");

		final FlockResult result = module.execute(context);
		
		result.printWarnings(EpsilonConsole.getInstance().getWarningStream());

		finishedCurrentTask();
	}
	
	
	private void startingTask(String task) {
		monitor.subTask(task);
		monitor.beginTask(task, 100);
	}
	
	private void finishedCurrentTask() {
		monitor.done();
	}
	
	//FIXME: Doesn't work with EpsilonLaunchConfigurationDelegate
	@Override
	public IEolExecutableModule createModule() {
		return null;
	}
	*/
}

