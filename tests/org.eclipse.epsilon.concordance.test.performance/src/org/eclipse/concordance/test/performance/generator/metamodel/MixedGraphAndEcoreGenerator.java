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
package org.eclipse.concordance.test.performance.generator.metamodel;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.concordance.test.performance.generator.AbstractGenerator;
import org.eclipse.concordance.test.performance.generator.ecore.EcoreGenerator;
import org.eclipse.concordance.test.performance.generator.graph.GraphGenerator;

public class MixedGraphAndEcoreGenerator {

	private final Collection<File> generated = new LinkedList<File>();
	
	public Iterable<File> generate(File directory, int total, int graphPercentage) {
		generated.clear();
		
		final int numberOfGraphs = total * graphPercentage / 100;
		
		generate(new GraphGenerator(), directory, numberOfGraphs);
		generate(new EcoreGenerator(), directory, total - numberOfGraphs);
		
		return generated;
	}
	
	private void generate(AbstractGenerator generator, File directory, int number) {
		System.out.println("Generating " + number + " " + generator.getModelName() + " models.");
		generated.addAll(generator.generate(directory, generator.getModelName(), number));
	}
}
