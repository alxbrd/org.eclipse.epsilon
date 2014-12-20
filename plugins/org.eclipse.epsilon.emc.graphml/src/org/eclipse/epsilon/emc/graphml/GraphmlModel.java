/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.graphml;

import java.io.File;
import java.net.URL;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.muddle.MuddleFactory;
import org.eclipse.epsilon.emc.muddle.MuddleModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class GraphmlModel extends MuddleModel {
	
	public static final String PROPERTY_FILE = "file";
	public static String PROPERTY_URI = "uri";
	protected File file = null;
	protected String uri = null;
	
	public GraphmlModel() {
		
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver)
			throws EolModelLoadingException {
		super.load(properties, resolver);
		
		String filePath = properties.getProperty(GraphmlModel.PROPERTY_FILE);
		
		if (filePath != null && filePath.trim().length() > 0) {
			file = new File(resolver.resolve(filePath));
		}
		else {
			uri = properties.getProperty(GraphmlModel.PROPERTY_URI);
		}
		
		load();
	}
	
	@Override
	public void load() throws EolModelLoadingException {
		if (readOnLoad) {
			GraphmlImporter importer = new GraphmlImporter();
			try {
				if (uri!=null) {
					muddle = importer.importGraph(uri);
				} else {
					muddle = importer.importGraph(file);
				}
			} catch (Exception e) {
				throw new EolModelLoadingException(e, this);
			}
		}
		else {
			muddle = MuddleFactory.eINSTANCE.createMuddle();
		}
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getUri() {
		return uri;
	}
}
