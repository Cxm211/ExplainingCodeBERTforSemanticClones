package CorrelationFinder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

public class M2HumanShapCorrelationFinder {

    static String PYTHON_VERSION = "python";
    static String pythonCodeFilePath = "correlation.py";

    public static void main(String args[]) throws IOException {
        //call python code to get correlation output
        ArrayList<PointBiserialResult> corrResultsList = new ArrayList<>();
        LinkedHashMap<String,String> m2IntuitionMap = new LinkedHashMap<>();
        HashMap<String,String> m2ShapMap = new HashMap<>();
        String humanintuitionfilename = "humanintuitionstatements.csv";
        String shapm2filename = "100_m2_shap.csv";
        Path filePath = Paths.get(humanintuitionfilename);
        List<String> lines = Files.readAllLines(filePath);

        String previousclonefilename = "";
        int previouslinenum = 0;
        boolean m1firstlineread = false;
        boolean m2arrived = false;
        String intuition = "";

        for(String l: lines) {
            //System.out.println(l);

            String currentclonefilename = l.substring(0,l.indexOf(','));

            int linenum = Integer.parseInt(l.substring(l.indexOf(',')+1,l.lastIndexOf(',')));
            if(linenum ==1 && previouslinenum == 0)
            {
                m1firstlineread = true;
                m2arrived = false;

                previouslinenum = linenum;
                previousclonefilename = currentclonefilename;
                continue;

            }
            int clonenumprev = Integer.parseInt(previousclonefilename.substring(previousclonefilename.indexOf("e")+1));
            int clonenumcurr = Integer.parseInt(currentclonefilename.substring(currentclonefilename.indexOf("e")+1));


            if((clonenumprev==clonenumcurr || previousclonefilename=="")  && linenum == 1 && m1firstlineread)
            {
                //
                m2arrived = true;
                //append value
                intuition = intuition.concat(l.substring(l.lastIndexOf(',')+1)+",");

                previouslinenum = linenum;
                previousclonefilename = currentclonefilename;
                continue;

            }
            if(clonenumprev==clonenumcurr && m2arrived)
            {
                //append value
                intuition = intuition.concat(l.substring(l.lastIndexOf(',')+1)+",");

                previouslinenum = linenum;
                previousclonefilename = currentclonefilename;
                continue;


            }

            if(!(clonenumprev==clonenumcurr))
            {
                m2IntuitionMap.put(previousclonefilename,intuition.substring(0,intuition.lastIndexOf(',')));
                previouslinenum = linenum;
                previousclonefilename = currentclonefilename;
                intuition = "";
                //previouslinenum = 0;
                m2arrived = false;
                m1firstlineread = true;



            }

        }

        m2IntuitionMap.put(previousclonefilename,intuition.substring(0,intuition.lastIndexOf(',')-1));


        //File file = new File(shapm2filename);
        try {
            InputStream ips = new FileInputStream(shapm2filename);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            String fullLine = "";
            while ((line = br.readLine()) != null) {
                if(!line.endsWith("]]\"") && !line.endsWith("]]"))
                {
                    //save the line in an append string
                    fullLine = fullLine.concat(line);
                }
                else
                {
                    fullLine = fullLine.concat(line);
                    String[] s = fullLine.split(",");

                    String clonefilename = s[0];
                    clonefilename = clonefilename.substring(0,clonefilename.indexOf("All"));
                    int prediction = Integer.parseInt(s[1]);
                    String shapvalues = "";
                    if(prediction == 0)
                        shapvalues = s[2];
                    else if (prediction == 1)
                        shapvalues = s[3];

                    String[] vals = shapvalues.split(" ");
                    String shapvaluestopass = "";
                    for(int i=0; i< vals.length; i++) {
                        float shapvalue;
                        try{
                            String trimmed = vals[i].replace("\"","");
                            trimmed = trimmed.replace("[","");
                            trimmed = trimmed.replace("]","");

                            if(!trimmed.contentEquals(""))
                                shapvaluestopass = shapvaluestopass.concat(trimmed+",");
                        }
                        catch(Exception e)
                        {
                            continue;
                        }


                    }

                    m2ShapMap.put(clonefilename,shapvaluestopass.substring(0,shapvaluestopass.length()-1));
                    fullLine = "";
                }



            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //iterate over wheat values hashmap
        //get corresponding shap values from shap values hashmap
        String shapvalues = "";
        String wheatvalues = "";
        for (Map.Entry<String, String> set :
                m2IntuitionMap.entrySet()) {

            shapvalues = m2ShapMap.get(set.getKey());
            wheatvalues = set.getValue();
            String cloneFileName = set.getKey();
            // Printing all elements of a Map
            //System.out.println(set.getKey() + " = "
            //        + set.getValue());

            //check the number of items in shapvalues string and wheat values string to see any difference, they should be equal
            String[] shapvaluesarray = shapvalues.split(",");
            String[] wheatvaluesarray = wheatvalues.split(",");
            boolean isequal = shapvaluesarray.length == wheatvaluesarray.length;
            //System.out.println(set.getKey()+","+isequal);
            if(isequal) {
                PointBiserialResult corrResult = getCorrelation(shapvalues, wheatvalues);
                if(corrResult!=null) {
                    corrResultsList.add(corrResult);
                    PointBiserialResult.printResult(cloneFileName, corrResult);
                }
            }

        }


    }

    private static PointBiserialResult getCorrelation(String s1, String s2) throws IOException {
        PointBiserialResult corrResult = new PointBiserialResult();
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_VERSION, resolvePythonScriptPath(pythonCodeFilePath),s1, s2 );
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());
        if(results.size()==1) {
            for (String s : results) {
                //System.out.println(s);
                Float correlation = 0.0f;
                Float pvalue = 0.0f;
                correlation = Float.parseFloat(s.substring(s.indexOf('=') + 1, s.indexOf(',')));
                pvalue = Float.parseFloat(s.substring(s.lastIndexOf('=') + 1, s.indexOf(')')));
                //System.out.println(correlation);
                //System.out.println(pvalue);
                corrResult.setCorrelation(correlation);
                corrResult.setPvalue(pvalue);
            }
        }
        else
            corrResult = null;
        return corrResult;
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
}
