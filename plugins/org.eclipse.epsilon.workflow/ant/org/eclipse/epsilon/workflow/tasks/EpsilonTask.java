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
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.workflow.tasks.transactions.NamedTransactionSupport;

public abstract class EpsilonTask extends Task {
	
	static final String EPSILON_REPOSITORY = "epsilon.modelRepository";
	static final String EPSILON_EXTENDEDPROPERTIES = "epsilon.extendedProperties";
	static final String EPSILON_FRAME = "epsilon.frame";
	static final String EPSILON_ACTIVETRANSACTIONS = "epsilon.activeTransactions";
	protected boolean profile;
	protected boolean failOnErrors = true;
	protected boolean failOnWarnings = false;
	
	@Override
	public final void execute() throws BuildException {
		try {
			executeImpl();
		}
		catch (BuildException ex) {
			for (IModel model : getProjectRepository().getModels()) {
				model.setStoredOnDisposal(false);
			}
			getProjectRepository().dispose();
			throw ex;
		}
	}
	
	public abstract void executeImpl() throws BuildException;
	
	protected void fail(String message) throws BuildException {
		if (failOnErrors) {
			getProjectRepository().dispose();
			throw new BuildException(message);
		}
		else {
			log(message, Project.MSG_ERR);
		}
	}
	
	protected void warn(String message) throws BuildException {
		if (failOnWarnings) {
			getProjectRepository().dispose();
			throw new BuildException(message);
		}
		else {
			log(message, Project.MSG_WARN);
		}		
	}
	
	protected Map<Object, Map<String, Object>> getExtendedProperties() {
		
		Map<Object, Map<String, Object>> extendedProperties = 
			(Map<Object, Map<String, Object>>) getProject().getReference(EpsilonTask.EPSILON_EXTENDEDPROPERTIES);
		
		if (extendedProperties == null) {
			extendedProperties = new HashMap<Object, Map<String, Object>>();
			getProject().addReference(EpsilonTask.EPSILON_EXTENDEDPROPERTIES, extendedProperties);
		}
		
		return extendedProperties;
	}

	
	protected ModelRepository getProjectRepository() {
		
		ModelRepository repository = 
			(ModelRepository) getProject().getReference(EpsilonTask.EPSILON_REPOSITORY);
		
		if (repository == null) {
			repository = new ModelRepository();
			getProject().addReference(EpsilonTask.EPSILON_REPOSITORY, repository);
		}
		
		return repository;
	
	}

	protected List<NamedTransactionSupport> getActiveTransactions() {
		
		List<NamedTransactionSupport> activeTransactions = 
			(List<NamedTransactionSupport>) getProject().getReference(EpsilonTask.EPSILON_ACTIVETRANSACTIONS);
		
		if (activeTransactions == null) {
			activeTransactions = new ArrayList<NamedTransactionSupport>();
			getProject().addReference(EpsilonTask.EPSILON_ACTIVETRANSACTIONS, activeTransactions);
		}
		
		return activeTransactions;
	
	}
	
	protected Frame getProjectStackFrame() {
		
		Frame frame = (Frame) getProject().getReference(EpsilonTask.EPSILON_FRAME);
		
		if (frame == null) {
			frame = new Frame(FrameType.PROTECTED, null);
			getProject().addReference(EpsilonTask.EPSILON_FRAME, frame);
		}
		
		return frame;
	}
	
	protected File getBaseDir() {
		return new File(getProject().getBaseDir(),"\\");
	}
	
	protected File getFile(String path) {
		File file = new File(path);
		if (!file.isAbsolute()) {
			file = new File(getBaseDir(), path);
		}
		return file;
	}
	
	public boolean isProfile() {
		return profile;
	}

	public void setProfile(boolean profile) {
		this.profile = profile;
	}
	
	public boolean isFailOnErrors() {
		return failOnErrors;
	}

	public void setFailOnErrors(boolean failOnErrors) {
		this.failOnErrors = failOnErrors;
	}

	public boolean isFailOnWarnings() {
		return failOnWarnings;
	}

	public void setFailOnWarnings(boolean failOnWarnings) {
		this.failOnWarnings = failOnWarnings;
	}
}
