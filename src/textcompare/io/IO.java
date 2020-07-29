package textcompare.io;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;

import textcompare.CellPair;

public interface IO {
	interface input {
		static String getInputPath() {
			return null;
		}
		int[] jpTextRange ();
		int ptTextColumn ();
		HashMap<CellPair, DataLocation> loadFile() throws IOException, InvalidFormatException;
	}
	
	interface output {
		String getOutputPath();
		String copyToNewFile();
	}
}
