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
package org.eclipse.epsilon.migration.dt.editor.outline;

import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleElementLabelProvider;
import org.eclipse.epsilon.migration.dt.MigrationPlugin;
import org.eclipse.epsilon.migration.model.MigrationRule;
import org.eclipse.swt.graphics.Image;

public class MigrationModuleElementLabelProvider extends EolModuleElementLabelProvider{

	@Override
	public Image getImage(Object element) {
		if (element instanceof MigrationRule) {
			return MigrationPlugin.getDefault().createImage("icons/migration.png");
		} else {
			return super.getImage(element);
		}
	}
	
	@Override
	public String getText(Object element) {
		// The implementation of MigrationRule#toString is good for tests and debugging,
		// but looks messy in outline view, so overriding getText for MigrationRules
		
		if (element instanceof MigrationRule) {
			return ((MigrationRule)element).getOriginalType() + " to " + ((MigrationRule)element).getMigratedType();
		} else {
			return super.getText(element);
		}
	}
	
}
