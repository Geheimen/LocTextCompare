package textcompare.io;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;

import textcompare.CellPair;

public class Output {
	
	public static String getOutputPath() {
	    FileDialog dialog = new FileDialog((Frame)null, "Select File to Write to");
	    dialog.setMode(FileDialog.LOAD);
	    dialog.setVisible(true);
	    //Get the full file path
	    String file = dialog.getDirectory()+dialog.getFile();
	    return file;
	}
		
	public static void writeToFile(HashMap<CellPair, DataLocation> textProblematic, String inputPath, String outputPath) throws EncryptedDocumentException, IOException {
		OutputStream output = null; //Default option overwrites the original file
		
        try {
		
        	// Copy all data from input
        	FileInputStream inputStream = new FileInputStream(new File(inputPath));
        	final Workbook workbook = WorkbookFactory.create(inputStream);
		
        	//Creating new fill style to highlight the cells
        	final CellStyle fillStyle = workbook.createCellStyle();
        	fillStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        	fillStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//			fillStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		
        	//We are only interested in the location of each data,
        	//so we don't need to look at the HashMap's keys, only their values
        	Collection<DataLocation> DataLocationSet = textProblematic.values();
		
        	//Changing the fill color of the affected cells		
        	for (DataLocation sheet: DataLocationSet) {
        		Sheet currentSheet = workbook.getSheet(sheet.getSheet());
        		Row row = CellUtil.getRow(sheet.getRow(), currentSheet);
        		//We are only interested in the PT text
        		Cell cellPT = CellUtil.getCell(row, sheet.getPTColumn());
        		cellPT.setCellStyle(fillStyle);
        	}
		
        	inputStream.close();
		
        	//Writing the output file to the disk
    		output = new FileOutputStream(outputPath);
    		workbook.write(output);
    		workbook.close();
    		output.close(); 
    		
			JOptionPane.showConfirmDialog(null, "File saved successfully", null,
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        	
        	
    		//THE CODE BELOW WAS REMOVED AFTER UI WAS INTRODUCED
    		
//        	int dialog = JOptionPane.showConfirmDialog(null,
//        			"Do you want to overwrite the input file?",
//        			null,
//        			JOptionPane.YES_NO_CANCEL_OPTION);
//        	
//        	if (dialog == JOptionPane.YES_OPTION) {
//        		output = new FileOutputStream(inputPath);
//                workbook.write(output);
//                workbook.close();
//                output.close(); 
//        		System.out.println("File overwritten successfully"); 
//        	} else if (dialog == JOptionPane.CANCEL_OPTION) {
//        		System.out.println("Operation canceled, program will be terminated");
//        		System.exit(0);
//        	} else {
////        		String outputPath = getOutputPath();
//        		output = new FileOutputStream(outputPath);
//                workbook.write(output);
//                workbook.close();
//                output.close();
//                System.out.println("File written successfully"); 
//        	}
//        	System.exit(0); //Terminating program after successfully running it
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
        
    } 
}
