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
package org.eclipse.epsilon.hutn.xmi.test.integration.consistent.slots.singleValued;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class ReferenceUsingFragment extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" pets=\"#//@contents.1\"/>" +
		                               "<contents xsi:type=\"families:Pet\"/>");
	}
	
	@Test
	public void testSlot() {
		slotTest(getFirstSlotOfFamily(), ReferenceSlot.class, "pets", "Pet");
	}
}
