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
package org.eclipse.epsilon.eol.models.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.BeforeClass;
import org.junit.Test;
public class JavaObjectModelTests {

	private static JavaObjectModel javaObjectModel = new JavaObjectModel("java.lang");
	
	private static Object string  = "foo";
	private static Object integer = 42;
	
	
	@BeforeClass
	public static void setup() {
		javaObjectModel.allContents().add(string);
		javaObjectModel.allContents().add(integer);
	}
	
	@Test
	public void contentsReturnsBothModelElements() {
		assertTrue(javaObjectModel.contents().contains(string));
		assertTrue(javaObjectModel.contents().contains(integer));
	}
	
	@Test
	public void getTypeNameOf() {
		assertEquals("java.lang.String",                      javaObjectModel.getTypeNameOf(string));
		assertEquals("org.eclipse.emf.ecore.impl.EClassImpl", javaObjectModel.getTypeNameOf(EcoreFactory.eINSTANCE.createEClass()));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void getPropertiesOf() {
		javaObjectModel.getPropertiesOf(string);
	}
}
