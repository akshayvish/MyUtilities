import java.io.File;
import java.util.ArrayList;


public class FileSearch1 {

	public String searchFileWithExt(String path, String ext){
		String files = "";
		File dir = new File(path);
		File[] list = dir.listFiles();
		for(int i=0;i<list.length;i++){
			if(list[i].isFile())
				files = files + FileSearch.FILE_REGEX + list[i].getName();
			else if(list[i].isDirectory())
				files = files + FileSearch.FILE_REGEX + searchFileWithExt(list[i].getAbsolutePath(), ext);
		}
		return files;
	}
}
