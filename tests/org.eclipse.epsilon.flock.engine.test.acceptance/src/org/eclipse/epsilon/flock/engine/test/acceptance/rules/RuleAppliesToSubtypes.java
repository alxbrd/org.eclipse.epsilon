/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.rules;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class RuleAppliesToSubtypes extends FlockAcceptanceTest {
	
	private static final String strategy = "migrate NamedElement {" +
	                                       "	migrated.name := original.name + ' Smith';" +
	                                       "}";
	
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
		
		migrated.setVariable("john", "NamedElement.all.selectOne(e|e.name.startsWith('John'))");
		migrated.setVariable("fido", "NamedElement.all.selectOne(e|e.name.startsWith('Fido'))");
	}
	
	@Test
	public void ruleShouldHaveBeenAppliedToDirectSubtype() {
		migrated.assertEquals("John Smith", "john.name");
	}
	
	@Test
	public void ruleShouldHaveBeenAppliedToIndirectSubtype() {
		migrated.assertEquals("Fido Smith", "fido.name");
	}
}
