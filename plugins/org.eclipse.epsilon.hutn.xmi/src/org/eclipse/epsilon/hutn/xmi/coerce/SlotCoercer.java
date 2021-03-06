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
package org.eclipse.epsilon.hutn.xmi.coerce;

import org.eclipse.epsilon.emc.emf.util.EListUtil;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;

public class SlotCoercer {
	
	private AttributeSlot slot;
	
	public Slot<?> coerce(AttributeSlot slot) {
		this.slot = slot;
				
		if (slotContainsOnlyStrings()) {
			return AbstractCoercionStrategy.getCoercerFor(slot).coerce();
		}
		
		return slot;
	}
	
	private boolean slotContainsOnlyStrings() {
		return EListUtil.elementsAreAllInstancesOf(slot.getValues(), String.class);			
	}
}
