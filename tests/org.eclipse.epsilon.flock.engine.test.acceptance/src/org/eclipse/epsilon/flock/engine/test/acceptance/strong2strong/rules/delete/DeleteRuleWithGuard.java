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
package org.eclipse.epsilon.flock.engine.test.acceptance.strong2strong.rules.delete;

import org.eclipse.epsilon.flock.engine.test.acceptance.strong2strong.Strong2StrongMigrationAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class DeleteRuleWithGuard extends Strong2StrongMigrationAcceptanceTest {

	private static final String strategy = "delete Person when: original.name = 'John'";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "	Person {"            +
	                                            "		name: \"Jane\""  +
	                                            "	}"                   +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void migratedShouldContainOnePerson() {
		migrated.assertEquals(1, "Person.all.size()");
	}
	
	@Test
	public void migratedShouldContainPersonCalledJane() {
		migrated.assertEquals("Jane", "Person.all.first.name");
	}
}
