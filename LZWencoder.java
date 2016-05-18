/*
 * Ragavendran Balaji
 * --------------------------------------------------------------------------------------------------------------
 * 
 * ITCS 6114 : ALGORITHMS AND DATA STRUCTURE *
 * NAME:	Ragavendran Balaji
 * ID:		800853917
 * EMAIL:	rbalaji1@uncc.edu
 * 
 * --------------------------------------------------------------------------------------------------------------
 * PROJECT1 - LZW encoding and decoding
 * 
 * 
 */

// LZWencoder.java
import java.io.DataOutputStream;		// for writing output in bytes
import java.io.File;					// for file operations
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;				// for writing output to file
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;				//arraylist functionalities
import java.util.Scanner;

public class LZWencoder {
	
	private ArrayList<String> table;		// arraylist "table" for storing all symbols 
	private double MAX;						// max size of the table using input N
	private ArrayList<Integer> output;				// "output" arraylist for the output code after encoding 
	
	
	
	// SETTERS AND GETTERS FOR THE PRIVATE VARIABLES
	public ArrayList<String> getTable() {
		return table;
	}



	public void setTable(ArrayList<String> table) {
		this.table = table;
	}



	public double getMAX() {
		return MAX;
	}



	public void setMAX(double mAX) {
		MAX = mAX;
	}



	public ArrayList<Integer> getOutput() {
		return output;
	}



	public void setOutput(ArrayList<Integer> output) {
		this.output = output;
	}



	// CONSTRUCTOR THAT INITIALIZES THE TABLE WITH 256 CHARACTERS OF THE ASCII TABLE
	LZWencoder(String max){
		
		double x = Double.parseDouble(max);
		
		this.MAX = Math.pow(2,x);			// maximum size of the table 2^N
		this.output = new ArrayList<Integer>();
		this.table = new ArrayList<String>();
		char c;
		String s;
		for(int i=0; i<256; i++){		// initializing the table with tha ascii table
			c = (char)i;
			s = c + "";					// converting the character to a string
			this.table.add(s);
		}
		
	}

	
	// MAIN METHOD WHERE ENCODING IS DONE BY READING A FILE AND BIT LENGTH SPECIFIED IN THE ARGUMENTS
	public static void main(String[] args){
		
		LZWencoder lzw = new LZWencoder(args[1]);		// creating an object of LZWencoder thus invoking the constructor and initializing the table
		ArrayList<String> code = new ArrayList<String>();
		char c;
		
		String s = new String();
		String cc = new String();
		
		// try catch to handle exception generated by SCANNER
		try {
			
			Scanner in = new Scanner(new File(args[0])); // reading the file from argument
			
			while(in.hasNextLine()){				// in variable reads each line seperately
				String st = in.nextLine();
				for(int i=0; i<st.length(); i++){	// read individual characters from the string and store it in an arraylist "code"
					c = st.charAt(i);
					cc = c + "";
					code.add(cc);
				}
                if(in.hasNextLine())
				code.add("\n");		//to include new line in encoding.
							
			}
			
			
		// ENCODING ALGORITHM	
		for(int i=0; i<code.size(); i++){	// itterate through every code available in the arraylist
				
				if(lzw.table.contains(s + code.get(i))){	// check if the symbol is present in the "table"
					s = s + code.get(i);					// append the string
					System.out.println(s);
				}
				
				else{
					
					lzw.output.add(lzw.table.indexOf(s));	// if not present the code for the previous string is added to the "output"
					
					if(lzw.table.size() < lzw.MAX){			// add the new string to the table if the size is less than the maximum size
						lzw.table.add(s + code.get(i));
					}
					
					s = code.get(i);			// set the string as the new code
					
				}  
				
			}
		
			lzw.output.add(lzw.table.indexOf(s));		// add the code of the final string to the "output"
			
			
			// PRINTING THE OUTPUT ON THE SCREEN
			System.out.println("----------");
			for(int i=0; i<lzw.output.size(); i++){
				
				System.out.println(lzw.output.get(i));
			}

			
		} catch (FileNotFoundException e) {
			
			// If the file is not found the catch block will execute
			System.out.println("----FILE NOT FOUND----");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// try catch block to handle exceptions generated by DataOutputStream		
		FileOutputStream file;
		String path = args[0];
		path = path.substring(0, path.lastIndexOf("."));	// remove the extension of the file from the arguments
		try {
			
			file = new FileOutputStream(path + ".lzw");		// creating a file with extension ".lzw"
			DataOutputStream dos = new DataOutputStream(file);	// creating DataOutputStream object to write in bytes
			
			for(int i=0; i<lzw.output.size(); i++){
		
					dos.writeShort(lzw.output.get(i));		// write two bytes form for each code in the output
					
			}
			dos.flush();
			dos.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

