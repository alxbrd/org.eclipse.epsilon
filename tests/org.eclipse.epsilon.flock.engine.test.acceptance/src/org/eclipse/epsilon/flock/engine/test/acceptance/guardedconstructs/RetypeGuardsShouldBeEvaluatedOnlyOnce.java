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
package org.eclipse.epsilon.flock.engine.test.acceptance.guardedconstructs;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class RetypeGuardsShouldBeEvaluatedOnlyOnce extends FlockAcceptanceTest {

	private static final String strategy = "retype Person to Family when: guardWithSideEffect() " +
	                                       "" +
	                                       "operation guardWithSideEffect() {" +
	                                       "  new Migrated!Dog;" +
	                                       "  return true;" +
	                                       "}";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void onlyOneDogShouldHaveBeenCreatedByTheGuard() {
		migrated.assertEquals(1, "Dog.all.size");
	}
}
