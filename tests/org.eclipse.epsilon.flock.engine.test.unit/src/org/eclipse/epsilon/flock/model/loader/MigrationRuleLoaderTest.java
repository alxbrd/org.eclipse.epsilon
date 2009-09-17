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
package org.eclipse.epsilon.flock.model.loader;

import static org.eclipse.epsilon.flock.model.loader.LoaderTestHelper.createBody;
import static org.eclipse.epsilon.flock.model.loader.LoaderTestHelper.createGuard;
import static org.eclipse.epsilon.flock.model.loader.LoaderTestHelper.createMigrationRuleAst;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.commons.parse.AST;
import org.eclipse.epsilon.flock.model.MigrationRule;
import org.eclipse.epsilon.flock.model.loader.MigrationRuleLoader;
import org.junit.Test;

public class MigrationRuleLoaderTest {

	@Test
	public void basicMigrationRule() {
		final AST body    = createBody();
		final AST ruleAst = createMigrationRuleAst("Person", null, null, body);
		
		final MigrationRule rule = runMigrationRuleLoaderOn(ruleAst);
		
		assertEquals(new MigrationRule("Person", "Person", null, body), rule);
	}
	
	@Test
	public void migrationRuleWithToPart() {
		final AST body    = createBody();
		final AST ruleAst = createMigrationRuleAst("Animal", "Dog", null, body);
		
		final MigrationRule rule = runMigrationRuleLoaderOn(ruleAst);
		
		assertEquals(new MigrationRule("Animal", "Dog", null, body), rule);
	}
	
	@Test
	public void migrationRuleWithGuard() {
		final AST body    = createBody();
		final AST guard   = createGuard();
		final AST ruleAst = createMigrationRuleAst("Person", null, guard, body);
		
		final MigrationRule rule = runMigrationRuleLoaderOn(ruleAst);
		
		assertEquals(new MigrationRule("Person", "Person", guard, body), rule);
	}
	
	@Test
	public void migrationRuleWithToPartAndGuard() {
		final AST body    = createBody();
		final AST guard   = createGuard();
		final AST ruleAst = createMigrationRuleAst("Animal", "Dog", guard, body);
		
		final MigrationRule rule = runMigrationRuleLoaderOn(ruleAst);
		
		assertEquals(new MigrationRule("Animal", "Dog", guard, body), rule);
	}
	
	private static MigrationRule runMigrationRuleLoaderOn(AST rule) {
		return new MigrationRuleLoader(rule).run();
	}
}