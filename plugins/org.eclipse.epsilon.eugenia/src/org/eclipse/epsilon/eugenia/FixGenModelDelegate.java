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
package org.eclipse.epsilon.eugenia;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emc.emf.EmfModel;

public class FixGenModelDelegate extends EolTransformationActionDelegate {
	
	@Override
	public String getBuiltinTransformation() {
		return "transformations/FixGenModel.eol";
	}

	@Override
	public String getCustomizationTransformation() {
		return "FixGenModel.eol";
	}
	
	@Override
	public List<EmfModel> getModels() throws Exception {
		
		String genModelPath = getSelectedFile().getFullPath().toOSString();
		String ecorePath = getSelectedFile().getFullPath().toOSString().replaceAll("genmodel", "ecore");
		
		List<EmfModel> models = new ArrayList<EmfModel>();
		models.add(loadModel("ECore", ecorePath, EcorePackage.eNS_URI, true, false, true));
		models.add(loadModel("GenModel", genModelPath, GenModelPackage.eNS_URI, true, true, false));
		return models;
	}

	@Override
	public String getTitle() {
		return "Synchronizing .genmodel model";
	}
}
 
