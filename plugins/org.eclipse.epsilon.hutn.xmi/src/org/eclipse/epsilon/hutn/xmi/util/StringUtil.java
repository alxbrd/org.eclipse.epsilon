/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.util;


public abstract class StringUtil {

	private StringUtil() {}
	
	public static boolean isWhitespace(String s) {
    	return s.trim().length() == 0;
    }
	
	public static boolean isNotWhitespace(String s) {
    	return !isWhitespace(s);
    }
	
	public static String removeLeading(char toRemove, String s) {
		if (s.length() == 0)
			return s;
		
		return s.charAt(0) == toRemove ? s.substring(1) : s;
	}
}
