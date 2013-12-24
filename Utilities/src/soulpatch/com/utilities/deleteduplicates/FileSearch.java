package soulpatch.com.utilities.deleteduplicates;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.rmi.CORBA.Util;

import soulpatch.com.utilities.deleteduplicates.util.FileExtension;
import soulpatch.com.utilities.deleteduplicates.util.Utils;

//Class to search a file name and list the path of the filenames
public class FileSearch {
	Scanner scan = new Scanner(System.in);
	MyFile searchFile = null;
	String userChoice = null;
	static int numOfFiles = 0;
	static int numOfFolders = 0;
	public static String FILE_REGEX = "MyFileSearch";
	//File output = new File(System.getProperty("user.dir") + "/Output.txt");

	public static void main(String args[]){
		FileSearch fs = new FileSearch();
		//fs.initialize();
		Utils.fileName = "Dil aaj shair hai";
		Utils.directory = "/Users/akshayviswanathan/Music/testbed/Oldies/Devanand hits";
		Utils.fileExtension = ".mp3";
		
		if((Utils.fileName!=null) &&
				(!Utils.fileName.isEmpty()) && 
				(!Utils.fileName.equalsIgnoreCase(""))){
			fs.searchFile = new MyFile(new File(Utils.directory + "/" + Utils.fileName + Utils.fileExtension));
			fs.searchFile.setDirectory(Utils.directory);
			fs.searchFile.setFileExtension(Utils.fileExtension);
		}
		else{
			fs.searchFile = new MyFile(new File(Utils.directory));
		}

		Utils.allFiles = fs.fileList(false,fs.searchFile(Utils.directory));
		Utils.extFiles = fs.searchExtFiles(Utils.allFiles, Utils.fileExtension);
		Utils.nameFiles = fs.searchExactFileNames(Utils.allFiles, Utils.fileName + Utils.fileExtension);
		Utils.sizeFiles = fs.searchExactFileSize(Utils.allFiles, Utils.fileSize);
		fs.writeListToFile(Utils.allFiles, "allFiles.txt");
		fs.writeListToFile(Utils.extFiles, "extFiles.txt");
		//ArrayList<MyFile> mylist = fs.findNameDuplicatesList(Utils.allFiles, Utils.extFiles);
		ArrayList<MyFile> mylist = fs.findNameDuplicatesList(Utils.allFiles);
		for(int i=0;i<mylist.size();i++)
			System.out.println(mylist.get(i).getFile().getAbsolutePath());
		
		//System.out.println(mylist.size() + " ** " + Utils.extFiles.size());
		
		//Utils.duplicates = fs.findDuplicatesFromList(Utils.extFiles);
		//for(int i=0;i<Utils.extFiles.size();i++)
			//System.out.println("Duplicates : " + Utils.extFiles.get(i).getAbsolutePath());
		/*System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
		for(int i=0;i<Utils.allFiles.size();i++)
			System.out.println(Utils.allFiles.get(i).getFile().getName());
		
		System.out.println("************************************************");
		
		for(int i=0;i<Utils.extFiles.size();i++)
			System.out.println(Utils.extFiles.get(i).getFile().getName());
		
		//fs.deleteDuplicates();
		
		//ArrayList<String> arr = fs.searchExactFileName(fs.searchFile, Utils.fileExtension, Utils.fileName);
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
		Utils.fileName = scan.nextLine();
		System.out.println("Enter the directory in which you want to search : ");
		Utils.directory = scan.nextLine();
		System.out.println("Enter the type of file you want to search for : ");
		Utils.fileExtension = scan.nextLine();
	}
	public void getUserChoice(FileSearch fs){
		System.out.println("Enter a choice ");
		System.out.println("1. Search files with exact name");
		System.out.println("2. Search files with similar names");
		System.out.println("3. Search all files with extension");
		fs.userChoice = scan.nextLine();
	}
	//List exact file matches given the path, filename and extension
	public ArrayList<MyFile> common(ArrayList<MyFile> array1, ArrayList<MyFile> array2){
		ArrayList<MyFile> matches = new ArrayList<MyFile>();
		for(int i=0;i<array1.size();i++){
			for(int j=0;j<array2.size();j++){
				if(!array1.get(i).getFile().getName().isEmpty() && array1.get(i).equals(array2.get(j)))
					matches.add(array1.get(i));
			}
		}
		return matches;
	}
	
	//List similar file names given file name, directory and extension
	/*public void searchSimilarFileName(FileSearch fs){

		if(fs.searchFile.exists()){
			if(fs.searchFile.isDirectory()){ //It's a folder / directory
				MyFile[] fileList = fs.searchFile.getFileList(new FileExtension(Utils.fileExtension));
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
	
	public String searchFile(String path){
		String files = "";
		File dir = new File(path);
		File[] list = dir.listFiles();
		for(int i=0;i<list.length;i++){
			if(list[i].isFile())
				files = files + FileSearch.FILE_REGEX + list[i].getAbsolutePath();
			else if(list[i].isDirectory())
				files = files + FileSearch.FILE_REGEX + searchFile(list[i].getAbsolutePath());
		}
		return files;
	}
	
	//Get file list and put it in an ArrayList and return.
	public ArrayList<MyFile> fileList(boolean hidden, String fileNameList){
		ArrayList<MyFile> fileList = new ArrayList<MyFile>();
		String[] str = fileNameList.split(FileSearch.FILE_REGEX);
		for(int i=0;i<str.length;i++){
			if(!str[i].isEmpty())
				fileList.add(new MyFile(new File(str[i])));
		}
		return fileList;
	}
	
	//Search files with the type or extension
	public ArrayList<MyFile> searchExtFiles(ArrayList<MyFile> allFiles, String extension){
		ArrayList<MyFile> extList = new ArrayList<MyFile>();
		for(int i=0;i<allFiles.size();i++){
			if(allFiles.get(i).isFileWithExt(extension))
				extList.add(allFiles.get(i));
		}
		return extList;
	}
	
	//Search files with given name
	public ArrayList<MyFile> searchExactFileNames(ArrayList<MyFile> allFiles, String name){
		ArrayList<MyFile> nameList = new ArrayList<MyFile>();
		for(int i=0;i<allFiles.size();i++){
			if(allFiles.get(i).getFile().getName().equalsIgnoreCase(name))
				nameList.add(allFiles.get(i));
		}
		return nameList;
	}
	
	//Search files with given name
	public ArrayList<MyFile> searchExactFileSize(ArrayList<MyFile> allFiles, double size){
		ArrayList<MyFile> sizeList = new ArrayList<MyFile>();
		for(int i=0;i<allFiles.size();i++){
			if(allFiles.get(i).getFile().length() == size)
				sizeList.add(allFiles.get(i));
		}
		return sizeList;
	}
	
	public ArrayList<MyFile> findSizeDuplicatesList(ArrayList<MyFile> inputArr){
		ArrayList<MyFile> sizeDuplicates = new ArrayList<MyFile>();
		for(int i=0;i<inputArr.size()-2;i++){
			for(int j=1;j<inputArr.size()-1;j++)
				if(inputArr.get(i).isSameSize(inputArr.get(j)))
					sizeDuplicates.add(inputArr.get(i));
		}
		return sizeDuplicates;
	}

	public ArrayList<MyFile> findSizeDuplicatesList(ArrayList<MyFile> inputArr1, ArrayList<MyFile> inputArr2){
		ArrayList<MyFile> sizeDuplicates = new ArrayList<MyFile>();
		for(int i=0;i<inputArr1.size();i++){
			for(int j=0;j<inputArr2.size();j++)
				if(inputArr1.get(i).isSameSize(inputArr2.get(j)))
					sizeDuplicates.add(inputArr1.get(i));
		}
		return sizeDuplicates;
	}

	
	public ArrayList<MyFile> findNameDuplicatesList(ArrayList<MyFile> inputArr){
		ArrayList<MyFile> nameDuplicates = new ArrayList<MyFile>();
		for(int i=0;i<inputArr.size()-2;i++){
			for(int j=1;j<inputArr.size()-1;j++){
				if(inputArr.get(i).isSameName(inputArr.get(j)) 
						&& !(inputArr.get(i).equals(inputArr.get(j)))){
					nameDuplicates.add(inputArr.get(i));
				}
			}
		}
		return nameDuplicates;
	}
	
	public ArrayList<MyFile> findNameDuplicatesList(ArrayList<MyFile> inputArr1, ArrayList<MyFile> inputArr2){
		ArrayList<MyFile> nameDuplicates = new ArrayList<MyFile>();
		for(int i=0;i<inputArr1.size();i++){
			for(int j=0;j<inputArr2.size();j++)
				if(inputArr1.get(i).isSameName(inputArr2.get(j)))
					nameDuplicates.add(inputArr2.get(i));
		}
		return nameDuplicates;
	}
	
	//Delete files in the fiven array. 
	public void deleteFiles(ArrayList<MyFile> delArray){
		for(int i = 0; i<delArray.size();i++)
			if(delArray.get(i).exists())
				delArray.get(i).getFile().delete();
	}
	
	//Return true if the path is one of the exclusion lists.
	public boolean isExcluded(String dirPath, ArrayList<MyFile> excludeDir){
		for(int i = 0;i<excludeDir.size();i++){
			if(dirPath.equalsIgnoreCase(excludeDir.get(i).getFile().getAbsolutePath()))
					return true;
		}
		return false;
	}
	
	public void writeListToFile(ArrayList<MyFile> inputArr, String fileName){
		try {
			File file = new File(Utils.directory + "/" +fileName);
			if(!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0;i<inputArr.size();i++)
				bw.write(inputArr.get(i).getAbsolutePath());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
