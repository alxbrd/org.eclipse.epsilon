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
package org.eclipse.epsilon.migration.model;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.reset;
import static org.easymock.classextension.EasyMock.verify;

import org.eclipse.epsilon.commons.parse.AST;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.migration.IMigrationContext;
import org.eclipse.epsilon.migration.emc.wrappers.ModelElement;
import org.eclipse.epsilon.migration.execution.MigrationExecutionException;
import org.junit.Before;
import org.junit.Test;

public class ExecutableMigrationRuleApplyToTests {
		
	private final IMigrationContext mockContext = createMock(IMigrationContext.class);
	
	private final AST body = new AST();
	private final ExecutableMigrationRule rule = new ExecutableMigrationRule("", "", null, body);
	
	private final Variable dummyOriginalVariable = createMock(Variable.class);
	private final Variable dummyMigratedVariable = createMock(Variable.class);
	
	private ModelElement mockOriginalModelElement = createMock(ModelElement.class);
	private ModelElement mockMigratedModelElement = createMock(ModelElement.class);
	
	@Before
	public void setup() {
		reset(mockOriginalModelElement, mockMigratedModelElement, mockContext);
		
		expect(mockOriginalModelElement.createReadOnlyVariable("original"))
			.andReturn(dummyOriginalVariable);
		
		expect(mockMigratedModelElement.createReadOnlyVariable("migrated"))
			.andReturn(dummyMigratedVariable);
	}
	
	@Test
	public void applyToCreatesVariablesAndDelegatesToExecuteBlock() throws MigrationExecutionException {

		// Expectations
		
		expect(mockContext.executeBlock(body, dummyOriginalVariable, dummyMigratedVariable))
			.andReturn(null);
		
		replay(mockOriginalModelElement, mockMigratedModelElement, mockContext);
		
		
		// Verification
		
		rule.applyTo(mockOriginalModelElement, mockMigratedModelElement, mockContext);
		
		verify(mockOriginalModelElement, mockMigratedModelElement, mockContext);
	}
	
	
	@Test(expected=MigrationExecutionException.class)
	public void applyToThrowsExceptionWhenExecuteBlockThrowsException() throws MigrationExecutionException {
		
		// Expectations
		
		expect(mockContext.executeBlock(body, dummyOriginalVariable, dummyMigratedVariable))
			.andThrow(new MigrationExecutionException("Fake exception", null));
		
		replay(mockOriginalModelElement, mockMigratedModelElement, mockContext);
		
		
		// Verification
		
		rule.applyTo(mockOriginalModelElement, mockMigratedModelElement, mockContext);
		
		verify(mockOriginalModelElement, mockMigratedModelElement, mockContext);
	}
}
