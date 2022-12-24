import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class StackOverflowQuestionIDGetter {
    public static void main(String args[]) throws IOException {
        //read files from folder
        List<File> javaFilesList = readProjectFiles(".\\Java\\Data\\100JavaClonePairs");
        Iterator<File> javaFileIter = javaFilesList.iterator();

        while (javaFileIter.hasNext()) {
            File currentFile = javaFileIter.next();
            String currentFilePath = currentFile.getAbsolutePath();
            String fileName = currentFile.getName();
            int dotIndex = fileName.indexOf(".");
            String cloneID = fileName.substring(5, dotIndex);
            int cloneIDint = Integer.parseInt(cloneID);

            //for each file search for string Stack overflow Question #:
            List<String> lines = Files.readAllLines(Paths.get(currentFilePath));
            for(String l:lines) {
                if(l.contains("Question"))
                {

                    dotIndex = l.indexOf(":");
                    String SOQID = l.substring(dotIndex+1);
                    System.out.println("Clone"+cloneID+","+SOQID);
                }

            }
            //if string found then extract ID , print clone file name and Ques ID break loop

        }
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
