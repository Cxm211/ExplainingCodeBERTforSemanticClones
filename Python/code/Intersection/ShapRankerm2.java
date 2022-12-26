

import java.io.*;
import java.util.*;

public class ShapRankerm2 {

    public static void main(String args[]) throws FileNotFoundException {
        //read csv file
        String shapcsvfilename = "sampledm2permuteshaplog.csv";
        File file = new File(shapcsvfilename);
        try {
            InputStream ips = new FileInputStream(shapcsvfilename);
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
                    LinkedHashMap<Integer,Float> stmtshap = rank(shapvalues);
                    writetocsv(clonefilename, stmtshap);
                    fullLine = "";
                }



            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //read clone number and shap values based on prediction
        //rank shap values

        //write to csv file

    }

    private static LinkedHashMap<Integer, Float> rank(String shapvalues) {
        String shapvals = shapvalues;
        LinkedHashMap<Integer, Float> shapmap = new LinkedHashMap<>();
        String[] vals = shapvalues.split(" ");
        int stmtnum = 0;
        for(int i=0; i< vals.length; i++) {
            float shapvalue;
            try{
                //String trimmed = vals[i].replace("\"[[","");
                //trimmed = trimmed.replace("]]\"","");

                String trimmed = vals[i].replace("\"","");
                trimmed = trimmed.replace("[","");
                trimmed = trimmed.replace("]","");
                shapvalue = Float.parseFloat(trimmed);
            }
            catch(Exception e)
            {
               continue;
            }
            stmtnum += 1;
            shapmap.put(stmtnum, shapvalue);
        }

        shapmap = sortMapDesc(shapmap);
        return shapmap;
    }

    public static LinkedHashMap<Integer, Float> sortMapDesc(Map<Integer, Float> map) {
        List<Map.Entry<Integer, Float>> list = new LinkedList<Map.Entry<Integer, Float>>(map.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator<Map.Entry<Integer, Float>>() {
            @Override
            public int compare(Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }


        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        LinkedHashMap<Integer, Float> sortedMap = new LinkedHashMap<Integer, Float>();
        for (Iterator<Map.Entry<Integer, Float>> it = list.iterator(); it.hasNext();) {
            Map.Entry<Integer, Float> entry = (Map.Entry<Integer, Float>) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static void writetocsv(String clonefilename, HashMap<Integer, Float> stmtshap) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter("ShapRankingm2.csv", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            for (Map.Entry<Integer, Float> set : stmtshap.entrySet()) {
                String writethis = clonefilename+ "," + set.getKey() + "," + set.getValue();
                pw.println(writethis);
            }


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
}
