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
package org.eclipse.epsilon.flock.model.loader;

import org.eclipse.epsilon.commons.parse.AST;
import org.eclipse.epsilon.flock.model.domain.typemappings.Deletion;

public class DeletionLoader extends Loader {

	public DeletionLoader(AST ast) {
		super(ast);
	}
	
	public Deletion run() {
		return new Deletion(ast, getAnnotations(), getOriginalType(), getGuard());
	}

	private String getOriginalType() {
		return getFirstChild().getText();
	}
}
