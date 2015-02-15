package org.eclipse.epsilon.emc.emf;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.xml.XmlModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import static org.junit.Assert.*;
import org.junit.Test;

public class XmlModelTests {

	private String SCHEMA_FILE_PATH = new File("schema/ygraphml.xsd").getAbsolutePath();
	
	private String MODEL_PATH = "model/Example.graphml";
	
	@Test
	public void testRelativePath() throws Exception {
		
		XmlModel model = createXmlModel(MODEL_PATH, SCHEMA_FILE_PATH);
		Resource resource = model.getResource();
		resource.getContents().add(createRootElement(resource));
		assertTrue(model.store());
	}
	
	@Test
	public void testAbsolutePath() throws Exception {
		
		String absolutePath = new File(MODEL_PATH).getAbsolutePath();
		XmlModel model = createXmlModel(absolutePath, SCHEMA_FILE_PATH);
		Resource resource = model.getResource();
		resource.getContents().add(createRootElement(resource));
		assertTrue(model.store());
	}
	

	@Test
	public void testFileURI() throws Exception {
		
		String absolutePath = new File(MODEL_PATH).getAbsolutePath();
		XmlModel model = createXmlModel(URI.createFileURI(absolutePath).toString(), SCHEMA_FILE_PATH);
		Resource resource = model.getResource();
		resource.getContents().add(createRootElement(resource));
		assertTrue(model.store());
	}
	
	private XmlModel createXmlModel(String uri, String xsdURI) throws EolModelLoadingException {
	  	XmlModel xmlModel = new XmlModel();
		StringProperties properties = new StringProperties();
	    properties.put(XmlModel.PROPERTY_MODEL_URI, uri);
	    properties.put(XmlModel.PROPERTY_XSD_FILE, xsdURI);
	    properties.put(XmlModel.PROPERTY_READONLOAD, "false");
	    properties.put(XmlModel.PROPERTY_STOREONDISPOSAL, "true");
	    xmlModel.load(properties);
		return xmlModel;
	}
	
	
	private EObject createRootElement(Resource resource) {
		EPackage ePck = resource.getResourceSet().getPackageRegistry().getEPackage("http://www.yworks.com/xml/graphml");
		EClass rootEClass = (EClass) ePck.getEClassifier("DocumentRoot");
		return ePck.getEFactoryInstance().create(rootEClass);
	}
}
