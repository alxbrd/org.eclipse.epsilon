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
package org.eclipse.epsilon.hutn.dt.nature;

import org.eclipse.epsilon.common.dt.nature.BuilderConfiguringNature;

public class HutnNature extends BuilderConfiguringNature {

	static final String ID = "org.eclipse.epsilon.hutn.dt.nature.HutnNature";
	
	@Override
	protected String getBuilderID() {
		return HutnBuilder.ID;
	}

}
