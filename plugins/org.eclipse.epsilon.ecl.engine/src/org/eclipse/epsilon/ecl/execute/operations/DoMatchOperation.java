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
package org.eclipse.epsilon.ecl.execute.operations;

import java.util.Collection;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.ecl.execute.context.IEclContext;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;


public class DoMatchOperation extends MatchesOperation{

	@Override
	public Object execute(Object obj, AST ast, IEolContext context_) throws EolRuntimeException {
		IEclContext context = (IEclContext) context_;
		AST parameterAst = ast.getFirstChild().getFirstChild();
		Object parameter = context.getExecutorFactory().executeAST(parameterAst, context);
		if (obj == null && parameter == null) return null;
		
		Collection leftCol = CollectionUtil.flatten(CollectionUtil.asCollection(obj));
		Collection rightCol = CollectionUtil.flatten(CollectionUtil.asCollection(parameter));
		
		for (Object left : leftCol) {
			for (Object right : rightCol) {
				matchInstances(left, right, context, true);
			}
		}

		return null;
		
	}
	
}
