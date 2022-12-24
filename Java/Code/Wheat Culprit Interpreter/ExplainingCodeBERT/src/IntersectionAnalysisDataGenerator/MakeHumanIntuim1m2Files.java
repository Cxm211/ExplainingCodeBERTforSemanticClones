package IntersectionAnalysisDataGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MakeHumanIntuim1m2Files {

    //input format
    //Clone556,stmtnum,intuition
    //output format
    //clonefilename,m1/m2,smtnum,human intuition(0 or 1 or -1)

    public static void main(String args[]) throws IOException {

        String humanintuitionfilename = "humanintuitionstatements.csv";
        Path filePath = Paths.get(humanintuitionfilename);
        List<String> lines = Files.readAllLines(filePath);

        outputM1Intuitions(lines);
        System.out.println("========Split here to analyze m1 and m2 intuitions separately with respect to wheat/culprit/shap m1 and m2");
        outputM2Intuitions(lines);

    }

    private static void outputM2Intuitions(List<String> lines) {
        String previousclonefilename = "";
        int previouslinenum = 0;
        boolean m1firstlineread = false;
        boolean m2arrived = false;
        String intuition = "";
        int clonenumprev = -1;

        for (String l : lines) {
            //System.out.println(l);
            String currentclonefilename = l.substring(0, l.indexOf(','));
            int linenum = Integer.parseInt(l.substring(l.indexOf(',') + 1, l.lastIndexOf(',')));
            int clonenumcurr = Integer.parseInt(currentclonefilename.substring(currentclonefilename.indexOf("e") + 1));
            if (clonenumcurr == clonenumprev && linenum == 1)//ignore m2
            {
                //m1IntuitionMap.put(previousclonefilename,intuition.substring(0,intuition.lastIndexOf(',')));
                m2arrived = true;
                System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
                continue;
            }
            if (clonenumcurr != clonenumprev)//for first time m1 entered
            {
                intuition = "";
                //read
                intuition = intuition.concat(l.substring(l.lastIndexOf(',') + 1) + ",");
                //clonefilename,m1/m2,smtnum,human intuition(0 or 1 or -1)
                //System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
                m2arrived = false;
            } else if (clonenumcurr == clonenumprev && linenum != previouslinenum && !m2arrived)//for remaining m1 lines
            {
                //read
                intuition = intuition.concat(l.substring(l.lastIndexOf(',') + 1) + ",");
                //clonefilename,m1/m2,smtnum,human intuition(0 or 1 or -1)
                //System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
            } else if (clonenumcurr == clonenumprev && m2arrived)//for m2 arrived keep ignoring
            {

                System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;

                continue;
            }

        }

    }

    private static void outputM1Intuitions(List<String> lines) {
        String previousclonefilename = "";
        int previouslinenum = 0;
        boolean m1firstlineread = false;
        boolean m2arrived = false;
        String intuition = "";
        int clonenumprev = -1;

        for (String l : lines) {
            //System.out.println(l);

            String currentclonefilename = l.substring(0, l.indexOf(','));
            int linenum = Integer.parseInt(l.substring(l.indexOf(',') + 1, l.lastIndexOf(',')));
            int clonenumcurr = Integer.parseInt(currentclonefilename.substring(currentclonefilename.indexOf("e") + 1));
            if (clonenumcurr == clonenumprev && linenum == 1)//ignore m2
            {
                m2arrived = true;
                //System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
                continue;
            }
            if (clonenumcurr != clonenumprev)//for first time m1 entered
            {
                intuition = "";
                //read
                intuition = intuition.concat(l.substring(l.lastIndexOf(',') + 1) + ",");
                //clonefilename,m1/m2,smtnum,human intuition(0 or 1 or -1)
                System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
                m2arrived = false;
            } else if (clonenumcurr == clonenumprev && linenum != previouslinenum && !m2arrived)//for remaining m1 lines
            {
                //read
                intuition = intuition.concat(l.substring(l.lastIndexOf(',') + 1) + ",");
                //clonefilename,m1/m2,smtnum,human intuition(0 or 1 or -1)
                System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;
            } else if (clonenumcurr == clonenumprev && m2arrived)//for m2 arrived keep ignoring
            {

                //System.out.println(currentclonefilename+","+linenum+","+l.substring(l.lastIndexOf(',') + 1));
                clonenumprev = clonenumcurr;
                previousclonefilename = currentclonefilename;
                previouslinenum = linenum;

                continue;
            }

        }
    }
}
