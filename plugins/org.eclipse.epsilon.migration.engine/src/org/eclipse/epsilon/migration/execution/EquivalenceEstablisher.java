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
package org.eclipse.epsilon.migration.execution;

import org.eclipse.epsilon.migration.IMigrationContext;
import org.eclipse.epsilon.migration.emc.wrappers.ModelElement;
import org.eclipse.epsilon.migration.execution.exceptions.MigrationExecutionException;
import org.eclipse.epsilon.migration.model.MigrationRule;
import org.eclipse.epsilon.migration.model.MigrationStrategy;

public class EquivalenceEstablisher {

	private final MigrationStrategy strategy;
	private final IMigrationContext context;
	
	public EquivalenceEstablisher(MigrationStrategy strategy, IMigrationContext context) {
		this.strategy = strategy;
		this.context  = context;
	}
	
	public Equivalences establishEquivalences() throws MigrationExecutionException {
		final Equivalences equivalences = new Equivalences();
		
		for (ModelElement original : context.getOriginalModelElements()) {
			equivalences.add(createEquivalenceFor(original));
		}
		
		return equivalences;
	}
	
	Equivalence createEquivalenceFor(ModelElement original) throws MigrationExecutionException {
		final MigrationRule rule = strategy.ruleFor(original, context);
				
		if (rule == null) {			
			return new TypeBasedEquivalence(context, original);
		
		} else {
			return new RuleBasedEquivalence(context, original, rule);
		}
	}
}