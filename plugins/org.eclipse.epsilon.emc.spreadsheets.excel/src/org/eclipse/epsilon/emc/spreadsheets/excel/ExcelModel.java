package org.eclipse.epsilon.emc.spreadsheets.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetWorksheetMetadata;
import org.eclipse.epsilon.emc.spreadsheets.MetadataXMLParser;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ExcelModel extends SpreadsheetModel
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelModel.class);

	/*
	 * Public identifiers used for receiving parameters from Epsilon Development Tools
	 */
	public static final String SPREADSHEET_FILE = "SPREADSHEET_FILE";
	public static final String CONFIGURATION_FILE = "CONFIGURATION_FILE";
	public static final String SPREADSHEET_PASSWORD = "SPREADSHEET_PASSWORD";

	protected Workbook workbook;

	private File spreadsheetFile;
	private File configurationFile;
	private Document configurationDoc;
	private String password;

	public ExcelModel()
	{
		super();
		this.spreadsheetFile = null;
		this.configurationFile = null;
		this.configurationDoc = null;
		this.password = null;
	}

	/*
	 * Password for test files out of box is "eps"
	 */
	public static void main(final String[] args) throws Exception
	{
		ExcelModel model = new ExcelModel();
		if (args.length == 4)
		{
			model.setSpreadsheetFile(args[0]);
			model.setConfigurationFile(args[1]);
			model.setName(args[2]);
			model.setPassword(args[3]);
			model.setStoredOnDisposal(true);
		}
		model.load();

		System.out.println("*** Executing EOL Code...");
		EolModule module = new EolModule();
		module.parse("Sheet1.all.println();");
		// module.parse("Problems.all.println(); Problems.all.reqId.println(); Problems.all.reqId.name.println();"); //
		// module.parse("Requirement.all.println(); new Requirement(Map{'c_0'='12345'});"); //
		// module.parse("var s = new Sheet1(Map{'c_0'='12345'}); s.a.println();"); //
		// module.parse("var first = Sheet1.all.first(); first.println(); new Sheet2(Map{'c_0'='hi'});");
		module.getContext().getModelRepository().addModel(model);
		module.execute();

		model.store();
	}

	public void setSpreadsheetFile(final String pathToSpreadsheet)
	{
		LOGGER.debug("Inside setSpreadsheetFile() method");
		LOGGER.debug("File path: '" + pathToSpreadsheet + "'");

		if (StringUtils.isNotEmpty(pathToSpreadsheet))
		{
			this.spreadsheetFile = new File(pathToSpreadsheet);
		}
		else
		{
			final String message = "Spreadsheet File must be provided";
			LOGGER.error(message);
			throw new IllegalArgumentException(message);
		}
	}

	public void setConfigurationFile(final String configurationFilePath) throws ParserConfigurationException,
			SAXException, IOException
	{
		if (StringUtils.isNotBlank(configurationFilePath))
		{
			this.configurationFile = new File(configurationFilePath);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			this.configurationDoc = documentBuilder.parse(this.configurationFile);
		}
	}

	/*
	 * For XLSX spreadsheets protected by password
	 */
	public void setPassword(final String password)
	{
		this.password = password;
	}

	@Override
	public void load(final StringProperties properties, final IRelativePathResolver resolver)
			throws EolModelLoadingException
	{
		super.load(properties, resolver);
		try
		{
			final String spreadsheetFilePath = properties.getProperty(ExcelModel.SPREADSHEET_FILE);
			this.setSpreadsheetFile(resolver.resolve(spreadsheetFilePath));

			final String configurationFilePath = properties.getProperty(ExcelModel.CONFIGURATION_FILE);
			if (StringUtils.isNotEmpty(configurationFilePath))
			{
				this.setConfigurationFile(resolver.resolve(configurationFilePath));
			}
			this.setPassword(properties.getProperty(ExcelModel.SPREADSHEET_PASSWORD));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage());
			throw new EolModelLoadingException(e, this);
		}
		this.load();
	}

	@Override
	protected void loadSpreadsheet() throws Exception
	{
		this.workbook = this.getWorkbook();
		for (int i = 0; i < this.workbook.getNumberOfSheets(); i++)
		{
			final Sheet sheet = this.workbook.getSheetAt(i);
			final ExcelWorksheet worksheet = new ExcelWorksheet(this, sheet, true);
			LOGGER.debug("Loaded worksheet from file: '" + worksheet.getName() + "'");
			this.addWorksheet(worksheet);
		}
	}

	private Workbook getWorkbook() throws IOException
	{
		if (this.getIsXlsxFile())
		{
			return new XSSFWorkbook(this.getFileInputStream());
		}
		else
		{
			return new HSSFWorkbook(this.getFileInputStream());
		}
	}

	private InputStream getFileInputStream() throws FileNotFoundException
	{
		if (StringUtils.isNotBlank(this.password))
		{
			if (this.getIsXlsxFile())
			{
				return this.getProtectedInputStreamForXlsx();
			}
			else
			{
				final String message = "Cannot load password protected XLS files";
				LOGGER.error(message);
				throw new UnsupportedOperationException(message);
			}
		}
		return new FileInputStream(this.spreadsheetFile);
	}

	private boolean getIsXlsxFile()
	{
		return StringUtils.endsWithIgnoreCase(this.spreadsheetFile.toString(), "xlsx");
	}

	private InputStream getProtectedInputStreamForXlsx()
	{
		try
		{
			final POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(this.spreadsheetFile));
			final EncryptionInfo encryptionInfo = new EncryptionInfo(fileSystem);
			final Decryptor decryptor = Decryptor.getInstance(encryptionInfo);
			decryptor.verifyPassword(this.password);
			return decryptor.getDataStream(fileSystem);
		}
		catch (Exception e)
		{
			final String message = "Failed to open file with the given password: " + e.getMessage();
			LOGGER.error(message);
			throw new RuntimeException(message);
		}
	}

	@Override
	protected ISpreadsheetMetadata getSpreadsheetMetadata()
	{
		return new MetadataXMLParser(this.configurationDoc);
	}

	@Override
	protected boolean isMetadataConfigurationDefined()
	{
		return this.configurationFile != null && this.configurationDoc != null;
	}

	@Override
	protected ExcelWorksheet createWorksheet(final SpreadsheetWorksheetMetadata worksheetMetadata)
	{
		final Sheet sheet = this.workbook.createSheet(worksheetMetadata.getName());
		return new ExcelWorksheet(this, sheet, false);
	}

	@Override
	public boolean store()
	{
		try
		{
			this.deleteNonexistentWorksheets();
			this.writeFile();
			this.encryptFile();
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Failed to write to workbook '" + this.name + "' " + e.getMessage());
			return false;
		}
	}

	/*
	 * Apache POI will create an actual worksheet whenever a new Sheet object is created from whatever has been defined
	 * in the configuration file when using EOL. This method removes any worksheet that has been defined in
	 * configuration file but has not actually been referenced in the program or defined to be created on load.
	 */
	private void deleteNonexistentWorksheets()
	{
		for (final SpreadsheetWorksheet worksheet : this.worksheets)
		{
			if (worksheet.getDoesNotExistInSpreadsheet())
			{
				this.deleteWorksheet((ExcelWorksheet) worksheet);
			}
		}
	}

	private void writeFile() throws IOException
	{
		this.writeFile(null);
	}

	private void writeFile(final POIFSFileSystem fileSystem) throws IOException
	{
		FileOutputStream fileOutputStream = null;
		try
		{
			fileOutputStream = new FileOutputStream(this.spreadsheetFile);
			if (fileSystem == null)
			{
				this.workbook.write(fileOutputStream);
			}
			else
			{
				fileSystem.writeFilesystem(fileOutputStream);
			}
		}
		finally
		{
			if (fileOutputStream != null)
			{
				fileOutputStream.close();
			}
		}

	}

	private void encryptFile() throws Exception
	{
		if (StringUtils.isNotBlank(this.password))
		{
			if (this.getIsXlsxFile())
			{
				final POIFSFileSystem fs = new POIFSFileSystem();
				final EncryptionInfo info = new EncryptionInfo(fs, EncryptionMode.agile);

				final Encryptor enc = info.getEncryptor();
				enc.confirmPassword(this.password);

				final OPCPackage opc = OPCPackage.open(this.spreadsheetFile, PackageAccess.READ_WRITE);
				OutputStream os = enc.getDataStream(fs);
				opc.save(os);
				opc.close();

				this.writeFile(fs);
			}
		}
	}

	@Override
	public void deleteWorksheet(final SpreadsheetWorksheet worksheet)
	{
		final int worksheetIndex = this.workbook.getSheetIndex(((ExcelWorksheet) worksheet).sheet);
		this.workbook.removeSheetAt(worksheetIndex);

	}

	@Override
	public Collection<SpreadsheetRow> find(Variable iterator, AST ast, IEolContext context) throws EolRuntimeException
	{
		throw new UnsupportedOperationException();
	}
}
