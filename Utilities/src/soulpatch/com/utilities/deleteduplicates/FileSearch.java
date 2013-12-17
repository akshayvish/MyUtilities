package soulpatch.com.utilities.deleteduplicates;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

//Class to search a file name and list the path of the filenames
public class FileSearch {
	Scanner scan = new Scanner(System.in);
	String fileName = null;
	String directory = null;
	String fileExtension = null;
	MyFile searchFile = null;
	String userChoice = null;
	static int numOfFiles = 0;
	static int numOfFolders = 0;
	public static String FILE_REGEX = "MyFileSearch";
	File output = new File(System.getProperty("user.dir") + "/Output.txt");
	ArrayList<MyFile> duplicates = new ArrayList<>();

	public static void main(String args[]){
		FileSearch fs = new FileSearch();
		//fs.initialize();
		fs.fileName = "Hamara naam banarsi babu";
		fs.directory = "/Users/akshayviswanathan/Music/test";
		fs.fileExtension = ".mp3";
		
		if((fs.fileName!=null) &&
				(!fs.fileName.isEmpty()) && 
				(!fs.fileName.equalsIgnoreCase(""))){
			fs.searchFile = new MyFile(new File(fs.directory + "/" + fs.fileName + fs.fileExtension));
			fs.searchFile.setDirectory(fs.directory);
			fs.searchFile.setFileExtension(fs.fileExtension);
		}
		else{
			fs.searchFile = new MyFile(new File(fs.directory));
		}

		ArrayList<MyFile> matches = fs.searchExactFileName(fs.searchFile, fs.fileExtension, fs.fileName);
		fs.findDuplicates(matches);
		//fs.deleteDuplicates();
		
		//ArrayList<String> arr = fs.searchExactFileName(fs.searchFile, fs.fileExtension, fs.fileName);
		//System.out.println("Matches " + arr.size());
		/*fs.getUserChoice(fs);
		switch(fs.userChoice){
			case "1":
				fs.searchExactFileName(fs);
			case "2":
				fs.searchSimilarFileName(fs);
			case "3":
				fs.searchFileWithExtensions(fs);
		}*/
	}
	public void initialize(){
		System.out.println("Enter the file name you want to search for : ");
		fileName = scan.nextLine();
		System.out.println("Enter the directory in which you want to search : ");
		directory = scan.nextLine();
		System.out.println("Enter the type of file you want to search for : ");
		fileExtension = scan.nextLine();
	}
	public void getUserChoice(FileSearch fs){
		System.out.println("Enter a choice ");
		System.out.println("1. Search files with exact name");
		System.out.println("2. Search files with similar names");
		System.out.println("3. Search all files with extension");
		fs.userChoice = scan.nextLine();
	}
	//List exact file matches given the path, filename and extension
	public ArrayList<MyFile> searchExactFileName(MyFile myFile, String extension, String fileName){
		ArrayList<MyFile> matches = new ArrayList<MyFile>();
		ArrayList<MyFile> fileList = fileList(myFile.getDirectory(), extension, false);
		for(int i=0;i<fileList.size();i++){
			if(fileList.get(i).getFile().getName().equalsIgnoreCase(fileName + extension)){
				matches.add(fileList.get(i));
				System.out.println("File -- " + fileList.get(i).getAbsolutePath());
			}
		}
		return matches;
	}
	
	/*public File[] listFiles(String dirPath){
		File file = new File(dirPath);
		File[] fileList = file.listFiles();
		
	}*/
	
	//List similar file names given file name, directory and extension
	/*public void searchSimilarFileName(FileSearch fs){

		if(fs.searchFile.exists()){
			if(fs.searchFile.isDirectory()){ //It's a folder / directory
				MyFile[] fileList = fs.searchFile.getFileList(new FileExtension(fs.fileExtension));
				for(int i=0;i<fileList.length;i++){
					System.out.println(fileList[i].getAbsolutePath());
				}
			}else if(fs.searchFile.isFile()){ //It's a file get the extension if you need to refine the search
				Vector<String[]> nameSplit = new Vector<String[]>();
				nameSplit.add(fs.searchFile.getName().split("[0-9]"));
				nameSplit.add(fs.searchFile.getName().split(" "));
				System.out.println("Splitting elements ");
				for(int i=0;i<nameSplit.size();i++){
					for(int j=0;j<nameSplit.get(i).length;j++)
						System.out.println(nameSplit.get(i)[j]);
				}
			}
		}

	}*/
	
	//List all files with the given extension in the given directory.
	public String searchFileWithExt(String path, String ext){
		String files = "";
		File dir = new File(path);
		File[] list = dir.listFiles();
		for(int i=0;i<list.length;i++){
			if(list[i].isFile())
				files = files + FileSearch.FILE_REGEX + list[i].getAbsolutePath();
			else if(list[i].isDirectory())
				files = files + FileSearch.FILE_REGEX + searchFileWithExt(list[i].getAbsolutePath(), ext);
		}
		return files;
	}
	
	//Get file list from searchFileWithExt() and put it in an ArrayList and return.
	public ArrayList<MyFile> fileList(String path, String ext, boolean hidden){
		ArrayList<MyFile> fileList = new ArrayList<MyFile>();
		
		String[] str = (searchFileWithExt(path, ext)).split(FILE_REGEX);
		for(int i=0;i<str.length;i++){
			fileList.add(new MyFile(new File(str[i])));
		}
		
		return fileList;
	}
	
	public void findDuplicates(ArrayList<MyFile> inputArr){
		this.duplicates = new ArrayList<MyFile>();
		for(int i=0;i<inputArr.size()-1;i++){
			if(inputArr.get(i).isSameSize(inputArr.get(i+1)))
				this.duplicates.add(inputArr.get(i));
		}
	}
	
	public void deleteDuplicates(){
		for(int i = 0; i<this.duplicates.size();i++)
			this.duplicates.get(i).getFile().delete();
	}
}
