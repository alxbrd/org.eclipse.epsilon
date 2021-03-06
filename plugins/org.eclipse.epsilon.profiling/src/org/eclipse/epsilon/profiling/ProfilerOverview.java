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
package org.eclipse.epsilon.profiling;

public class ProfilerOverview {
	
	protected long executionTime;
	protected long executionCount;
	
	public long getExecutionTime() {
		return executionTime;
	}
	
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	
	public long getExecutionCount() {
		return executionCount;
	}
	
	public void setExecutionCount(long executionCount) {
		this.executionCount = executionCount;
	}
	
	public double getAverageExecutionTime() {
		if (executionCount == 0 || executionTime == 0) return 0;
		else return (double) executionTime / (double) executionCount;
	}
	
}
