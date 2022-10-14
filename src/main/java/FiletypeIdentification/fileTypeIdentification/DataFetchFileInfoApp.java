package FiletypeIdentification.fileTypeIdentification;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import pojo.*;
import java.io.File;
import java.util.Map;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ThreadforProcess.DataInfoMapForThreding;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Hello world!
 *
 */
/*This java file is used to scrap the data from fileInfo.com using multithreading 
 * and store it in result.json.*/
public class DataFetchFileInfoApp 
{
	
    public static void main( String[] args )
    
    {
    	String directory=System.getProperty("user.dir");
    	String filetypes[]={"developer","common","text","web","system","data","misc","database","font","spreadsheet","executable","game","gis","settings"}; 
    	//creating the new task for each filetype using multiThreding
    	DataInfoMapForThreding arr[]=new DataInfoMapForThreding[filetypes.length];
    	Thread pThreads[] = new Thread[filetypes.length]; 
    	System.out.println("Data Fetch process Starts It may takes 10 to 15 min to loads 10000 extensions(Depends on net speed as it is webscraping)");
    	//fetching process started
    	for(int i=0;i<arr.length;i++) {
    		arr[i]=new DataInfoMapForThreding(filetypes[i]);
    		pThreads[i]=new Thread(arr[i]);
    		pThreads[i].start();
    	}
    	for (int k = 0; k < pThreads.length; k++) {
    	    try {
				pThreads[k].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	//fetching process terminate.
    	System.out.println("doneee....");
    	
    	HashMap<String,DataFileInfo> j=new HashMap<String,DataFileInfo>();
    	//all fetched information put into HashMap<String,DataFileInfo> 
    	for(int i=0;i<arr.length;i++) {
    		j.putAll(arr[i]. data);
    	}
//store into result.json
    	ObjectMapper mapper = new ObjectMapper();
    	try {
            mapper.writeValue(new File(directory+"\\src\\main\\data\\result.json"), j);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
}
