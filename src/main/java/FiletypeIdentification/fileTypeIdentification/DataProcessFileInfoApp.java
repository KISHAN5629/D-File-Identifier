package FiletypeIdentification.fileTypeIdentification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import ThreadforProcess.DataFetchFromFileThreading;
import ThreadforProcess.DataInfoMapForThreding;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.DataFileInfo;
import process.InputDataProcessMethod;

public class DataProcessFileInfoApp {

	
	public static void main(String[] args) {
		String directory=System.getProperty("user.dir");
		//Retrive Data From result.json file and store it into "HashMap<String,DataFileInfo> data"
		 HashMap<String,DataFileInfo> data=InputDataProcessMethod.loadDataFromJson();
		 List<String> inputFiles= new ArrayList<String>(); 
		 try {
			inputFiles=InputDataProcessMethod.readDataFromInput(directory+"\\src\\main\\input\\input.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 Thread mThreads[] = new Thread[inputFiles.size()];
		 DataFetchFromFileThreading arr[]=new DataFetchFromFileThreading[inputFiles.size()];
		 //create new task for each file type to get information of each file type using multithreading
		 for(int i=0;i<arr.length;i++) {
	    		arr[i]=new DataFetchFromFileThreading(inputFiles.get(i),data);
	    		mThreads[i]=new Thread(arr[i]);
	    		mThreads[i].start();
	     }
		 
		 for (int k = 0; k < mThreads.length; k++) {
	    	    try {
					mThreads[k].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    }
		 System.out.println("doneee....");
	    
		 HashMap<String,Integer> overallResult=new HashMap<String,Integer>();
		 //count each file type present in input.csv file
	    	for(int i=0;i<arr.length;i++) {
	    		if(overallResult.containsKey(arr[i].extension)) {
	    			overallResult.replace(arr[i].extension, overallResult.get(arr[i].extension)+1);
	    		}
	    		else {
	    			overallResult.put(arr[i].extension, 1);
	    		}
	    	}
	    	
	    	//path for output file
	    	String path=directory+"\\src\\main\\outout\\output.txt";
	    	  //write each file type's informaton into output.txt file using write() of FileWriter class
	    	try {
	    	FileWriter fw=new FileWriter(path);
	    	fw.write("\n\n");
	    	fw.write("TOTAL FILES ARE : "+overallResult.toString());//Display Total number of files
	    	fw.write("\n\n");
	    	
	    	fw.write("EACH FILE DETAILS DESCRIBED BELOW:-\n");
	    	fw.write("______________________________________\n");
	      	//Display Complete description of each file type    
	    	for(int i=0;i<arr.length;i++) {
	    		if(overallResult.containsKey(arr[i].extension)) {
	    			overallResult.replace(arr[i].extension, overallResult.get(arr[i].extension)+1);
	    			fw.write(arr[i].output);

	    		}
	    		
	    	}
	    	fw.close();
	    	
	    	
	    	}
	    	catch (Exception e) {
				// TODO: handle exception
	    		e.printStackTrace();
			}
	    	
	    	
	    	
		 

 
	}

}
