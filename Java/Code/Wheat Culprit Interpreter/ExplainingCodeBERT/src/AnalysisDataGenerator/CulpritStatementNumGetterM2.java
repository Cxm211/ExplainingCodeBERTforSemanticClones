package AnalysisDataGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CulpritStatementNumGetterM2 {
    public static void main(String args[])
    {
        String csvfilename = "m2_culpritlog.csv";
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
                String status = s[4];
                if(status.contains("culprit not found") || status.contains("found"))
                {
                    ArrayList<Integer> stmtnums = new ArrayList<>();
                    ArrayList<Integer> binaryculpritstatus = new ArrayList<>();
                    if(status.contains("culprit not found"))
                    {
                        stmtnums.add(0);
                        //binaryculpritstatus.add(-1);//means no wheat
                        String filename = clonefilename + "AllCodeParts.java";
                        String filepath = m2FlattenedCodeFolderPath + filename;

                        // read a codeparts file m1 statements into a hashmap with statement num as index and statements as value
                        HashMap<Integer,String> m1statements = new HashMap<>();
                        m1statements = readm2codepartstmtsfromfile(filepath);
                        if(m1statements.size()==0) {
                            m1statements = readm2codepartstmtsfromfileotherway(filepath);

                            //System.out.println("nothing found for " + filepath);

                        }
                        if(m1statements.size()==0)
                            continue;

                        //iterate over hashmap and search for each statement number in string


                        for (Map.Entry<Integer, String> set : m1statements.entrySet()) {
                            binaryculpritstatus.add(1);
                        }
                        int size = (binaryculpritstatus.size());
                        binaryculpritstatus.set(size-1,0);
                    }
                    else {
                        int stmtscount = Integer.parseInt(s[5]);
                        String wheatcul = s[7];
                        //to get the culprit statements we need to ignore the first two method signatures
                        String fullwheatculstmts = line.substring(line.indexOf(wheatcul));
                        String remove1  = fullwheatculstmts.substring(fullwheatculstmts.indexOf(",")+1);

                        fullwheatculstmts = remove1.substring(remove1.indexOf(",")+1);

                        //String afterremoval = line.substring(line.lastIndexOf(startofretain));
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
                        if(m1statements.size()==0)
                            continue;

                        //iterate over hashmap and search for each statement number in string


                        for (Map.Entry<Integer, String> set : m1statements.entrySet()) {
                            String stmt = set.getValue();
                            if(!stmt.contentEquals("}")) {
                                if (fullwheatculstmts.contains(stmt)) {
                                    fullwheatculstmts.replace(stmt, "");
                                    stmtnums.add(set.getKey());
                                    binaryculpritstatus.add(1);
                                }
                                else
                                    binaryculpritstatus.add(0);
                            }
                        }
                    }


                    writetocsv(clonefilename,stmtnums);
                    writetocsvBinaryCulprit(clonefilename,binaryculpritstatus);
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
            fw = new FileWriter("100CulStmtNumsm2permute.csv", true);
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

    private static void writetocsvBinaryCulprit(String clonefilename, ArrayList<Integer> stmtnums) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter("New100CulStmtNumsm2permuteBinary.csv", true);
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

    private static HashMap<Integer, String> readm2codepartstmtsfromfile(String filepath) throws FileNotFoundException {
        HashMap<Integer, String> stmtmap = new HashMap<>();
        try {
            InputStream ips = new FileInputStream(filepath);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            String fullLine = "";
            int i = 1;
            while (!(line = br.readLine()).contentEquals("")) {
                //do nothing
            }
            while (!(line = br.readLine()).contentEquals("}")) {

                stmtmap.put(i, line);


                i = i + 1;
            }
        }
        catch(Exception ex)
        {

        }
        return stmtmap;


    }

}
