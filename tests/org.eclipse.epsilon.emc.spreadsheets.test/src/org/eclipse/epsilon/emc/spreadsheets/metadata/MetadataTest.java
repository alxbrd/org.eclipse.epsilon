package org.eclipse.epsilon.emc.spreadsheets.metadata;

import static org.junit.Assert.fail;

import java.util.Collection;

import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetColumnMetadata;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetReferenceMetadata;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetWorksheetMetadata;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.excel.ExcelModel;
import org.eclipse.epsilon.emc.spreadsheets.google.GSModel;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MetadataTest
{

	private SpreadsheetModel model = null;

	public MetadataTest(SpreadsheetModel model)
	{
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception
	{
		String pathToFile = "resources/read/ReadTest.xlsx";
		return SharedTestMethods.getModelsToTest("", pathToFile, "ReadTest");
	}

	@Test
	public void testMetadataToString()
	{
		SpreadsheetWorksheetMetadata worksheet = new SpreadsheetWorksheetMetadata();
		System.out.println(worksheet.toString());
		SpreadsheetColumnMetadata column = new SpreadsheetColumnMetadata();
		System.out.println(column.toString());
		SpreadsheetReferenceMetadata reference = new SpreadsheetReferenceMetadata();
		System.out.println(reference.toString());
	}

	@Test
	public void testMetadataWorksheetMissingName()
	{
		try
		{
			String configFile = "resources/metadata/WorksheetMissingNameTestConfig.xml";
			if (model instanceof ExcelModel)
			{
				((ExcelModel) model).setConfigurationFile(configFile);
			}
			else if (model instanceof GSModel)
			{
				((GSModel) model).setConfigurationFile(configFile);
			}
			model.load();
			fail("Expecting worksheet metadata not to be loaded as worksheet name must be set");
		}
		catch (EolModelLoadingException e)
		{
			assert (true);
		}
		catch (Exception e)
		{
			fail();
		}
	}

	@Test
	public void testMetadataColumnMissingIndexAndName()
	{
		try
		{
			String configFile = "resources/metadata/ColumnMissingNameTestConfig.xml";
			if (model instanceof ExcelModel)
			{
				((ExcelModel) model).setConfigurationFile(configFile);
			}
			else if (model instanceof GSModel)
			{
				((GSModel) model).setConfigurationFile(configFile);
			}
			model.load();
			fail("Expecting worksheet metadata not to be loaded as column index or name must be set");
		}
		catch (EolModelLoadingException e)
		{
			assert (true);
		}
		catch (Exception e)
		{
			fail();
		}
	}

	@Test
	public void testMetadataReferenceMissingSource()
	{
		try
		{
			String configFile = "resources/metadata/ReferenceMissingSourceTestConfig.xml";
			if (model instanceof ExcelModel)
			{
				((ExcelModel) model).setConfigurationFile(configFile);
			}
			else if (model instanceof GSModel)
			{
				((GSModel) model).setConfigurationFile(configFile);
			}
			model.load();
			fail("Expecting worksheet metadata not to be loaded as reference source must be set");
		}
		catch (EolModelLoadingException e)
		{
			assert (true);
		}
		catch (Exception e)
		{
			fail();
		}
	}

	@Test
	public void testMetadataReferenceMissingTarget()
	{
		try
		{
			String configFile = "resources/metadata/ReferenceMissingTargetTestConfig.xml";
			if (model instanceof ExcelModel)
			{
				((ExcelModel) model).setConfigurationFile(configFile);
			}
			else if (model instanceof GSModel)
			{
				((GSModel) model).setConfigurationFile(configFile);
			}
			model.load();
			fail("Expecting worksheet metadata not to be loaded as reference target must be set");
		}
		catch (EolModelLoadingException e)
		{
			assert (true);
		}
		catch (Exception e)
		{
			fail();
		}
	}

}
