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
package org.eclipse.epsilon.eml.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.eml.execute.context.IEmlContext;
import org.eclipse.epsilon.eml.parse.EmlParser;
import org.eclipse.epsilon.eml.trace.MergeTrace;
import org.eclipse.epsilon.eml.trace.Merges;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.erl.dom.ExtensibleNamedRule;

public class MergeRule extends ExtensibleNamedRule {
	
	protected ExecutableBlock<Boolean> guardBlock = null;
	protected StatementBlock bodyBlock = null;
	protected Parameter leftParameter;
	protected Parameter rightParameter;
	protected List<Parameter> targetParameters = new ArrayList<Parameter>();
	protected boolean auto = false;
	
	public MergeRule(){ }
	
	@SuppressWarnings("unchecked")
	@Override
	public void build() {
		
		super.build();
		
		this.guardBlock = (ExecutableBlock<Boolean>) AstUtil.getChild(this, EmlParser.GUARD);
		this.bodyBlock = (StatementBlock) AstUtil.getChild(this, EmlParser.BLOCK);
		
		//Parse the formal parameters
		leftParameter = (Parameter) getSecondChild();
		rightParameter = (Parameter) getThirdChild();
		
		for (AST mergedParameterAst : getFourthChild().getChildren()) {
			targetParameters.add((Parameter) mergedParameterAst);
		}
		
	}
	
	public boolean isLazy(IEmlContext context) throws EolRuntimeException {
		return getBooleanAnnotationValue("lazy", context);
	}
	
	public boolean isPrimary(IEmlContext context) throws EolRuntimeException {
		return getBooleanAnnotationValue("primary", context);
	}
	
	public boolean appliesTo(Match match, IEmlContext context) throws EolRuntimeException{
		
		if (hasMerged(match)) return true;
		
		Object left = match.getLeft();
		Object right = match.getRight();
		
		boolean appliesToTypes = getAllInstances(leftParameter, context, !isGreedy()).contains(left) && 
				getAllInstances(rightParameter, context, !isGreedy()).contains(right);
	
		boolean guardSatisfied = true;
		
		if (appliesToTypes && guardBlock != null){
			
			guardSatisfied = guardBlock.execute(context, 
				Variable.createReadOnlyVariable(leftParameter.getName(), left), 
				Variable.createReadOnlyVariable(rightParameter.getName(), right),
				Variable.createReadOnlyVariable("self", this));
		}
		
		return appliesToTypes && guardSatisfied;
	}
	

	public Collection<?> merge(Match match, Collection<Object> targets, IEmlContext context) throws EolRuntimeException{
		
		MergeTrace mergeTrace =(context).getMergeTrace();
		Merges merges = mergeTrace.getMerges(match, this);
		
		if (!merges.isEmpty()) return merges.getTargets();
		
		executeSuperRulesAndBody(match,targets,context);
		
		return targets;
	}

	
	public boolean hasMerged(Match match) {
		return mergedMatches.contains(match);
	}
	
	HashSet<Match> mergedMatches = new HashSet<Match>();
	
	public Collection<?> merge(Match match, IEmlContext context) throws EolRuntimeException{
		
		MergeTrace mergeTrace =(context).getMergeTrace();
		
		if (hasMerged(match)) {
			return mergeTrace.getMerges(match, this).getTargets();
		}
		else {
			mergedMatches.add(match);
		}
		
		Collection<Object> targets = CollectionUtil.createDefaultList();

		ListIterator<Parameter> li = targetParameters.listIterator();
		
		while (li.hasNext()){

			Parameter targetParameter = (Parameter) li.next();
			EolType targetParameterType = (EolType) targetParameter.getType(context);
			targets.add(targetParameterType.createInstance());
		}

		mergeTrace.add(match,targets,this);
	
		executeSuperRulesAndBody(match, targets, context);
		
		return targets;
	}
		
	@Override
	public String toString(){
		String str = name;
		str = str + " (";
		str = str + 
		leftParameter.getTypeName() + ", " +
		rightParameter.getTypeName();
		str = str + ") : ";
		ListIterator<Parameter> li = targetParameters.listIterator();
		while (li.hasNext()){
			Parameter targetParameter = (Parameter) li.next();
			str += targetParameter.getTypeName();
			if (li.hasNext()){
				str += ", ";
			}
		}
		return str;
	}
	
	public void executeSuperRulesAndBody(Match match, Collection<Object> targets, IEmlContext context) throws EolRuntimeException{
		
		// Execute the super rules
		for (ExtensibleNamedRule superRule : superRules){
			((MergeRule) superRule).merge(match, targets, context);
		}
		
		FrameStack scope = context.getFrameStack();
		
		scope.enterLocal(FrameType.PROTECTED, this);
		
		scope.put(new Variable(leftParameter.getName(), match.getLeft(), leftParameter.getType(context), true));
		scope.put(new Variable(rightParameter.getName(), match.getRight(), rightParameter.getType(context), true));
		
		scope.put(Variable.createReadOnlyVariable("self",this));
		
		for (int i=0; i<targetParameters.size(); i++){
			Parameter targetParameter = (Parameter) targetParameters.get(i);
			scope.put(new Variable(targetParameter.getName(), CollectionUtil.asList(targets).get(i),targetParameter.getType(context),true));
		}
		context.getExecutorFactory().executeAST(bodyBlock, context);
		
		scope.leaveLocal(this);
		
	}

	@Override
	public AST getSuperRulesAst() {
		return AstUtil.getChild(this, EmlParser.EXTENDS);
	}

	public List<?> getModuleElements() {
		return Collections.emptyList();
	}
	
}
