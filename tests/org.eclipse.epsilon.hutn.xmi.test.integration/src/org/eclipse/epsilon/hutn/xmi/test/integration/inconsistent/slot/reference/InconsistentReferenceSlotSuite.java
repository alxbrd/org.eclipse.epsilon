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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.slot.reference;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({NonExistentReferenceValue.class, NonExistentUriFragmentReferenceValue.class,
               ReferenceValueInUnknownSlot.class, ReferenceValueUsingFragmentInUnknownSlot.class,
               SeveralReferenceValuesInUnknownSlot.class, SeveralReferenceValuesUsingUriFragmentInUnknownSlot.class})
public class InconsistentReferenceSlotSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(InconsistentReferenceSlotSuite.class);
	}
}
