/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.engine.test.acceptance;

import org.eclipse.epsilon.ewl.engine.test.acceptance.builtins.EwlCanAccessBuiltinsTests;
import org.eclipse.epsilon.ewl.engine.test.acceptance.trees.TestXmlTreeWizards;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EwlCanAccessBuiltinsTests.class, TestXmlTreeWizards.class})
public class EwlAcceptanceTestSuite {

}
