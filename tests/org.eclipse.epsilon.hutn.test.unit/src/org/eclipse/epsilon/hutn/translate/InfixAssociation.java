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
 * $Id: InfixAssociation.java,v 1.1 2008/08/14 12:37:27 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.hutn.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class InfixAssociation extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node petNode    = createClass("Pet", "Fido");
		final Node familyNode = createClass("Family", "The Smiths");
		
		final Node assocBlock = createInfixAssociation("pets", new Association(new Reference("Family", "The Smiths"),
		                                                                       new Reference("Pet", "Fido")));
		
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(assocBlock);
		packageNode.getChildren().add(petNode);
		packageNode.getChildren().add(familyNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("family",  "package.slots.first().objects.select(o : Object | o.type = 'Family').first()");
		
		model.setVariable("slot", "family.slots.first()");
	}
	
	@Test
	public void packageShouldContainTwoObjects() {
		model.assertEquals(2, "package.slots.first().objects.size()");
	}

	@Test
	public void familyShouldContainOneSlot() {
		model.assertEquals(1, "family.slots.size()");
	}

	@Test
	public void slotShouldBeCorrectType() {
		model.assertTrue("ReferenceSlot.isType(slot)");
	}
	
	@Test
	public void slotShouldHaveCorrectFeatureName() {
		model.assertEquals("pets", "slot.feature");
	}
	
	@Test
	public void slotShouldHaveOneIdentifier() {
		model.assertEquals(1, "slot.identifiers.size()");
	}
	
	@Test
	public void slotShouldContainCorrectIdentifier() {
		model.assertEquals("Fido", "slot.identifiers.first()");
	}
}
