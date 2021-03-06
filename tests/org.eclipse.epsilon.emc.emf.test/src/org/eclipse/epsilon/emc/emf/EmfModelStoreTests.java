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
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmfModelStoreTests {
	
	private static String UNIX_ABSOLUTE_PATH;
	
	@BeforeClass
	public static void createTempFile() throws IOException {
		UNIX_ABSOLUTE_PATH = File.createTempFile("EpsilonEmfModelTest", "model").getAbsolutePath();
	}
	
	@AfterClass
	public static void removeFile() {
		new File(UNIX_ABSOLUTE_PATH).delete();
	}
	
	@Test
	public void storeUnixAbsolutePath() throws Exception {	
		if (OperatingSystem.isUnix()) {
			final EmfModel model = new EmfModel();
			model.setModelImpl(EmfUtil.createResource());
			
			assertTrue(model.store(URI.createFileURI(UNIX_ABSOLUTE_PATH)));
		}
	}
	
	@Test
	public void storeFailsForAbsolutePathsWithoutASchema() throws Exception {	
		if (OperatingSystem.isUnix()) {
			final EmfModel model = new EmfModel();
			model.setModelImpl(EmfUtil.createResource());
				
			assertFalse(model.store(UNIX_ABSOLUTE_PATH));
		}
	}
}
