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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.epsilon.evl.CommandLineFixer;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.EvlUnsatisfiedConstraint;
import org.eclipse.epsilon.evl.IEvlModule;

public class EvlTask extends ExecutableModuleTask {
	
	protected String exportConstraintTrace;
	private boolean exportAsModel = false;
	
	public String getExportConstraintTrace() {
		return exportConstraintTrace;
	}

	public void setExportConstraintTrace(String exportConstraintTrace) {
		this.exportConstraintTrace = exportConstraintTrace;
	}

	public boolean isExportAsModel() {
		return exportAsModel;
	}

	public void setExportAsModel(boolean exportAsModel) {
		this.exportAsModel = exportAsModel;
	}

	@Override
	protected IEolExecutableModule createModule() {
		return new EvlModule();
	}

	@Override
	protected void initialize() throws Exception {
		IEvlModule evlModule = (IEvlModule) module;
		CommandLineFixer clf = new CommandLineFixer();
		clf.setFix(false);
		evlModule.setUnsatisfiedConstraintFixer(clf);
	}

	@Override
	protected void examine() throws Exception {
		IEvlModule evlModule = (IEvlModule) module;
		int errors = 0;
		int warnings = 0;
		for (EvlUnsatisfiedConstraint unsatisfiedConstraint : evlModule.getContext().getUnsatisfiedConstraints()){
			if (!unsatisfiedConstraint.getConstraint().isCritique()) {
				errors ++;
			}
			else {
				warnings ++;
			}
		}
		log("Errors : " + errors);
		
		final String message = errors + " error(s) and " + warnings + " warning(s) found during validation";
		if (errors > 0) {
			fail(message, null);
		}
		else if (warnings > 0) {
			warn(message);
		}
		
		if (exportConstraintTrace != null) {
			getProjectStackFrame().put(exportConstraintTrace, 
					evlModule.getContext().getConstraintTrace());
		}

		if (exportAsModel) {
			Collection<Object> unsatisfiedConstraints
				= new ArrayList<Object>(evlModule.getContext().getUnsatisfiedConstraints());
			Collection<Class<?>> classes = new ArrayList<Class<?>>();
			classes.add(EvlUnsatisfiedConstraint.class);
			final JavaModel model = new JavaModel(unsatisfiedConstraints, classes);
			model.setName("EVL");
			getProjectRepository().addModel(model);
		}
	}

}
