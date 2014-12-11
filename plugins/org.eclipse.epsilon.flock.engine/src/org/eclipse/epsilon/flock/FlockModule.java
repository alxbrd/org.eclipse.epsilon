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
package org.eclipse.epsilon.flock;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;
import org.eclipse.epsilon.flock.model.domain.common.Guard;
import org.eclipse.epsilon.flock.model.domain.rules.Body;
import org.eclipse.epsilon.flock.model.domain.rules.MigrateRule;
import org.eclipse.epsilon.flock.model.domain.typemappings.Deletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageDeletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageRetyping;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;
import org.eclipse.epsilon.flock.parse.FlockLexer;
import org.eclipse.epsilon.flock.parse.FlockParser;

public class FlockModule extends ErlModule implements IFlockModule {
	
	private MigrationStrategy strategy;
	protected IFlockContext context = new FlockContext();
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new FlockLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new FlockParser(tokenStream);
	}
	
	@Override
	public String getMainRule() {
		return "flockModule";
	}
	
	@Override
	public AST adapt(AST cst, AST parentAst) {
		if (cst.getType() == FlockParser.GUARD) {
			return new Guard();
		
		} else if (cst.getType() == FlockParser.DELETE) {
			return new Deletion();
		
		} else if (cst.getType() == FlockParser.RETYPE) {
			return new Retyping();
		
		} else if (cst.getType() == FlockParser.MIGRATE) {
			return new MigrateRule();
			
		} else if (cst.getType() == FlockParser.RETYPEPACKAGE) {
			return new PackageRetyping();
		
		} else if (cst.getType() == FlockParser.DELETEPACKAGE) {
			return new PackageDeletion();

		} else if (cst.getType() == FlockParser.BLOCK && cst.getParent() != null && cst.getParent().getType() == FlockParser.MIGRATE) {
			return new Body();
		}
		
		return super.adapt(cst, parentAst);
	}

	@Override
	public void buildModel() throws Exception {
		super.buildModel();
		
		strategy = new MigrationStrategyLoader(ast).run();
	}

	public FlockResult execute(IModel original, IModel migrated) throws EolRuntimeException {
		context.setOriginalModel(original);
		context.setMigratedModel(migrated);
			
		return execute();
	}

	public FlockResult execute() throws EolRuntimeException {
		FlockResult result = null;
		
		prepareContext(context);
		
		execute(getPre(), context);
		result = context.execute(strategy);
		execute(getPost(), context);
		
		return result;
	}
	
	@Override
	public List<ModuleElement> getModuleElements(){
		final List<ModuleElement> children = new ArrayList<ModuleElement>();
		
		children.addAll(getImports());
		children.addAll(getDeclaredPre());
		children.addAll(strategy.getTypeMappingsAndRules());
		children.addAll(getDeclaredOperations());
		children.addAll(getDeclaredPost());
		
		return children;
	}

	public IFlockContext getContext() {
		return context;
	}
	
	@Override
	protected int getPreBlockTokenType() {
		return FlockParser.PRE;
	}

	@Override
	protected int getPostBlockTokenType() {
		return FlockParser.POST;
	}

	@Override
	public void setContext(IEolContext context) {
		if (context instanceof IFlockContext) {
			this.context = (IFlockContext) context;
		}
	}
}
