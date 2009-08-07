/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.migration.dt.launching;

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.commons.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.dt.launching.EclipseContextManager;
import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.migration.IMigrationContext;
import org.eclipse.epsilon.migration.IMigrationModule;
import org.eclipse.epsilon.migration.MigrationContext;
import org.eclipse.epsilon.migration.MigrationModule;
import org.eclipse.epsilon.migration.execution.exceptions.MigrationExecutionException;

public class MigrationLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	private ILaunchConfiguration launchConfig;
	private ILaunch launch;
	private IProgressMonitor monitor;

	private IMigrationContext context;
	private IMigrationModule module; 
	
	
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
		
		} catch (MigrationExecutionException e) {
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
	
	private void reportRuntimeException(MigrationExecutionException ex) {
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

	private void loadModels() throws EolRuntimeException, CoreException {
		startingTask("Loading models");
		
		context = new MigrationContext();
		
		EclipseContextManager.setup(context, launchConfig, monitor, launch);
		
		context.setOriginalModel(launchConfig.getAttribute(MigrationLaunchConfigurationAttributes.ORIGINAL_MODEL, -1));
		context.setMigratedModel(launchConfig.getAttribute(MigrationLaunchConfigurationAttributes.MIGRATED_MODEL, -1));
		
		finishedCurrentTask();
	}
	
	private boolean parseSource() throws Exception {
		module = new MigrationModule();
		
		final String fileName = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString() + launchConfig.getAttribute(EolLaunchConfigurationAttributes.SOURCE, "");
		
		startingTask("Parsing " + fileName);
		
		boolean parsed = module.parse(new File(fileName)) && module.getParseProblems().isEmpty();
				
		finishedCurrentTask();
		
		return parsed;
	}
	
	private void executeMigration() throws MigrationExecutionException {
		startingTask("Migrating");

		module.execute(context);

		finishedCurrentTask();
	}
	
	
	private void startingTask(String task) {
		monitor.subTask(task);
		monitor.beginTask(task, 100);
	}
	
	private void finishedCurrentTask() {
		monitor.done();
	}
}

