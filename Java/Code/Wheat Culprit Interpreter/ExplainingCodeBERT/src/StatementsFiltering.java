import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/*
* This class parses the code of the clone pair files and flattens the code into statements.
* The clonefilename, statement no., and statements are output to console
* The output is used for manual human labeling of clone pairs and statements importance levels
* */
public class StatementsFiltering {
    //path to the folder containing the clone pairs
    static String datasetFolderPath = ".\\Java\\Data\\100JavaClonePairs";

    public static void main(String args[]) throws IOException {
        //read a java clone pair file
        //get code parts
        //count code parts
        //print code parts

        Parser.javaFilesList = DirectoryExplorer.readProjectFiles(datasetFolderPath);
        Iterator<File> javaFileIter = Parser.javaFilesList.iterator();
        boolean hasMoreThanOneSlashN = false;

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

            if(m1hasmorethanslashn || m2hasmorethanslashn)
                continue;//process next file

            if(StatementVisitor.codePartsList.size() <= 10 && StatementVisitor.codePartsListm2.size() <= 10) {
                //uncomment these two lines
                //int predictionFromOriginalFile = Parser.checkPredictionOfOriginalFile(currentFile);
                //System.out.println("Clone" + cloneID + "," + StatementVisitor.codePartsList.size() + "," + StatementVisitor.codePartsListm2.size()+ "," + predictionFromOriginalFile);
                //System.out.println("Clone" + cloneID + "," + StatementVisitor.codePartsList.size() + "," + StatementVisitor.codePartsListm2.size());

                //iterate over the stmts in m1 and print the clonefilename, lineno, stmt
                int partcounter = 1;
                for(CodeParts cp: StatementVisitor.codePartsList) {
                    System.out.println("Clone" + cloneID + "%%%" + partcounter + "%%%" +  cp.getPartName().substring(0,cp.getPartName().length()-1)+ "%%%" + cp.getStatementType());
                    partcounter += 1;
                }

                //iterate over the stmts in m2 and print the clonefilename, lineno, stmt
                partcounter = 1;
                for(CodeParts cp: StatementVisitor.codePartsListm2) {
                    System.out.println("Clone" + cloneID + "%%%" + partcounter +"%%%" +  cp.getPartName().substring(0,cp.getPartName().length()-1));
                    partcounter += 1;
                }


            }


        }

    }
}
