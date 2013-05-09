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
package org.eclipse.epsilon.flock.model.domain.common;

import java.util.Collection;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.execution.GuardedConstructContext;
import org.eclipse.epsilon.flock.execution.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.checker.TypedConstructChecker;

public abstract class TypedAndGuardedConstruct extends GuardedConstruct {

	private final String originalType;
	
	public TypedAndGuardedConstruct(AST ast, Collection<String> annotations, AST guard, String originalType) {
		super(ast, annotations, guard);
		
		if (originalType == null)
			throw new IllegalArgumentException("originalType cannot be null");
		
		this.originalType = originalType;
	}
	
	public String getOriginalType() {
		return originalType;
	}
	
	protected boolean isStrict() {
		return isAnnotatedWith("strict");
	}
	
	public boolean appliesIn(GuardedConstructContext context) throws FlockRuntimeException {
		return context.originalConformsTo(originalType, isStrict()) && 
			   super.appliesIn(context);
	}
	
	public void check(MigrationStrategyCheckingContext context) {
		new TypedConstructChecker(originalType, context).check();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TypedAndGuardedConstruct))
			return false;
		
		final TypedAndGuardedConstruct other = (TypedAndGuardedConstruct)object;
		
		return super.equals(other) &&
			   originalType.equals(other.originalType);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + originalType.hashCode();
	}
}
