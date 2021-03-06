/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.graphml;

import org.eclipse.epsilon.emc.muddle.MuddleElement;

public class OrphanLink {
	
	protected MuddleElement source;
	protected MuddleElement target;
	
	public MuddleElement getSource() {
		return source;
	}
	
	public void setSource(MuddleElement source) {
		this.source = source;
	}
	
	public MuddleElement getTarget() {
		return target;
	}
	
	public void setTarget(MuddleElement target) {
		this.target = target;
	}

	public OrphanLink(MuddleElement source, MuddleElement target) {
		super();
		this.source = source;
		this.target = target;
	}
	
	
	
	
}
