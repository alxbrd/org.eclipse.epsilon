/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.egl.IEglModule;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.status.StatusMessage;
import org.eclipse.epsilon.egl.test.models.Model;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.eclipse.epsilon.egl.util.StringUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class AcceptanceTestUtil {
	
	private AcceptanceTestUtil() {}
	
	private final static IEglModule module = new EglModule();
	
	public static void test(File program, File expected, Model... models) throws IOException, EglRuntimeException, EolModelLoadingException {
		test(program, FileUtil.read(expected), models);
	}
	
	public static void test(File program, String expected, Model... models) throws IOException, EglRuntimeException, EolModelLoadingException {
		module.reset();
		
		loadModels(models);
		
		module.parse(program);
		
		final String actual = module.execute();
		
		report();
		
		assertEquals(StringUtil.normalizeNewlines(expected), StringUtil.normalizeNewlines(actual));
	}
	
	public static void run(File program, Model... models) throws EglRuntimeException, IOException, EolModelLoadingException {
		module.reset();
		
		loadModels(models);
		
		module.parse(program);
		module.execute();
		
		report();
	}
	
	private static void loadModels(Model... models) throws EolModelLoadingException {
		for (Model model : models) {
			module.getContext().getModelRepository().addModel(model.loadEmfModel());
		}
	}
	
	private static void report() {
		for (StatusMessage message : module.getContext().getStatusMessages()) {
			System.out.println(message);
		}
	}
	
	public static List<StatusMessage> getStatusMessages() {
		return module.getContext().getStatusMessages();
	}
	
	public static Template getTemplate() {
		return module.getContext().getTemplate();
	}
}
