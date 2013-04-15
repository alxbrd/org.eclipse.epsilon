/*******************************************************************************
 * Copyright (c) 2013 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.concordance.dt;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.concordance.reporter.metamodel.MetamodelChangeReporter;

public class MetamodelChangeReporterScheduler {

	private final MetamodelChangeReporter schedulee;
	private Job job;
	
	public MetamodelChangeReporterScheduler(MetamodelChangeReporter schedulee) {
		this.schedulee = schedulee;
	}
	
	public void schedule() {
		try {
			if (job == null && thereAreAnyProjectsWithTheConcordanceNature()) {
				job = new PeriodicallyUpdateEPackageRegistryMonitorJob();
				job.schedule();
			}
			
		} catch (CoreException e) {
			LogUtil.log("Error encountered whilst scheduling Concordance's metamodel change reporter", e);
		}
	}
	
	private boolean thereAreAnyProjectsWithTheConcordanceNature() throws CoreException {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (ConcordanceNature.hasConcordanceNature(project)) {
				return true;
			}
		}
		
		return false;
	}

	private class PeriodicallyUpdateEPackageRegistryMonitorJob extends Job {
				
		public PeriodicallyUpdateEPackageRegistryMonitorJob() {
			super("Update Concordance EPackage Registry Monitor Job");
			setSystem(true);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			schedule(1000);
			
			schedulee.pollRegistry();
					
			return Status.OK_STATUS;
		}
	}
}
