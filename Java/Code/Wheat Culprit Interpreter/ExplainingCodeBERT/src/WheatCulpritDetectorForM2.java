//import methodsearch.LuceneWriteIndexExample;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;

//import ModelInterpreter.CodeParts;
//import ModelInterpreter.DirectoryExplorer;
//import ModelInterpreter.SpecialCharFileReader;
//import ModelInterpreter.StatementVisitorform2;
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

public class WheatCulpritDetectorForM2 {

    static String PYTHON_VERSION = "python3";
    static String pythonTestFilePath = "pythontestfolder2/test.java";
    static String pythonCodeFilePath = "codebertoutputm2.py";
    static String datasetFolderPath = ".\\Java\\Data\\100JavaClonePairs";
    static String mutationsOutputFolderPath = ".\\Java\\Data\\m1Mutations";
    static String CLASSPATH = "rt.jar";
    static String culpritlogfile = "m2_culpritlog.csv";
    static String wheatlogfile = "m2_wheatlog.csv";
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
    //public static Method currentMethod = null;
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
            if(StatementVisitorform2.codePartsListm2.size()<=10) {
                int predictionFromOriginalFile = checkPredictionOfOriginalFile(currentFile);
                int predictionFromCodeParts = checkPredictionOfOriginalStatements(StatementVisitorform2.codePartsListm2);
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
                } else
                    System.out.println("Error in prediction codebertoutput.py");
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
            pw.println(d.toString()+","+ StatementVisitorform2.className+","+predictionFromOriginalFile+","+predFromCodeParts+","+culpritStatus+","+numCuplritStmts +","+ numMutations+","+ StatementVisitorform2.methodBody1Signature.replace(",",";")+","+ StatementVisitorform2.methodBody2Signature.replace(",",";")+","+culpritStmts);

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
            pw.println(d.toString()+","+ StatementVisitorform2.className+","+wheatStatus+","+numStmts +","+ numMutations+","+ stmts);

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
            StatementVisitorform2.codePartsListm2.get(index).setProcessing(true);
        }
        String codePartsJoined = "";
        for (CodeParts cp : StatementVisitorform2.codePartsListm2) {
            if (cp.isProcessing())
                codePartsJoined = codePartsJoined.concat(cp.getPartName());
        }


        //create new file with only the one codepart
        String program = "public class " + StatementVisitorform2.className + "Mod" + i + " { \n" +
                StatementVisitorform2.methodBody1 + "\n" +
                codePartsJoined +
                "}\n" +
                 "\n" +
                "}";

        File file = new File(pythonTestFilePath);
        try {
            // Creates a Writer using FileWriter
            //File file = new File("G:/pythontestfolder/test.java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter output = new FileWriter(file);
            FileWriter output2 = new FileWriter(mutationsOutputFolderPath + StatementVisitorform2.className + "SuffMod" + i + ".java");

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
                StatementVisitorform2.codePartsListm2.get(index).setSufficient(true);
            }

            System.out.println("Found sufficient statements so breaking the combination loop");
            found = true;

        }

        //else if flipped, set as not sufficient

        else {
            for (int index : combination) {
                StatementVisitorform2.codePartsListm2.get(index).setSufficient(false);
            }
        }

        for (int index : combination) {
            StatementVisitorform2.codePartsListm2.get(index).setProcessing(false);
        }
        //output codeparts with sufficient true
        System.out.println("Sufficient code parts for file: " + currentFilePath );
        boolean hasSufficient = false;
        for (CodeParts cp : StatementVisitorform2.codePartsListm2) {
            if (cp.isSufficient()) {
                hasSufficient = true;
                System.out.println(cp.getPartName());
            }
        }
        return hasSufficient;

    }


    private static void showCodeParts() {
        for (CodeParts cp : StatementVisitorform2.codePartsListm2) {
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
            StatementVisitorform2 walk1 = new StatementVisitorform2();
            parse.accept(walk1);
        }
    }

    public static void resetCodeParts() {
        StatementVisitorform2.codePartsListm2 = new ArrayList<>();
        StatementVisitorform2.codePartsListm2 = new ArrayList<>();
        StatementVisitorform2.methodBody1Signature = "";
        StatementVisitorform2.methodBody2 = "";
        StatementVisitorform2.methodBody1InnerStmts = "";
        StatementVisitorform2.methodBody2Signature = "";
        StatementVisitorform2.methodBody2InnerStmts = "";
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
        //get combinations of indices of codePartsListm2
        //for each combination, set the statements in those indices to isProcessing and
        //get the prediction for remaining code, iterate over statements processing and set them as necessary or not necessary
        //if at any time necessary statements found stop the loop and return necessary statements

        int size = StatementVisitorform2.codePartsListm2.size();
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
                        StatementVisitorform2.codePartsListm2.get(index).setProcessing(true);
                    }
                    String codePartsJoined = "";
                    for (CodeParts cp : StatementVisitorform2.codePartsListm2) {
                        if (!cp.isProcessing())
                            codePartsJoined = codePartsJoined.concat(cp.getPartName());
                    }
                    //create new file with not processing codeparts
                    String program = "public class " + StatementVisitorform2.className + "Mod" + i + " { \n" +
                            StatementVisitorform2.methodBody1 + "\n" +
                            codePartsJoined +
                            "}\n"+"\n" +
                            "}";

                    numMutations = i;

                    File file = new File(pythonTestFilePath);
                    try {
                        // Creates a Writer using FileWriter
                        //File file = new File("G:/pythontestfolder/test.java");
                        file.setWritable(true);
                        file.setReadable(true);
                        FileWriter output = new FileWriter(file);
                        FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitorform2.className + "Mod" + i + ".java");

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
                            StatementVisitorform2.codePartsListm2.get(index).setNecessary(false);
                        }
                    }

                    //else if flipped set as necessary

                    else {
                        numWheatStmts = 0;
                        numNecStmts = 0;
                        for (int index : combination) {
                            StatementVisitorform2.codePartsListm2.get(index).setNecessary(true);
                            numWheatStmts += 1;
                            numNecStmts += 1;
                        }
                        System.out.println("Found necessary statements");
                        wheatStatus = "Found necessary statements";
                        for (CodeParts cp : StatementVisitorform2.codePartsListm2) {

                            if (cp.isNecessary()) {

                                necStmts = necStmts.concat(cp.getPartName());
                            }
                        }
                        writeWheatNecOutputToFile(wheatStatus,numNecStmts,necStmts);
                        //writeWheatOutputToFile(wheatStatus);

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
                                StatementVisitorform2.codePartsListm2.get(index).setNecessary(false);
                                numWheatStmts = 0;
                                numNecStmts = 0;
                            }
                        }

                    }
                    for (int index : combination) {
                        StatementVisitorform2.codePartsListm2.get(index).setProcessing(false);
                    }
                    //end for
                }
            }
        }
        boolean hasNecessary = false;
        boolean hasWheat = false;
        for (CodeParts cp : StatementVisitorform2.codePartsListm2) {

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
        if(!hasNecessary &&!hasWheat)
            writeWheatNecOutputToFile("No wheat",numNecStmts,necStmts);

        return necessarycombination;
    }

    private static void testProblemCodePartsKComb() throws IOException{

        String codeParts = getCulpritStatementsByMutations();
        if(!codeParts.contentEquals("")) {
            for (CodeParts cp : StatementVisitorform2.codePartsListm2) {
                if (cp.isCulprit()) {
                    numCuplritStmts += 1;
                    System.out.println(cp.getPartName());
                    culpritStmts = culpritStmts.concat(cp.getPartName());
                }
            }
        }

    }

    private static String getCulpritStatementsByMutations() throws IOException {
        //System.out.println();
        //Make statement level mutations and reductions with Modbothsignvoid.java file
        String codePartsJoined = "";
        boolean flippedFomFalseToTrue = false;
        int lastSuccessfulModification = 0;
        int i = 0;
        int found = 0;
        //get combinations of indices of codePartsListm2
        //for each combination, set the statements in those indices to isProcessing and
        //get the prediction for remaining code, iterate over statements processing and set them as necessary or not necessary
        //if at any time necessary statements found stop the loop and return necessary statements

        int size = StatementVisitorform2.codePartsListm2.size();
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
                        StatementVisitorform2.codePartsListm2.get(index).setProcessing(true);
                    }
                    codePartsJoined = "";

                    for (CodeParts cp : StatementVisitorform2.codePartsListm2) {
                        if (!cp.isProcessing())
                            codePartsJoined = codePartsJoined.concat(cp.getPartName());
                    }
                    //create new file with not processing codeparts
                    String program = "public class " + StatementVisitorform2.className + "CulpritMod" + i + " { \n" +
                            StatementVisitorform2.methodBody1+ "\n" +
                            codePartsJoined +
                            "}\n\n" +
                            "}";

                    File file = new File(pythonTestFilePath);
                    try {
                        // Creates a Writer using FileWriter
                        //File file = new File("G:/pythontestfolder/test.java");
                        file.setWritable(true);
                        file.setReadable(true);
                        FileWriter output = new FileWriter(file);
                        FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitorform2.className + "CulpritMod" + i + ".java");

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
                            StatementVisitorform2.codePartsListm2.get(index).setCulprit(false);
                        }
                    }

                    //else of flipped set as culprit
                    else {
                        for (int index : combination) {
                            StatementVisitorform2.codePartsListm2.get(index).setCulprit(true);
                        }
                        System.out.println("Found problem statements so breaking the combination loop");
                        culpritStatus = "found culprit";
                        found = 1;
                        flippedFomFalseToTrue = true;
                        lastSuccessfulModification = i;
                        break;

                    }
                    for (int index : combination) {
                        StatementVisitorform2.codePartsListm2.get(index).setProcessing(false);
                    }
                    //end for
                }
            }
        }
        boolean hasCulprit = false;
        for (CodeParts cp : StatementVisitorform2.codePartsListm2) {
            if (cp.isCulprit()) {
                hasCulprit = true;
                //System.out.println(cp.getPartName());
            }
        }
        if(!hasCulprit) {
            codePartsJoined = "";
            System.out.println("No one of the code parts is problematic.");
            culpritStatus = "culprit not found for any combination of statements";
        }
        return codePartsJoined;
    }

    private static int checkPredictionOfOriginalFile(File file) throws IOException {
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
        System.out.println("Original file prediction: "+ result);

        return result;

    }

    private static int checkPredictionOfOriginalStatements(List<CodeParts> codePartsListm2) throws IOException {

        int result = 0;
        //make test program using all statements in codePartsListm2
        String codePartsJoined = "";
        for (CodeParts cp : codePartsListm2) {
            codePartsJoined = codePartsJoined.concat(cp.getPartName());
            //System.out.println(cp.getPartName());
        }
        //create new file
        String program = "public class "+ StatementVisitorform2.className+"AllCodeParts" +" { \n" +
                StatementVisitorform2.methodBody1 + "\n" +
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
            FileWriter output2 = new FileWriter(mutationsOutputFolderPath+ StatementVisitorform2.className+"AllCodeParts.java");

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
        System.out.println("Original code parts prediction: "+ result);
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
//        return Integer.parseInt(results.get(index).substring(results.get(index).length()-1));
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

    private static void displayStartStopTime(Date startTime) throws FileNotFoundException, UnsupportedEncodingException {
        final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        System.out.println("Start Date and Time was: " + f.format(startTime));
        System.out.println("Stop Date and Time was: " + f.format(new Date()));

        PrintWriter writer = new PrintWriter("output_report.txt", "UTF-8");
        writer.println("Start Date and Time was: " + f.format(startTime));
        writer.println("Stop Date and Time was: " + f.format(new Date()));
        writer.close();
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

        //Map options = JavaCore.getOptions();
        //parser.setCompilerOptions(options);

        File f = new File(currentFilePath);
        String unitName = f.getName();
        parser.setUnitName(unitName);
        //below commented for saad
        //String[] sources = {Constants.SOURCES};
        String[] classpath = {CLASSPATH};
        String[] sources = {};
        //below commented for saad
        //parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
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