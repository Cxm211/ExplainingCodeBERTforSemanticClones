//import ModelInterpreter.CodeParts;
//import ModelInterpreter.NewParser;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class StatementVisitorform1 extends ASTVisitor{

    boolean firstMethodEnetered = false;
    boolean firstMethodExited = false;
    StatementVisitorform1()
    {

    }

    static List<CodeParts> codePartsList = new ArrayList<CodeParts>();
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


    @Override
    public boolean visit(TypeDeclaration node) {

        //System.out.println("Class name is: " + node.getName());
        className = node.getName().toString();
        return true;

    }

    @Override
    public boolean visit(MethodDeclaration node) {
        //methods.add(node);
        WheatCulpritDetectorForM1.methodEntered += 1;
        if(WheatCulpritDetectorForM1.methodEntered == 1 && !firstMethodEnetered)
        {
            firstMethodEnetered = true;
            WheatCulpritDetectorForM1.m1AddNodeCount("Parameters",node.parameters().size());
            WheatCulpritDetectorForM1.m1AddNodeCount("Exceptions",node.thrownExceptionTypes().size());
            WheatCulpritDetectorForM1.currentFunctionName = node.getName().toString();
            startLine = WheatCulpritDetectorForM1.parse.getLineNumber(node.getStartPosition());
            int nodeLength = node.getLength();
            endLine = WheatCulpritDetectorForM1.parse.getLineNumber(node.getStartPosition() + nodeLength);
            methodBody1Signature = WheatCulpritDetectorForM1.fullFile.get(startLine-1);

            if(methodBody1Signature.contains("@")) {
                methodBody1Signature = WheatCulpritDetectorForM1.fullFile.get(startLine);
                WheatCulpritDetectorForM1.F1_fulltext = getMethodInnerText(WheatCulpritDetectorForM1.fullFile, startLine + 1, endLine);
                methodBody1InnerStmts = getMethodInnerText(WheatCulpritDetectorForM1.fullFile, startLine + 1, endLine);
            }
            else {
                WheatCulpritDetectorForM1.F1_fulltext = getMethodInnerText(WheatCulpritDetectorForM1.fullFile, startLine, endLine);
                methodBody1InnerStmts = getMethodInnerText(WheatCulpritDetectorForM1.fullFile, startLine, endLine);
            }

            codePartsList.add(new CodeParts(CodeParts.STATEMENT, methodBody1Signature+"\n","signature"));

            //read each line of method body as a CodeParts object
//            for(int i = startLine -1; i<endLine; i++) {
//                result = fullFile.get(i);
//            }

// the following code is the correct way to parse statements
            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setSource(WheatCulpritDetectorForM1.F1_fulltext.toCharArray());

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
                    WheatCulpritDetectorForM1.m1AddNodeCount("ExpressionStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, node.toString(),"expression"));
                    return true;

                }
                public boolean visit(TypeDeclarationStatement node) {
                    //System.out.println("TypeDec Node Name: " + node.toString());
                    WheatCulpritDetectorForM1.m1AddNodeCount("TypeDeclarationStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, node.toString(),"typedec"));
                    return true;

                }
                public boolean visit(VariableDeclarationStatement node) {
                    //System.out.println("VarDec Node Name: " + node.toString());
                    WheatCulpritDetectorForM1.m1AddNodeCount("VariableDeclarationStatement");
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
                    WheatCulpritDetectorForM1.m1AddNodeCount("IfStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, "if("+node.getExpression().toString()+")\n","ifstmt"));
                    return true;
                }

                public boolean visit(WhileStatement node) {

                    //System.out.println("ReturnNode Name: " + node.toString());
                    //System.out.println("While statement expression is: " + node.getExpression());
                    WheatCulpritDetectorForM1.m1AddNodeCount("WhileStatement");
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, "while("+node.getExpression().toString()+")\n","whilestmt"));
                    return true;
                }

                public boolean visit(ForStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    WheatCulpritDetectorForM1.m1AddNodeCount("ForStatement");
                    List for_inits = node.initializers();
                    List for_updts = node.updaters();
                    String fullExpression =  "for("+ for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString()+")"+ "\n";
                    //System.out.println("For statement expression is: " + for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString());
                    codePartsList.add(new CodeParts(CodeParts.STATEMENT, fullExpression,"forstmt"));
                    return true;
                }
                public boolean visit(MethodInvocation node) {
                    WheatCulpritDetectorForM1.m1AddNodeCount("MethodInvocation");
                    //System.out.println("Invo: "+node.toString());
                    return true;
                }
//                public boolean endvisit(ForStatement node) {
//                    //System.out.println("ReturnNode Name: " + node.toString());
//                     codePartsList.add(new CodeParts(CodeParts.STATEMENT, "}"));
//                    return true;
//                }

                public boolean visit(TryStatement node) {

                    WheatCulpritDetectorForM1.m1AddNodeCount("TryStatement");
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

                    WheatCulpritDetectorForM1.m1AddNodeCount("SwitchStatement");
                    return true;
                }
                public boolean visit(ClassInstanceCreation node) {

                    WheatCulpritDetectorForM1.m1AddNodeCount("ClassInstanceCreation");
                    return true;
                }
                public boolean visit(ConstructorInvocation node) {

                    WheatCulpritDetectorForM1.m1AddNodeCount("ConstructorInvocation");
                    return true;
                }
                public boolean visit(LineComment node) {

                    WheatCulpritDetectorForM1.m1AddNodeCount("LineComment");
                    return true;
                }
                public boolean visit(BlockComment node) {

                    WheatCulpritDetectorForM1.m1AddNodeCount("BlockComment");
                    return true;
                }
                public boolean visit(PrimitiveType node) {

                    WheatCulpritDetectorForM1.m1AddNodeCount("PrimitiveType");
                    return true;
                }
                public boolean visit(StringLiteral node) {

                    WheatCulpritDetectorForM1.m1AddNodeCount("StringLiteral");
                    return true;
                }


            });
            //now you have all statements on CodePartsList list.


        }

        if(WheatCulpritDetectorForM1.methodEntered == 1 && firstMethodExited) {

            WheatCulpritDetectorForM1.m2AddNodeCount("Parameters",node.parameters().size());
            WheatCulpritDetectorForM1.m2AddNodeCount("Exceptions",node.thrownExceptionTypes().size());

            //NewParser.currentFunctionName = node.getName().toString();
            startLine = WheatCulpritDetectorForM1.parse.getLineNumber(node.getStartPosition());
            int nodeLength = node.getLength();
            endLine = WheatCulpritDetectorForM1.parse.getLineNumber(node.getStartPosition() + nodeLength);
            methodBody2Signature = WheatCulpritDetectorForM1.fullFile.get(startLine - 1);
            methodBody2 = getMethodText(WheatCulpritDetectorForM1.fullFile, startLine, endLine);

            methodBody2Signature = WheatCulpritDetectorForM1.fullFile.get(startLine-1);
            if(methodBody2Signature.contains("@")) {
                methodBody2Signature = WheatCulpritDetectorForM1.fullFile.get(startLine);
                methodBody2InnerStmts = getMethodInnerText(WheatCulpritDetectorForM1.fullFile, startLine + 1, endLine);
            }
            else
                methodBody2InnerStmts = getMethodInnerText(WheatCulpritDetectorForM1.fullFile,startLine,endLine);

            codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, methodBody2Signature+"\n"));

            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setSource(methodBody2InnerStmts.toCharArray());
            parser.setKind(parser.K_STATEMENTS);
            Block block = (Block) parser.createAST(null);
            block.accept(new ASTVisitor() {


                public boolean visit(ExpressionStatement node) {
                    //System.out.println("Expression Node Name: " + node.toString());
                    WheatCulpritDetectorForM1.m2AddNodeCount("ExpressionStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, node.toString()));
                    return true;

                }
                public boolean visit(TypeDeclarationStatement node) {
                    //System.out.println("TypeDec Node Name: " + node.toString());
                    WheatCulpritDetectorForM1.m2AddNodeCount("TypeDeclarationStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, node.toString()));
                    return true;

                }
                public boolean visit(VariableDeclarationStatement node) {
                    //System.out.println("VarDec Node Name: " + node.toString());
                    WheatCulpritDetectorForM1.m2AddNodeCount("VariableDeclarationStatement");
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
                    WheatCulpritDetectorForM1.m2AddNodeCount("IfStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, "if("+node.getExpression().toString()+")\n"));
                    return true;
                }

                public boolean visit(WhileStatement node) {

                    //System.out.println("ReturnNode Name: " + node.toString());
                    //System.out.println("While statement expression is: " + node.getExpression());
                    WheatCulpritDetectorForM1.m2AddNodeCount("WhileStatement");
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, "while("+node.getExpression().toString()+")\n"));
                    return true;
                }

                public boolean visit(ForStatement node) {
                    //System.out.println("ReturnNode Name: " + node.toString());
                    WheatCulpritDetectorForM1.m2AddNodeCount("ForStatement");
                    List for_inits = node.initializers();
                    List for_updts = node.updaters();
                    String fullExpression =  "for("+ for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString()+")"+ "\n";
                    //System.out.println("For statement expression is: " + for_inits.toString()+ " " + node.getExpression() + " " + for_updts.toString());
                    codePartsListm2.add(new CodeParts(CodeParts.STATEMENT, fullExpression));
                    return true;
                }

                public boolean visit(MethodInvocation node) {
                    WheatCulpritDetectorForM1.m2AddNodeCount("MethodInvocation");
                    //System.out.println("Invo: "+node.toString());
                    return true;
                }
//                public boolean endvisit(ForStatement node) {
//                    //System.out.println("ReturnNode Name: " + node.toString());
//                     codePartsList.add(new CodeParts(CodeParts.STATEMENT, "}"));
//                    return true;
//                }

                public boolean visit(TryStatement node) {

                    WheatCulpritDetectorForM1.m2AddNodeCount("TryStatement");
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

                    WheatCulpritDetectorForM1.m2AddNodeCount("SwitchStatement");
                    return true;
                }

                public boolean visit(ClassInstanceCreation node) {

                    WheatCulpritDetectorForM1.m2AddNodeCount("ClassInstanceCreation");
                    return true;
                }
                public boolean visit(ConstructorInvocation node) {

                    WheatCulpritDetectorForM1.m2AddNodeCount("ConstructorInvocation");
                    return true;
                }
                public boolean visit(LineComment node) {

                    WheatCulpritDetectorForM1.m2AddNodeCount("LineComment");
                    return true;
                }
                public boolean visit(BlockComment node) {

                    WheatCulpritDetectorForM1.m2AddNodeCount("BlockComment");
                    return true;
                }
                public boolean visit(PrimitiveType node) {

                    WheatCulpritDetectorForM1.m2AddNodeCount("PrimitiveType");
                    return true;
                }
                public boolean visit(StringLiteral node) {

                    WheatCulpritDetectorForM1.m2AddNodeCount("StringLiteral");
                    return true;
                }


            });
        }
        return super.visit(node);

    }

    public void endVisit(MethodDeclaration node) {

        WheatCulpritDetectorForM1.methodEntered -= 1;
        if(WheatCulpritDetectorForM1.methodEntered == 0)
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

}
