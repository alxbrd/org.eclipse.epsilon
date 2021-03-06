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

package org.eclipse.epsilon.dt.epackageregistryexplorer;

import org.eclipse.emf.ecore.EClass;

public class SuperTypesDescriptor {
	
	protected EClass eClass;
	
	public SuperTypesDescriptor(EClass eClass) {
		this.eClass = eClass;
	}
	
	public EClass getEClass() {
		return eClass;
	}
	
}
 