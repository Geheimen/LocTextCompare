package textcompare.io;

import javax.swing.JOptionPane;

import textcompare.Letters;

public class RangeParser {
	//Returns the index of the column provided
	static int[] parseRange(String range) {
		int[] result = new int[3]; // [Index of the column, first row, last row]
		int firstRow;
		int lastRow;
		int columnIndex;
		
		//The first 'if' validates the input. If a incomplete range is provided (such as A20:),
		//only the first cell will be returned
		//What this regex says: Match the following pattern:
		// 1 - Starts with any number of letters (at least one)
		// 2 - Followed by at least one digit
		// 3 - After the digit(s), it must have a ':'
		// 4 - After the ':', it must have the exact same letter pattern as before (to ensure the input is the same column)
		// 5 - After the second letter pattern, it must have at least one digit and end with that/those digit(s)
		// If there are no matches for that pattern, try to match this other pattern:
		// 1 - Starts with at least one letter
		// 2 - Followed or not by any number of digits, with no other character after
		// Examples of input and their outputs:
		//		AA - valid input (column)
		//		AA20 - valid input (single cell)
		//		AA20: - invalid input
		//		AA20:A21 - invalid input
		//		AA20:AA2 - valid input (an additional check will be made later to ensure the last row index is higher than the first row index)
		//		A20:B21 - invalid input
			if (range.matches("^([A-Z]+)\\d*:\\1\\d+$|^[A-Z]+\\d*$")) {
			System.out.println("Valid Input (JP)");
			//There's no need to look for the column index in each case, so let's look for it and store it now
			//Remove all unnecessary characters from the valid range input and get equivalent index from enum
			//We'll use only the first set of letters for the range, since the end of the range, if present, has the same column
			columnIndex = Letters.valueOf(range.replaceAll("(\\d+:[A-Z]+\\d+)|(\\d+)", "")).getIndex() - 1; //We subtract 1 because apache POI starts the rows and columns at 0
			//What the regex above says: Match the following pattern:
			// 1 - The pattern must start with at least one digit (but not necessarily the string itself)
			// 2 - Followed by a ':'
			// 3 - Followed by at least one uppercase letter
			// 4 - After the letter(s), it must have at least one digit
			// If there are no matches for that pattern, try to match this other pattern:
			// 1 - Any and all numbers (in sequence)
			// Examples of input and their outputs:
			//		AA - no match
			//		AA20 - matches '20'
			//		AA20:AA21 - matches '20:AA21'
			//		AA20:AA2 - matches '20:AA2'
			// -----------------------------------------------------------------------------------------------
			//The next if treats the input received
			//If the input is a single letter, return -1 as row numbers so the code can find the actual range later
			//What the regex below says:
			//1 - Match strings that begin and end with at least one uppercase letter
			//2 - No other characters other than uppercase letters are present
			// Examples of input and their outputs:
			//		A - match
			//		A2 - no match
			//		A2: - no match
			//		A: - no match
			if (range.matches("^([A-Z]+)$")) {
				firstRow = -1;
				lastRow = -1;
			//Checking if input is a single cell
			//What the regex says:
			//1 - Match strings that begin with at least one uppercase letter and end with at least one digit
			//2 - No other characters other than those are present
			// Examples of input and their outputs:
			//		A - no match
			//		A2 - match
			//		A2: - no match
			//		A: - no match
			} else if (range.matches("^([A-Z]+\\d+)$")){
				//Remove all uppercase letters from the cell input and parse number as int
				firstRow = Integer.parseInt(range.replaceAll("[A-Z]*", "")) - 1;
				//If only a cell is provided, the range is from that cell to itself
				lastRow = firstRow;
			// If the string passed the initial validation, we can assume the 'else' receives a range
			} else {
				String[] copy = range.split(":"); //Produces a 2-dimensional array with [first cell in range, last cell in range]
				//Removing all letters from each cell
				firstRow = Integer.parseInt(copy[0].replaceAll("[A-Z]*", "")) - 1; //We need to subtract one because apache starts counting the rows and columns from zero
				lastRow = Integer.parseInt(copy[1].replaceAll("[A-Z]*", "")) - 1; //We need to subtract one because apache starts counting the rows and columns from zero
			}
			//Writing the results obtained if the range provided is valid
			result[0] = columnIndex;
			result[1] = firstRow;
			result[2] = lastRow;
			return result;
		}
		//If the range provided is invalid, throw an error message and stop the program
		JOptionPane.showMessageDialog(null,"No valid range or column provided", null, JOptionPane.ERROR_MESSAGE, null);
		System.exit(0);
		return null; //Only for the IDE to stop bitching about it
	}
	
	static int parseRangePT(String range) {
		//This regex only accepts a column as input
		if (range.matches("^([A-Z]+)$")) {
			System.out.println("Valid Input (PT)");
			return Letters.valueOf(range).getIndex() - 1;
		}
		JOptionPane.showMessageDialog(null,"No valid column provided", null, JOptionPane.ERROR_MESSAGE, null);
		System.exit(0);
		return 0; //Only for the IDE to stop bitching about it
	}
	
	
}
