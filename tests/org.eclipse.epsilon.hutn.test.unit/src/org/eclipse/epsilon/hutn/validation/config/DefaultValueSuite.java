/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: DefaultValueSuite.java,v 1.2 2008/08/07 12:44:22 louis Exp $
 */
package org.eclipse.epsilon.hutn.validation.config;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.validation.config.defaultValue.IncorrectlyTypedDefaultValue;
import org.eclipse.epsilon.hutn.validation.config.defaultValue.ValidDefaultValue;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ValidDefaultValue.class, IncorrectlyTypedDefaultValue.class})
public class DefaultValueSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(DefaultValueSuite.class);
	}
}
