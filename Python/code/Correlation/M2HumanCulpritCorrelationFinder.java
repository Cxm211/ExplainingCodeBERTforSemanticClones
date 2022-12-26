import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class M2HumanCulpritCorrelationFinder {

    static String PYTHON_VERSION = "python";
    static String pythonCodeFilePath = "Filtered Python Codes/correlation.py";

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
        HashMap<String,String> m2IntuitionMap = new LinkedHashMap<>();
        HashMap<String,String> m2WheatMap = new HashMap<>();
        String humanintuitionfilename = "humanIntuition.csv";
        String wheatfilename = "formatted_culprit_m2.csv";
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
            //get m2 intuition values by detecting the line numbers, esp. the second time 1 appears

//            String wheatbinaryform = l.substring(l.indexOf(',')+1,l.lastIndexOf(','));
//            //System.out.println(wheatbinaryform);
//            if(!wheatbinaryform.contentEquals("-1"))

        }

        m2IntuitionMap.put(previousclonefilename,intuition.substring(0,intuition.lastIndexOf(',')-1));


        Path wheatfilePath = Paths.get(wheatfilename);
        List<String> wheatlines = Files.readAllLines(wheatfilePath);

        for(String l: wheatlines) {
            //System.out.println(l);
            String clonefilename = l.substring(0,l.indexOf(','));
            String wheatbinaryform = l.substring(l.indexOf(',')+1);
            //System.out.println(wheatbinaryform);
            if(!wheatbinaryform.contentEquals("-1"))
                m2WheatMap.put(clonefilename,wheatbinaryform);
        }


        //iterate over wheat values hashmap
        //get corresponding shap values from shap values hashmap
        String humanvalues = "";
        String wheatvalues = "";
        for (Map.Entry<String, String> set :
                m2IntuitionMap.entrySet()) {

            wheatvalues = m2WheatMap.get(set.getKey());
            humanvalues = set.getValue();

            if(wheatvalues == null)
                continue;
            String cloneFileName = set.getKey();
//            System.out.println(cloneFileName);
//            System.out.println(humanvalues);
//            System.out.println(wheatvalues);
            // Printing all elements of a Map
            //System.out.println(set.getKey() + " = "
            //        + set.getValue());

            //check the number of items in shapvalues string and wheat values string to see any difference, they should be equal
            String[] humanvaluesarray = humanvalues.split(",");
            String[] wheatvaluesarray = wheatvalues.split(",");
            boolean isequal = humanvaluesarray.length == wheatvaluesarray.length;
            //System.out.println(set.getKey()+","+isequal);
            if(isequal) {
                PointBiserialResult corrResult = getCorrelation(humanvalues, wheatvalues);
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
