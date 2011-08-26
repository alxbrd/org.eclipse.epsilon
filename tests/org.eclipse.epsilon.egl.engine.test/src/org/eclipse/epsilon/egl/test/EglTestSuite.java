/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test;


import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.egl.engine.traceability.fine.EglFineGrainedTraceabilityUnitTestSuite;
import org.eclipse.epsilon.egl.test.acceptance.traceability.EglFineGrainedTraceabilityAcceptanceTestSuite;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestSuite;
import org.eclipse.epsilon.egl.test.unit.UnitTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({UnitTestSuite.class, AcceptanceTestSuite.class,
               EglFineGrainedTraceabilityUnitTestSuite.class,
               EglFineGrainedTraceabilityAcceptanceTestSuite.class})
public class EglTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(EglTestSuite.class);
	}
}
