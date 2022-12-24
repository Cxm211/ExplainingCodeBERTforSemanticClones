package IntersectionAnalysisDataGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MakeWheatCulpritStmtFileForM1M2 {
    //input format
    //WheatStmtNumsm1permuteBinary.csv
    //Clone556,0,0,0,1,0,0,
    //output format
    //clonefilename,m1/m2,smtnum,wheatculpritstatusbit(0 or 1)

    //how to do it
    //for every line in input csv read the numbers into an array
    //for every number in array write to csv
    public static void main(String args[]) throws IOException {
        String inputfilename = "New100WheatStmtNumsm1permuteBinary.csv";
        getStmtScoresLineWise(inputfilename, "m1");
        System.out.println("========Split here ===========");

        inputfilename = "New100WheatStmtNumsm2permuteBinary.csv";
        getStmtScoresLineWise(inputfilename, "m2");
        System.out.println("========Split here ===========");

        inputfilename = "New100CulpritStmtNumsm1permuteBinary.csv";
        getStmtScoresLineWise(inputfilename, "m1");
        System.out.println("========Split here ===========");

        inputfilename = "New100CulpritStmtNumsm2permuteBinary.csv";
        getStmtScoresLineWise(inputfilename, "m2");
        System.out.println("========Split here ===========");

    }

    private static void getStmtScoresLineWise(String inputfilename, String m1orm2) throws IOException {
        Path filePath = Paths.get(inputfilename);
        List<String> lines = Files.readAllLines(filePath);
        for(String l:lines)
        {
            //read clonefilename
            //read numbers to array
            String clonefilename = l.substring(0,l.indexOf(','));
            String binaryform = l.substring(l.indexOf(',')+1,l.lastIndexOf(','));
            String[] arrayofbinaryscores = binaryform.split(",");
            if(!binaryform.contentEquals("-1"))
            {
                for(int i=0;i<arrayofbinaryscores.length;i++)
                {
                    int linenum = i+1;
                    System.out.println(clonefilename+ "," + m1orm2 + "," + linenum + "," + arrayofbinaryscores[i]);
                }
            }
        }
    }
}
