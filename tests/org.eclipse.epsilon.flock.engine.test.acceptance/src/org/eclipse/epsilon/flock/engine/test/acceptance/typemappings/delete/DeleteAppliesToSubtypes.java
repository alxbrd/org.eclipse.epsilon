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
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class DeleteAppliesToSubtypes extends FlockAcceptanceTest {

	private static final String strategy = "delete NamedElement";
	
	private static final String originalModel = "Families {"                   +
	                                            "	Person {"                  +
	                                            "		name: \"John\""        +
	                                            "	}"                         +
	                                            "	Dog {"                     +
	                                            "		name: \"Fido\""        +
	                                            "	}"                         +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void migratedShouldContainNoPeople() {
		migrated.assertEquals(0, "Person.all.size()");
	}
	
	@Test
	public void migratedShouldContainNoDogs() {
		migrated.assertEquals(0, "Dog.all.size()");
	}
}
