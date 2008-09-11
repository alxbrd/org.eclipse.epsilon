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
package org.eclipse.epsilon.common.dt.launching.dialogs;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.launching.extensions.ModelTypeExtension;
import org.eclipse.epsilon.common.dt.launching.tabs.ModelTypeLabelProvider;
import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ModelTypeSelectionDialog extends TitleAreaDialog implements ISelectionChangedListener {
	
	protected ArrayList modelTypes;
	protected TableViewer modelTypesViewer;
	protected ModelTypeExtension modelType;
	
	public ModelTypeSelectionDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected void setShellStyle(int newShellStyle) {
	   super.setShellStyle(newShellStyle | SWT.RESIZE);
	}
	
	@Override
	protected Rectangle getConstrainedShellBounds(Rectangle preferredSize) {
		preferredSize.height = 350;
		return super.getConstrainedShellBounds(preferredSize);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		GridData dialogAreaData = new GridData(GridData.FILL_BOTH);
		dialogArea.setLayoutData(dialogAreaData);
		
		this.setTitle("Select type of model");
		this.setMessage("Select the type of model to add");
		this.getShell().setText("Select type of model");
		this.getShell().setImage(EpsilonCommonsPlugin.createImage("icons/model.gif"));
		
		//GridLayout controlLayout = new GridLayout(1, false);
		//control.setLayout(controlLayout);
		
		Composite control = new Composite(dialogArea, SWT.NONE);
		GridLayout controlLayout = new GridLayout(1, false);
		control.setLayout(controlLayout);
		GridData controlData = new GridData(GridData.FILL_BOTH);
		//controlData.horizontalIndent = 10;
		//controlData.verticalIndent = 10;
		control.setLayoutData(controlData);
		
		findModelTypes();
		
		modelTypesViewer = new TableViewer(control, SWT.BORDER);
		
		GridData viewerData = new GridData(GridData.FILL_BOTH);
		modelTypesViewer.getControl().setLayoutData(viewerData);
		
		//modelTypesViewer.getControl().setLayoutData(viewerData);
		
		modelTypesViewer.setContentProvider(new ListContentProvider());
		modelTypesViewer.setInput(modelTypes);
		modelTypesViewer.addSelectionChangedListener(this);
		modelTypesViewer.setLabelProvider(new ModelTypeLabelProvider());
		modelTypesViewer.getControl().addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
				ModelTypeSelectionDialog.this.modelType = (ModelTypeExtension)((IStructuredSelection)modelTypesViewer.getSelection()).getFirstElement();
				if (ModelTypeSelectionDialog.this.modelType != null) {
					ModelTypeSelectionDialog.this.close();
				}
			}

			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		modelTypesViewer.refresh();
		
		return control;
	}
	
	private void findModelTypes() {		
		modelTypes = new ArrayList();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.common.dt.modelType");
		IConfigurationElement[] configurationElements =  extensionPoint.getConfigurationElements();
		for (int i=0;i<configurationElements.length; i++){
			IConfigurationElement configurationElement = configurationElements[i];
			ModelTypeExtension modelType = new ModelTypeExtension();
			modelType.setClazz(configurationElement.getAttribute("class"));
			modelType.setType(configurationElement.getAttribute("type"));
			modelType.setLabel(configurationElement.getAttribute("label"));
			String contributingPlugin = configurationElement.getDeclaringExtension().getNamespaceIdentifier();
			Image image = AbstractUIPlugin.imageDescriptorFromPlugin(contributingPlugin,configurationElement.getAttribute("icon")).createImage();
			modelType.setImage(image);
			modelType.setConfigurationElement(configurationElement);
			modelTypes.add(modelType);
		}
		
	}

	public void selectionChanged(SelectionChangedEvent event) {
		this.modelType = (ModelTypeExtension)((IStructuredSelection)event.getSelection()).getFirstElement();
	}
	
	public ModelTypeExtension getModelType(){
		return modelType;
	}

}
