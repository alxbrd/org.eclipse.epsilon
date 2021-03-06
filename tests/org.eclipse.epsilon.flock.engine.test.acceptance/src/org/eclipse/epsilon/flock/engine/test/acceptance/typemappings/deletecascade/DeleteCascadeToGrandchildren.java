/*******************************************************************************
 * Copyright (c) 2013 The University of York.
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
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletecascade;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class DeleteCascadeToGrandchildren extends FlockAcceptanceTest {

	private static final String strategy = "@cascade" + "\n" +
	                                       "delete Model";
	
	private static final String originalModel = "Families {"                      +
	                                            "	Model {"                      +
	                                            "		contents: Family {"       +
	                                            "			name: \"The Smiths\"" +
	                                            "			members: Person { "   +
	                                            "				name: \"John\""   +
	                                            "			}"                    +
	                                            "		}"                        +
	                                            "	}"                            +
	                                            "	Person {"                     +
	                                            "		name: \"Jane\""           +
	                                            "	}"                            +
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
	public void migratedPersonShouldBeJaneNotJohn() {
		migrated.assertEquals("Jane", "Person.all.first.name");
	}
}
