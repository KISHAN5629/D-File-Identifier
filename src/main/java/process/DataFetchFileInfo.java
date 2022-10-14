package process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import pojo.DataFileInfo;
import pojo.DataUrl;
//This is a utility class which contains all methods for webscrappin
public class DataFetchFileInfo {
	
	public static List<DataUrl> getExtensionUrlPair(String category){
		WebClient client = new WebClient();
    	client.getOptions().setCssEnabled(false);
    	client.getOptions().setJavaScriptEnabled(false);
    	HtmlPage page=null;
    	try {
    	  String searchUrl = "https://fileinfo.com/filetypes/"+category;
    	  page = client.getPage(searchUrl);
    	}catch(Exception e){
    	  e.printStackTrace();
    	}
    	
    	List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//table[@class='list sortable filetypes']/tbody/tr") ;
    	List<DataUrl> dataUrl=new ArrayList<DataUrl>();
    	if(items.isEmpty()){
    	  System.out.println("No items found !");
    	}else{
    	for(HtmlElement htmlItem : items){
    	  HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//td[@class='extcol']/a"));
    						
    	  String itemName = itemAnchor.asText();
    	  String itemUrl =  itemAnchor.getHrefAttribute();
    	  DataUrl data=new DataUrl();
    	  data.setName(itemName);
    	  data.setUrl(itemUrl);
    	  dataUrl.add(data);
    	  }
    	}
    	return dataUrl;
	}
	public static HashMap<String,DataFileInfo> fetchAndStoreData(List<DataUrl> dataUrls){
		HashMap<String,DataFileInfo> dataFileInfo=new HashMap<String,DataFileInfo>();
		for (DataUrl i : dataUrls) {
			WebClient client = new WebClient();
	    	client.getOptions().setCssEnabled(false);
	    	client.getOptions().setJavaScriptEnabled(false);
	    	HtmlPage page=null;
	    	try {
	    	  String searchUrl = "https://fileinfo.com/"+i.getUrl();
	    	  page = client.getPage(searchUrl);
	    	}catch(Exception e){
	    	  e.printStackTrace();
	    	}
	    	DataFileInfo f=new DataFileInfo();
	    	
	    	HtmlElement typeElement = (HtmlElement) page.getFirstByXPath("//h2[@class='title']") ;
	    	HtmlElement infoElement = (HtmlElement) page.getFirstByXPath("//div[@class='infoBox']/p") ;
	    	String type=typeElement.asText();
	    	String description=infoElement.asText();
	    	
	    	List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//table[@class='headerInfo']/tbody/tr") ;
	    	List<HtmlElement> td=(List<HtmlElement>) page.getByXPath(".//td") ;
	    	
	    	String devloper=td.get(1).asText();
	    	
	    	HtmlElement categoryElement=td.get(5);
	    	String category=categoryElement.asText();
	    	String format=td.get(7).asText();
	    	//List<HtmlElement> appsList will get the supported apps name for each filytype and store into List<String> apps
	    	
	    	List<HtmlElement> appsList=(List<HtmlElement>) page.getByXPath("//div[@class='appmid']/div[@class='program']/a") ;
	    	List<String> apps=new ArrayList<String>();
	    	for(HtmlElement htmlItem : appsList){
	      	  apps.add(htmlItem.asText());
	      	}
	    	//set with values of each data member of DataFileInfo class
	    	f.setName(i.getName());
	    	f.setCategory(category);
	    	f.setDescription(description);
	    	f.setDevloper(devloper);
	    	f.setFormat(format);
	    	f.setType(type);
	    	f.setApps(apps);
	    	//storing into HashMap<String,DataFileInfo> dataFileInfo
	    	dataFileInfo.put(i.getName(), f);
	    		
		}
		
    	return dataFileInfo;
	}
}
