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
package org.eclipse.epsilon.evl.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentOutlinePage;
import org.eclipse.epsilon.commons.module.IModule;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dt.editor.outline.EvlModuleElementLabelProvider;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class EvlEditor extends EolEditor{
		
	@Override
	public List getKeywords() {
		
		List keywords = new ArrayList();
		
		keywords.add("context");
		keywords.add("constraint");
		keywords.add("guard");
		keywords.add("pre");
		keywords.add("post");
		keywords.add("assumes");
		keywords.add("critique");
		keywords.add("message");
		keywords.add("title");
		keywords.add("do");
		keywords.add("check");
		keywords.add("fix");
		keywords.add("typeOf");
		keywords.add("kindOf");
		keywords.add("high");
		keywords.add("medium");
		keywords.add("low");

		keywords.addAll(super.getKeywords());
		
		return keywords;
	}
	
	@Override
	public List getBuiltinVariables() {
		
		ArrayList builtIn = new ArrayList();
		
		builtIn.add("constraintTrace");
		
		builtIn.addAll(super.getBuiltinVariables());
		
		return builtIn;
	}

	@Override
	public IContentOutlinePage createOutlinePage() {
		ModuleContentOutlinePage outline = 
			new ModuleContentOutlinePage(
					this.getDocumentProvider(), 
					this, 
					new EvlModule(), 
					new EvlModuleElementLabelProvider());
		
		return outline;
	}
	
	@Override
	public IModule getModule(){
		return new EvlModule();
	}
	
	List<Template> templates = null;
	public List<Template> getTemplates() {
		if (templates == null) {
			templates = super.getTemplates();
			
			System.err.println(templates.size());
			
			templates.add(new Template("context", "context", "", 
					"context ${classname} {\r\n" + 
					"\t ${cursor}\r\n" + 
					"}",false));
			
			templates.add(new Template("constraint", "constraint", "", 
						"constraint ${name} {\r\n" + 
						"\tcheck : ${cursor}\r\n" + 
						"\tmessage : \r\n" +
						"}",false));
			
			templates.add(new Template("fix", "fix", "", 
					"fix \r\n" + 
					"\ttitle : ${cursor}\r\n" + 
					"\tdo {\r\n" +
					"\t\t\r\n" +
					"\t}\r\n" +
					"}",false));
			
			System.err.println(templates.size());
			
		}
		return templates;
	}
	
}
