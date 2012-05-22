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
package org.eclipse.epsilon.evl.emf.validation;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;

public class EvlMarkerResolverManager implements IEvlMarkerResolver {
	
	public static EvlMarkerResolverManager INSTANCE = new EvlMarkerResolverManager();
	
	protected Collection<IEvlMarkerResolver> delegates = new ArrayList<IEvlMarkerResolver>();
	
	private EvlMarkerResolverManager() {
		delegates.add(new EmfMarkerResolver());
		try {
			Class.forName("org.eclipse.gmf.runtime.notation.View");
			delegates.add(new GmfMarkerResolver());
		} catch (ClassNotFoundException ex) {
			// GMF is not available - do not add the marker resolver, but log it somewhere (just in case)
			Activator.getDefault().getLog().log(
				new Status(IStatus.WARNING, Activator.PLUGIN_ID,
						"GMF is not available: disabling the GMF marker resolver", ex));
		}
	}
	
	public boolean canResolve(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return true;
		}
		return false;
	}

	public EObject resolve(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.resolve(marker);
		}
		return null;
	}

	public String getAbsoluteElementId(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getAbsoluteElementId(marker);
		}
		return null;
	}

	public String getMessage(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getMessage(marker);
		}
		return null;
	}

	public EditingDomain getEditingDomain(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getEditingDomain(marker);
		}
		return null;
	}

	public EditingDomain getEditingDomain(IEditorPart editor) {
		return null;
	}

	
}
