package textcompare.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import textcompare.CellPair;
import textcompare.io.DataLocation;
import textcompare.io.Input;

public class DataIterator {
	public static HashMap<CellPair, DataLocation> dataIterator() {
		Input input = new Input();
		HashMap<CellPair, DataLocation> textProvided = new HashMap<>();
		//Let's create a new HashMap to store only the problematic values
		HashMap<CellPair, DataLocation> textProblematic = new HashMap<>();
		int cellsFound = 0;
		
		try {
			textProvided = input.loadFile();
			Iterator<CellPair> it = textProvided.keySet().iterator();

			while(it.hasNext()) {
				CellPair next = it.next();
				//Checking if the JP text contains a '*' character
				if (next.getJPText().contains("*")) {
					//If the JP text contains at least one '*', ALL lines in the PT text must contain a '*'
					//This was changed later, but let's assume that every '*' has a line break after it
					String[] PT = next.getPTText().split("\\n"); //Splitting at the line breaks
					for (String PTText: PT) {
						if(!PTText.contains("*")) {
							CellPair problematicCells = new CellPair(next.getJPText(), next.getPTText());
							textProblematic.put(problematicCells, textProvided.get(problematicCells));
							cellsFound++;
							break; //No need to check the other lines if at least one is problematic
							//This also prevents the cellsFound counter to count the same cell more than once
						}
					}
				}
			}
			System.out.println("Found " + cellsFound + " problematic cells.");
			return textProblematic;
//			textCompare(jp, pt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
