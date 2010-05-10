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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.commons.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;

public class FixGmfGenDelegate extends EugeniaActionDelegate {
	
	@Override
	public String getBuiltinTransformation() {
		return "transformations/FixGMFGen.eol";
	}

	@Override
	public String getCustomizationTransformation() {
		return "FixGMFGen.eol";
	}
	
	@Override
	public List<EmfModel> getModels() throws Exception {
		
		List<EmfModel> models = new ArrayList<EmfModel>();
		
		models.add(loadModel("ECore", gmfFileSet.getEcorePath(), EcorePackage.eNS_URI, true, false, true));
		models.add(loadModel("GmfGen", gmfFileSet.getGmfGenPath(), GMFGenPackage.eINSTANCE.getNsURI(), true, true, false));
		
		return models;
	}

	@Override
	public List<Variable> getExtraVariables() {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		variables.add(CopyrightProvider.getCopyrightVariable(getSelectedFile()));
		return variables;
	}
	
	@Override
	public String getTitle() {
		return "Synchronizing .gmfgen model";
	}

}
