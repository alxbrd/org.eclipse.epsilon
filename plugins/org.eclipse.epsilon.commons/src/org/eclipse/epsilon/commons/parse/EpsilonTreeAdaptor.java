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
package org.eclipse.epsilon.commons.parse;

import java.io.File;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;


public class EpsilonTreeAdaptor extends CommonTreeAdaptor {
	
	protected File file = null;
	
	public EpsilonTreeAdaptor(File file) {
		this.file = file;
	}
	
    public AST create(Token token)
    {
        return new AST(token, file);
    }
}
