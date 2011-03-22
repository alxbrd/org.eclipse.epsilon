/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.history;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.epsilon.eol.eunit.EUnitModule;

/**
 * Class which tracks the results of all the EUnit tests run in this session.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class EUnitHistory {

	private final Map<ILaunch, List<EUnitModule>> launches = new LinkedHashMap<ILaunch, List<EUnitModule>>();
	private ILaunch currentLaunch = null;

	public void addLaunch(ILaunch launch, EUnitModule module) {
		if (launches.containsKey(launch)) {
			launches.get(launch).add(module);
		}
		else {
			final List<EUnitModule> newList = new ArrayList<EUnitModule>();
			newList.add(module);
			launches.put(launch, newList);
		}
		setCurrentLaunch(launch);
	}

	public Map<ILaunch, List<EUnitModule>> getAllLaunches() {
		return launches;
	}

	public ILaunch getCurrentLaunch() {
		return currentLaunch;
	}

	public void setCurrentLaunch(ILaunch launch) {
		currentLaunch = launch;
	}

	public List<EUnitModule> getModules(ILaunch launch) {
		return launches.get(launch);
	}
}
