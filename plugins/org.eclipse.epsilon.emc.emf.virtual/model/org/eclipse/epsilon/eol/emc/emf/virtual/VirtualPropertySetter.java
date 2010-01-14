/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.emc.emf.virtual;

import org.eclipse.epsilon.emc.emf.virtual.Slot;
import org.eclipse.epsilon.emc.emf.virtual.VirtualObject;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;

class VirtualPropertySetter extends AbstractPropertySetter {
	
	public void invoke(Object value) throws EolRuntimeException {
		final VirtualObject vObject = (VirtualObject)object;
				
		final Slot slot = SlotFactory.getInstance().createSlotFor(value);
		
		if (slot == null)
			throw new EolIllegalPropertyException(object, property, ast, context);
		
		slot.setType(property);
		
		vObject.getSlots().add(slot);
	}
	
	public Object coerce(Object value) throws EolIllegalPropertyException {
		return value;
	}

	public boolean conforms(Object value) throws EolIllegalPropertyException {
		return SlotFactory.getInstance().createSlotFor(value) != null;
	}
}
