//import methodsearch.LuceneWriteIndexExample;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;

//import ModelInterpreter.CodeParts;
//import ModelInterpreter.DirectoryExplorer;
//import ModelInterpreter.SpecialCharFileReader;
//import ModelInterpreter.StatementVisitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;



public class Parser {
    static String datasetFolderPath = ".\\Java\\Data\\100JavaClonePairs";
    static String pythonCodeFilePath = "codebertoutput.py";
    static String pythonTestFilePath = "pythontestfolder\\test.java";
    static String mutationsOutputFolderPath = ".\\Java\\Data\\m1Mutations";
    static String m2mutationsOutputFolderPath = ".\\Java\\Data\\m2Mutations";
    static String CLASSPATH = "C:\\Program Files\\Java\\jre1.8.0_144\\lib\\rt.jar";
    static String PYTHON_VERSION = "python";
    static String culpritlogfile = "culpritlog.csv";
    static String wheatlogfile = "wheatlog.csv";
    public static List<String> types = new ArrayList<String>();
    public static int projectId = 0;
    public static int fileId = 0;
    private static int lastFileId = 0;
    private static int firstFileId = 0;
    private static String currentProjectName = null;
    private static ArrayList<String> classDeclarations = new ArrayList<>();
    public static int currentFunctionID = 0;
    private static int firstFunctionID = 0;
    private static int lastFunctionID = 0;
    public static int currentApiCallID = 0;
    public static List<File> javaFilesList;
    //control flow detection flags and counters
    public static boolean ifBlockRegion = false;
    public static boolean elseBlockRegion = false;
    public static boolean catchBlockRegion = false;
    public static boolean basicBlockRegion = false;
    public static int ifCount = 0;
    public static int elseCount = 0;
    public static int catchCount = 0;

    public static String currentClass = null;
    public static String currentFunctionName = null;
    public static String currentFilePath = null;
    public static ArrayList<String> commentsList = new ArrayList<String>();
    public static int methodEntered = 0;
    public static int m_nStatementCount = 0;
    public static boolean isNestedFunction = false;
    public static String functionData = "";
    public static String fileString = "";
    public static List<String> fullFile = new ArrayList<String>();
    public static String F1_fulltext;
    public static String F2_FQN;
    public static String F3_simpleName;
    public static CompilationUnit parse;
    private static Map<String,Integer> m1NodeTypesCountsMap =new HashMap<String,Integer>();
    private static Map<String,Integer> m2NodeTypesCountsMap =new HashMap<String,Integer>();
    private static String culpritStatus="";
    private static String wheatStatus="";
    private static int numCuplritStmts = 0;
    private static int numWheatStmts = 0;
    private static int numNecStmts = 0;
    private static int numMutations = 0;
    private static String signature1 = "";
    private static String signature2 = "";
    private static String culpritStmts = "";
    private static String wheatStmts = "";
    private static String necStmts = "";

    public static void main(String args[]) throws Exception {

        //datasetFolderPath = args[0];
        //String datasetFolderPath = "Java\\Stand Alone Clones";
        //pythonCodeFilePath = args[1];
        //pythonTestFilePath = args[2];
        //mutationsOutputFolderPath = args[3];

        ArrayList<String> fileNamesExclude = new ArrayList<>();
        javaFilesList = DirectoryExplorer.readProjectFiles(datasetFolderPath);

        Iterator<File> javaFileIter = javaFilesList.iterator();

        while (javaFileIter.hasNext()) {

            System.out.println("===============");
            //reset attributes of code parts to default boolean values
            m1NodeTypesCountsMap =new HashMap<String,Integer>();
            m2NodeTypesCountsMap =new HashMap<String,Integer>();
            methodEntered = 0;
            resetCodeParts();

            File currentFile = javaFileIter.next();
            currentFilePath = currentFile.getAbsolutePath();
            String fileName = currentFile.getName();
            int dotIndex = fileName.indexOf(".");
            String cloneID = fileName.substring(5,dotIndex);
            int cloneIDint = Integer.parseInt(cloneID);

            parseFile();

            if(StatementVisitor.codePartsList.size()<=10) {
                int predictionFromOriginalFile = checkPredictionOfOriginalFile(currentFile);
                int predictionFromCodeParts = checkPredictionOfOriginalStatements(StatementVisitor.codePartsList);
                if (predictionFromOriginalFile == 1) {//if True Positive
                    if (predictionFromOriginalFile == predictionFromCodeParts) {
                        getNecessaryCodePartsKComb();//also checks for sufficient ones
                    }
                    else
                    {
                        System.out.println("code parts prediction failed for TruePositive");
                    }

                } else if (predictionFromOriginalFile == 0) {
                    if (predictionFromOriginalFile == predictionFromCodeParts) {
                        testProblemCodePartsKComb();
                    } else {
                        System.out.println("Status: Maybe braces are the culprit removed to form codeparts.");
                        culpritStatus = "complete method minus codeparts include culprit(s) maybe braces";
                    }
                    writeCulpritOutputToFile(predictionFromOriginalFile, predictionFromCodeParts, culpritStatus);
                }
            }
            else
                writeErrorLogToFile(fileName + " ignored because of >10 code parts");
            }
    }

    private static void writeErrorLogToFile(String s) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter("errorlog.csv", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.println(s);
            pw.flush();

        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {// can't do anything }
            }

        }
    }

    private static void writeCulpritOutputToFile(int predictionFromOriginalFile, int  predFromCodeParts, String culpritStatus) throws IOException {

        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(culpritlogfile, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            Date d = new Date();
            culpritStmts = culpritStmts.replace("\n"," ");
            pw.println(d.toString()+","+ StatementVisitor.className+","+predictionFromOriginalFile+","+predFromCodeParts+","+culpritStatus+","+numCuplritStmts +","+ numMutations+","+ StatementVisitor.methodBody1Signature.replace(",",";")+","+ StatementVisitor.methodBody2Signature.replace(",",";")+","+culpritStmts);
            //System.out.println("Data Successfully appended into file");
            pw.flush();

        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {// can't do anything }
            }

        }
    }

    private static void writeWheatNecOutputToFile(String wheatStatus, int numStmts, String stmts) throws IOException {

        stmts = stmts.replace("\n"," ");
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(wheatlogfile, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            Date d = new Date();
            pw.println(d.toString()+","+ StatementVisitor.className+","+wheatStatus+","+numStmts +","+ numMutations+","+ stmts);
            //System.out.println("Data Successfully appended into file");
            pw.flush();

        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {// can't do anything }
            }

        }
    }

    public static void m1AddNodeCount(String nodeName){
        if(m1NodeTypesCountsMap.containsKey(nodeName))
        {
            int oldcount = m1NodeTypesCountsMap.get(nodeName);
            m1NodeTypesCountsMap.put(nodeName,oldcount+1);
        }
        else
            m1NodeTypesCountsMap.put(nodeName,1);
    }

    public static void m1AddNodeCount(String nodeName, int size) {

            m1NodeTypesCountsMap.put(nodeName,size);
    }

    public static void m2AddNodeCount(String nodeName, int size) {
        m2NodeTypesCountsMap.put(nodeName,size);
    }

    public static void m2AddNodeCount(String nodeName){
        if(m2NodeTypesCountsMap.containsKey(nodeName))
        {
            int oldcount = m2NodeTypesCountsMap.get(nodeName);
            m2NodeTypesCountsMap.put(nodeName,oldcount+1);
        }
        else
            m2NodeTypesCountsMap.put(nodeName,1);
    }

    private static boolean getSufficientCodePartsForComb(List<Integer> combination) throws IOException {

        //for each code part
        int i = 0;

        boolean found = false;
        for (int index : combination) {
            StatementVisitor.codePartsList.get(index).setProcessing(true);
        }
        String codePartsJoined = "";
        for (CodeParts cp : StatementVisitor.codePartsList) {
            if (cp.isProcessing())
                codePartsJoined = codePartsJoined.concat(cp.getPartName());
        }


        //create new file with only the one codepart
        String program = "public class " + StatementVisitor.className + "Mod" + i + " { \n" +
                StatementVisitor.methodBody1Signature + "\n" +
                //"public {\n" +
                codePartsJoined +
                "}\n" +
                StatementVisitor.methodBody2 + "\n\n" +
                "}";

        File file = new File(pythonTestFilePath);
        try {
            // Creates a Writer using FileWriter
            //File file = new File("G:/pythontestfolder/test.java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter output = new FileWriter(file);
            FileWriter output2 = new FileWriter(mutationsOutputFolderPath + StatementVisitor.className + "SuffMod" + i + ".java");

            // Writes the program to file
            output.write(program);
            output2.write(program);
            //System.out.println("Data is written to the file.");

            // Closes the writer
            output.close();
            output2.close();
        } catch (Exception e) {
            e.getStackTrace();
        }


        //get prediction for new file
        int prediction = getPrediction();
        System.out.println("Prediction is: " + prediction);

        //if preserved, set codepart as sufficient
        if (prediction == 1) {
            for (int index : combination) {
                StatementVisitor.codePartsList.get(index).setSufficient(true);
            }

            System.out.println("Found sufficient statements so breaking the combination loop");
            found = true;

        }

        //else if flipped, set as not sufficient

        else {
            for (int index : combination) {
                StatementVisitor.codePartsList.get(index).setSufficient(false);
            }
        }
        //codePart.setNecessary(true);
        //set processing as false
        //codePart.setProcessing(false);
        for (int index : combination) {
            StatementVisitor.codePartsList.get(index).setProcessing(false);
        }
        //end for

        //output codeparts with sufficient true
        System.out.println("Sufficient code parts for file: " + currentFilePath );
        boolean hasSufficient = false;
        for (CodeParts cp : StatementVisitor.codePartsList) {
            if (cp.isSufficient()) {
                hasSufficient = true;
                System.out.println(cp.getPartName());
            }
        }
        //}
        //else
        //if(!hasSufficient)
          //  System.out.println("Given combination of the code parts is sufficient.");
        return hasSufficient;

    }


    private static void showCodeParts() {
        for (CodeParts cp : StatementVisitor.codePartsList) {
            System.out.println(cp.getPartName());
        }
    }

    public static void parseFile() throws IOException {
        fileString = readFileToString(currentFilePath);
        try {
            parse = parse(fileString);
        } catch (Exception ex) {
            System.out.println("Could not parse file!");
        }

        if (parse != null) {
            fullFile = SpecialCharFileReader.getFileText(currentFilePath);
            StatementVisitor walk1 = new StatementVisitor();
            parse.accept(walk1);
        }
    }

    public static void resetCodeParts() {
        StatementVisitor.codePartsList = new ArrayList<>();
        StatementVisitor.codePartsListm2 = new ArrayList<>();
        StatementVisitor.methodBody1Signature = "";
        StatementVisitor.methodBody2 = "";
        StatementVisitor.methodBody1InnerStmts = "";
        StatementVisitor.methodBody2Signature = "";
        StatementVisitor.methodBody2InnerStmts = "";
        numCuplritStmts = 0;
        numNecStmts = 0;
        numWheatStmts = 0;
        culpritStmts = "";
        wheatStmts = "";
        necStmts = "";
        numMutations = 0;

    }

    private static List<Integer> getNecessaryCodePartsKComb() throws IOException {
        //for each code part
        List<Integer> necessarycombination = new ArrayList<Integer>();
        int i = 0;
        int found = 0;
        //get combinations of indices of codePartsList
        //for each combination, set the statements in those indices to isProcessing and
        //get the prediction for remaining code, iterate over statements processing and set them as necessary or not necessary
        //if at any time necessary statements found stop the loop and return necessary statements

        int size = StatementVisitor.codePartsList.size();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int a = 0; a <= size - 1; a++) {
            indices.add(a);
        }

        for (int a = 1; a <= size - 1; a++) {
            if(found == 1)
                break;
            IGenerator<List<Integer>> statementIndices = Generator.combination(indices)
                    .simple(a);
            for (List<Integer> combination : statementIndices) {
                i = i + 1;//a counter to keep track of modification number
                if(found == 0) {
                    //System.out.println(combination);
                    //in this for loop, read the integer indices in list and set isprocessing for these statements in codeparts list
                    for (int index : combination) {
                        StatementVisitor.codePartsList.get(index).setProcessing(true);
                    }
                    String codePartsJoined = "";
                    for (CodeParts cp : StatementVisitor.codePartsList) {
                        if (!cp.isProcessing())
                            codePartsJoined = codePartsJoined.concat(cp.getPartName());
                    }
                    //create new file with not processing codeparts
                    String program = "public class " + StatementVisitor.className + "Mod" + i + " { \n" +
                            StatementVisitor.methodBody1Signature + "\n" +
                            //"public {\n" +
                            codePartsJoined +
                            "}\n" +
                            StatementVisitor.methodBody2 + "\n\n" +
                            "}";

                    numMutations = i;
                    File file = new File(pythonTestFilePath);
                    try {
                        // Creates a Writer using FileWriter
                        file.setWritable(true);
                        file.setReadable(true);
                        FileWriter output = new FileWriter(file);
                        FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitor.className + "Mod" + i + ".java");
                        // Writes the program to file
                        output.write(program);
                        output2.write(program);
                        //System.out.println("Data is written to the file.");
                        // Closes the writer
                        output.close();
                        output2.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                    //get prediction for new file
                    int prediction = getPrediction();
                    System.out.println("Prediction is: " + prediction);

                    //if preserved, set codepart as unnecessary
                    if (prediction == 1) {
                        for (int index : combination) {
                            StatementVisitor.codePartsList.get(index).setNecessary(false);
                        }
                    }

                    //else if flipped set as necessary

                    else {
                        numWheatStmts = 0;
                        numNecStmts = 0;
                        for (int index : combination) {
                            StatementVisitor.codePartsList.get(index).setNecessary(true);
                            numWheatStmts += 1;
                            numNecStmts += 1;
                        }
                        System.out.println("Found necessary statements");
                        wheatStatus = "Found necessary statements";
                        for (CodeParts cp : StatementVisitor.codePartsList) {
                            if (cp.isNecessary()) {
                                necStmts = necStmts.concat(cp.getPartName());
                            }
                        }
                        writeWheatNecOutputToFile(wheatStatus,numNecStmts,necStmts);
                        necessarycombination =  combination;
                        boolean isSuffToo = getSufficientCodePartsForComb(combination);
                        if(isSuffToo) {
                            found = 1;
                            wheatStatus = "Found suf and nec statements";
                            break;
                        }
                        else{
                            System.out.println("Necessary statements not sufficient so proceeding.");
                            for (int index : combination) {
                                StatementVisitor.codePartsList.get(index).setNecessary(false);
                                numWheatStmts = 0;
                                numNecStmts = 0;
                            }
                        }

                    }
                    for (int index : combination) {
                        StatementVisitor.codePartsList.get(index).setProcessing(false);
                    }
                    //end for
                }
            }
        }

        boolean hasNecessary = false;
        boolean hasWheat = false;
            for (CodeParts cp : StatementVisitor.codePartsList) {
                if (cp.isNecessary() && cp.isSufficient()) {
                    hasWheat = true;
                    //System.out.println("");
                    wheatStmts = wheatStmts.concat(cp.getPartName());
                    necStmts = necStmts.concat(cp.getPartName());
                }
            }
        if(hasWheat)
        {
            System.out.println("Wheat statements are: "+ wheatStmts);
            writeWheatNecOutputToFile(wheatStatus,numWheatStmts,wheatStmts);

        }
        if(!hasNecessary && !hasWheat)
            writeWheatNecOutputToFile("No wheat",numNecStmts,necStmts);
        return necessarycombination;
    }

    private static void testProblemCodePartsKComb() throws IOException{
        int prediction = getPredictionBothSignVoid();
        System.out.println("Prediction with both method signatures void is: " + prediction);
        if(prediction==0)
        {
            String codeParts = getCulpritStatementsByMutations();
            if(!codeParts.contentEquals(""))
            {
                //get prediction with getting back signatures in final mutated file
                prediction = getPredictionMutatedAndBothSign(codeParts);
                //if prediction is 0
                if(prediction == 0)//output the signature and culprit statements
                {
                 System.out.println("Status: The signatures and culprit statements are both problematic:");
                    Parser.culpritStatus = "signatures and culprit statements are both problematic";
                    for (CodeParts cp : StatementVisitor.codePartsList) {
                        if (cp.isCulprit()) {
                            numCuplritStmts += 1;
                            System.out.println(cp.getPartName());
                            culpritStmts = culpritStmts.concat(cp.getPartName());
                        }
                    }
                }
                else {
                    //output culprit statements only
                    System.out.println("Status: Only statements are culprits");
                    Parser.culpritStatus = "Only statements are culprits";
                    for (CodeParts cp : StatementVisitor.codePartsList) {
                        if (cp.isCulprit()) {
                            System.out.println(cp.getPartName());
                        }
                    }

                }

            }
            else {
                System.out.println("Status: I shouldn't be here!");
                Parser.culpritStatus = "No culprits in code parts found";
            }
        }
        else {
            System.out.println("Status: Signature is the problem and the statements are not the problem");
            Parser.culpritStatus = "signature is culprit not statements";
        }

    }

    private static int getPredictionMutatedAndBothSign(String codeParts) throws IOException {
        //change sign of both methods to void main and get prediction
        String program = "public class " + StatementVisitor.className + "MutatedAndBothSign" +  " { \n" +
                StatementVisitor.methodBody1Signature + "\n" +
                codeParts +
                "}\n\n" +
                StatementVisitor.methodBody2Signature + "\n" +
                StatementVisitor.methodBody2InnerStmts+ "\n" +
                "}\n\n" +
                "}";

        File file = new File(pythonTestFilePath);
        try {
            file.setWritable(true);
            file.setReadable(true);
            FileWriter output = new FileWriter(file);
            FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitor.className + "MutatedAndBothSign.java");
            output.write(program);
            output2.write(program);
            //System.out.println("Data is written to the file.");
            // Closes the writer
            output.close();
            output2.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        //get prediction for new file
        int prediction = getPrediction();
        return prediction;
    }

    private static String getCulpritStatementsByMutations() throws IOException {
        //System.out.println();
        //Make statement level mutations and reductions with Modbothsignvoid.java file
        String codePartsJoined = "";
        boolean flippedFomFalseToTrue = false;
        int lastSuccessfulModification = 0;
        int i = 0;
        int found = 0;
        //get combinations of indices of codePartsList
        //for each combination, set the statements in those indices to isProcessing and
        //get the prediction for remaining code, iterate over statements processing and set them as necessary or not necessary
        //if at any time necessary statements found stop the loop and return necessary statements

        int size = StatementVisitor.codePartsList.size();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int a = 0; a <= size - 1; a++) {
            indices.add(a);
        }

        for (int a = 1; a <= size - 1; a++) {
            if(found == 1)
                break;
            IGenerator<List<Integer>> statementIndices = Generator.combination(indices)
                    .simple(a);
            for (List<Integer> combination : statementIndices) {
                i = i + 1;//a counter to keep track of modification number
                if(found == 0) {
                    //System.out.println(combination);
                    //in this for loop, read the integer indices in list and set isprocessing for these statements in codeparts list
                    for (int index : combination) {
                        StatementVisitor.codePartsList.get(index).setProcessing(true);
                    }
                    codePartsJoined = "";
                    for (CodeParts cp : StatementVisitor.codePartsList) {
                        if (!cp.isProcessing())
                            codePartsJoined = codePartsJoined.concat(cp.getPartName());
                    }


                    //create new file with not processing codeparts
                    String program = "public class " + StatementVisitor.className + "CulpritMod" + i + " { \n" +
                            "public void main(){\n" +
                            codePartsJoined +
                            "}\n\n" +
                            "public void main(){\n" +
                            StatementVisitor.methodBody2InnerStmts+ "\n" +
                            "}\n\n" +
                            "}";

                    File file = new File(pythonTestFilePath);
                    try {
                        // Creates a Writer using FileWriter
                        //File file = new File("G:/pythontestfolder/test.java");
                        file.setWritable(true);
                        file.setReadable(true);
                        FileWriter output = new FileWriter(file);
                        FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitor.className + "CulpritMod" + i + ".java");
                        numMutations = i;
                        // Writes the program to file
                        output.write(program);
                        output2.write(program);
                        //System.out.println("Data is written to the file.");
                        // Closes the writer
                        output.close();
                        output2.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                    //get prediction for new file
                    int prediction = getPrediction();
                    //System.out.println("Prediction is: " + prediction);

                    //if preserved, set codepart as unnecessary
                    if (prediction == 0) {
                        for (int index : combination) {
                            StatementVisitor.codePartsList.get(index).setCulprit(false);
                        }


                    }

                    //else of flipped set as culprit

                    else {
                        for (int index : combination) {
                            StatementVisitor.codePartsList.get(index).setCulprit(true);
                        }
                        System.out.println("Found problem statements so breaking the combination loop");
                        Parser.culpritStatus = "found culprit";
                        found = 1;
                        flippedFomFalseToTrue = true;
                        lastSuccessfulModification = i;
                        break;

                    }
                    //codePart.setNecessary(true);
                    //set processing as false
                    //codePart.setProcessing(false);
                    for (int index : combination) {
                        StatementVisitor.codePartsList.get(index).setProcessing(false);
                    }
                    //end for
                }
            }
        }

        boolean hasCulprit = false;
        for (CodeParts cp : StatementVisitor.codePartsList) {
            if (cp.isCulprit()) {
                hasCulprit = true;
                //System.out.println(cp.getPartName());
            }
        }
        if(!hasCulprit) {
            codePartsJoined = "";
            System.out.println("No one of the code parts is problematic.");
            Parser.culpritStatus = "culprit not found for any combination of statements";
        }
        return codePartsJoined;
    }

    private static int getPredictionBothSignVoid() throws IOException {
        //change sign of both methods to void main and get prediction
        String program = "public class " + StatementVisitor.className + "Modbothsignvoid" +  " { \n" +
                "public void main(){\n" +
                StatementVisitor.methodBody1InnerStmts+ "\n" +
                "}\n\n" +
                "public void main(){\n" +
                StatementVisitor.methodBody2InnerStmts+ "\n" +
                "}\n\n" +
                "}";

        File file = new File(pythonTestFilePath);
        try {

            file.setWritable(true);
            file.setReadable(true);
            FileWriter output = new FileWriter(file);
            FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitor.className + "Modbothsignvoid.java");

            // Writes the program to file
            output.write(program);
            output2.write(program);
            //System.out.println("Data is written to the file.");

            // Closes the writer
            output.close();
            output2.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

        //get prediction for new file
        int prediction = getPrediction();
        return prediction;
    }

    static int checkPredictionOfOriginalFile(File file) throws IOException {
        FileReader ins = new FileReader(file);
        FileWriter outs = new FileWriter(pythonTestFilePath);

        try {

            int ch;
            while ((ch = ins.read()) != -1) {
                outs.write(ch);
            }
        } catch (IOException e) {
            System.out.println(e);
            System.exit(-1);
        } finally {
            try {
                ins.close();
                outs.close();
            } catch (IOException e) {}
        }

        int result = getPrediction();
        //System.out.println("Original file prediction: "+ result);

        return result;

    }



    static int checkPredictionOfOriginalStatements(List<CodeParts> codePartsList) throws IOException {

        int result = 0;
        //make test program using all statements in codePartsList
        String codePartsJoined = "";
        for (CodeParts cp : codePartsList) {
                codePartsJoined = codePartsJoined.concat(cp.getPartName());
                //System.out.println(cp.getPartName());
        }
        //create new file
        String program = "public class "+ StatementVisitor.className+"AllCodeParts" +" { \n" +
                StatementVisitor.methodBody1Signature + "\n" +
                codePartsJoined +
                "}\n"+
                StatementVisitor.methodBody2 + "\n\n" +
                "}";

        File file = new File(pythonTestFilePath);
        try {
            // Creates a Writer using FileWriter
            //File file = new File("G:/pythontestfolder/test.java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter output = new FileWriter(file);
            FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitor.className+"AllCodeParts.java");

            // Writes the program to file
            output.write(program);
            output2.write(program);
            //System.out.println("Data is written to the file.");

            // Closes the writer
            output.close();
            output2.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }


        //get prediction for new file
        result = getPrediction();
        //System.out.println("Original code parts prediction: "+ result);

        return result;
    }

    static int checkPredictionOfOriginalStatements2(List<CodeParts> codePartsList) throws IOException {

        int result = 0;
        //make test program using all statements in codePartsList
        String codePartsJoined = "";
        for (CodeParts cp : codePartsList) {
            codePartsJoined = codePartsJoined.concat(cp.getPartName());
            //System.out.println(cp.getPartName());
        }
        //create new file
        String program = "public class "+ StatementVisitor.className+"AllCodeParts" +" { \n" +
                codePartsJoined +
                "}\n"+
                StatementVisitor.methodBody2 + "\n\n" +
                "}";

        File file = new File(pythonTestFilePath);
        try {
            // Creates a Writer using FileWriter
            //File file = new File("G:/pythontestfolder/test.java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter output = new FileWriter(file);
            FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitor.className+"AllCodeParts.java");

            // Writes the program to file
            output.write(program);
            output2.write(program);
            //System.out.println("Data is written to the file.");

            // Closes the writer
            output.close();
            output2.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }


        //get prediction for new file
        result = getPrediction();
        //System.out.println("Original code parts prediction: "+ result);

        return result;
    }

    static int checkPredictionOfOriginalStatementsofm2(List<CodeParts> codePartsListm2) throws IOException {

        int result = 0;
        //make test program using all statements in codePartsListm2
        String codePartsJoined = "";
        for (CodeParts cp : codePartsListm2) {
            codePartsJoined = codePartsJoined.concat(cp.getPartName());
        }
        //create new file
        String program = "public class "+ StatementVisitor.className+"AllCodeParts" +" { " +
                StatementVisitor.methodBody1 + "\n\n" +
                codePartsJoined +
                "}\n"+
                "\n" +
                "}";

        File file = new File(pythonTestFilePath);
        try {
            // Creates a Writer using FileWriter
            //File file = new File("G:/pythontestfolder/test.java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter output = new FileWriter(file);
            FileWriter output2 = new FileWriter(m2mutationsOutputFolderPath+ StatementVisitor.className+"AllCodePartsm2.java");

            // Writes the program to file
            output.write(program);
            output2.write(program);
            //System.out.println("Data is written to the file.");

            // Closes the writer
            output.close();
            output2.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }


        //get prediction for new file
        result = getPrediction();
        //System.out.println("Original code parts prediction: "+ result);

        return result;
    }
    private static int getPrediction() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_VERSION, resolvePythonScriptPath(pythonCodeFilePath) );
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());
        //System.out.println("Results length: "+ results.size());
        //System.out.println("Results are as follows: "+ results);
        //System.out.println("Processing...");
        //System.out.println("Prediction is as follows: "+ results.get(3).substring(results.get(3).length()-1));
        int index = results.size()-1;
        int prediction = -1;
        for(String s: results)
        {
            if(s.contains("prediction =")) {
                prediction = Integer.parseInt(s.substring(s.length() - 1));
                break;
            }

        }
        return prediction;
    }

    public static String resolvePythonScriptPath(String filename) {
        //File file = new File("src/test/resources/" + filename);
        File file = new File( filename);
        return file.getAbsolutePath();
    }

    public static List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    /**
     * Reads a ICompilationUnit and creates the AST DOM for manipulating the
     * Java source file
     *
     * @param unit
     * @return
     */

    public static CompilationUnit parse(String unit) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
        //parser.setCompilerOptions(options);
        //parser.setSource(unit.toCharArray());
        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setBindingsRecovery(true);
        File f = new File(currentFilePath);
        String unitName = f.getName();
        parser.setUnitName(unitName);
        String[] classpath = {CLASSPATH};
        String[] sources = {};
        parser.setEnvironment(classpath, sources, new String[] {}, true);
        parser.setSource(unit.toCharArray());
        CompilationUnit cu = null;
        try{
            cu = (CompilationUnit) parser.createAST(null);// parse
        }catch(Exception ex){
            System.out.println("Unable to create AST!" + ex.getMessage());
            return null;

        }
        return cu;
    }

    //read file content into a string
    public static String readFileToString(String filePath) throws IOException
    {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1)
        {
            //System.out.println(numRead);
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }

        reader.close();

        return  fileData.toString();
    }



}