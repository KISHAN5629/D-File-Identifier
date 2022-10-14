package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import FiletypeIdentification.fileTypeIdentification.DataProcessFileInfoApp;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import pojo.DataFileInfo;

//
public class InputDataProcessMethod {
	//readDataFromInput() : will read the data from input file and store into ArrayList.
	public static List<String> readDataFromInput(String fileLocation) throws IOException { 
	   
	    List<String> content = new ArrayList<>();
	    try(BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
	        String line = "";
	        while ((line = br.readLine()) != null) {
	        	Collections.addAll(content,line.split(","));
	        }
	    } catch (FileNotFoundException e) {
	    }
	    return content;
	}
	//loadDataFromJson() : will load the data from result1.json file.
	public static HashMap<String,DataFileInfo> loadDataFromJson() {
		String directory=System.getProperty("user.dir");
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String,DataFileInfo> data=new HashMap<String,DataFileInfo>();
		try {
			data = mapper.readValue(new File(
                   directory +"\\src\\main\\data\\result.json"), new TypeReference<HashMap<String,DataFileInfo>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
		return data;
	}
	//returnOutput() : return the information of each file type from DataFileInfo Class.
	public static String returnOutput(String filename,String extension,HashMap<String,DataFileInfo> data) {
		String output="";
		try {
			DataFileInfo d=data.get(extension);
			String fileTypeByjdk=InputDataProcessMethod.filetypeByjdk(filename);
			String mimeType=InputDataProcessMethod.typeByURLConnection(filename);
			d.setTypeByjdkSource(fileTypeByjdk);
			d.setMimeType(mimeType);
			output=filename+":"+"\n\tName: "+d.getName()+"\n\tCategory: "+d.getCategory()+"\n\tType: "+d.getType()+"\n\tTypeByJdk: "+d.getTypeByjdkSource()+"\n\tMimeType: "+d.getMimeType()+"\n\tDeveloper: "+d.getDevloper()+"\n\tFormat: "+d.getFormat()+"\n\tDescription: "+d.getDescription()+"\n\tApps Used For: "+d.getApps()+"\n\n";
		}
		catch(Exception e) {
			output=filename+":\n\n Data not Available";
		}
		return output;
	}
	//filetypeByjdk() : return the filetype by using probeContentType() method available into Files class of JDK-8
	public static String filetypeByjdk(String filename) {
		String fileType = "Undetermined";
		  File file = new File(filename);
		   try
		   {
		      fileType = Files.probeContentType(file.toPath());
		   }
		   catch (IOException ioException)
		   {
		     
		   }
		   return fileType;
	}
	//typeByURLConnection() : will return mimeType of a file type which is present in JDK-7
	public static String typeByURLConnection(String filename) {
		String mimeType="Not Defined By URLConnection class";
		File file=new File(filename);
		mimeType= URLConnection.guessContentTypeFromName(file.getName());
	 return mimeType;
	}
	
}
