package org.eclipse.epsilon.evl.emf.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;

public interface IEvlMarkerResolver {
	
	public boolean canResolve(IMarker marker);
	
	public EObject resolve(IMarker marker);
	
	public String getAbsoluteElementId(IMarker marker);
	
	public String getMessage(IMarker marker);
	
	public EditingDomain getEditingDomain(IMarker marker);

	public EditingDomain getEditingDomain(IEditorPart editor);
}
