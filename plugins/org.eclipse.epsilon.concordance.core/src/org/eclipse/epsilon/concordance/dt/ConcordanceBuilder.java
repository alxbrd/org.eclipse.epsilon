/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.concordance.dt;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.concordance.model.IConcordanceModel;
import org.eclipse.epsilon.concordance.model.ConcordanceModelFactory;
import org.eclipse.epsilon.concordance.reporter.model.ModelChangeReporter;
import org.eclipse.ui.PlatformUI;

public class ConcordanceBuilder extends IncrementalProjectBuilder {

	static final String ID = "org.eclipse.epsilon.concordance.builder.ConcordanceBuilder";

	private final ModelChangeReporter reporter;
	private final ResourceCategoriser categoriser = new ResourceCategoriser();

	public ConcordanceBuilder() {
		this(ConcordancePlugin.getDefault().getModelChangeReporter());
	}

	public ConcordanceBuilder(ModelChangeReporter reporter) {
		this.reporter = reporter;
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {
		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}

		refreshProject(monitor);

		return null;
	}

	private void fullBuild(final IProgressMonitor monitor) {
		try {
			getProject().accept(new IResourceVisitor() {

				@Override
				public boolean visit(IResource resource) throws CoreException {

					if (categoriser.isModel(resource)) {
						final IConcordanceModel model = ConcordanceModelFactory.createModel(resource);

						// Report model change, so the model listeners can 'build' whatever they need to
						reporter.reportChange(model);
					}

					return true; // visit children too
				}
			});
		} catch (Exception e) {
			logException(e);
		}
	}

	private void incrementalBuild(IResourceDelta delta, final IProgressMonitor monitor) {
		try {
			delta.accept(new IResourceDeltaVisitor() {

				@Override
				public boolean visit(IResourceDelta delta) {
					if (categoriser.isModel(delta.getResource())) {
						final IConcordanceModel model = ConcordanceModelFactory.createModel(delta.getResource());

						if (delta.getKind() == IResourceDelta.ADDED && delta.getMovedFromPath() == null) {
							reporter.reportAddition(model);
						} else if (delta.getKind() == IResourceDelta.CHANGED) {
							reporter.reportChange(model);
						} else if (delta.getKind() == IResourceDelta.REMOVED && delta.getMovedToPath() == null) {
							reporter.reportRemoval(model);
						} else if (delta.getKind() == IResourceDelta.ADDED && delta.getMovedFromPath() != null) {
							reporter.reportMove(ConcordanceModelFactory.createModel(delta.getMovedFromPath()), model);
						}
					}

					return true; // visit children too
				}
			});
		} catch (CoreException e) {
			logException(e);
		}
	}

	private void refreshProject(IProgressMonitor monitor) {
		try {
			getProject().refreshLocal(IProject.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			logException(e);
		}
	}

	private static void logException(Exception e) {
		if (PlatformUI.isWorkbenchRunning()) {
			LogUtil.log("Error encountered while running Concordance builder.", e);
		} else {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	// FIXME : build isn't called when workbench starts up
}
