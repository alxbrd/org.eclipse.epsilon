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
package org.eclipse.epsilon.concordance.core.hashing.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.concordance.core.hashing.hashers.ecore.EAttributeHasherTests;
import org.eclipse.epsilon.concordance.core.hashing.hashers.ecore.EClassHasherTests;
import org.eclipse.epsilon.concordance.core.hashing.hashers.ecore.EPackageHasherTests;
import org.eclipse.epsilon.concordance.core.hashing.hashers.ecore.EReferenceHasherTests;
import org.eclipse.epsilon.concordance.core.hashing.hashers.ecore.FeaturesTests;
import org.eclipse.epsilon.concordance.core.hashing.test.integration.BidirectionalReference;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({FeaturesTests.class,
               EAttributeHasherTests.class, EReferenceHasherTests.class, EClassHasherTests.class, EPackageHasherTests.class,
               BidirectionalReference.class})
public class HashersTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(HashersTestSuite.class);
	}
}
