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
 * $Id: HutnAcceptanceTest.java,v 1.2 2008/08/07 12:44:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.util;

import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.hutn.test.util.HutnTestWithFamiliesMetaModel;
import org.eclipse.epsilon.commons.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.HutnModule;
import org.eclipse.epsilon.hutn.IHutnModule;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.junit.AfterClass;

public abstract class HutnAcceptanceTest extends HutnTestWithFamiliesMetaModel {

	protected static ModelWithEolAssertions model;
	
	protected static ModelWithEolAssertions generateModel(String hutn) throws Exception {
		return generateModel(hutn, null);
	}
	
	protected static ModelWithEolAssertions generateModel(String hutn, String path) throws Exception {
		final IHutnModule module = new HutnModule();
		
		if (!module.parse(hutn)) {
			for (ParseProblem p : module.getParseProblems()) {
				System.err.println(p);
			}
			assertTrue("Could not parse HUTN.", false);
		}
		
		final ModelWithEolAssertions result = new ModelWithEolAssertions(module.generateEmfModel());
		
		if (path != null) {
			result.store(path);
		}
		
		return result;
	}
	
	@AfterClass
	public static void disposeModel() {
		if (model != null) model.dispose();
	}
}
