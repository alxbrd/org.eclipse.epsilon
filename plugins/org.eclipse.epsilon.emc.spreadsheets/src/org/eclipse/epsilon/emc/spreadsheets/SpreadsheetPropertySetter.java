package org.eclipse.epsilon.emc.spreadsheets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;

/**
 * This class allows setting values of spreadsheet row cells.
 * 
 * @author Martins Francis
 */
public class SpreadsheetPropertySetter extends JavaPropertySetter
{
	protected final SpreadsheetModel model;

	public SpreadsheetPropertySetter(final SpreadsheetModel model)
	{
		this.model = model;
	}

	@Override
	public void invoke(final Object value) throws EolRuntimeException
	{
		if (object instanceof Collection<?>)
		{
			this.edit((Collection<?>) object, value);
		}
		else if (object instanceof SpreadsheetRow)
		{
			this.edit((SpreadsheetRow) object, value);
		}
		else
		{
			super.invoke(value);
		}
	}

	/**
	 * Convenience method for editing a row and column.
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws EolRuntimeException
	 */
	public void invoke(final SpreadsheetRow row, final SpreadsheetColumn column, final Object value)
			throws EolRuntimeException
	{
		super.setObject(row);
		super.setProperty(column.getIdentifier());
		this.invoke(value);
	}

	public void edit(final Collection<?> rows, final Object value) throws EolRuntimeException
	{
		for (final Object row : rows)
		{
			final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(this.model);
			setter.setObject(row);
			setter.setProperty(property);
			setter.invoke(value);
		}
	}

	public void edit(final SpreadsheetRow row, final Object value) throws EolRuntimeException
	{
		final SpreadsheetColumn column = row.getColumn(property);
		if (column == null)
		{
			throw new EolIllegalPropertyException(object, property, ast, context);
		}

		final boolean columnIsReferencing = CollectionUtils.isNotEmpty(row.getReferencesBySource(column));
		final boolean columnIsReferenced = CollectionUtils.isNotEmpty(row.getReferencesByTarget(column));
		if (columnIsReferencing)
		{
			this.editReferencingCell(row, column, value);
		}
		else if (columnIsReferenced)
		{
			this.editReferencedCell(row, column, value);
		}
		else
		{
			this.editPlainCell(row, column, value);
		}
	}

	public void editReferencingCell(final SpreadsheetRow row, final SpreadsheetColumn column, final Object value)
	{
		final List<SpreadsheetRow> referencedRows = SpreadsheetUtils.extractAllRowsFromObject(value);
		if (referencedRows.isEmpty())
		{
			final String message = "Referencing cell can be edited by passing one row or collection of rows";
			throw new IllegalArgumentException(message);
		}

		final Set<SpreadsheetReference> references = row.getReferencesBySource(column);
		final List<String> valuesToWrite = this.getReferencedValues(referencedRows, references);

		this.editReferencingCell(row, column, valuesToWrite);
	}

	private List<String> getReferencedValues(final List<SpreadsheetRow> rows, final Set<SpreadsheetReference> references)
	{
		final List<String> values = new ArrayList<String>();
		for (final SpreadsheetRow row : rows)
		{
			boolean thisRowIsReferenced = false;
			for (final SpreadsheetReference reference : references)
			{
				thisRowIsReferenced = reference.getReferencedWorksheet() == row.getWorksheet();
				if (thisRowIsReferenced)
				{
					values.addAll(row.getAllVisibleCellValues(reference.getReferencedColumn()));
				}
			}
			if (!thisRowIsReferenced)
			{
				throw new IllegalArgumentException("Row is not referenced");
			}
		}
		return values;
	}

	private void editReferencingCell(final SpreadsheetRow row, final SpreadsheetColumn column, final List<String> values)
	{
		final boolean moreThanOneValue = column.isNotMany() && values.size() > 1;
		if (moreThanOneValue)
		{
			final String firstValue = values.iterator().next();
			values.clear();
			values.add(firstValue);
		}

		final boolean columnIsReferenced = CollectionUtils.isNotEmpty(row.getReferencesByTarget(column));
		if (columnIsReferenced)
		{
			this.editReferencedCell(row, column, values);
		}
		else
		{
			row.writeVisibleCellValues(column, values);
		}
	}

	public void editReferencedCell(final SpreadsheetRow row, final SpreadsheetColumn column, final Object value)
	{
		final List<String> currentValues = row.getAllVisibleCellValues(column);
		this.editPlainCell(row, column, value);
		final List<String> newValues = row.getAllVisibleCellValues(column);
		currentValues.removeAll(newValues);
		final List<String> removedValues = currentValues;
		final Set<SpreadsheetReference> targetReferences = row.getReferencesByTarget(column);
		for (final SpreadsheetReference reference : targetReferences)
		{
			if (reference.isCascadingUpdates())
			{
				this.cascadeChangesToReference(reference, row, column, removedValues, newValues);
			}
		}
	}

	private void cascadeChangesToReference(final SpreadsheetReference reference, final SpreadsheetRow row,
			final SpreadsheetColumn column, final List<String> removedValues, final List<String> newCellValues)
	{
		for (final String removedValue : removedValues)
		{
			final List<SpreadsheetRow> referencedRowsWithValue = row.getWorksheet().findRows(column, removedValue);
			if (CollectionUtils.isNotEmpty(referencedRowsWithValue))
			{
				continue; // another referenced row has the value thus no need to cascade it
			}

			final SpreadsheetWorksheet referencingWorksheet = reference.getReferencingWorksheet();
			final SpreadsheetColumn referencingColumn = reference.getReferencingColumn();
			final List<SpreadsheetRow> referencingRows = referencingWorksheet.findRows(referencingColumn, removedValue);
			for (final SpreadsheetRow referencingRow : referencingRows)
			{
				this.cascadeChangesToReferencingRow(referencingRow, reference, removedValue, newCellValues);
			}
		}
	}

	private void cascadeChangesToReferencingRow(final SpreadsheetRow referencingRow,
			final SpreadsheetReference reference, final String removedValue, final List<String> newValues)
	{
		final SpreadsheetColumn referencingColumn = reference.getReferencingColumn();
		final List<String> cellValues = referencingRow.getAllVisibleCellValues(referencingColumn);
		final Set<String> cellValueSet = new LinkedHashSet<String>(cellValues);
		cellValueSet.remove(removedValue);

		// Only when reference column relationship is many to not many we can be sure that the new value replaces old
		// and thus should be cascaded to referencing rows
		final boolean knowWhichValueIsReplaced = reference.getReferencedColumn().isNotMany();
		if (knowWhichValueIsReplaced)
		{
			cellValueSet.addAll(newValues);
		}
		this.editReferencingCell(referencingRow, referencingColumn, new ArrayList<String>(cellValueSet));
	}

	public void editPlainCell(final SpreadsheetRow row, final SpreadsheetColumn column, final Object newCellValues)
	{
		List<String> valuesToWrite = new ArrayList<String>();
		if (column.isMany())
		{
			if (newCellValues instanceof Collection<?>)
			{
				valuesToWrite.addAll(SpreadsheetUtils.convertObjectToList(newCellValues));
			}
			else
			{
				valuesToWrite.addAll(Arrays.asList(String.valueOf(newCellValues).split(column.getDelimiter())));
			}
		}
		else
		{
			valuesToWrite.add(SpreadsheetUtils.convertObjectToString(column, newCellValues));
		}
		row.writeVisibleCellValues(column, valuesToWrite);
	}

}
