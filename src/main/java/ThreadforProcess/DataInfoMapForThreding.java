package ThreadforProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import process.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.DataFileInfo;
import pojo.DataUrl;



public class DataInfoMapForThreding  implements Runnable {
	String category;
	public HashMap<String,DataFileInfo> data;
	public DataInfoMapForThreding(String category){
		this.category=category;
	}
	
	public void run(){  
		HashMap<String,DataFileInfo> j=new HashMap<String,DataFileInfo>();
    	List<DataUrl> d=new ArrayList<DataUrl>();
    	d.addAll(DataFetchFileInfo.getExtensionUrlPair(this.category));
    	j=DataFetchFileInfo.fetchAndStoreData(d);
    	this.data=j;
	}  
}
