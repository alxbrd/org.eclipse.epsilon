/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: HutnFactoryImpl.java,v 1.4 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.epsilon.hutn.model.hutn.BooleanSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.EnumSlot;
import org.eclipse.epsilon.hutn.model.hutn.FloatSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.IntegerSlot;
import org.eclipse.epsilon.hutn.model.hutn.ModelElement;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.NullSlot;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.model.hutn.StringSlot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HutnFactoryImpl extends EFactoryImpl implements HutnFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HutnFactory init() {
		try {
			HutnFactory theHutnFactory = (HutnFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/gmt/epsilon/hutn"); 
			if (theHutnFactory != null) {
				return theHutnFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HutnFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case HutnPackage.SPEC: return createSpec();
			case HutnPackage.NS_URI: return createNsUri();
			case HutnPackage.MODEL_ELEMENT: return createModelElement();
			case HutnPackage.PACKAGE_OBJECT: return createPackageObject();
			case HutnPackage.CLASS_OBJECT: return createClassObject();
			case HutnPackage.STRING_SLOT: return createStringSlot();
			case HutnPackage.BOOLEAN_SLOT: return createBooleanSlot();
			case HutnPackage.INTEGER_SLOT: return createIntegerSlot();
			case HutnPackage.FLOAT_SLOT: return createFloatSlot();
			case HutnPackage.NULL_SLOT: return createNullSlot();
			case HutnPackage.CONTAINMENT_SLOT: return createContainmentSlot();
			case HutnPackage.REFERENCE_SLOT: return createReferenceSlot();
			case HutnPackage.ENUM_SLOT: return createEnumSlot();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Spec createSpec() {
		SpecImpl spec = new SpecImpl();
		return spec;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NsUri createNsUri() {
		NsUriImpl nsUri = new NsUriImpl();
		return nsUri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelElement createModelElement() {
		ModelElementImpl modelElement = new ModelElementImpl();
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageObject createPackageObject() {
		PackageObjectImpl packageObject = new PackageObjectImpl();
		return packageObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassObject createClassObject() {
		ClassObjectImpl classObject = new ClassObjectImpl();
		return classObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringSlot createStringSlot() {
		StringSlotImpl stringSlot = new StringSlotImpl();
		return stringSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanSlot createBooleanSlot() {
		BooleanSlotImpl booleanSlot = new BooleanSlotImpl();
		return booleanSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerSlot createIntegerSlot() {
		IntegerSlotImpl integerSlot = new IntegerSlotImpl();
		return integerSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatSlot createFloatSlot() {
		FloatSlotImpl floatSlot = new FloatSlotImpl();
		return floatSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullSlot createNullSlot() {
		NullSlotImpl nullSlot = new NullSlotImpl();
		return nullSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContainmentSlot createContainmentSlot() {
		ContainmentSlotImpl containmentSlot = new ContainmentSlotImpl();
		return containmentSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceSlot createReferenceSlot() {
		ReferenceSlotImpl referenceSlot = new ReferenceSlotImpl();
		return referenceSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumSlot createEnumSlot() {
		EnumSlotImpl enumSlot = new EnumSlotImpl();
		return enumSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnPackage getHutnPackage() {
		return (HutnPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HutnPackage getPackage() {
		return HutnPackage.eINSTANCE;
	}

} //HutnFactoryImpl
