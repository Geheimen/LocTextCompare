package textcompare;
import java.io.IOException;

import textcompare.logic.DataIterator;
import textcompare.io.Output;

//import textcompare.io.input;

public class Main {
	public static void main(String[] args) throws IOException {
		//			textCompare(jp, pt);
		Output.writeToFile(DataIterator.dataIterator());
	}
}
