import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/*
 * Three inputs for folder paths are required.
 * datasetFolderPath is the path to the clone pairs
 * m1flattedFolderPath is the path to an empty folder
 * m2flattedFolderPath is the path to an empty folder
 * This class parses the code of the clone pair files and flattens the code into statements.
 * The output is a set of clone pair files with m1 flattened and
 * another set of clone pair files with m2 flattened. This output is used by
 * The clone pair similarity predictions for the original file, the file with flattened m1codeparts,
 * and the file with flattened m2codeparts are obtained and are output to console
 * The console output is used for filtering analysis
 * */
public class FlattenedClonePairsGenerator {

    public static void main(String args[]) throws IOException {

        Parser.datasetFolderPath = ".\\Java\\Data\\100JavaClonePairs";
        Parser.pythonCodeFilePath = "codebertoutput.py";
        Parser.pythonTestFilePath = "pythontestfolder\\test.java";
        Parser.mutationsOutputFolderPath = ".\\Java\\Data\\100Javaclones_m1_flattenedGit";
        Parser.m2mutationsOutputFolderPath = ".\\Java\\Data\\100Javaclones_m2_flattenedGit";
        Parser.CLASSPATH = "C:\\Program Files\\Java\\jre1.8.0_144\\lib\\rt.jar";
        Parser.PYTHON_VERSION = "python";

        //read a java clone pair file
        //get code parts
        //count code parts
        //print code parts

        Parser.javaFilesList = DirectoryExplorer.readProjectFiles(Parser.datasetFolderPath);
        Iterator<File> javaFileIter = Parser.javaFilesList.iterator();
        boolean hasMoreThanOneSlashN = false;
        int countOfClonePairsWithMultilineCodeparts = 0;

        while (javaFileIter.hasNext()) {
            hasMoreThanOneSlashN = false;
            //System.out.println("===============");
            //reset attributes of code parts to default boolean values
            Parser.resetCodeParts();

            File currentFile = javaFileIter.next();
            Parser.currentFilePath = currentFile.getAbsolutePath();
            String fileName = currentFile.getName();
            int dotIndex = fileName.indexOf(".");
            String cloneID = fileName.substring(5,dotIndex);
            int cloneIDint = Integer.parseInt(cloneID);

            Parser.parseFile();

            //check if codepartslist hase some codepart with more than one \n
            boolean m1hasmorethanslashn = false;
            boolean m2hasmorethanslashn = false;
            for(CodeParts cp: StatementVisitor.codePartsList)
            {
                String s = cp.getPartName();
                int index = s.indexOf('\n', s.indexOf('\n') + 1);
                m1hasmorethanslashn = index > -1;
                if(m1hasmorethanslashn) {
                    //System.out.println(cp.getPartName() + "has more than one slash n");
                    break;
                }
            }
            for(CodeParts cp: StatementVisitor.codePartsListm2)
            {
                String s = cp.getPartName();
                int index = s.indexOf('\n', s.indexOf('\n') + 1);
                m2hasmorethanslashn = index > -1;
                if(m2hasmorethanslashn) {
                    //System.out.println(cp.getPartName() + "has more than one slash n");
                    break;
                }
            }

            if(m1hasmorethanslashn || m2hasmorethanslashn) {
             System.out.println("Codeparts on multiple lines case discarded");
                countOfClonePairsWithMultilineCodeparts += 1;
                continue;//process next file
            }

            if(StatementVisitor.codePartsList.size() <= 10 && StatementVisitor.codePartsListm2.size() <= 10) {
                int predictionFromOriginalFile = Parser.checkPredictionOfOriginalFile(currentFile);
                int predictionFromCodePartsm1 = Parser.checkPredictionOfOriginalStatements2(StatementVisitor.codePartsList);
                int predictionFromCodePartsm2 = Parser.checkPredictionOfOriginalStatementsofm2(StatementVisitor.codePartsListm2);
                System.out.println("Clone" + cloneID + "," + predictionFromOriginalFile+"," + predictionFromCodePartsm1 +"," + predictionFromCodePartsm2 + ", 10 or less lines in m1 m2");
            }
        }
        System.out.println("countOfMultilineCodeparts: "  + countOfClonePairsWithMultilineCodeparts);

    }
}
