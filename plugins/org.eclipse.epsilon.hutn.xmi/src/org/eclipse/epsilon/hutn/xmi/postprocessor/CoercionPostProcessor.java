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
package org.eclipse.epsilon.hutn.xmi.postprocessor;

import java.util.List;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.coerce.SlotCoercer;
import org.eclipse.epsilon.hutn.xmi.util.EmfUtil;

public class CoercionPostProcessor {

	private final Spec spec;
	private final SlotCoercer coercer = new SlotCoercer();
	
	public CoercionPostProcessor(Spec spec) {
		this.spec = spec;
	}

	public void process() {
		coerceAttributeSlotsWithoutAFeature();
	}
	
	private void coerceAttributeSlotsWithoutAFeature() {
		for (AttributeSlot slot : allAttributeSlots()) {
			if (!slot.hasEStructuralFeature() && !slot.getValues().isEmpty()) {
				coercer.coerce(slot);
			}
		}
	}

	private List<AttributeSlot> allAttributeSlots() {
		return EmfUtil.getAllModelElementsOfType(spec, AttributeSlot.class);
	}
}
