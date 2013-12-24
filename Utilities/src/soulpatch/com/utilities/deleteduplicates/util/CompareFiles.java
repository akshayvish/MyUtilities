package soulpatch.com.utilities.deleteduplicates.util;

import java.util.Comparator;

import soulpatch.com.utilities.deleteduplicates.MyFile;

public class CompareFiles implements Comparator<MyFile>{

	@Override
	public int compare(MyFile myFile1, MyFile myFile2) {
		return (myFile1.getFile().compareTo(myFile2.getFile()));
	}
}
