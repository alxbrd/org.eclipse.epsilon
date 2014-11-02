/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn;

import java.io.File;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.eol.IEolLibraryModule;
import org.eclipse.epsilon.hutn.exceptions.HutnGenerationException;
import org.eclipse.epsilon.hutn.model.hutn.Spec;

public interface IHutnModule extends IModule {
	
	/**
	 * @return an in-memory EMF model for the HUTN source passed to
	 * parse.
	 * @throws HutnGenerationException
	 */
	public AbstractEmfModel generateEmfModel() throws HutnGenerationException;

	/**
	 * <p>Generates an EMF model for the HUTN source passed to parse.</p>
	 * 
	 * <p>The model is stored in the given baseDirectory, with filename 
	 * defaultModelPath. The HUTN source may contain a modelFile attribute 
	 * (in a model package, in the Spec preamble). When a modelFile 
	 * attribute is specified in the HUTN source, that value takes 
	 * precedence over defaultModelPath.</p>
	 * 
	 * <p>When a metamodel is inferred (because no metamodel is specfied in
	 * the Spec preamble), it is generated in the given baseDirectory,
	 * with filename inferredMetamodelPath.</p>
	 * 
	 * @param baseDirectory
	 * @param defaultModelPath
	 * @param inferredMetamodelPath
	 * 
	 * @return the list of Files generated by this method invocation.
	 * 
	 * @throws HutnGenerationException
	 */
	public List<File> storeEmfModel(File baseDirectory, String defaultModelPath, String inferredMetamodelPath) throws HutnGenerationException;
	
	public boolean hasValidMetaModel();
	
	public List<String> getNsUris();
	
	public String getModelFile();
	
	public void setConfigFileDirectory(File configFileDirectory);
	
	public Spec getIntermediateModel();
	
	public void storeIntermediateModel(File destination);
	
	public void storeIntermediateModelTransformation(File destination) throws HutnGenerationException;
	
	public void storeIntermediateModelTransformationForAllInputModels(File destination) throws HutnGenerationException;

	/**
	 * Returns false only if the HUTN source passed to parse is not valid HUTN.
	 * True is returned when the HUTN is valid, regardless of whether it conforms
	 * to the metamodel specified in the preamble.
	 */
	public boolean hasValidHutn();
}
