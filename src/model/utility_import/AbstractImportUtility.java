package model.utility_import;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Vector;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import model.database.AbstractDatabase;
import model.utility.FileManager;

public abstract class AbstractImportUtility {

	private final static String CSV_EXTENSION = "csv";
	private File myFile;
	protected String[] myHeader;
	protected int headerLinePosition;
	protected CSVParser parser;
	protected List<CSVRecord> recordList;
	protected CSVFormat myCSVFormat;
	protected AbstractDatabase myAbstractDatabase;

	/**
	 * This method is used to get data from a {@code csv} file.
	 * 
	 * @throws Exception
	 */
	public long getData() throws Exception {

		// Loading abstract database object...
		this.myAbstractDatabase = new AbstractDatabase() {

			@Override
			public void createTable() {
			}
		};

		this.myFile = FileManager.getFile(CSV_EXTENSION);

		// Excel file format (using a comma as the value delimiter)...
		this.myCSVFormat = CSVFormat.EXCEL.withHeader(myHeader);
		this.parser = myCSVFormat.parse(new FileReader(myFile.getAbsolutePath()));
		this.recordList = this.parser.getRecords();

		// Test input file
		Vector<String> myReadHeader = readData(headerLinePosition);
		for (int i = 0; i < myHeader.length; i++)
			if(!myHeader[i].equals(myReadHeader.get(i)))
				throw new Exception("Invalid source file");
		
		// Get start time...
		long startTime = System.currentTimeMillis();
		
		ImportData();
		return startTime;
	}

	/**
	 * This method is used to read a specified line from a CSV file.
	 * 
	 * @param line
	 *            - Represents an {@code int}.
	 * @return A {@code Vector} object.
	 */
	public Vector<String> readData(int line) throws Exception {
		
		try {
			CSVRecord record = this.recordList.get(line);
			Vector<String> output = new Vector<>();
			
			// Reading line...
			for (String field : myHeader) {
				String var0 = record.get(field);
				System.out.println("Read " + field + ": " + var0);
				output.add(var0);
			}
			
			return output;
		} catch (Exception e) {
			throw new Exception("Invalid source file");
		}
	}

	/**
	 * This method is used to import data from a {@code List<CSVRecord>} object.
	 */
	protected abstract void ImportData() throws Exception;
}
