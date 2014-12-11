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
package org.eclipse.epsilon.flock.model.domain.typemappings;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.FlockExecution;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext.EquivalentFactory;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.equivalences.NoEquivalence;
import org.eclipse.epsilon.flock.equivalences.TypeBasedEquivalence;
import org.eclipse.epsilon.flock.execution.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.common.PackageTypedConstruct;

public class PackageRetyping extends PackageTypedConstruct implements TypeMappingConstruct {

	private String evolvedPackage;
	
	@Override
	public void build() {
		super.build();

		evolvedPackage = getSecondChild().getText();
		if (evolvedPackage == null)
			throw new IllegalArgumentException("evolvedPackage cannot be null");
	}

	public String getEvolvedPackage() {
		return evolvedPackage;
	}

	public Equivalence createEquivalence(IEolContext context, FlockExecution execution, ModelElement original, EquivalentFactory factory) throws FlockRuntimeException {
		final String equivalentType = evolvedPackage + "::" + original.getUnqualifiedTypeName();

		if (factory.typeConformsToEvolvedMetamodel(equivalentType)) {
			final ModelElement equivalent = factory.createModelElementInMigratedModel(equivalentType);
			return new TypeBasedEquivalence(context, execution, original, equivalent);
		
		} else {
			return new NoEquivalence(context, execution, original);
		}
	}
	
	@Override
	public String toString() {
		return "retype package " + getOriginalPackage() + " to " + evolvedPackage + " when " + getGuard();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PackageRetyping))
			return false;
		
		final PackageRetyping other = (PackageRetyping)object;
		
		return super.equals(object) &&
		       this.evolvedPackage.equals(other.evolvedPackage);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + evolvedPackage.hashCode();
	}
}
