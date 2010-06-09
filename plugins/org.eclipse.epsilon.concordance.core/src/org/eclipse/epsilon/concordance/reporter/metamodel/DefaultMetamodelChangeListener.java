/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.concordance.reporter.metamodel;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.concordance.dt.ConcordancePlugin;
import org.eclipse.epsilon.concordance.index.ConcordanceIndex;

public abstract class DefaultMetamodelChangeListener implements MetamodelChangeListener {

	protected final ConcordanceIndex index;
	
	public DefaultMetamodelChangeListener() {
		this(ConcordancePlugin.getDefault().getIndex());
	}
	
	public DefaultMetamodelChangeListener(ConcordanceIndex index) {
		this.index = index;
	}
	
	public void ePackageAdded(EPackage ePackage) {}

	public void ePackageChanged(EPackage oldEPackage, EPackage newEPackage) {}

	public void ePackageRemoved(EPackage ePackage) {}
}
