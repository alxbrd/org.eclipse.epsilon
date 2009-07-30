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
package org.eclipse.epsilon.migration;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.commons.parse.EpsilonParser;
import org.eclipse.epsilon.eol.EolLibraryModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.migration.execution.MigrationExecutionException;
import org.eclipse.epsilon.migration.model.MigrationStrategy;
import org.eclipse.epsilon.migration.model.loader.MigrationStrategyLoader;
import org.eclipse.epsilon.migration.parse.MigrationLexer;
import org.eclipse.epsilon.migration.parse.MigrationParser;

public class MigrationModule extends EolLibraryModule implements IMigrationModule {
	
	private MigrationStrategy strategy;
	
	// FIXME ! Could tidy up the next two methods with a generic?
	
	@Override
	public Lexer createLexer(InputStream inputStream) {
		ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new MigrationLexer(input);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new MigrationParser(tokenStream);
	}
	
	@Override
	public String getMainRule() {
		return "migrationStrategy";
	}

	@Override
	public void buildModel() throws Exception {
		super.buildModel();
		
		strategy = new MigrationStrategyLoader(ast).run();
	}

	public void execute(IModel original, IModel target) throws MigrationExecutionException {
		execute(new MigrationContext(original, target));
	}

	public void execute(IMigrationContext context) throws MigrationExecutionException {
		reset();
		strategy.establishEquivalences(context).populateEachEquivalent(context);
	}
}

