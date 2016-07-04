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
package org.eclipse.epsilon.egl.output;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.exceptions.EglStoppedException;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.internal.EglPreprocessorModule;
import org.eclipse.epsilon.egl.merge.output.RegionType;
import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.preprocessor.Preprocessor;
import org.eclipse.epsilon.egl.status.Warning;
import org.eclipse.epsilon.egl.util.FileUtil;

public class OutputBuffer implements IOutputBuffer {
	
	protected StringBuilder buffer = new StringBuilder();
	protected IEglContext context;

	protected LineCounter lineCounter   = new LineCounter(FileUtil.NEWLINE);
	protected ColumnCounter columnCounter = new ColumnCounter(FileUtil.NEWLINE);
	
	protected List<CommentBlockPartitioner> customPartitioners = new LinkedList<CommentBlockPartitioner>();
	protected boolean contentTypeSet = false;
	protected String lastLine = null;
	
	protected boolean hasProtectedRegions = false;
	protected boolean hasControlledRegions = false;
	
	public OutputBuffer(IEglContext context) {
		this(context, null);
	}
	
	public OutputBuffer() {
		this(null, null);
	}
	
	// For unit tests
	OutputBuffer(IEglContext context, String contents) {
		this.context = context;
		if (contents != null) buffer.append(contents);
	}
	
	@Override
	public void chop(int chars){
		int limit = Math.min(chars, buffer.length());
		for (int i=0;i<limit;i++){
			buffer.deleteCharAt(buffer.length()-1);
		}
	}
	
	@Override
	public void print(Object o) {
		buffer.append(o == null ? "null" : o.toString());
	}
	
	@Override
	public void printdyn(Object o) {
		final String indentation = calculateIndentationToMatch(getLastLineInBuffer());
		final String[] lines = StringUtil.toString(o).split(FileUtil.NEWLINE);
		
		for (int i=0;i<lines.length;i++) {
			if (i == 0) {
				// Any text before the first newline character should not be
				// placed on a newline nor indented  
				buffer.append(lines[i]);
			} else {
				buffer.append(FileUtil.NEWLINE + indentation + lines[i]);
			}
		}
	}
	
	/**
	 * An alias for {@link #print(Object)} that should only be called
	 * internally, by code generated by the {@link Preprocessor}. This
	 * is a workaround to allow us to identify, in the preprocessed EOL, 
	 * statements that were generated from an EGL static section. We then
	 * adjust these ASTs to allow better traceability in the AST outline 
	 * view.
	 * 
	 * @see EglPreprocessorModule#updateRegionsOfStaticTextASTs
	 */
	@Override
	public void prinx(Object o) {
		print(o);
	}

	protected String getLastLineInBuffer() {
		final int indexOfLastLine = buffer.lastIndexOf("\n");
		
		if (indexOfLastLine == -1) {
			return buffer.substring(0, buffer.length());
		}
		else {
			return buffer.substring(indexOfLastLine + 1, buffer.length());
		}
	}
	
	protected String calculateIndentationToMatch(String previousLine) {
		final StringBuilder builder = new StringBuilder();
		
		for (char c : previousLine.toCharArray()) {
			if (Character.isWhitespace(c)) {
				builder.append(c);
			}
			else {
				builder.append(' ');
			}
		}
		
		return builder.toString();
	}
	
	
	@Override
	public String getSpaces(int howMany) {
		String str = "";
		for (int i=0;i<howMany;i++) {
			str += " ";
		}
		return str;
	}
	
	@Override
	public void println(){
		buffer.append(FileUtil.NEWLINE);
	}
	
	@Override
	public void println(Object o) {
		print(o);
		println();
	}
	
	@Override
	public String preserve(String id,
	                       boolean enabled,
	                       String contents) throws EglRuntimeException {

		return startPreserve(id, enabled) + FileUtil.NEWLINE +
		       contents + FileUtil.NEWLINE +
		       stopPreserve();
	}
	
	@Override
	public String control(String id,
	                       boolean enabled,
	                       String contents) throws EglRuntimeException {

		return startControl(id, enabled) + FileUtil.NEWLINE +
		       contents + FileUtil.NEWLINE +
		       stopPreserve();
	}
	
	@Override
	public String preserve(String startComment,
	                       String endComment,
	                       String id,
	                       boolean enabled,
	                       String contents) throws EglRuntimeException {
		
		return startPreserve(startComment, endComment, id, enabled) + FileUtil.NEWLINE +
		       contents + FileUtil.NEWLINE +
		       stopPreserve();
	}
	
	@Override
	public String control(String startComment,
	                       String endComment,
	                       String id,
	                       boolean enabled,
	                       String contents) throws EglRuntimeException {
		
		return startControl(startComment, endComment, id, enabled) + FileUtil.NEWLINE +
		       contents + FileUtil.NEWLINE +
		       stopPreserve();
	}
	
	
	@Override
	public void setContentType(String name) throws EglRuntimeException {
		if (contentTypeSet) {
			context.addStatusMessage(new Warning("Cannot set content type to '" + name + "' - content type already specified."));
		} else {
			if (!context.usePartitionerFor(name)) {
				throw new EglRuntimeException("'" + name + "' is not a recognised content type.", context.getModule());
			}
			
			for (CommentBlockPartitioner customPartitioner : customPartitioners) {
				context.getPartitioner().addPartitioner(customPartitioner);
			}
			
			contentTypeSet = true;
		}
	}
	
	@Override
	public String startPreserve(String id, boolean enabled) throws EglRuntimeException {
		return startLocate(id, enabled, RegionType.Protected);
	}
	
	@Override
	public String startControl(String id, boolean enabled) throws EglRuntimeException {
		return startLocate(id, enabled, RegionType.Controlled);
	}
	
	public String startLocate(String id, boolean enabled, RegionType regionType) throws EglRuntimeException {
		
		assertNoMixedRegions(regionType);
		
		if (lastLine != null)
			throw new EglRuntimeException("The current region must be stopped before another region may begin.", context.getModule());
		
		if (context.getPartitioner().getDefaultPartitioner() == null)
			throw new EglRuntimeException("A content type must be specified before using startPreserve(id, enabled).", context.getModule());
		
		lastLine = context.getPartitioner().getDefaultPartitioner().getLastLine(id, regionType);
		
		return context.getPartitioner().getDefaultPartitioner().getFirstLine(id, enabled, regionType);	
	}
	
	@Override
	public String startControl(String startComment, String endComment,
			String id, boolean enabled) throws EglRuntimeException {
		return startLocate(startComment, endComment, id, enabled, RegionType.Controlled);
	}
	
	@Override
	public String startPreserve(String startComment, String endComment,
			String id, boolean enabled) throws EglRuntimeException {
		return startLocate(startComment, endComment, id, enabled, RegionType.Protected);
	}
	
	public String startLocate(String startComment,
	                            String endComment,
	                            String id,
	                            boolean enabled, RegionType regionType) throws EglRuntimeException {
		
		assertNoMixedRegions(regionType);
		
		if (lastLine != null)
			throw new EglRuntimeException("The current region must be stopped before another region may begin.", context.getModule());
		
		final CommentBlockPartitioner customPartitioner = new CommentBlockPartitioner(startComment, endComment);
		lastLine = customPartitioner.getLastLine(id, regionType);
		
		context.getPartitioner().addPartitioner(customPartitioner);
		customPartitioners.add(customPartitioner);
		
		return customPartitioner.getFirstLine(id, enabled, regionType);	
	}
	
	protected void assertNoMixedRegions(RegionType regionType) throws EglRuntimeException {
		if (regionType == RegionType.Controlled) hasControlledRegions = true;
		else if (regionType == RegionType.Protected) hasProtectedRegions = true;
		
		if (hasControlledRegions && hasProtectedRegions) {
			throw new EglRuntimeException("Templates cannot contain both protected and controlled regions", context.getModule());
		}
	}
	
	@Override
	public String stopControl() throws EglRuntimeException {
		return stopLocate();
	}
	
	@Override
	public String stopPreserve() throws EglRuntimeException {
		return stopLocate();
	}
	
	public String stopLocate() throws EglRuntimeException {
		if (lastLine == null)
			throw new EglRuntimeException("There is no current region to stop.", context.getModule());

		final String result = lastLine;
		lastLine = null;
		
		return result;
	}
	
	@Override
	public void stop() throws EglStoppedException {
		throw new EglStoppedException(context.getModule());
	}
	
	@Override
	public int getCurrentLineNumber() {
		return lineCounter.getCurrentLineNumberFor(this.buffer.toString());
	}
	
	@Override
	public int getCurrentColumnNumber() {
		return columnCounter.getCurrentColumnNumberFrom(this.buffer.toString());
	}

	@Override
	public int getOffset() {
		return buffer.toString().length();
	}
	
	@Override
	public void formatWith(Formatter formatter) {
		replaceContentsWith(formatter.format(buffer.toString()));
	}

	protected void replaceContentsWith(String newContents) {
		buffer.setLength(0);
		buffer.append(newContents);
	}
	
	@Override
	public String toString(){
		return buffer.toString();
	}
}
