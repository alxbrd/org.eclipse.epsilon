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
 * $Id: ReferenceNodeImpl.java,v 1.1 2008/08/14 13:04:22 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.impl.NodeImpl;

import org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.ReferenceNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ReferenceNodeImpl extends NodeImpl implements ReferenceNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenceNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnAntlrAstPackage.Literals.REFERENCE_NODE;
	}

} //ReferenceNodeImpl
