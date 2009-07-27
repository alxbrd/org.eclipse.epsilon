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
package org.eclipse.epsilon.migration;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.migration.copy.Copier;
import org.eclipse.epsilon.migration.copy.CopyingException;
import org.eclipse.epsilon.migration.copy.Equivalence;
import org.eclipse.epsilon.migration.execution.ExecutionContext;
import org.eclipse.epsilon.migration.model.MigrationStrategy;

public class MigrationContext extends EolContext implements IMigrationContext {

	private final IModel originalModel;
	private final IModel targetModel;
	private final ExecutionContext executionContext;
	
	private final List<Equivalence> equivalences = new LinkedList<Equivalence>();
	
	public MigrationContext() {
		this(null, null);
	}
	
	public MigrationContext(IModel original, IModel target) {
		this.originalModel = original;
		this.targetModel   = target;
		
		executionContext = new ExecutionContext(originalModel, targetModel);
	}
	
	
	public IModel execute(MigrationStrategy strategy) {
		return execute(strategy, new Copier(originalModel, targetModel));
	}
	
	IModel execute(MigrationStrategy strategy, Copier copier) {
		try {
			reset();
			createCopies(copier);
			migrateUsing(strategy);
			
		} catch (CopyingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return targetModel;
	}

	private void reset() {
		equivalences.clear();
	}
	
	private void createCopies(Copier copier) throws CopyingException {
		for (Object original : originalModel.contents()) {
			equivalences.addAll(copier.deepCopy(original));
		}
	}
	
	private void migrateUsing(MigrationStrategy strategy) {
		for (Equivalence equivalence : equivalences) {
			strategy.migrate(equivalence.getOriginal(), equivalence.getCopy(), executionContext);
		}
	}
}
