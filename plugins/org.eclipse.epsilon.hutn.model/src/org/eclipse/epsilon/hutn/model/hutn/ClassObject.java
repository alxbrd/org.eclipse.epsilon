/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: ClassObject.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Object</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getClassObject()
 * @model
 * @generated
 */
public interface ClassObject extends org.eclipse.epsilon.hutn.model.hutn.Object {
	
	/**
	 * <!-- begin-user-doc -->
	 * Returns an EClass from the specified collection that
	 * matches the type of this Object
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	EClass getEClass(Collection<EClass> eClasses);
	
	
	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether every Slot contained in this ClassObject
	 * has the same type as some EStructuralFeature contained in eClass
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	boolean typeCompatibleWith(EClass eClass);
	
} // ClassObject
