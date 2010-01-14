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
package org.eclipse.epsilon.flock.emc.wrappers;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

class ModelType {

	private final Model model;
	private final String name;
	
	ModelType(Model model, String name) {
		this.model = model;
		this.name  = name;
	}
	
	String getName() {
		return name;
	}
	
	Collection<String> getProperties() throws EolModelElementTypeNotFoundException {
		return model.getPropertiesOf(name);
	}
		
	Collection<String> getPropertiesSharedWith(ModelType other) throws EolModelElementTypeNotFoundException {
		final Collection<String> sharedProperties = new LinkedList<String>(getProperties());
		
		sharedProperties.retainAll(other.getProperties());
		
		return sharedProperties;
	}
}
