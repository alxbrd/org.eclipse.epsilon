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
package org.eclipse.epsilon.hutn.translate;

class MetaModelDetail {

	private final Detail nsUriDetail;
	private final Detail configFileDetail;
	
	public MetaModelDetail(Detail nsUriDetail, Detail configFileDetail) {
		this.nsUriDetail      = nsUriDetail;
		this.configFileDetail = configFileDetail;
	}
	
	public Detail getNsUriDetail() {
		return nsUriDetail;
	}
	
	public Detail getConfigFileDetail() {
		return configFileDetail;
	}	
}
