import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

public class M1HumanShapCorrelationFinder {

    static String PYTHON_VERSION = "python";
    static String pythonCodeFilePath = "Python/code/Correlation/Sampled Python Codes/correlation.py";

//    static ArrayList<String> safelistClones = new ArrayList<~>(Arrays.asList("Clone105",
//            "Clone127",
//            "Clone13",
//            "Clone131",
//            "Clone132",
//            "Clone136",
//            "Clone16",
//            "Clone18",
//            "Clone182",
//            "Clone19",
//            "Clone192",
//            "Clone196",
//            "Clone20",
//            "Clone23",
//            "Clone271",
//            "Clone29",
//            "Clone291",
//            "Clone304",
//            "Clone343",
//            "Clone349",
//            "Clone364",
//            "Clone39",
//            "Clone400",
//            "Clone401",
//            "Clone457",
//            "Clone459",
//            "Clone463",
//            "Clone467",
//            "Clone468",
//            "Clone469",
//            "Clone506",
//            "Clone529",
//            "Clone530",
//            "Clone537",
//            "Clone554",
//            "Clone556",
//            "Clone573",
//            "Clone589",
//            "Clone608",
//            "Clone610",
//            "Clone632",
//            "Clone645",
//            "Clone660",
//            "Clone707",
//            "Clone712",
//            "Clone732",
//            "Clone746",
//            "Clone752",
//            "Clone807",
//            "Clone815",
//            "Clone819",
//            "Clone822",
//            "Clone823",
//            "Clone86",
//            "Clone868",
//            "Clone869",
//            "Clone87",
//            "Clone896",
//            "Clone915",
//            "Clone916",
//            "Clone917",
//            "Clone926",
//            "Clone934",
//            "Clone939",
//            "Clone944",
//            "Clone963"));

    public static void main(String args[]) throws IOException {
        //call python code to get correlation output
        ArrayList<PointBiserialResult> corrResultsList = new ArrayList<>();
        LinkedHashMap<String,String> m1IntuitionMap = new LinkedHashMap<>();
        HashMap<String,String> m1ShapMap = new HashMap<>();
        String humanintuitionfilename = "Python/code/Correlation/humanIntuition.csv";
        String shapm1filename = "Python/code/Correlation/sampledclonefileshaplogm1.csv";
        Path filePath = Paths.get(humanintuitionfilename);
        List<String> lines = Files.readAllLines(filePath);

        String previousclonefilename = "";
        int previouslinenum = 0;
        boolean m1firstlineread = false;
        boolean m2arrived = false;
        String intuition = "";
        int clonenumprev = 0;

        for(String l: lines) {
            //System.out.println(l);

            String currentclonefilename = l.substring(0,l.indexOf(','));
            int linenum = Integer.parseInt(l.substring(l.indexOf(',')+1,l.lastIndexOf(',')));
            int clonenumcurr = Integer.parseInt(currentclonefilename.substring(currentclonefilename.indexOf("e")+1));
            if(clonenumcurr==clonenumprev && linenum ==1)//ignore m2
            {
                m1IntuitionMap.put(previousclonefilename,intuition.substring(0,intuition.lastIndexOf(',')));
                m2arrived=true;
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
                continue;
            }
            if(clonenumcurr!=clonenumprev)//for first time m1 entered
            {
                intuition = "";
                //read
                intuition = intuition.concat(l.substring(l.lastIndexOf(',')+1)+",");
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
                m2arrived=false;
            }

            else if(clonenumcurr==clonenumprev && linenum != previouslinenum && !m2arrived)//for remaining m1 lines
            {
               //read
                intuition = intuition.concat(l.substring(l.lastIndexOf(',')+1)+",");
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
            }
            else if(clonenumcurr==clonenumprev && m2arrived)//for m2 arrived keep ignoring
            {

                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;

                continue;
            }

        }


        m1IntuitionMap.put(previousclonefilename,intuition.substring(0,intuition.lastIndexOf(',')-1));



        //File file = new File(shapm2filename);
        try {
            InputStream ips = new FileInputStream(shapm1filename);
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
                    clonefilename = clonefilename.substring(0,clonefilename.indexOf(".py"));
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
                            //trimmed = vals[i].replace("\"[[","");
                            //trimmed = trimmed.replace("]]\"","");
                            //shapvalue = Float.parseFloat(trimmed);
                            if(!trimmed.contentEquals(""))
                                shapvaluestopass = shapvaluestopass.concat(trimmed+",");
                        }
                        catch(Exception e)
                        {
                            continue;
                        }


                    }

                    m1ShapMap.put(clonefilename,shapvaluestopass.substring(0,shapvaluestopass.length()-1));

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
                m1IntuitionMap.entrySet()) {

            shapvalues = m1ShapMap.get(set.getKey());
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
               // System.out.println(s);
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
