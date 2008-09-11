package org.eclipse.epsilon.eugenia;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.tooldef.GMFToolPackage;

public class GenerateToolGraphMapDelegate extends EolTransformationActionDelegate {

	public GenerateToolGraphMapDelegate() {
		super();
	}

	@Override
	public String getEolPath() {
		return "transformations/ECore2GMF.eol";
	}

	@Override
	public List<EmfModel> getModels() throws Exception {
		
		String ecorePath = getSelectedFile().getFullPath().toOSString();
		String gmfGraphPath = getSelectedFile().getFullPath().toOSString().replaceAll("ecore", "gmfgraph");
		String gmfToolPath = getSelectedFile().getFullPath().toOSString().replaceAll("ecore", "gmftool");
		String gmfMapPath = getSelectedFile().getFullPath().toOSString().replaceAll("ecore", "gmfmap");
		
		List<EmfModel> models = new ArrayList<EmfModel>();
		models.add(loadModel("ECore", ecorePath, EcorePackage.eNS_URI, true, true, true));
		models.add(loadModel("GmfMap", gmfMapPath, GMFMapPackage.eNS_URI, false, true, true));
		models.add(loadModel("GmfGraph", gmfGraphPath, GMFGraphPackage.eNS_URI, false, true, true));
		models.add(loadModel("GmfTool", gmfToolPath, GMFToolPackage.eNS_URI, false, true, true));
		
		return models;
	}

}
