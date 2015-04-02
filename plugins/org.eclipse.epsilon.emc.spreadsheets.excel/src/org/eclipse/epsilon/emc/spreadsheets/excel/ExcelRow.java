package org.eclipse.epsilon.emc.spreadsheets.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;

public class ExcelRow extends SpreadsheetRow
{
	protected ExcelWorksheet worksheet;
	protected Row row;

	public ExcelRow(final ExcelWorksheet worksheet, final Row row)
	{
		super(worksheet);
		this.worksheet = worksheet;
		this.row = row;
	}

	@Override
	public String getVisibleCellValue(final SpreadsheetColumn column)
	{
		super.validateColumn(column);

		String visibleCellValue = "";
		final Cell cell = this.row.getCell(column.getIndex());
		if (cell != null)
		{
			final FormulaEvaluator evaluator = worksheet.model.workbook.getCreationHelper().createFormulaEvaluator();
			final CellValue cellValue = evaluator.evaluate(cell);
			if (cellValue != null) {
				switch (cellValue.getCellType())
				{
				case Cell.CELL_TYPE_NUMERIC:
					visibleCellValue += cell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_STRING:
					visibleCellValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					visibleCellValue += cell.getBooleanCellValue();
					break;
				}
			}
			else {
				visibleCellValue += cell.getStringCellValue();
			}
		}
		return visibleCellValue;
	}

	@Override
	public void overwriteCellValue(final SpreadsheetColumn column, final String value)
	{
		super.validateColumn(column);
		final Cell cell = this.row.getCell(column.getIndex());
		cell.setCellValue(value);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + row.getRowNum();
		result = prime * result + ((worksheet == null) ? 0 : worksheet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ExcelRow other = (ExcelRow) obj;
		if (row.getRowNum() != other.row.getRowNum())
		{
			return false;
		}
		if (worksheet == null)
		{
			if (other.worksheet != null)
			{
				return false;
			}
		}
		else if (!worksheet.equals(other.worksheet))
		{
			return false;
		}
		return true;
	}

}
