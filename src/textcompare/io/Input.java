package textcompare.io;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JList;
import javax.swing.JOptionPane;


import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;

import textcompare.CellPair;


public class Input implements IO.input {
	public static final String XLSX_FILE_PATH = getInputPath();

	public static String getInputPath() {
		    FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
		    dialog.setMode(FileDialog.LOAD);
		    dialog.setVisible(true);
		    //Get the full file path
		    String file = dialog.getDirectory()+dialog.getFile();
		    return file;
	}

	@Override
	public int[] jpTextRange() {
		String dialog = JOptionPane.showInputDialog(null,"Please type the JP text column or range");
		int[] range = RangeParser.parseRange(dialog);
		return range;
		}

	//Reads the file and sheets needed
	public HashMap<CellPair, DataLocation> loadFile() throws IOException, InvalidFormatException {
//		final String XLSX_FILE_PATH = getInputPath();
		final int[] COLUMN_RANGE_JP = jpTextRange();
		final int COLUMN_PT = ptTextColumn();
		int columnIndexJP = COLUMN_RANGE_JP[0];
		int columnIndexPT = COLUMN_PT;
		int firstRow;
		int lastRow;
		
//		ArrayList<String> retrievedJpText = new ArrayList<String>();
		HashMap<CellPair, DataLocation> retrievedData = new HashMap<CellPair, DataLocation>();
		ArrayList<String> workValues = new ArrayList<String>();
		
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		FileInputStream inputStream = new FileInputStream(new File(XLSX_FILE_PATH));
		Workbook workbook = WorkbookFactory.create(inputStream);
		
		//Creating List of sheets since we don't know how many are there yet
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        ArrayList<String> sheetList = new ArrayList<String>();
        
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            sheetList.add(sheet.getSheetName());
        }
		
        //Converting the list of sheets to an array so we can use JList
        JList list = new JList(sheetList.toArray());
        int choice = JOptionPane.showConfirmDialog(null, list, "Pick the sheets to work on", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		//Picking the sheets to work on
        if (choice == JOptionPane.OK_OPTION) {
            workValues = (ArrayList<String>) list.getSelectedValuesList();
            System.out.println("Selected sheets = " + workValues);
        } else {
        	JOptionPane.showConfirmDialog(null, list, "Please pick a sheet to work on", JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
        }
        
		//Working on each sheet chosen previously
        
      //Initializing the textDataLocation object of the class DataLocation we'll use to store where the data is located
//        DataLocation textDataLocation = new DataLocation(null, columnIndexJP, columnIndexPT, 0);
        
      //Initializing the textData object of the class DataLocation we'll use to store where the data is located
//        CellPair textData = new CellPair("", "");
        
        // Create a DataFormatter to format and get each cell's value as String
//        DataFormatter dataFormatter = new DataFormatter();
        
		for (int i=0; i < workValues.size(); i++) {
			Sheet sheet = workbook.getSheet(workValues.get(i));
//			textDataLocation.setSheet(sheet); //Set the sheet of the JpText object to the current one
			
			if (COLUMN_RANGE_JP[1] == -1) {
			// Decide which rows to process if only a column was passed as argument (parseRange returns -1 as row values)
				firstRow = sheet.getFirstRowNum();
				lastRow = sheet.getLastRowNum();
			} else {
				firstRow = COLUMN_RANGE_JP[1];
				lastRow = COLUMN_RANGE_JP[2];
			}
	        
			//Cycling through all rows in a column
			for (int j=firstRow; j <= lastRow; j++) {
				DataLocation textDataLocation = new DataLocation(workValues.get(i), columnIndexJP, columnIndexPT, j);
				Row row = CellUtil.getRow(j, sheet); //Iterating through all rows
			    Cell cellJP = CellUtil.getCell(row, columnIndexJP); //Iterating through all cells in a column (JP)
			    Cell cellPT = CellUtil.getCell(row, columnIndexPT); //Iterating through all cells in a column (PT)
			    CellPair textData = new CellPair(cellJP.getStringCellValue(), cellPT.getStringCellValue());
//			    textDataLocation.setRow(j);
//			    textData.setJPText(dataFormatter.formatCellValue(cellJP));
//			    textData.setPTText(dataFormatter.formatCellValue(cellPT));
//			    System.out.println("JP cell = " + currentData[0]);
//			    System.out.println("PT cell = " + currentData[1]);
//			    System.out.println("textData = " + textData.getRow());
			    retrievedData.put(textData, textDataLocation);
//			    System.out.println("HashMap = " + retrievedData.values());
//			    retrievedJpText.add(dataFormatter.formatCellValue(cell));
			    //The column never changes, so we don't need to reset if every time
//				JpText.setColumn(columnIndex);
//			    System.out.println("Current Sheet = " + JpText.getSheet());
//			    System.out.println("Current Column = " + JpText.getColumn());
//			    System.out.println("Current Row = " + JpText.getRow());
			}
//			System.out.println("HashMap size = " + retrievedData.size());
//			retrievedJpText.add("#"); //Delimiter to know where a sheet ends and another begins in this list
//			textData.setRow(-1);
//		    System.out.println("Current Sheet = " + JpText.getSheet());
//		    System.out.println("Current Column = " + JpText.getColumn());
//		    System.out.println("Current Row = " + JpText.getRow());
//			System.out.println("Sanity check = " + retrievedData.containsKey(textData));
		}
//		System.out.println("Retrieved values (JpText)= " + retrievedJpText);
//		System.out.println("Retrieved values (Data) = " + retrievedData);
//		System.out.println(retrievedData);
		workbook.close(); //Closing the workbook
		inputStream.close();
		return retrievedData;
	}

	@Override
	public int ptTextColumn() {
		String dialog = JOptionPane.showInputDialog(null,"Please type the PT text column");
		int column = RangeParser.parseRangePT(dialog);
		return column;
	}
}
