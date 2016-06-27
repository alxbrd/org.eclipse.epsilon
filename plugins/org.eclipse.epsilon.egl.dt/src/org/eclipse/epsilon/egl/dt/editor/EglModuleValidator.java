package org.eclipse.epsilon.egl.dt.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.AbstractModuleValidator;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.IModuleValidator;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.module.ModuleMarker.Severity;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.egl.internal.EglPreprocessorModule;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.ExpressionStatement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.dom.StringLiteral;

public class EglModuleValidator extends AbstractModuleValidator implements IModuleValidator {
	
	@Override
	public List<ModuleMarker> validate(IModule module) {
		if (!appliesTo(module)) return Collections.emptyList();
		
		ArrayList<ModuleMarker> markers = new ArrayList<ModuleMarker>();
		EglPreprocessorModule preprocessorModule = ((EglTemplateFactoryModuleAdapter) module).getCurrentTemplate().getModule().getPreprocessorModule();
		
		if (!preprocessorModule.getDeclaredOperations().isEmpty()) {
			
			boolean looseStatements = false;
			
			// Fix for bug #393988
			for (Statement statement : preprocessorModule.getPostOperationStatements()) {
				if (!isEmptyPrintStatement(statement)) {
					looseStatements = true; break;
				}
			}
			
			if (looseStatements) {
				markers.add(new ModuleMarker(null, preprocessorModule.getOperations().get(0).getRegion(), 
						"All loose statements and textual content after the first operation will be ignored at runtime.", 
						Severity.Warning));
			}
			
		}
		
		return markers;
		
	}
	
	protected boolean appliesTo(IModule module) {
		return module instanceof EglTemplateFactoryModuleAdapter;
	}
	
	protected boolean isEmptyPrintStatement(ModuleElement ast) {
		// We're looking for out.prinx("<whitespace only here>") 
		if (ast instanceof ExpressionStatement) {
			Expression expression = ((ExpressionStatement) ast).getExpression();
			if (expression instanceof OperationCallExpression) {
				OperationCallExpression operationCallExpression = (OperationCallExpression) expression;
				if (operationCallExpression.getTargetExpression() instanceof NameExpression) {
					NameExpression nameExpression = (NameExpression) operationCallExpression.getTargetExpression();
					if (operationCallExpression.getParameterExpressions().size() == 1 && operationCallExpression.getParameterExpressions().get(0) instanceof StringLiteral) {
						String parameterValue = ((StringLiteral) operationCallExpression.getParameterExpressions().get(0)).getValue();
						return "out".equals(nameExpression.getName()) && "prinx".equals(operationCallExpression.getOperationName())
								&& (parameterValue.trim().equals("\\n") || parameterValue.trim().isEmpty());
					}
				}
			}
		}
		
		return false;
	}

}
