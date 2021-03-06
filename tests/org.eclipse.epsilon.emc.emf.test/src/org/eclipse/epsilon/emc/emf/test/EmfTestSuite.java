/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.emc.emf.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.emc.emf.AbstractEmfModelTests;
import org.eclipse.epsilon.emc.emf.CachedResourceSetTests;
import org.eclipse.epsilon.emc.emf.EmfModelCachedContentsTests;
import org.eclipse.epsilon.emc.emf.EmfModelDeleteTests;
import org.eclipse.epsilon.emc.emf.EmfModelGetPropertiesOfTests;
import org.eclipse.epsilon.emc.emf.EmfModelIsPropertySetTests;
import org.eclipse.epsilon.emc.emf.EmfModelLoadTests;
import org.eclipse.epsilon.emc.emf.EmfModelPackageForNameTests;
import org.eclipse.epsilon.emc.emf.EmfModelPropertyMigratorTests;
import org.eclipse.epsilon.emc.emf.EmfModelStoreTests;
import org.eclipse.epsilon.emc.emf.EmfModelTestSuite;
import org.eclipse.epsilon.emc.emf.EmfPropertySetterCoerceTests;
import org.eclipse.epsilon.emc.emf.EmfPropertySetterConformsTests;
import org.eclipse.epsilon.emc.emf.EmfToolResolveUriTests;
import org.eclipse.epsilon.emc.emf.URITest;
import org.eclipse.epsilon.emc.emf.xml.XmlModelTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({URITest.class, AbstractEmfModelTests.class, EmfModelTestSuite.class, XmlModelTests.class,
               EmfPropertySetterConformsTests.class, EmfPropertySetterCoerceTests.class,
               EmfModelPackageForNameTests.class, CachedResourceSetTests.class,
               EmfModelDeleteTests.class, EmfModelIsPropertySetTests.class,
               EmfModelCachedContentsTests.class, EmfToolResolveUriTests.class})
public class EmfTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(EmfTestSuite.class);
	}
}
 