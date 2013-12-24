package soulpatch.com.utilities.deleteduplicates.util;

import java.util.ArrayList;

import soulpatch.com.utilities.deleteduplicates.MyFile;

public class Utils {
	
	public static String fileName = null;
	public static String directory = null;
	public static String fileExtension = null;
	public static double fileSize = 3412229.0;
	public static boolean hidden = false;
	public static boolean exactName = false;

	public static ArrayList<MyFile> allFiles = new ArrayList<>(); //list of all files in the directory 
	public static ArrayList<MyFile> hiddenFiles = new ArrayList<>(); //List of all hidden files 
	public static ArrayList<MyFile> extFiles = new ArrayList<>(); //List of all files with the given extension
	public static ArrayList<MyFile> nameFiles = new ArrayList<>(); // List of all files with the given names
	public static ArrayList<MyFile> sizeFiles = new ArrayList<>(); // List of all files given the size of the files
	public static ArrayList<MyFile> duplicates = new ArrayList<>(); // List of all duplicate files.
	public static ArrayList<MyFile> excludeDir = new ArrayList<>(); // list of absolute paths of directories to be excluded from the search.

}
