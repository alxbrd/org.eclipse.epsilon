/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.slot.containment.feature;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingleTyped extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_Ev2KMBhbEd6T2uYUGKXrWQ\">" +
		                    "	<people xmi:id=\"_VyN2UBhsEd6T2uYUGKXrWQ\"/>" +
		                    "	<people xmi:id=\"_cMvCsBhsEd6T2uYUGKXrWQ\"/>" +
		                    "</contents>");
	}
	
	@Test
	public void peopleSlot() {
		slotTest(getFirstSlotOfFamily(), ContainmentSlot.class, "people", "UnknownType", "UnknownType");
	}
}
