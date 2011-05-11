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
package org.eclipse.epsilon.flock.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.commons.module.IModule;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.dt.editor.outline.FlockModuleElementLabelProvider;

public class FlockEditor extends EolEditor {
		
	public FlockEditor() {
		this.addTemplateContributor(new FlockEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		final List<String> keywords = new ArrayList<String>(super.getKeywords());

		keywords.add("delete");
		keywords.add("retype");
		keywords.add("to");
		keywords.add("migrate");
		keywords.add("when");
		keywords.add("ignoring");
		
		return keywords;
	}
	
	@Override
	public List<String> getBuiltinVariables() {
		final List<String> builtIn = new ArrayList<String>(super.getBuiltinVariables());
		
		builtIn.add("original");
		builtIn.add("migrated");
		
		
		return builtIn;
	}
	
	@Override
	public IModule createModule(){
		return new FlockModule();
	}
	
	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new FlockModuleElementLabelProvider();
	}
	
}
