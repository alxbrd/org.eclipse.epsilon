package org.eclipse.epsilon.emc.spreadsheets.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;

public class ExcelWorksheet extends SpreadsheetWorksheet
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWorksheet.class);

	protected ExcelModel model;
	protected Sheet sheet;

	public ExcelWorksheet(final ExcelModel model, final Sheet sheet, final boolean existsInSpreadsheet)
	{
		super(model, sheet.getSheetName(), existsInSpreadsheet);

		this.model = model;
		this.sheet = sheet;

		if (this.existsInSpreadsheet)
		{
			this.loadHeader();
		}
	}

	@Override
	protected void createInSpreadsheet()
	{
		this.existsInSpreadsheet = true;
		this.writeHeaderRow();
	}

	private void writeHeaderRow()
	{
		LOGGER.debug("Inside writeHeaderRow() method");
		LOGGER.debug("Header columns: " + this.getHeader().getColumns());

		final Row row = this.sheet.createRow(this.getHeaderRowIndex());
		final ExcelRow headerRow = new ExcelRow(this, row);

		for (final SpreadsheetColumn column : this.getHeader().getColumns())
		{
			if (StringUtils.isNotBlank(column.getName()))
			{
				LOGGER.debug("Writing header column with name '" + column.getName() + "'");
				row.createCell(column.getIndex());
				headerRow.overwriteCellValue(column, column.getName());
			}
		}
	}

	@Override
	protected void loadHeader()
	{
		LOGGER.debug("Inside loadHeader() method");
		super.checkThatWorksheetExists();

		if (this.sheet.getPhysicalNumberOfRows() > 0)
		{
			final Row row = this.sheet.getRow(0);
			final ExcelRow excelRow = new ExcelRow(this, row);
			final Iterator<Cell> it = row.cellIterator();
			while (it.hasNext())
			{
				final Cell headerCell = it.next();
				final ExcelColumn excelColumn = new ExcelColumn(this, headerCell.getColumnIndex());
				final String columnName = excelRow.getVisibleCellValue(excelColumn);
				LOGGER.debug("Adding column to header; name: '" + columnName + "'");
				super.addColumn(headerCell.getColumnIndex(), columnName);
			}
		}
	}

	@Override
	protected SpreadsheetColumn createColumn(final int index)
	{
		return new ExcelColumn(this, index);
	}

	@Override
	public List<SpreadsheetRow> getRows()
	{
		LOGGER.debug("Inside getRows() method");
		final List<SpreadsheetRow> rows = new ArrayList<SpreadsheetRow>();
		final int numOfRows = this.sheet.getPhysicalNumberOfRows();
		for (int i = this.getFirstRowIndex(); i <= numOfRows; i++)
		{
			final Row row = this.sheet.getRow(i);
			if (row != null)
			{
				rows.add(new ExcelRow(this, row));
			}
		}
		return rows;
	}

	@Override
	public SpreadsheetRow insertRow(final Map<SpreadsheetColumn, String> values)
	{
		LOGGER.debug("Inside insertRow() method");
		LOGGER.debug("Values: " + values);
		final int newRowIndex = this.sheet.getPhysicalNumberOfRows() + 1;
		final Row row = sheet.createRow(newRowIndex);
		for (final Map.Entry<SpreadsheetColumn, String> entry : values.entrySet())
		{
			final Cell cell = row.createCell(entry.getKey().getIndex());
			cell.setCellValue(entry.getValue());
		}

		final ExcelRow excelRow = new ExcelRow(this, row);
		LOGGER.debug("Created row: " + excelRow);
		return excelRow;
	}

	@Override
	public void removeRow(final SpreadsheetRow row)
	{
		LOGGER.debug("Inside removeRow() method");
		LOGGER.debug("Row: " + row);
		if (row != null)
		{
			final ExcelRow excelRow = (ExcelRow) row;
			final int rowIndex = excelRow.row.getRowNum();
			final int lastRowNum = this.sheet.getLastRowNum();

			this.sheet.removeRow(excelRow.row);

			if (rowIndex >= this.getFirstRowIndex() && rowIndex < lastRowNum)
			{
				sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
			}
		}
	}

	@Override
	public String getDefaultEmptyCellValue()
	{
		return "";
	}

	public int getHeaderRowIndex()
	{
		return 0;
	}

	public int getFirstRowIndex()
	{
		return 1;
	}

}
