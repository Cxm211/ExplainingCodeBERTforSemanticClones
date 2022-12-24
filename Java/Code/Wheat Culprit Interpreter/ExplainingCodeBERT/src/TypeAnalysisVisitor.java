//import ModelInterpreter.CodeParts;
//import ModelInterpreter.StmtTypeAnalysis;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class TypeAnalysisVisitor extends ASTVisitor{
    // private CodeMetadata codeMetadata;
    boolean firstMethodEnetered = false;
    boolean firstMethodExited = false;
    TypeAnalysisVisitor()
    {
        //codeMetadata = CodeMetadata.getInstance();
    }


    //List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
    //List<MethodInvocation> method_invocations = new ArrayList<MethodInvocation>();

    static List<CodeParts> codePartsList = new ArrayList<CodeParts>();
    static List<CodeParts> subexpressionsList = new ArrayList<CodeParts>();
    static List<CodeParts> codePartsListm1 = new ArrayList<CodeParts>();
    static List<CodeParts> codePartsListm2 = new ArrayList<CodeParts>();
    static String methodBody1Signature;
    static String methodBody1InnerStmts;
    static String methodBody2;
    static String className;
    static String methodBody2Signature;
    static String methodBody2InnerStmts;

    Statement s ;

    private static int startLine;
    private static int endLine;



    private String tokenize4sourcerer(String input) {
        String tokens = "";
        String  result="";
        //removing special characters and numbers
        result = input.replaceAll("[^a-zA-Z]", " ");
        //tokenizing by space
        String[] tokenizedBySpaces = result.split("\\s+");
        //tokenize each term by camelcase and underscore and hyphen
        for(String term: tokenizedBySpaces)
        {
            for (String w : term.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {

                tokens = tokens + " " + w;
            }
        }

        return tokens;
    }

//    @Override
//    public boolean visit(TypeDeclaration node)
//    {
//        String typeName = node.getName().toString();
//        StmtTypeAnalysis.currentClass = typeName;
//        return super.visit(node);
//    }

    @Override
    public boolean visit(TypeDeclaration node) {

        //System.out.println("Class name is: " + node.getName());
        className = node.getName().toString();
        return true;

    }

    @Override
    public boolean visit(MethodDeclaration node) {
        //methods.add(node);
        StmtTypeAnalysis.methodEntered += 1;
        if(StmtTypeAnalysis.methodEntered == 1 && !firstMethodEnetered)
        {
            firstMethodEnetered = true;
            StmtTypeAnalysis.m1AddNodeCount("Parameters",node.parameters().size());
            StmtTypeAnalysis.m1AddNodeCount("Exceptions",node.thrownExceptionTypes().size());
            StmtTypeAnalysis.currentFunctionName = node.getName().toString();
            startLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition());
            int nodeLength = node.getLength();
            endLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition() + nodeLength);
            methodBody1Signature = StmtTypeAnalysis.fullFile.get(startLine-1);

            if(methodBody1Signature.contains("@")) {
                methodBody1Signature = StmtTypeAnalysis.fullFile.get(startLine);
                StmtTypeAnalysis.F1_fulltext = getMethodInnerText(StmtTypeAnalysis.fullFile, startLine + 1, endLine);
                methodBody1InnerStmts = getMethodInnerText(StmtTypeAnalysis.fullFile, startLine + 1, endLine);
            }
            else {
                StmtTypeAnalysis.F1_fulltext = getMethodInnerText(StmtTypeAnalysis.fullFile, startLine, endLine);
                methodBody1InnerStmts = getMethodInnerText(StmtTypeAnalysis.fullFile, startLine, endLine);
            }

            codePartsList.add(new CodeParts(CodeParts.STATEMENT, methodBody1Signature+"\n","signature"));

            //read each line of method body as a CodeParts object
//            for(int i = startLine -1; i<endLine; i++) {
//                result = fullFile.get(i);
//            }

// the following code is the correct way to parse statements
            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setSource(StmtTypeAnalysis.F1_fulltext.toCharArray());

            parser.setKind(ASTParser.K_STATEMENTS);

            Block block = (Block) parser.createAST(null);

            //here can access the first element of the returned statement list
            //String str = block.statements().get(1).toString();

            //System.out.println(str);

            block.accept(new ASTVisitor() {

//                public boolean visit(SimpleName node) {
//
//                    System.out.println("Simple Node Name: " + node.getFullyQualifiedName());
//                    return true;
//                }

                public boolean visit(ExpressionStatement node) {
                    //System.out.println("Expression Node Name: " + node.toString());
                    StmtTypeAnalysis.m1AddNodeCount("ExpressionStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, node.toString(),"expression"));
                    return true;

                }
                public boolean visit(TypeDeclarationStatement node) {
                    //System.out.println("TypeDec Node Name: " + node.toString());
                    StmtTypeAnalysis.m1AddNodeCount("TypeDeclarationStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, node.toString(),"typedec"));
                    return true;

                }
                public boolean visit(VariableDeclarationStatement node) {
                    //System.out.println("VarDec Node Name: " + node.toString());
                    StmtTypeAnalysis.m1AddNodeCount("VariableDeclarationStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, node.toString(),"vardec"));
                    return true;

                }

                public boolean visit(ReturnStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, node.toString(),"return"));
                    return true;

                }

                public boolean visit(IfStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    //System.out.println("If statement expression is: " + node.getExpression());
                    StmtTypeAnalysis.m1AddNodeCount("IfStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, "if("+node.getExpression().toString()+")\n","ifstmt"));
                    return true;
                }

                public boolean visit(WhileStatement node) {

                    //System.out.println("ReturnNode Name: " + node.toString());
                    //System.out.println("While statement expression is: " + node.getExpression());
                    StmtTypeAnalysis.m1AddNodeCount("WhileStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, "while("+node.getExpression().toString()+")\n","whilestmt"));
                    return true;
                }

                public boolean visit(ForStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    StmtTypeAnalysis.m1AddNodeCount("ForStatement");
                    List for_inits = node.initializers();
                    List for_updts = node.updaters();
                    String fullExpression =  "for("+ for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString()+")"+ "\n";
                    //System.out.println("For statement expression is: " + for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString());
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, fullExpression,"forstmt"));
                    return true;
                }
                public boolean visit(MethodInvocation node) {
                    StmtTypeAnalysis.m1AddNodeCount("MethodInvocation");
                    subexpressionsList.add(new CodeParts(CodeParts.EXPRESSION, node.toString(),"methodinvoc"));
                    //System.out.println("Invo: "+node.toString());
                    return true;
                }
//                public boolean endvisit(ForStatement node) {
//                    //System.out.println("ReturnNode Name: " + node.toString());
//                     codePartsList.add(new CodeParts(CodeParts.STATEMENT, "}"));
//                    return true;
//                }

                public boolean visit(TryStatement node) {

                    StmtTypeAnalysis.m1AddNodeCount("TryStatement");
//                    List<Statement> trystmts = node.getBody().statements();
//                    for(Statement s: trystmts)
//                    {
//
//                        System.out.println("Try Block Inner Statement: "+s.toString());
//
//                    }
                    return true;
                }

                public boolean visit(SwitchStatement node) {

                    StmtTypeAnalysis.m1AddNodeCount("SwitchStatement");
                    return true;
                }
                public boolean visit(ClassInstanceCreation node) {

                    StmtTypeAnalysis.m1AddNodeCount("ClassInstanceCreation");
                    subexpressionsList.add(new CodeParts(CodeParts.EXPRESSION, node.toString(),"classinstancecreation"));
                    return true;
                }
                public boolean visit(ConstructorInvocation node) {

                    StmtTypeAnalysis.m1AddNodeCount("ConstructorInvocation");
                    subexpressionsList.add(new CodeParts(CodeParts.EXPRESSION, node.toString(),"constructorinvocation"));
                    return true;
                }
                public boolean visit(LineComment node) {

                    StmtTypeAnalysis.m1AddNodeCount("LineComment");
                    return true;
                }
                public boolean visit(BlockComment node) {

                    StmtTypeAnalysis.m1AddNodeCount("BlockComment");
                    return true;
                }
                public boolean visit(PrimitiveType node) {

                    StmtTypeAnalysis.m1AddNodeCount("PrimitiveType");
                    return true;
                }
                public boolean visit(StringLiteral node) {

                    StmtTypeAnalysis.m1AddNodeCount("StringLiteral");
                    subexpressionsList.add(new CodeParts(CodeParts.EXPRESSION, node.toString(),"stringliteral"));
                    return true;
                }


            });
            //now you have all statements on CodePartsList list.


        }

        if(StmtTypeAnalysis.methodEntered == 1 && firstMethodExited) {

            StmtTypeAnalysis.m2AddNodeCount("Parameters",node.parameters().size());
            StmtTypeAnalysis.m2AddNodeCount("Exceptions",node.thrownExceptionTypes().size());

            //StmtTypeAnalysis.currentFunctionName = node.getName().toString();
            startLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition());
            int nodeLength = node.getLength();
            endLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition() + nodeLength);
            methodBody2Signature = StmtTypeAnalysis.fullFile.get(startLine - 1);
            methodBody2 = getMethodText(StmtTypeAnalysis.fullFile, startLine, endLine);

            methodBody2Signature = StmtTypeAnalysis.fullFile.get(startLine-1);
            if(methodBody2Signature.contains("@")) {
                methodBody2Signature = StmtTypeAnalysis.fullFile.get(startLine);
                methodBody2InnerStmts = getMethodInnerText(StmtTypeAnalysis.fullFile, startLine + 1, endLine);
            }
            else
                methodBody2InnerStmts = getMethodInnerText(StmtTypeAnalysis.fullFile,startLine,endLine);

            codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, methodBody2Signature+"\n"));

            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setSource(methodBody2InnerStmts.toCharArray());
            parser.setKind(parser.K_STATEMENTS);
            Block block = (Block) parser.createAST(null);
            block.accept(new ASTVisitor() {


                public boolean visit(ExpressionStatement node) {
                    //System.out.println("Expression Node Name: " + node.toString());
                    StmtTypeAnalysis.m2AddNodeCount("ExpressionStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, node.toString()));
                    return true;

                }
                public boolean visit(TypeDeclarationStatement node) {
                    //System.out.println("TypeDec Node Name: " + node.toString());
                    StmtTypeAnalysis.m2AddNodeCount("TypeDeclarationStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, node.toString()));
                    return true;

                }
                public boolean visit(VariableDeclarationStatement node) {
                    //System.out.println("VarDec Node Name: " + node.toString());
                    StmtTypeAnalysis.m2AddNodeCount("VariableDeclarationStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, node.toString()));
                    return true;

                }

                public boolean visit(ReturnStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, node.toString()));
                    return true;

                }

                public boolean visit(IfStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    //System.out.println("If statement expression is: " + node.getExpression());
                    StmtTypeAnalysis.m2AddNodeCount("IfStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, "if("+node.getExpression().toString()+")\n"));
                    return true;
                }

                public boolean visit(WhileStatement node) {

                    //System.out.println("ReturnNode Name: " + node.toString());
                    //System.out.println("While statement expression is: " + node.getExpression());
                    StmtTypeAnalysis.m2AddNodeCount("WhileStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, "while("+node.getExpression().toString()+")\n"));
                    return true;
                }

                public boolean visit(ForStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    StmtTypeAnalysis.m2AddNodeCount("ForStatement");
                    List for_inits = node.initializers();
                    List for_updts = node.updaters();
                    String fullExpression =  "for("+ for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString()+")"+ "\n";
                    //System.out.println("For statement expression is: " + for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString());
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, fullExpression));
                    return true;
                }

                public boolean visit(MethodInvocation node) {
                    StmtTypeAnalysis.m2AddNodeCount("MethodInvocation");
                    //System.out.println("Invo: "+node.toString());
                    return true;
                }
//                public boolean endvisit(ForStatement node) {
//                    //System.out.println("ReturnNode Name: " + node.toString());
//                     codePartsList.add(new CodeParts(CodeParts.STATEMENT, "}"));
//                    return true;
//                }

                public boolean visit(TryStatement node) {

                    StmtTypeAnalysis.m2AddNodeCount("TryStatement");
//                    List<Statement> trystmts = node.getBody().statements();
//                    for(Statement s: trystmts)
//                    {
//
//                        System.out.println("Try Block Inner Statement: "+s.toString());
//
//                    }
                    return true;
                }

                public boolean visit(SwitchStatement node) {

                    StmtTypeAnalysis.m2AddNodeCount("SwitchStatement");
                    return true;
                }

                public boolean visit(ClassInstanceCreation node) {

                    StmtTypeAnalysis.m2AddNodeCount("ClassInstanceCreation");
                    return true;
                }
                public boolean visit(ConstructorInvocation node) {

                    StmtTypeAnalysis.m2AddNodeCount("ConstructorInvocation");
                    return true;
                }
                public boolean visit(LineComment node) {

                    StmtTypeAnalysis.m2AddNodeCount("LineComment");
                    return true;
                }
                public boolean visit(BlockComment node) {

                    StmtTypeAnalysis.m2AddNodeCount("BlockComment");
                    return true;
                }
                public boolean visit(PrimitiveType node) {

                    StmtTypeAnalysis.m2AddNodeCount("PrimitiveType");
                    return true;
                }
                public boolean visit(StringLiteral node) {

                    StmtTypeAnalysis.m2AddNodeCount("StringLiteral");
                    return true;
                }


            });
        }
        return super.visit(node);

    }

    public void endVisit(MethodDeclaration node) {

        StmtTypeAnalysis.methodEntered -= 1;
        if(StmtTypeAnalysis.methodEntered == 0)
            firstMethodExited = true;
    }

    private String getMethodText(List<String> fullFile, int startLine,
                                 int endLine) {
        String result = "";
        for(int i = startLine -1; i<endLine; i++)
            result = result + " \n " + fullFile.get(i);
        return result;
    }

    private String getMethodInnerText(List<String> fullFile, int startLine,
                                      int endLine) {
        String result = "";
        for(int i = startLine ; i<endLine-1; i++)
            result = result + " \n " + fullFile.get(i);
        return result;
    }

    private String tokenizeFQN4sourcerer(String currentFilePath) {
        String tokens = "";
        String result = "";
        //take a substring from the file name from src to .java
        if(currentFilePath.contains("src") && currentFilePath.contains(".java")){
            result = currentFilePath.substring(currentFilePath.indexOf("\\src\\")+4, currentFilePath.indexOf(".java"));

            //removing special characters and numbers
            result = result.replaceAll("[^a-zA-Z]", " ");
            //tokenizing by space
            String[] tokenizedBySpaces = result.split("\\s+");
            //tokenize each term by camelcase
            for(String term: tokenizedBySpaces)
            {
                for (String w : term.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {

                    tokens = tokens + " " + w;
                }
            }
        }
        return tokens;
    }

//    @Override
//    public boolean visit(MethodInvocation node)
//    {
//        if(StmtTypeAnalysis.methodEntered >= 1)
//        {
//            int lineNum = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition());
//             if(StmtTypeAnalysis.currentFunctionID != 0)
//            {
//                method_invocations.add(node);
//                String APIName = "";
//                String APIUsage = "";
//                int callerObjTypeID = 0;
//                Expression e = node.getExpression();
//
//
//                //String methodName = node.getName().toString();
//                //ITypeBinding typeBinding = node.getExpression().resolveTypeBinding();
//                //IType type = (IType)typeBinding.getJavaElement();
//                //typeBinding.isFromSource();
//                //System.out.printf("Type %s (method %s) calls %s\n", type, methodName, type.getFullyQualifiedName());
//
//                APIUsage = node.getName().toString();
//                if(e instanceof Name)
//                {
//                    StmtTypeAnalysis.currentApiCallID += 1;
//                    Name n = (Name) e;
////    	 	String firstPart, middleMan = "";
//
////    	 	if(n.isQualifiedName())
////    	 	{
////    	 		String full = n.getFullyQualifiedName();
////    	 		System.out.println("isqualifiedname: "+full);
////       	 	 	}
//                    //System.out.println(node.getExpression().toString());
//                    //System.out.println(node.resolveMethodBinding().getName());
//
//                    ITypeBinding typeBinding = e.resolveTypeBinding();
//                    if ( typeBinding != null)//type resolution found actual type
//                    {
//    	 		/*try {
//    	 		IType j = (IType)typeBinding.getDeclaringClass().getJavaElement();
//    	 		IPackageFragmentRoot an = (IPackageFragmentRoot) j.getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
//    	 		System.out.println("Is archive: "+an.isArchive());
//
//    	 			System.out.println(an.getResolvedClasspathEntry().toString());
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}*/
//
//                        APIName = typeBinding.getName();
//
//                        //boolean a = typeBinding.isTypeVariable();
//                        //boolean b = typeBinding.isClass();
//                        //boolean c = typeBinding.isFromSource();
//                        //boolean d = typeBinding.isFromSource();
//                        //String w = typeBinding.getKey();
//                        //String f = typeBinding.getQualifiedName();
//                        //System.out.println("binding FQN:"+f);
//                        //System.out.println("Type: " + typeBinding.getName());
//                        //get typeID from APIName
//                        //callerObjTypeID = codeMetadata.getTypeId(APIName, StmtTypeAnalysis.projectId);
//
//
//                        //int targetID = codeMetadata.getMethodId(callerObjTypeID, APIUsage, StmtTypeAnalysis.projectId);
//                        //codeMetadata.addInCalls(new Calls(0, StmtTypeAnalysis.currentFunctionID, targetID, lineNum,
//                        //            0,0,0,0));
//
//
//                            codeMetadata.addInAPICallsList(new APICall(0, StmtTypeAnalysis.currentFunctionID, APIName, APIUsage, lineNum,
//                                    0,0,0,0));
//
//                            StmtTypeAnalysis.functionData = StmtTypeAnalysis.functionData.concat(" " + StmtTypeAnalysis.splitCamelCase(APIName));
//                            StmtTypeAnalysis.functionData = StmtTypeAnalysis.functionData.concat(" " + StmtTypeAnalysis.splitCamelCase(APIUsage));
//
//                    }
//                    else
//                    {
//                        APIName = n.toString();
//                        int baseType = codeMetadata.getBaseTypeByTypeName(APIName, StmtTypeAnalysis.projectId);
//                        if(baseType != 2 && baseType != 1)
//                        {
//                            codeMetadata.addInAPICallsList(new APICall(0, StmtTypeAnalysis.currentFunctionID, APIName, APIUsage, lineNum,
//                                    0,0,0,0));
//
//                            StmtTypeAnalysis.functionData = StmtTypeAnalysis.functionData.concat(" " + StmtTypeAnalysis.splitCamelCase(APIName));
//                            StmtTypeAnalysis.functionData = StmtTypeAnalysis.functionData.concat(" " + StmtTypeAnalysis.splitCamelCase(APIUsage));
//                        }
//
//                        //System.out.println("I have no typebinding:" + APIName);
//                    }
//                    //if API call has a middle man and has two levels like a.b.c() then check for a dot in API Name
////    	 	String firstPart, middleMan = "";
////    	 	firstPart = APIName;
////    	 	if(APIName.contains("."))
////    	 	{
////    	 		firstPart = APIName.substring(0, APIName.indexOf("."));
////    	 		//middleMan = APIName.substring(APIName.indexOf(".")+1);
////    	 		//APIName = firstPart + "." + middleMan;
////    	 	}
//                    //APIUsage = node.getName().toString();
//
//                    //System.out.println("API call: " + APIName + "."+ APIUsage);
//
//
//
//                }
//                /////simple method invocation
//                //else
//                //{
//                    //System.out.println("Method Invocation name: " + node.getName());
//                    //String funcName = node.getName().toString();//APIUsage
//
//    		/*if(StmtTypeAnalysis.currentFunctionName.contentEquals("onCreate"))
//        	{
//        		int targetOnStartMethodID = sharedSpace.getTargetOnStartMethodID(currentClassTypeID, StmtTypeAnalysis.projectId);
//        		sharedSpace.addInCalls(new Calls(0, method.id, targetOnStartMethodID, 0,
//	        			1, 0, 0, 0));
//
//        	}*/
//
//                        //Method target = codeMetadata.getTargetMethodId(funcName, StmtTypeAnalysis.projectId);
//                        //codeMetadata.addInCalls(new Calls(0, StmtTypeAnalysis.currentFunctionID, target.id,lineNum,
//                        //        0,0,0,0));
//
//
//                //}
//            }
//        }
//        return super.visit(node);
//    }

    private void setNodeRegion(StructuralPropertyDescriptor location) {

        if(location == IfStatement.EXPRESSION_PROPERTY ||
                location == IfStatement.THEN_STATEMENT_PROPERTY)
        {
            StmtTypeAnalysis.ifBlockRegion = true;
        }
        else
        {
            if(location == IfStatement.ELSE_STATEMENT_PROPERTY)
            {
                StmtTypeAnalysis.elseBlockRegion = true;
            }
            else
            {
                if(location == CatchClause.BODY_PROPERTY)
                {
                    StmtTypeAnalysis.catchBlockRegion = true;
                }
                else
                {
                    StmtTypeAnalysis.basicBlockRegion = true;
                }
            }
        }
    }
//    private boolean isParentThread(int callerObjTypeID)
//    {
//        return codeMetadata.isParentThread(callerObjTypeID);
//
//    }


//    @Override
//    public boolean visit(ClassInstanceCreation node)
//    {
//        if(StmtTypeAnalysis.methodEntered >= 1) {
//            //System.out.println("class instance created: "+ node.toString());
//            String nodeType = node.getType().toString();
//
//            //is it an API?
//            //then add as an api call
//            int lineNum = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition());
//            String APIName = nodeType;
//            if (!nodeType.contains(".")) {
//                String APIUsage = "new";
//
//                codeMetadata.addInAPICallsList(new APICall(0, StmtTypeAnalysis.currentFunctionID, APIName, APIUsage, lineNum,
//                        0, 0, 0, 0));
//                StmtTypeAnalysis.functionData = StmtTypeAnalysis.functionData.concat(" " + StmtTypeAnalysis.splitCamelCase(APIName));
//                StmtTypeAnalysis.functionData = StmtTypeAnalysis.functionData.concat(" " + StmtTypeAnalysis.splitCamelCase(APIUsage));
//
//            }
//        }
//        return super.visit(node);
//
//    }

//    @Override
//    public void endVisit(MethodDeclaration node) {
//        //when we exit a method body whose name is onCreate() we have to add call of current method to
//        //the onStart() method of the same class
//
////        try
////        {
////            int lineNum = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition());
////            Method host;
////            int currentClassTypeID = codeMetadata.getTypeId(StmtTypeAnalysis.currentClass, StmtTypeAnalysis.projectId);
////            host = codeMetadata.getMethodId(StmtTypeAnalysis.currentFunctionName, currentClassTypeID, StmtTypeAnalysis.projectId);
////
////        }
////
////        catch(Exception ex)
////        {
////
////
////        }
//        StmtTypeAnalysis.methodEntered -= 1;
//        if(StmtTypeAnalysis.methodEntered == 0)
//            StmtTypeAnalysis.isNestedFunction = false;
//
//        try
//        {
//            if(!StmtTypeAnalysis.isNestedFunction)
//            {
//                StmtTypeAnalysis.currentMethod.setExpressionStmtsCount(StmtTypeAnalysis.m_nStatementCount);
//                codeMetadata.addInMethod(StmtTypeAnalysis.currentMethod);
//                //StmtTypeAnalysis.commentsList.addAll(StmtTypeAnalysis.getComments(StmtTypeAnalysis.currentFilePath, StmtTypeAnalysis.currentFunctionID,startLine,endLine));
//                //printComments(commentsList);
//                //StmtTypeAnalysis.createLuceneMethodDocument(StmtTypeAnalysis.currentFunctionID, StmtTypeAnalysis.splitCamelCase(StmtTypeAnalysis.currentFunctionName), StmtTypeAnalysis.commentsList, StmtTypeAnalysis.functionData);
//                StmtTypeAnalysis.createLuceneMethodDocument4Sourcerer(StmtTypeAnalysis.currentFunctionID, StmtTypeAnalysis.F1_fulltext, StmtTypeAnalysis.F2_FQN, StmtTypeAnalysis.F3_simpleName, StmtTypeAnalysis.functionData);
//                ////System.out.println("Lucene method document: " + currentFunctionID + ")" + currentFunctionName );
//                StmtTypeAnalysis.functionData = "";
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }



//    @Override
//    public boolean visit(MethodDeclaration node) {
//        StmtTypeAnalysis.m_nStatementCount = 0;
//        methods.add(node);
//        StmtTypeAnalysis.methodEntered += 1;
//        if(StmtTypeAnalysis.methodEntered > 1)
//            StmtTypeAnalysis.isNestedFunction = true;
//        if (!StmtTypeAnalysis.isNestedFunction)
//        {
//            StmtTypeAnalysis.currentFunctionName = node.getName().toString();
//            //StmtTypeAnalysis.functionData = StmtTypeAnalysis.functionData.concat(" " + StmtTypeAnalysis.splitCamelCase(StmtTypeAnalysis.currentFunctionName));
//            StmtTypeAnalysis.currentFunctionID += 1;
//            StmtTypeAnalysis.commentsList = new ArrayList<String>();
//
//            String returnType;
//            if(node.getReturnType2()!=null)
//            {
//                returnType = node.getReturnType2().toString();
//
//                int startLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition());
//                int nodeLength = node.getLength();
//                int endLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition() + nodeLength) ;
//
//
//                StmtTypeAnalysis.currentMethod = new Method(
//                        StmtTypeAnalysis.currentFunctionID,
//                        StmtTypeAnalysis.currentFunctionName,
//                        codeMetadata.getTypeId(returnType, StmtTypeAnalysis.projectId),
//                        codeMetadata.getTypeId(StmtTypeAnalysis.currentClass, StmtTypeAnalysis.projectId),
//                        startLine,
//                        endLine,
//                        StmtTypeAnalysis.fileId, StmtTypeAnalysis.m_nStatementCount);
//
//            }
//            else//It is a constructor!!
//            {
//                int startLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition());
//                int nodeLength = node.getLength();
//                int endLine = StmtTypeAnalysis.parse.getLineNumber(node.getStartPosition() + nodeLength) ;
//
//                StmtTypeAnalysis.currentMethod = new Method(
//                        StmtTypeAnalysis.currentFunctionID,
//                        StmtTypeAnalysis.currentFunctionName,
//                        0,
//                        codeMetadata.getTypeId(StmtTypeAnalysis.currentClass, StmtTypeAnalysis.projectId),
//                        startLine,
//                        endLine,
//                        StmtTypeAnalysis.fileId,
//                        StmtTypeAnalysis.m_nStatementCount);
//
//
//            }
//        }
//
//        return super.visit(node);
//    }


//    @Override
//    public void endVisit(MethodDeclaration node)
//    {
//
//        //System.out.println(StmtTypeAnalysis.currentFunctionID+","+StmtTypeAnalysis.m_nStatementCount);
//
//
//        StmtTypeAnalysis.methodEntered -= 1;
//        if(StmtTypeAnalysis.methodEntered == 0)
//            StmtTypeAnalysis.isNestedFunction = false;
//        if (!StmtTypeAnalysis.isNestedFunction)
//        {
//            StmtTypeAnalysis.currentMethod.setExpressionStmtsCount(StmtTypeAnalysis.m_nStatementCount);
//            codeMetadata.addInMethod(StmtTypeAnalysis.currentMethod);
//        }
//    		/*try
//    		{
//    			if(!StmtTypeAnalysis.isNestedFunction)
//    			{
//    				StmtTypeAnalysis.commentsList.addAll(StmtTypeAnalysis.getComments(StmtTypeAnalysis.currentFilePath, StmtTypeAnalysis.currentMethod));
//    				//printComments(commentsList);
//    				StmtTypeAnalysis.createLuceneMethodDocument(StmtTypeAnalysis.currentFunctionID, StmtTypeAnalysis.currentFunctionName, StmtTypeAnalysis.commentsList, StmtTypeAnalysis.functionData);
//    				////System.out.println("Lucene method document: " + currentFunctionID + ")" + currentFunctionName );
//    				StmtTypeAnalysis.functionData = "";
//    			}
//    		}
//    		catch (IOException e)
//    		{
//    			e.printStackTrace();
//    		}*/
//
//    }

//    public boolean visit (ExpressionStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (AssertStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
    /*public boolean visit (Block node) {
        StmtTypeAnalysis.m_nStatementCount+=1;
        //System.out.println(node.toString());
        return true;
    }*/
//    public boolean visit (BreakStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//
//    public boolean visit (ConstructorInvocation node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (ContinueStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (DoStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (EnhancedForStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (ForStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//
//    public boolean visit (IfStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (LabeledStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (TryStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//
//    public boolean visit (ReturnStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (VariableDeclarationStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (TypeDeclarationStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (WhileStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (ThrowStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (SynchronizedStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (SwitchStatement node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (SwitchCase node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//    public boolean visit (SuperConstructorInvocation node) {
//        StmtTypeAnalysis.m_nStatementCount+=1;
//        //System.out.println(node.toString());
//        return true;
//    }
//


//    public List<MethodDeclaration> getMethods() {
//        return methods;
//    }



}
