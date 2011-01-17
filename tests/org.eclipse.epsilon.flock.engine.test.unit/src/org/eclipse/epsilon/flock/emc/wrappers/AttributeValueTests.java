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
package org.eclipse.epsilon.flock.emc.wrappers;

import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.flock.IFlockContext;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.emc.wrappers.AttributeValue;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.execution.exceptions.ConservativeCopyException;
import org.junit.Test;

public class AttributeValueTests {

	private static final Model          dummyModel  = createMock(Model.class);
	private static final AttributeValue value       = new AttributeValue(dummyModel, "foo");
	
	@Test
	public void unwrapShouldReturnUnderlyingModelObject() {
		assertEquals("foo", value.unwrap());
	}
	
	@Test
	public void getEquivalentShouldCreateEquivalentValue() throws ConservativeCopyException {
		final Model               dummyMigratedModel = createMock(Model.class);
		final ConservativeCopyContext dummyContext       = createMock(ConservativeCopyContext.class);
		
		// Expectations
		replay(dummyMigratedModel, dummyContext);
		
		
		// Verification
		
		assertEquals(new AttributeValue(dummyMigratedModel, "foo"),
		             value.getEquivalentIn(dummyMigratedModel, dummyContext));
		
		verify(dummyMigratedModel, dummyContext);
	}
}
