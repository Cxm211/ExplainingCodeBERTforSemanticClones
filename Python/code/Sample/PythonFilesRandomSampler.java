package PythonInteraction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PythonFilesRandomSampler {
    static String datasetFolderPath = "Filtered Python Codes";
    public static void main(String args[]) throws IOException {


        File directory = new File(datasetFolderPath);
        File[] filesList = directory.listFiles();

        List<File> pyFilesList = new ArrayList<File>();

        for (File file : filesList)
        {
            if (file.isFile() && file.getAbsolutePath().endsWith("py"))
            {
                pyFilesList.add(file);
                //System.out.println(file.getAbsolutePath());
            }

        }

        Collections.shuffle( pyFilesList ) ;
        Iterator<File> pyFileIter = pyFilesList.iterator();

        boolean hasMoreThanOneSlashN = false;
        int samplecount = 0;

        while (pyFileIter.hasNext() && samplecount<100) {
            samplecount +=1;
            File currentFile = pyFileIter.next();
            String currentFilePath = currentFile.getAbsolutePath();
            String fileName = currentFile.getName();
            int dotIndex = fileName.indexOf(".");
            String cloneID = fileName.substring(5,dotIndex);
            int cloneIDint = Integer.parseInt(cloneID);
            Path filePath = Paths.get(currentFilePath);
            List<String> lines = Files.readAllLines(filePath);

                int linecounter = 1;
                for(String cp: lines) {
                    if(cp.contentEquals(""))
                        break;
                    System.out.println("Clone" + cloneID + "~" + linecounter + "~" +  cp);
                    linecounter += 1;
                }

                //iterate over the stmts in m2 and print the clonefilename, lineno, stmt
            int newlinecounter = 1;
                for(int i=linecounter+1; i<lines.size();i++) {
                    if(lines.get(i).contentEquals(""))
                        break;
                    System.out.println("Clone" + cloneID + "~" + newlinecounter +"~" +  lines.get(i));
                    newlinecounter += 1;
                }





        }

    }
}
