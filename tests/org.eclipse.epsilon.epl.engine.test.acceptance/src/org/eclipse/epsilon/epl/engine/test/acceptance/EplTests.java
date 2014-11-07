/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.engine.test.acceptance;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;

import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.execute.PatternMatchModel;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class EplTests {
	
	protected PatternMatchModel patternMatchModel = null;
	
	@Test
	public void testEpl() throws Exception {
		
		HashMap<String, String> blackboard = new HashMap<String, String>();
		
		EplModule module = new EplModule();
		module.parse(EplTests.class.getResource("test.epl").toURI());
		module.getContext().getFrameStack().putGlobal(Variable.createReadOnlyVariable("blackboard", blackboard));
		
		PlainXmlModel model = new PlainXmlModel();
		model.setName("M");
		model.setFile(new File(EplTests.class.getResource("test.xml").toURI()));
		model.load();
		
		module.getContext().getModelRepository().addModel(model);
		patternMatchModel = (PatternMatchModel) module.execute();
		
		assertNumberOfMatches(2, "B");
		assertNumberOfMatches(4, "BC");
		assertNumberOfMatches(2, "BfromAll");
		assertNumberOfMatches(2, "BfromReturnAll");
		assertNumberOfMatches(2, "Bmatch");
		assertNumberOfMatches(0, "NoB");
		assertNumberOfMatches(0, "NoBguardReturn");
		assertNumberOfMatches(2, "Bguard");
		assertNumberOfMatches(2, "BCinactive");
		assertNumberOfMatches(4, "BCactive");
		assertEquals("xx", blackboard.get("Bonmatch"));
		assertEquals("xx", blackboard.get("Bnomatch"));
		assertEquals("xxxx", blackboard.get("BCdo"));
		//assertNumberOfMatches(0, "Binactive"); <- Should be 0 but is 1 TODO: Investigate
		
	}
	
	protected void assertNumberOfMatches(int matches, String name) throws Exception {
		assertEquals(matches, patternMatchModel.getAllOfType(name).size());
	}
	
}
