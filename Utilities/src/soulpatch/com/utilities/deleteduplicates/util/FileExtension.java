package soulpatch.com.utilities.deleteduplicates.util;

import java.io.File;
import java.io.FilenameFilter;

//My own file Extension filter that returns if the file name passed has the same extension as passed to the class.
public class FileExtension implements FilenameFilter{
	String extension = null;
	
	public FileExtension(String ext){
		this.extension = ext.toLowerCase();
	}
	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(extension);
	}

}
