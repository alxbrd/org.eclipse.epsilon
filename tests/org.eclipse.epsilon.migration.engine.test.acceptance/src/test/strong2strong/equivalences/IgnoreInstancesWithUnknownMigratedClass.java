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
package test.strong2strong.equivalences;

import static org.eclipse.epsilon.migration.engine.test.util.builders.MetamodelBuilder.aMetamodel;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecore.EPackage;
import org.junit.BeforeClass;
import org.junit.Test;

import test.strong2strong.Strong2StrongMigrationAcceptanceTest;

public class IgnoreInstancesWithUnknownMigratedClass extends Strong2StrongMigrationAcceptanceTest {

	private static final String strategy = "";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "}";
	
	private static final EPackage evolvedMetamodel = aMetamodel().build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesTo(evolvedMetamodel, strategy, originalModel);
	}
	
	@Test
	public void migratedModelShouldBeEmpty() {
		assertFalse(migrated.getResource().getAllContents().hasNext());
	}
}
