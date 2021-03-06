/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.formatter;

/**
 * A Formatter provides a means for postprocessing 
 * the text generated by an EGL template. Both TemplateFactories
 * and Templates can specify a set of Formatters to
 * be used for postprocessing.
 */
public interface Formatter {

	public String format(String text);
}
