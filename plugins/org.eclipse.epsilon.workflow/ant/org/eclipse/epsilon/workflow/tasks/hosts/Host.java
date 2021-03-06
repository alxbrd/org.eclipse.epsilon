/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.hosts;

import java.io.File;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.models.IModel;

public interface Host {
	
	public boolean isRunning();
	
	public void initialise();
	
	public void addNativeTypeDelegates(IEolModule module);
	
	public void addStopCapabilities(Project project, IEolModule module);

	public boolean supportsDebugging();
	
	public Object debug(IEolModule module, File file) throws Exception;
	
	public void configureUserInput(IEolModule module, boolean isGui);

	public IModel createModel(String type) throws BuildException;

	public <T> List<T> getExtensionsOfType(Class<T> klazz) throws Exception;

}
