package textcompare.io;

import java.awt.FileDialog;
import java.awt.Frame;

public class FileChooser {
	public static void main(String[] args) {
		   FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
		    dialog.setMode(FileDialog.LOAD);
		    dialog.setVisible(true);
		    String file = dialog.getFile();
		    System.out.println(file + " chosen.");
	}
	}
