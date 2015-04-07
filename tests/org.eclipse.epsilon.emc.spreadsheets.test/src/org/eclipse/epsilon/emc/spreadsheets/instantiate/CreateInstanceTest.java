package org.eclipse.epsilon.emc.spreadsheets.instantiate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateInstanceTest
{
	private static SpreadsheetModel model = null;

	public CreateInstanceTest(SpreadsheetModel model)
	{
		CreateInstanceTest.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception
	{
		String pathToFile = "resources/instantiate/CreateInstanceTest.xlsx";
		String pathToConfig = "resources/instantiate/CreateInstanceTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "CreateInstanceTest");
	}

	@Test
	public void testWriteCollectionOfMap() throws Exception
	{
		String worksheetName = "Sheet4";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		List<Object> list = new ArrayList<Object>();
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("c_1", "string1");
		list.add(values);
		model.createInstance(worksheetName, list);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(CollectionUtils.isNotEmpty(rows));
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
	}

	@Test
	public void testWriteEmptyCollection() throws Exception
	{
		String worksheetName = "Sheet4";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		List<Object> list = new ArrayList<Object>();
		model.createInstance(worksheetName, list);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(CollectionUtils.isNotEmpty(rows));
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals(" "));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
	}

	@Test
	public void testWriteCollectionOfSet() throws Exception
	{
		String worksheetName = "Sheet4";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		List<Object> list = new ArrayList<Object>();
		Set<String> values = new HashSet<String>();
		values.add("string1");
		list.add(values);
		try
		{
			model.createInstance(worksheetName, list);
			fail("Should not be possible to write collection of non-Map objects");
		}
		catch (IllegalArgumentException e)
		{
			assertTrue(true);
		}
		assertTrue(model.getAllOfType(worksheetName).size() == 0);
	}

	@Test
	public void testInstantiateUnknownWorksheet() throws Exception
	{
		String worksheetName = "UNKNOWN";

		List<Object> list = new ArrayList<Object>();
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("c_1", "string1");
		list.add(values);
		try
		{
			model.createInstance(worksheetName, list);
			fail("Unspecified worksheet");
		}
		catch (EolModelElementTypeNotFoundException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void testInstantiateToUnknownColumn() throws Exception
	{
		String worksheetName = "Sheet1";

		List<Object> list = new ArrayList<Object>();
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("c_10", "string1");
		list.add(values);
		try
		{
			model.createInstance(worksheetName, list);
			fail("Unspecified column");
		}
		catch (RuntimeException e)
		{
			assertTrue(true);
		}
	}

}
