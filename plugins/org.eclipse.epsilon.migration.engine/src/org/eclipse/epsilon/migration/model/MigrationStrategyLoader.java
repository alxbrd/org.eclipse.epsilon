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
package org.eclipse.epsilon.migration.model;

import org.eclipse.epsilon.commons.parse.AST;
import org.eclipse.epsilon.migration.parse.MigrationParser;

/**
 * Walks the AST, constructing an equivalent domain model
 * containing MigrationRule objects.
 * 
 * @author lrose
 */
public class MigrationStrategyLoader {

	private AST ast;
	
	public MigrationStrategyLoader(AST ast) {
		this.ast = ast;
	}
	
	public MigrationStrategy run() {
		final MigrationStrategy strategy = new MigrationStrategy();
		
		if (ast != null) {
			strategy.addRule(new MigrationRule(getSourceType(), getTargetType(), getGuard(), getBody()));
		}
		
		return strategy;
	}

	private String getSourceType() {
		return getFirstChild().getText();
	}

	private String getTargetType() {
		return hasToPart() ? getSecondChild().getText() : getSourceType();
	}
	
	private AST getGuard() {
		return hasGuard() ? getPenultimateChild() : null;
	}

	private AST getBody() {
		return getLastChild();
	}
	
	private boolean hasToPart() {
		return getSecondChild().getType() == MigrationParser.NAME;
	}
		
	private boolean hasGuard() {
		return getPenultimateChild().getType() != MigrationParser.NAME;
	}
	
	
	private AST getFirstChild() {
		return ast.getChild(0);
	}
	
	private AST getSecondChild() {
		return ast.getChild(1);
	}
	
	private AST getPenultimateChild() {
		return ast.getChild(ast.getChildCount() - 2);
	}
	
	private AST getLastChild() {
		return ast.getChild(ast.getChildCount() - 1);
	}
}
