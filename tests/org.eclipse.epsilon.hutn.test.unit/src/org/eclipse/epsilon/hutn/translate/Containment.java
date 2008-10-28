/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: Containment.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class Containment extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	private static Node createPerson(String name) {
		final Node person = createClass("Person", name);
		person.getChildren().add(createAttribute("name", name));
		return person;
	}
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node member1Node = createPerson("John");
		final Node member2Node = createPerson("Gill");
		
		final Node familyNode = createClass("Family", "The Smiths");
		familyNode.getChildren().add(createContainment("members", member1Node, member2Node));
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(familyNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("family",  "package.classObjects.selectOne(c : ClassObject | c.type = 'Family')");
		
		model.setVariable("slot", "family.slots.first()");
	}
	
	@Test
	public void packageShouldContainOneClassObject() {
		model.assertEquals(1, "package.classObjects.size()");
	}
	
	@Test
	public void slotShouldBeCorrectType() {
		model.assertTrue("ContainmentSlot.isType(slot)");
	}
	
	@Test
	public void slotShouldHaveCorrectFeatureName() {
		model.assertEquals("members", "slot.feature");
	}
	
	@Test
	public void slotShouldContainTwoObjects() {
		model.assertEquals(2, "slot.objects.size()");
	}
	
	@Test
	public void slotShouldContainFirstObject() {
		model.assertEquals("John", "slot.objects.at(0).identifier");
	}
	
	@Test
	public void slotShouldContainSecondObject() {
		model.assertEquals("Gill", "slot.objects.at(1).identifier");
	}
}
