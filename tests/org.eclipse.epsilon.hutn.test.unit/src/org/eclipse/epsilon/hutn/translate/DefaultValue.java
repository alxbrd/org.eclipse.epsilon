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
 * $Id: DefaultValue.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultValue extends HutnTranslatorTest {
		
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(createClass("Family", "id-001"));
		
		final Node configFileAttribute = createAttribute("configFile", CONFIG_FILE);
		
		final Node metaModelClass = createClass("Metamodel", "FamiliesMetaModel");
		metaModelClass.getChildren().add(configFileAttribute);
		
		final Node specPackage = createPackage("@Spec");
		specPackage.getChildren().add(metaModelClass);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(specPackage);
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("class",   "package.classObjects.first()");
		model.setVariable("slot",    "class.slots.select(s|s.feature <> 'id').first()");
	}
	
	@Test
	public void slotShouldHaveCorrectFeatureName() {
		model.assertEquals("numberOfChildren", "slot.feature");
	}
	
	@Test
	public void slotShouldHaveSingleValue() {
		model.assertEquals(1, "slot.values.size()");
	}
	
	@Test
	public void slotShouldHaveCorrectDefaultValue() {
		model.assertEquals(2, "slot.values.first()");
	}
}
