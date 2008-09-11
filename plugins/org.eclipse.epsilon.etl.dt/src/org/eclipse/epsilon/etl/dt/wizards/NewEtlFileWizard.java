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
package org.eclipse.epsilon.etl.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard;

public class NewEtlFileWizard extends AbstractNewFileWizard {

	@Override
	public String getTitle() {
		return "New ETL Transformation";
	}

	@Override
	public String getExtension() {
		return "etl";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new ETL transformation file with *.etl extension";
	}
	
	/*
	public ImageDescriptor getImageDescriptor() {
		return EtlPlugin.getImageDescriptor("icons/etl.gif");
	}
	*/
}
