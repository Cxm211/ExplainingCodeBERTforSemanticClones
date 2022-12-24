//import methodsearch.LuceneWriteIndexExample;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;

//import ModelInterpreter.CodeParts;
//import ModelInterpreter.DirectoryExplorer;
//import ModelInterpreter.SpecialCharFileReader;
//import ModelInterpreter.TypeAnalysisVisitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.*;
import java.util.*;

public class StmtTypeAnalysis {

    static String datasetFolderPath = ".\\Java\\Data\\100JavaClonePairs";
    static String CLASSPATH = "C:\\Program Files\\Java\\jre1.8.0_144\\lib\\rt.jar";

    public static List<String> types = new ArrayList<String>();
    public static List<File> javaFilesList;
    public static String currentFilePath = null;
    public static int methodEntered = 0;
    public static String fileString = "";
    public static List<String> fullFile = new ArrayList<String>();
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
    public static ArrayList<String> commentsList = new ArrayList<String>();
    public static int m_nStatementCount = 0;
    public static boolean isNestedFunction = false;
    public static String functionData = "";
    public static String F1_fulltext;
    public static String F2_FQN;
    public static String F3_simpleName;


    private static LinkedHashMap<Integer,String> codePartTypeMap = new LinkedHashMap<>();
    private static LinkedHashMap<Integer,String> subExprTypeMap = new LinkedHashMap<>();
    private static LinkedHashMap<String,Integer> stmtTypeCountsMap = new LinkedHashMap<>();
    private static LinkedHashMap<String,Integer> exprTypeCountsMap = new LinkedHashMap<>();

    public static void main(String args[]) throws Exception {
        String wheatCulStmtNumsFile = "100CulStmtNumsm2permute.csv";
        stmtTypeCountsMap.put("signature",0);
        stmtTypeCountsMap.put("expression",0);
        stmtTypeCountsMap.put("ifstmt",0);
        stmtTypeCountsMap.put("whilestmt",0);
        stmtTypeCountsMap.put("forstmt",0);
        stmtTypeCountsMap.put("typedec",0);
        stmtTypeCountsMap.put("vardec",0);
        stmtTypeCountsMap.put("return",0);
        exprTypeCountsMap.put("methodinvoc",0);
        exprTypeCountsMap.put("classinstancecreation",0);
        exprTypeCountsMap.put("constructorinvocation",0);
        exprTypeCountsMap.put("stringliteral",0);

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
            String fileNamewoJava = fileName.substring(0,dotIndex);
            int cloneIDint = Integer.parseInt(cloneID);
            parseFile();
            int cpcounter = 0;
            for(CodeParts cp:TypeAnalysisVisitor.codePartsList)
            {
                cpcounter += 1;
                codePartTypeMap.put(cpcounter, cp.getStatementType());
            }

            if(TypeAnalysisVisitor.codePartsList.size()<=10) {

                File file = new File(wheatCulStmtNumsFile);
                try {
                    InputStream ips = new FileInputStream(wheatCulStmtNumsFile);
                    InputStreamReader ipsr = new InputStreamReader(ips);
                    BufferedReader br = new BufferedReader(ipsr);
                    String line;

                    while ((line = br.readLine()) != null) {
                        String[] s = line.split(",");
                        if(s[0].contentEquals(fileNamewoJava))
                        {
                            for(int i=1; i<s.length; i++)
                            {
                               int stmtno = Integer.parseInt(s[i]);

                               if(stmtno!=0) {
                                   String type = codePartTypeMap.get(stmtno);
                                   stmtTypeCountsMap.put(type, stmtTypeCountsMap.get(type) + 1);
                               }
                            }
                        }
                    }


                }catch(Exception ex)
                {

                }

                for(CodeParts cp: TypeAnalysisVisitor.subexpressionsList)
                {
                    String type = cp.getStatementType();
                    exprTypeCountsMap.put(type,exprTypeCountsMap.get(type)+1);
                }

            }

        }

        for (Map.Entry<String, Integer> set : stmtTypeCountsMap.entrySet()) {
            System.out.println(set.getKey()+":"+set.getValue());
        }

        for (Map.Entry<String, Integer> set : exprTypeCountsMap.entrySet()) {
            System.out.println(set.getKey()+":"+set.getValue());
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

    public static void parseFile() throws IOException {
        fileString = readFileToString(currentFilePath);
        try {
            parse = parse(fileString);
        } catch (Exception ex) {
            System.out.println("Could not parse file!");
        }

        if (parse != null) {
            fullFile = SpecialCharFileReader.getFileText(currentFilePath);
            TypeAnalysisVisitor walk1 = new TypeAnalysisVisitor();
            parse.accept(walk1);
        }
    }

    public static void resetCodeParts() {
        TypeAnalysisVisitor.codePartsList = new ArrayList<>();
        TypeAnalysisVisitor.codePartsListm2 = new ArrayList<>();
        TypeAnalysisVisitor.methodBody1Signature = "";
        TypeAnalysisVisitor.methodBody2 = "";

        TypeAnalysisVisitor.methodBody1InnerStmts = "";


        TypeAnalysisVisitor.methodBody2Signature = "";
        TypeAnalysisVisitor.methodBody2InnerStmts = "";
        numCuplritStmts = 0;
        numNecStmts = 0;
        numWheatStmts = 0;
        culpritStmts = "";
        wheatStmts = "";
        necStmts = "";
        numMutations = 0;
        codePartTypeMap = new LinkedHashMap<>();
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