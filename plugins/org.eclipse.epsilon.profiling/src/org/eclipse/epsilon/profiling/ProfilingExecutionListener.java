package org.eclipse.epsilon.profiling;

import org.eclipse.epsilon.commons.parse.AST;
import org.eclipse.epsilon.commons.util.AstUtil;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;
import org.eclipse.epsilon.eol.parse.EolParser;

public class ProfilingExecutionListener implements IExecutionListener {

	@Override
	public void aboutToExecute(AST ast, IEolContext context) {
		//if (AstUtil.getParentType(ast) == EolParser.POINT)
			Profiler.INSTANCE.start(getLabel(ast), "", new FileMarker(ast.getFile(), ast.getLine(), ast.getColumn()));
	}

	@Override
	public void finishedExecuting(AST ast, Object evaluatedAst, IEolContext context) {
		//if (AstUtil.getParentType(ast) == EolParser.POINT)
			Profiler.INSTANCE.stop(getLabel(ast));
	}
	
	protected String getLabel(AST ast) {
		return ast.getText() + " (" + ast.getLine() + ":" + ast.getColumn() + ")";
	}
	
}
