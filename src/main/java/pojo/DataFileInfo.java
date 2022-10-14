package pojo;

import java.util.List;
//DataFileInfo contains the getter() and setter() method of each fileType's data .
public class DataFileInfo {
	private String name;
	private String category;
	private String type;
	private String devloper;
	private String format;
	private String description;
	private List<String> apps;
	private String typeByjdkSource;
	private String mimeType;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevloper() {
		return devloper;
	}
	public void setDevloper(String devloper) {
		this.devloper = devloper;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getApps() {
		return apps;
	}
	public void setApps(List<String> apps) {
		this.apps = apps;
	}
	public String getTypeByjdkSource() {
		return typeByjdkSource;
	}
	public void setTypeByjdkSource(String typeByjdkSource) {
		this.typeByjdkSource = typeByjdkSource;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
