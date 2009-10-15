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
package org.eclipse.epsilon.hutn.xmi.dt.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.xmi.dt.startup.FileLocatorTests;
import org.eclipse.epsilon.hutn.xmi.dt.startup.RegisteredMetamodels;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({FileLocatorTests.class})
public class HutnXmiBridgeDevelopmentToolsPluggedInTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(FileLocatorTests.class);
	}
}
