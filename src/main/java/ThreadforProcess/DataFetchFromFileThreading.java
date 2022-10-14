package ThreadforProcess;

import java.util.HashMap;

import pojo.DataFileInfo;
import process.InputDataProcessMethod;
//This class is use to create threading for fetch the data of each filename and store the result
public class DataFetchFromFileThreading implements Runnable {
	String filename;
	public String extension;
	HashMap<String,DataFileInfo> data;
	public String output;
	
	public DataFetchFromFileThreading(String filename,HashMap<String,DataFileInfo> data) {
		this.filename=filename;
		String extension="."+filename.split("\\.")[1];
		this.extension=extension.toUpperCase();
		this.data=data;
	}
	
	@Override
	public void run() {
		this.output=InputDataProcessMethod.returnOutput(this.filename,this.extension, this.data);
		
	}



}
