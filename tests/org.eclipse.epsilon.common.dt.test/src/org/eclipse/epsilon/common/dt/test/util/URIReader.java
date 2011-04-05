/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

public class URIReader {

	private final URI uri;
	
	public URIReader(URI uri) {
		this.uri = uri;
	}

	public String getContents() throws IOException {
		final BufferedReader reader   = new BufferedReader(new InputStreamReader(URIConverter.INSTANCE.createInputStream(uri)));
		final StringBuilder  contents = new StringBuilder();
		
		String line;
		while ((line = reader.readLine()) != null) {
			contents.append(line);
			contents.append('\n');
		}
		
		return contents.toString();
	}
}
