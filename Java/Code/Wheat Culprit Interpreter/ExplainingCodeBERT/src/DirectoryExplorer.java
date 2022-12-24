import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryExplorer {

	public static List<File> readRootDirectoryFolders(String projectsRootDirectory) 
	{
		File directory = new File(projectsRootDirectory);
		File[] foldersList = directory.listFiles();
		
		List<File> javaProjectsList = new ArrayList<File>();
		
		for (File file : foldersList) 
		{
			if (file.isFile()) 
			{
				//do nothing
			} 
			else if (file.isDirectory()) 
			{
				javaProjectsList.add(file);
			}
		}
		return javaProjectsList;
	}
	
	public static String findDir(File root, String name)
	{ 
	    if (root.getName().equals(name))
	    { 
	        return root.getAbsolutePath();
	    } 
	 
	    File[] files = root.listFiles();
	 
	    if(files != null)
	    { 
	        for (File f : files)  
	        { 
	            if(f.isDirectory())
	            {    
	                String myResult = findDir(f, name);
	                if (myResult != null) 
	                {
	               
	                    return myResult;
	                } 
	            } 
	        } 
	    } 
	    return null; 
	} 

	public static List<File> readProjectFiles(String projectDirectory) 
	{		
		File directory = new File(projectDirectory);
		File[] filesList = directory.listFiles();
		
		List<File> javaFilesList = new ArrayList<File>();
		
		for (File file : filesList) 
		{
			if (file.isFile() && file.getAbsolutePath().endsWith("java")) 
			{
				javaFilesList.add(file);
				//System.out.println(file.getAbsolutePath());
			} 
			else if (file.isDirectory()) 
			{
				javaFilesList.addAll(readProjectFiles(file.getAbsolutePath()));
			}
		}
		return javaFilesList;
	}

}
