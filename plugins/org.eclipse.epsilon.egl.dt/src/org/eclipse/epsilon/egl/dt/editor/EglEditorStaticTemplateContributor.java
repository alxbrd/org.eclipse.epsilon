package org.eclipse.epsilon.egl.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.jface.text.templates.Template;

public class EglEditorStaticTemplateContributor implements IAbstractModuleEditorTemplateContributor {

	List<Template> templates = null;
	
	public List<Template> getTemplates() {
		if (templates==null) {
			templates = new ArrayList<Template>();
			templates.add(new Template("[% %]","dynamic block","","[%${cursor}%]",false));
			templates.add(new Template("[%= %]","output block","","[%=${cursor}%]",false));
			templates.add(new Template("[* *]","multiline comment","","[*${cursor}*]",false));
			templates.add(new Template("for", "iterate over collection", "", "[%for (${iterator} in ${collection}) { %]\r\n\t${cursor}\r\n[%}%]",false));
			String generateTemplate = "var ${templateName} : Template;\r\n" + 
									"-- Pass parameters to the template\r\n" + 
									"${templateName} := TemplateFactory.load('${templateName}.egl');\r\n" + 
									"${templateName}.populate('${parameterName}', ${parameterValue});\r\n" + 
									"${templateName}.generate('${targetFile}');";
			templates.add(new Template("generate", "invokes a subtemplate and stores results in a file", "", generateTemplate, false));
			
			String processTemplate = "var ${templateName} : Template;\r\n" + 
									 "-- Pass parameters to the template\r\n" + 
									 "${templateName} := TemplateFactory.load('${templateName}.egl');\r\n" + 
									 "${templateName}.populate('${parameterName}', ${parameterValue});\r\n" + 
									 "var ${result} := ${templateName}.process();";
			
			templates.add(new Template("process", "invoke a subtemplate and return results", "org.eclipse.epsilon.egl.dt.editor.EOL", processTemplate, false));

		}
		return templates;
	}

}
