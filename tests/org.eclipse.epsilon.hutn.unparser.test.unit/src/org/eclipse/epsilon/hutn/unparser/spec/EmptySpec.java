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
package org.eclipse.epsilon.hutn.unparser.spec;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createSpec;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.unparser.internal.AbstractSpecUnparserTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmptySpec extends AbstractSpecUnparserTest {

	@BeforeClass
	public static void setup() {
		specUnparserTest(createSpec());
	}
	
	@Test
	public void hasNoPackageObjects() {
		assertEquals(0, numberOfPackageObjects());
	}
}
