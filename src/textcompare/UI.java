package textcompare;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFrame;
import java.awt.Label;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.apache.poi.EncryptedDocumentException;

import textcompare.io.Output;
import textcompare.io.RangeParser;
import textcompare.logic.DataIterator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class UI {

	private JFrame frmTextFormattingCompare;
	private static JTextField inputPath;
	private static JTextField outputPath;
	private JButton getOutputButton;
	private JLabel containsStringLabel;
	private JTextField textCheck;
	private static Label firstRangeLabel;
	private JTextField firstRange;
	private static JLabel parseFirstRangeLabel;
	private static Label secondRangeLabel;
	private JTextField secondRange;
	private static JLabel parseSecondRangeLabel;
	private JButton compareButton;
	private static JLabel parseContainsLabel;
	
	//For some reason, using that method invokes the file dialog twice, so we'll copy and define the method here
	private static String getPath() {
		String inputFilePath;
		
	    FileDialog dialog = new FileDialog((Frame)null, "Select the File");
	    dialog.setFile("*.xlsx");
	    dialog.setMode(FileDialog.LOAD);
	    dialog.setVisible(true);
	    
	    //Get the full file path
	    inputFilePath = dialog.getDirectory()+dialog.getFile();
		System.out.println("Path obtained");
		return inputFilePath;
	}
	
	private static boolean pathsAreValid() {
		int valid = 0;
		String[] paths = {inputPath.getText(), outputPath.getText()};
		
		for(int i = 0; i<2; i++) {
			if (paths[i].matches("[A-Z]+:.*\\.xlsx")) valid++;
		}
		
		if(valid == 2) return true;
		
		return false;
	}
	
	private static boolean inputsAreValid() {
		int valid = 0;
		String[] inputs = {parseContainsLabel.getText(), parseFirstRangeLabel.getText(), parseSecondRangeLabel.getText()};
		
		for(int i = 0; i<3; i++) {
			if (inputs[i].equals("Valid Input")) valid++;
		}
		
		if(valid == 3) return true;
		
		return false;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frmTextFormattingCompare.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmTextFormattingCompare = new JFrame();
		frmTextFormattingCompare.setTitle("Text Formatting Compare Tool");
		frmTextFormattingCompare.setBounds(100, 100, 600, 210);
		frmTextFormattingCompare.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{160, 80, 128, 154, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmTextFormattingCompare.getContentPane().setLayout(gridBagLayout);
		
		JButton getInputButton = new JButton("Browse File");
		getInputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputPath.setText(getPath());
				
				if (!pathsAreValid()) {
					outputPath.setText(inputPath.getText());
				}
				
				if (pathsAreValid() && inputsAreValid()) {
					compareButton.setEnabled(true);
				} else {
					compareButton.setEnabled(false);
				}
			}
		});
		
		inputPath = new JTextField();
		inputPath.setHorizontalAlignment(SwingConstants.CENTER);
		inputPath.setText("Please select the input file");
		GridBagConstraints gbc_inputPath = new GridBagConstraints();
		gbc_inputPath.gridwidth = 3;
		gbc_inputPath.insets = new Insets(0, 0, 5, 5);
		gbc_inputPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputPath.gridx = 1;
		gbc_inputPath.gridy = 0;
		frmTextFormattingCompare.getContentPane().add(inputPath, gbc_inputPath);
		inputPath.setColumns(10);
		GridBagConstraints gbc_getInputButton = new GridBagConstraints();
		gbc_getInputButton.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
		gbc_getInputButton.insets = new Insets(0, 0, 5, 0);
		gbc_getInputButton.gridx = 4;
		gbc_getInputButton.gridy = 0;
		frmTextFormattingCompare.getContentPane().add(getInputButton, gbc_getInputButton);
		
		Label outputLabel = new Label("Output  File");
		outputLabel.setAlignment(Label.CENTER);
		GridBagConstraints gbc_outputLabel = new GridBagConstraints();
		gbc_outputLabel.insets = new Insets(0, 0, 5, 5);
		gbc_outputLabel.gridx = 0;
		gbc_outputLabel.gridy = 1;
		frmTextFormattingCompare.getContentPane().add(outputLabel, gbc_outputLabel);
		
		Label inputLabel = new Label("Input File");
		inputLabel.setAlignment(Label.CENTER);
		GridBagConstraints gbc_inputLabel = new GridBagConstraints();
		gbc_inputLabel.insets = new Insets(0, 0, 5, 5);
		gbc_inputLabel.gridx = 0;
		gbc_inputLabel.gridy = 0;
		frmTextFormattingCompare.getContentPane().add(inputLabel, gbc_inputLabel);
		
		getOutputButton = new JButton("Browse File");
		getOutputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputPath.setText(getPath());
				
				if(pathsAreValid() && inputsAreValid()) {
					compareButton.setEnabled(true);
				} else {
					compareButton.setEnabled(false);
				}
			}
		});
		
		outputPath = new JTextField();
		outputPath.setHorizontalAlignment(SwingConstants.CENTER);
		outputPath.setText("Please select the output file");
		GridBagConstraints gbc_outputPath = new GridBagConstraints();
		gbc_outputPath.gridwidth = 3;
		gbc_outputPath.insets = new Insets(0, 0, 5, 5);
		gbc_outputPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputPath.gridx = 1;
		gbc_outputPath.gridy = 1;
		frmTextFormattingCompare.getContentPane().add(outputPath, gbc_outputPath);
		outputPath.setColumns(10);
		GridBagConstraints gbc_getOutputButton = new GridBagConstraints();
		gbc_getOutputButton.insets = new Insets(0, 0, 5, 0);
		gbc_getOutputButton.gridx = 4;
		gbc_getOutputButton.gridy = 1;
		frmTextFormattingCompare.getContentPane().add(getOutputButton, gbc_getOutputButton);
		
		containsStringLabel = new JLabel("Check if contains:");
		containsStringLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_containsStringLabel = new GridBagConstraints();
		gbc_containsStringLabel.insets = new Insets(0, 0, 5, 5);
		gbc_containsStringLabel.gridx = 0;
		gbc_containsStringLabel.gridy = 2;
		frmTextFormattingCompare.getContentPane().add(containsStringLabel, gbc_containsStringLabel);
		
		textCheck = new JTextField();
		textCheck.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textCheck.getText().equals("")) {
					compareButton.setEnabled(false);
					parseContainsLabel.setText("Invalid Input");
					parseContainsLabel.setForeground(Color.RED);
				} else {
					parseContainsLabel.setText("Valid Input");
					parseContainsLabel.setForeground(Color.GREEN);
				}
				
				if (pathsAreValid() && inputsAreValid()) {
					compareButton.setEnabled(true);
				} else {
					compareButton.setEnabled(false);
				}
				
			}
		});
		textCheck.setHorizontalAlignment(SwingConstants.CENTER);
		textCheck.setText("*");
		GridBagConstraints gbc_textCheck = new GridBagConstraints();
		gbc_textCheck.insets = new Insets(0, 0, 5, 5);
		gbc_textCheck.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCheck.gridx = 1;
		gbc_textCheck.gridy = 2;
		frmTextFormattingCompare.getContentPane().add(textCheck, gbc_textCheck);
		textCheck.setColumns(10);
		
		parseContainsLabel = new JLabel("Valid Input");
		parseContainsLabel.setForeground(Color.GREEN);
		GridBagConstraints gbc_parseContainsLabel = new GridBagConstraints();
		gbc_parseContainsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_parseContainsLabel.gridx = 2;
		gbc_parseContainsLabel.gridy = 2;
		frmTextFormattingCompare.getContentPane().add(parseContainsLabel, gbc_parseContainsLabel);
		
		firstRangeLabel = new Label("First text column/range/cell");
		firstRangeLabel.setAlignment(Label.CENTER);
		GridBagConstraints gbc_firstRangeLabel = new GridBagConstraints();
		gbc_firstRangeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_firstRangeLabel.gridx = 0;
		gbc_firstRangeLabel.gridy = 3;
		frmTextFormattingCompare.getContentPane().add(firstRangeLabel, gbc_firstRangeLabel);
		
		parseFirstRangeLabel = new JLabel("Valid Input");
		GridBagConstraints gbc_parseFirstRangeLabel = new GridBagConstraints();
		gbc_parseFirstRangeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_parseFirstRangeLabel.gridwidth = 2;
		gbc_parseFirstRangeLabel.gridx = 3;
		gbc_parseFirstRangeLabel.gridy = 3;
		frmTextFormattingCompare.getContentPane().add(parseFirstRangeLabel, gbc_parseFirstRangeLabel);
		
		firstRange = new JTextField();
		firstRange.setText("E");
		parseFirstRangeLabel.setForeground(Color.GREEN);
		firstRange.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {				
				if(RangeParser.parseRange(firstRange.getText()) == null) {
					parseFirstRangeLabel.setText("Invalid Input");
					parseFirstRangeLabel.setForeground(Color.RED);
					compareButton.setEnabled(false);
				} else {
					parseFirstRangeLabel.setText("Valid Input");
					parseFirstRangeLabel.setForeground(Color.GREEN);
				}
				
				if (pathsAreValid() && inputsAreValid()) {
					compareButton.setEnabled(true);
				} else {
					compareButton.setEnabled(false);
				}
			}
		});
		
		firstRange.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_firstRange = new GridBagConstraints();
		gbc_firstRange.gridwidth = 2;
		gbc_firstRange.insets = new Insets(0, 0, 5, 5);
		gbc_firstRange.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstRange.gridx = 1;
		gbc_firstRange.gridy = 3;
		frmTextFormattingCompare.getContentPane().add(firstRange, gbc_firstRange);
		firstRange.setColumns(10);
		
		secondRangeLabel = new Label("Second text column");
		secondRangeLabel.setAlignment(Label.CENTER);
		GridBagConstraints gbc_secondRangeLabel = new GridBagConstraints();
		gbc_secondRangeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_secondRangeLabel.gridx = 0;
		gbc_secondRangeLabel.gridy = 4;
		frmTextFormattingCompare.getContentPane().add(secondRangeLabel, gbc_secondRangeLabel);
		
		secondRange = new JTextField();
		secondRange.setText("Q");
		secondRange.setHorizontalAlignment(SwingConstants.LEFT);
		secondRange.setColumns(10);
		GridBagConstraints gbc_secondRange = new GridBagConstraints();
		gbc_secondRange.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_secondRange.gridwidth = 2;
		gbc_secondRange.insets = new Insets(0, 0, 5, 5);
		gbc_secondRange.fill = GridBagConstraints.HORIZONTAL;
		gbc_secondRange.gridx = 1;
		gbc_secondRange.gridy = 4;
		frmTextFormattingCompare.getContentPane().add(secondRange, gbc_secondRange);
		
		parseSecondRangeLabel = new JLabel("Valid Input");
		parseSecondRangeLabel.setForeground(Color.GREEN);
		GridBagConstraints gbc_parseSecondRangeLabel = new GridBagConstraints();
		gbc_parseSecondRangeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_parseSecondRangeLabel.gridwidth = 2;
		gbc_parseSecondRangeLabel.gridx = 3;
		gbc_parseSecondRangeLabel.gridy = 4;
		frmTextFormattingCompare.getContentPane().add(parseSecondRangeLabel, gbc_parseSecondRangeLabel);
		secondRange.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {				
				if(RangeParser.parseRangePT(secondRange.getText()) == 0) {
					parseSecondRangeLabel.setText("Invalid Input");
					parseSecondRangeLabel.setForeground(Color.RED);
					compareButton.setEnabled(false);
				} else {
					parseSecondRangeLabel.setText("Valid Input");
					parseSecondRangeLabel.setForeground(Color.GREEN);
				}
				
				if (pathsAreValid() && inputsAreValid()) {
					compareButton.setEnabled(true);
				} else {
					compareButton.setEnabled(false);
				}
			}
		});
		
		compareButton = new JButton("Compare texts");
		compareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					/*There is no action listener for input and output paths,
					 *  so we need to check them before running the code
					 */
					if(pathsAreValid()) {
						Output.writeToFile(
								DataIterator.dataIterator(
										inputPath.getText(),
										RangeParser.parseRange(firstRange.getText()),
										RangeParser.parseRangePT(secondRange.getText()),
										textCheck.getText()
										),
								inputPath.getText(),
								outputPath.getText()
								);
					} else {
						JOptionPane.showConfirmDialog(null, "Please select a valid input/output path", null,
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				} catch (EncryptedDocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		GridBagConstraints gbc_compareButton = new GridBagConstraints();
		gbc_compareButton.gridwidth = 5;
		gbc_compareButton.gridx = 0;
		gbc_compareButton.gridy = 5;
		frmTextFormattingCompare.getContentPane().add(compareButton, gbc_compareButton);
		
		if(!pathsAreValid() || !inputsAreValid()) compareButton.setEnabled(false);
	}
}
