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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;

public class EvlMarkerResolverManager implements IEvlMarkerResolver {
	
	public static EvlMarkerResolverManager INSTANCE = new EvlMarkerResolverManager();
	
	protected Collection<IEvlMarkerResolver> delegates = new ArrayList<IEvlMarkerResolver>();
	
	private EvlMarkerResolverManager() {
		delegates.add(new EmfMarkerResolver());
		delegates.add(new GmfMarkerResolver());
	}
	
	@Override
	public boolean canResolve(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			//System.err.println(delegate);
			if (delegate.canResolve(marker))
				return true;
		}
		return false;
	}

	@Override
	public EObject resolve(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.resolve(marker);
		}
		return null;
	}

	@Override
	public String getAbsoluteElementId(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getAbsoluteElementId(marker);
		}
		return null;
	}

	@Override
	public String getMessage(IMarker marker) {
		for (IEvlMarkerResolver delegate : delegates) {
			if (delegate.canResolve(marker))
				return delegate.getMessage(marker);
		}
		return null;
	}

	@Override
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
