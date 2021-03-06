/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.test.model.factories;

import org.eclipse.epsilon.hutn.test.model.families.Bike;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory;
import org.eclipse.epsilon.hutn.test.model.families.Family;
import org.eclipse.epsilon.hutn.test.model.families.Person;

public abstract class BikeFactory {

	private BikeFactory() {}
	
	public static Bike createBike() {
		return FamiliesFactory.eINSTANCE.createBike();
	}
	
	public static Bike createBike(Person rider) {
		final Bike bike = createBike();
		
		bike.setRider(rider);
		
		return bike;
	}
	
	public static Bike createBike(Family owner) {
		final Bike bike = createBike();
		
		bike.setOwner(owner);
		
		return bike;
	}
}
