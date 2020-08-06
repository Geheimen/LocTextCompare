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
	private JTextField inputPath;
	private JTextField outputPath;
	private JButton getOutputButton;
	private JLabel containsString;
	private JTextField textCheck;
	private Label inputRangeLabel;
	private JTextField inputRange;
	private JLabel parseInput;
	private Label outputRangeLabel;
	private JTextField outputRange;
	private JLabel parseOutput;
	private JButton compareButton;
	
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
				
				if (outputPath.getText().equals("Please select the output file")) {
					outputPath.setText(inputPath.getText());
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
		
		containsString = new JLabel("Check if contains:");
		containsString.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_containsString = new GridBagConstraints();
		gbc_containsString.insets = new Insets(0, 0, 5, 5);
		gbc_containsString.gridx = 0;
		gbc_containsString.gridy = 2;
		frmTextFormattingCompare.getContentPane().add(containsString, gbc_containsString);
		
		textCheck = new JTextField();
		textCheck.setHorizontalAlignment(SwingConstants.CENTER);
		textCheck.setText("*");
		GridBagConstraints gbc_textCheck = new GridBagConstraints();
		gbc_textCheck.insets = new Insets(0, 0, 5, 5);
		gbc_textCheck.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCheck.gridx = 1;
		gbc_textCheck.gridy = 2;
		frmTextFormattingCompare.getContentPane().add(textCheck, gbc_textCheck);
		textCheck.setColumns(10);
		
		inputRangeLabel = new Label("First text column/range/cell");
		inputRangeLabel.setAlignment(Label.CENTER);
		GridBagConstraints gbc_inputRangeLabel = new GridBagConstraints();
		gbc_inputRangeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_inputRangeLabel.gridx = 0;
		gbc_inputRangeLabel.gridy = 3;
		frmTextFormattingCompare.getContentPane().add(inputRangeLabel, gbc_inputRangeLabel);
		
		parseInput = new JLabel("");
		GridBagConstraints gbc_parseInput = new GridBagConstraints();
		gbc_parseInput.insets = new Insets(0, 0, 5, 0);
		gbc_parseInput.gridwidth = 2;
		gbc_parseInput.gridx = 3;
		gbc_parseInput.gridy = 3;
		frmTextFormattingCompare.getContentPane().add(parseInput, gbc_parseInput);
		
		inputRange = new JTextField();
		inputRange.setText("E");
		inputRange.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				parseInput.setText("Valid Input");
				parseInput.setForeground(Color.GREEN);
				
				if(RangeParser.parseRange(inputRange.getText()) == null) {
					parseInput.setText("Invalid Input");
					parseInput.setForeground(Color.RED);
				}
			}
		});
		inputRange.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_inputRange = new GridBagConstraints();
		gbc_inputRange.gridwidth = 2;
		gbc_inputRange.insets = new Insets(0, 0, 5, 5);
		gbc_inputRange.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputRange.gridx = 1;
		gbc_inputRange.gridy = 3;
		frmTextFormattingCompare.getContentPane().add(inputRange, gbc_inputRange);
		inputRange.setColumns(10);
		
		outputRangeLabel = new Label("Second text column");
		outputRangeLabel.setAlignment(Label.CENTER);
		GridBagConstraints gbc_outputRangeLabel = new GridBagConstraints();
		gbc_outputRangeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_outputRangeLabel.gridx = 0;
		gbc_outputRangeLabel.gridy = 4;
		frmTextFormattingCompare.getContentPane().add(outputRangeLabel, gbc_outputRangeLabel);
		
		outputRange = new JTextField();
		outputRange.setText("Q");
		outputRange.setHorizontalAlignment(SwingConstants.LEFT);
		outputRange.setColumns(10);
		GridBagConstraints gbc_outputRange = new GridBagConstraints();
		gbc_outputRange.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_outputRange.gridwidth = 2;
		gbc_outputRange.insets = new Insets(0, 0, 5, 5);
		gbc_outputRange.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputRange.gridx = 1;
		gbc_outputRange.gridy = 4;
		frmTextFormattingCompare.getContentPane().add(outputRange, gbc_outputRange);
		
		parseOutput = new JLabel("");
		GridBagConstraints gbc_parseOutput = new GridBagConstraints();
		gbc_parseOutput.insets = new Insets(0, 0, 5, 0);
		gbc_parseOutput.gridwidth = 2;
		gbc_parseOutput.gridx = 3;
		gbc_parseOutput.gridy = 4;
		frmTextFormattingCompare.getContentPane().add(parseOutput, gbc_parseOutput);
		
		compareButton = new JButton("Compare texts");
		compareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!inputPath.getText().equals("Please select the intput file")
							&& !outputPath.getText().equals("Please select the output file")) {
						Output.writeToFile(
								DataIterator.dataIterator(
										inputPath.getText(),
										RangeParser.parseRange(inputRange.getText()),
										RangeParser.parseRangePT(outputRange.getText()),
										textCheck.getText()
										),
								inputPath.getText(),
								outputPath.getText()
								);
					} else {
						JOptionPane.showConfirmDialog(null, "Please select an input/output path", null,
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
		gbc_compareButton.insets = new Insets(0, 0, 0, 5);
		gbc_compareButton.gridx = 0;
		gbc_compareButton.gridy = 5;
		frmTextFormattingCompare.getContentPane().add(compareButton, gbc_compareButton);
		
	}
}
