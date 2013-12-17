package soulpatch.com.utilities.deleteduplicates;
import java.io.File;

//MyFile has methods that will be useful for comparing and deleting files.
public class MyFile {
	
	File file = null;
	long fileSize = 0;
	MyFile[] fileList = null; 
	boolean isSameName = false; //True if the names are exact match.
	boolean isSimilarName = false; // True if the names have any words same.
	boolean isSameSize = false; //True if the sizes of the files are exactly same. (Will later modify to have a range)
	boolean isDuplicate = false; //True if the above three boolean variables for each file combination is true.
	String directory = null;
	String extension = null;
	
	//Constructor overload
	public MyFile(File file){
		this.setFile(file);
	}

	public boolean isFile(){
		return this.getFile().isFile();
	}
	public boolean isFileWithExt(String extension){
		return	this.getFile().getName().endsWith(extension);
	}
	public boolean isDirectory(){
		return this.getFile().isDirectory();
	}
	public boolean exists(){
		return this.getFile().exists();
	}
	public String getAbsolutePath(){
		return this.getFile().getAbsolutePath();
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getDirectory() {
		return this.directory;
	}
	public void setFileExtension(String extension) {
		this.extension = extension;
	}
	public double getFileSize() {
		 double size = this.getFile().length()/1024; //FileSize in kilobytes
		 return size;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public boolean isSimilarName() {
		return isSimilarName;
	}
	public boolean isSameName(MyFile myFile) {
		if(this.getFile().getName().equalsIgnoreCase(myFile.getFile().getName()))
			isSameName = true;
		return isSameName;
	}
	public boolean isSameSize(MyFile myFile) {
		if(this.getFileSize() == myFile.getFileSize())
			isSameSize = true; //Will later have a range
		return isSameSize;
	}
	public boolean isDuplicate(MyFile myFile2) {
		if(this.isSameName(myFile2) && this.isSameSize(myFile2))
			isDuplicate = true;
		return isDuplicate;
	}
	public MyFile[] getFileList(FileExtension fe) {
		if(this.isDirectory()){
			File file[] = this.getFile().listFiles(fe);
			fileList = new MyFile[file.length];
			for(int i=0;i<fileList.length;i++)
				fileList[i] = new MyFile(file[i]);
		}
		return fileList;
	}
	public MyFile[] getFileList() {
		if(this.isDirectory()){
			File file[] = this.getFile().listFiles();
			fileList = new MyFile[file.length];
			for(int i=0;i<fileList.length;i++)
				fileList[i] = new MyFile(file[i]);
		}
		return fileList;
	}
}
