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
package org.eclipse.epsilon.flock.context;

import org.eclipse.epsilon.flock.FlockExecution;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.execution.exceptions.ConservativeCopyException;

public class ConservativeCopyContext {
	
	private final Model originalModel;
	private final Model migratedModel;
	private final FlockExecution execution;
		
	public ConservativeCopyContext(Model originalModel, Model migratedModel, FlockExecution execution) {
		this.originalModel = originalModel;
		this.migratedModel = migratedModel;
		this.execution = execution;
	}

	public ModelElement getEquivalent(ModelElement originalModelElement) {
		return execution.getEquivalent(originalModelElement);
	}
	
	public Object getEquivalent(Object unwrappedModelElement) throws ConservativeCopyException {
		return originalModel.getUnwrappedEquivalent(unwrappedModelElement, migratedModel, this);
	}
	
	public void addWarning(String warning) {
		execution.addWarning(warning);
	}
}