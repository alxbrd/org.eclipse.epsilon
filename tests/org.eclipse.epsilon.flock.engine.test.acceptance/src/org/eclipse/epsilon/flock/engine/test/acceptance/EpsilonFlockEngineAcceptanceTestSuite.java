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
package org.eclipse.epsilon.flock.engine.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.flock.engine.test.acceptance.builtins.FlockCanAccessBuiltinsTests;
import org.eclipse.epsilon.flock.engine.test.acceptance.preandpost.PreAndPostBlocks;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({FlockCanAccessBuiltinsTests.class,
               GuardedConstructSuite.class,
               TypeMappingsSuite.class,
               RulesSuite.class,
               MigrationLogicSuite.class,
               CopyingSuite.class,
               IgnoringSuite.class,
               EquivalencesSuite.class,
               WarningsSuite.class,
               PreAndPostBlocks.class})
public class EpsilonFlockEngineAcceptanceTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EpsilonFlockEngineAcceptanceTestSuite.class);
	}
}
