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
package org.eclipse.epsilon.hutn.unparser.internal;

import org.eclipse.epsilon.hutn.model.hutn.Object;


abstract class Unparser {

	protected final StringBuilder builder;
	
	public Unparser() {
		this.builder = new StringBuilder();
	}
	
	public Unparser(StringBuilder builder) {
		this.builder = builder;
	}
	

	public String unparse() {
		doUnparse();
		
		return builder.toString();
	}

	protected abstract void doUnparse();

	
	
	protected void appendSpace() {
		builder.append(' ');
	}
	
	protected void appendNewLine() {
		builder.append('\n');
	}
	
	protected void appendStringValue(String value) {
		builder.append('"');
		builder.append(value);
		builder.append('"');
	}
	
	
	protected void appendSignature(Object co) {
		unparseType(co);
		unparseIdentifier(co);
	}
	
	private void unparseType(Object co) {
		builder.append(co.getType());
		appendSpace();
	}
	
	private void unparseIdentifier(Object co) {
		if(co.getIdentifier() != null) {
			appendStringValue(co.getIdentifier());
		}
	}
}
