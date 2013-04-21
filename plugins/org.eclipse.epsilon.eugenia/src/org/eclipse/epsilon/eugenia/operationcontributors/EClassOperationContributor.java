package org.eclipse.epsilon.eugenia.operationcontributors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class EClassOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof EClass;
	}
	
	public EAttribute attr(String name) throws EolRuntimeException {
		for (EAttribute attr : ((EClass) target).getEAttributes()) {
			if (attr.getName().equals(name)) return attr;
		}
		throw new EolRuntimeException("Class " + ((EClass) target).getName() + " does not own an attributed named " + name);
	}
	
	public EReference ref(String name) throws EolRuntimeException {
		for (EReference ref : ((EClass) target).getEReferences()) {
			if (ref.getName().equals(name) && !ref.isContainment()) return ref;
		}
		throw new EolRuntimeException("Class " + ((EClass) target).getName() + " does not own an non-containment reference named " + name);
	}
	
	public EReference val(String name) throws EolRuntimeException {
		for (EReference ref : ((EClass) target).getEReferences()) {
			if (ref.getName().equals(name) && ref.isContainment()) return ref;
		}
		throw new EolRuntimeException("Class " + ((EClass) target).getName() + " does not own a containment reference named " + name);
	}
	
}
