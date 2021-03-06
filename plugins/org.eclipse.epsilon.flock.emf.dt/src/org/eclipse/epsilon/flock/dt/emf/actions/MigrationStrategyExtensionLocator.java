/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.emf.actions;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class MigrationStrategyExtensionLocator {

	private static final String MIGRATION_STRATEGY_EXT_POINT_ID = "org.eclipse.epsilon.flock.dt.strategy";
	
	public MigrationStrategyExtension findMigrationStrategyExtensionFor(IFile file) throws MigrationStrategyExtensionLocatorException {
		try {
			for (IConfigurationElement extPoint : getMigrationStrategyExtensionDefinitions()) {
				final List<String> fileExtensions = Arrays.asList(extPoint.getAttribute("modelExtensions").split(","));
				
				if (fileExtensions.contains(file.getLocation().getFileExtension())) {
					return new MigrationStrategyExtension(extPoint);
				}
			}
		
		} catch (Exception e) {
			throw new MigrationStrategyExtensionLocatorException(e);
		}
//			
		
		return null;
	}
	
	private static IConfigurationElement[] getMigrationStrategyExtensionDefinitions() {
		return Platform.getExtensionRegistry().getConfigurationElementsFor(MIGRATION_STRATEGY_EXT_POINT_ID);
	}
}
