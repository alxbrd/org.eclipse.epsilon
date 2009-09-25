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
package org.eclipse.epsilon.flock.model;

import org.eclipse.epsilon.commons.module.ModuleElement;
import org.eclipse.epsilon.flock.IFlockContext;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.execution.EquivalenceCreator;
import org.eclipse.epsilon.flock.execution.exceptions.FlockRuntimeException;

public interface Rule extends ModuleElement, EquivalenceCreator {

	public boolean appliesFor(ModelElement original, IFlockContext context) throws FlockRuntimeException;

	public String getOriginalType();
}
