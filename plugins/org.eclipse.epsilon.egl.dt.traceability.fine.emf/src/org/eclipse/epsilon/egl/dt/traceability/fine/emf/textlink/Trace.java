/**
 * Copyright (c) 2011 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Louis Rose - initial API and implementation
 */
package org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Trace#getTraceLinks <em>Trace Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextlinkPackage#getTrace()
 * @model
 * @generated
 */
public interface Trace extends EObject {
	/**
	 * Returns the value of the '<em><b>Trace Links</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace Links</em>' containment reference list.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextlinkPackage#getTrace_TraceLinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<TraceLink> getTraceLinks();

} // Trace
