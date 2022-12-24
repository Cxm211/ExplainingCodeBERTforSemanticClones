package AnalysisDataGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WheatStatementNumGetterM2 {
    public static void main(String args[])
    {
        String csvfilename = "m2_wheatlog.csv";
        String m2FlattenedCodeFolderPath = "Java\\Data\\100Javaclones_m2_flattenedGit\\";
        File file = new File(csvfilename);
        try {
            InputStream ips = new FileInputStream(csvfilename);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            String fullLine = "";
            while ((line = br.readLine()) != null) {

                    //fullLine = fullLine.concat(line);
                    String[] s = line.split(",");

                    String clonefilename = s[1];
                    //clonefilename = clonefilename.substring(0,clonefilename.indexOf("All"));
                    String status = s[2];
                    if(status.contains("No wheat") || status.contains("Found suf and nec"))
                    {
                        ArrayList<Integer> stmtnums = new ArrayList<>();
                        ArrayList<Integer> binarywheatstatus = new ArrayList<>();
                        if(status.contains("No wheat"))
                        {
                            stmtnums.add(0);
                            //binarywheatstatus.add(-1);//means no wheat

                            String filename = clonefilename + "AllCodeParts.java";
                            String filepath = m2FlattenedCodeFolderPath + filename;
                            // read a codeparts file m1 statements into a hashmap with statement num as index and statements as value
                            HashMap<Integer,String> m1statements = new HashMap<>();

                            m1statements = readm2codepartstmtsfromfile(filepath);
                            if(m1statements.size()==0) {
                                m1statements = readm2codepartstmtsfromfileotherway(filepath);

                                //System.out.println("nothing found for " + filepath);

                            }
                            if(m1statements.size()==0) {


                                System.out.println("nothing found for " + filepath);
                                continue;
                            }
                            for (Map.Entry<Integer, String> set : m1statements.entrySet()) {
                                binarywheatstatus.add(1);
                            }
                            int size = (binarywheatstatus.size());
                            binarywheatstatus.set(size-1,0);

                        }
                        else {
                        int stmtscount = Integer.parseInt(s[3]);
                        String wheatcul = s[5];
                        String fullwheatculstmts = line.substring(line.indexOf(wheatcul));
                        int i = 0;
                        //open code parts file using clone number
                        //String cfilename = clonefilename.substring(0,clonefilename.indexOf(".java"));
                        String filename = clonefilename + "AllCodeParts.java";
                        String filepath = m2FlattenedCodeFolderPath + filename;
                        // read a codeparts file m1 statements into a hashmap with statement num as index and statements as value
                        HashMap<Integer,String> m1statements = new HashMap<>();
                        m1statements = readm2codepartstmtsfromfile(filepath);
                        if(m1statements.size()==0) {
                            m1statements = readm2codepartstmtsfromfileotherway(filepath);

                            //System.out.println("nothing found for " + filepath);

                        }
                            if(m1statements.size()==0) {


                                System.out.println("nothing found for " + filepath);
                                continue;
                            }


                            //iterate over hashmap and search for each statement number in string
                                for (Map.Entry<Integer, String> set : m1statements.entrySet()) {
                                    String stmt = set.getValue();
                                if(!stmt.contentEquals("}")) {
                                    if (fullwheatculstmts.contains(stmt)) {
                                        fullwheatculstmts.replace(stmt, "");
                                        stmtnums.add(set.getKey());
                                        binarywheatstatus.add(1);
                                    }
                                    else
                                        binarywheatstatus.add(0);
                                }
                            }
                        }
                        writetocsv(clonefilename,stmtnums);
                        writetocsvbinarywheat(clonefilename,binarywheatstatus);
                    }






            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    private static void writetocsv(String clonefilename, ArrayList<Integer> stmtnums) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter("100WheatStmtNumsm2permute.csv", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            String nums = "";
            for (Integer x:stmtnums)
                nums = nums+x+",";

            //System.out.println("Data Successfully appended into file");
            pw.println(clonefilename+","+nums);
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

    private static void writetocsvbinarywheat(String clonefilename, ArrayList<Integer> stmtnums) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            //fw = new FileWriter("WheatStmtNumsm1permuteBinary.csv", true);
            fw = new FileWriter("New100WheatStmtNumsm2permuteBinary.csv", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            String nums = "";
            for (Integer x:stmtnums)
                nums = nums+x+",";

            //System.out.println("Data Successfully appended into file");
            pw.println(clonefilename+","+nums);
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

    private static HashMap<Integer, String> readm2codepartstmtsfromfile(String filepath) throws FileNotFoundException {
        HashMap<Integer, String> stmtmap = new HashMap<>();
        try {

            Scanner scanner = new Scanner(new File(filepath));

            while (scanner.hasNextLine()) {
                //String curline = scanner.nextLine();
                while(!scanner.nextLine().contentEquals(""))
                {

                }
                    //System.out.println("empty line");
                int i=1;
                String line = "";
                while(!(line=scanner.nextLine()).contentEquals("}"))
                {
                    //
                    stmtmap.put(i, line);
                    i=i+1;
                }

            }

            scanner.close();

        }
        catch(Exception ex)
        {
                System.out.println("i am hre in exception");
        }
        return stmtmap;


    }

    private static HashMap<Integer, String> readm2codepartstmtsfromfileotherway(String filepath) throws FileNotFoundException {
        HashMap<Integer, String> stmtmap = new HashMap<>();
        try {
            //System.out.println("Check me! '" + filepath + "'");
            InputStream ips = new FileInputStream(filepath);

            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line = "";
            String fullLine = "";
            int i = 1;
            while (!(line = br.readLine()).contentEquals(" ")) {
                //do nothing
            }
            while (!(line = br.readLine()).contentEquals("}")) {

                    stmtmap.put(i, line);


                i = i + 1;
            }
        }
        catch(Exception ex)
        {
            System.out.println("i am hre in exception");
        }
        return stmtmap;


    }
}
