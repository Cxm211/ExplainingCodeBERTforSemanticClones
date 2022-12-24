public class CodeParts {

    public static int LINE = 1;
    public static int STATEMENT = 2;
    public static int EXPRESSION = 3;
    public static int DATAFLOW = 4;

    private boolean necessary;
    private boolean sufficient;
    private int granularity;
    private boolean processing;
    private boolean culprit;

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    private String statementType;




    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    private boolean safe;
    private String partName;

    public boolean isCulprit() {
        return culprit;
    }

    public void setCulprit(boolean culprit) {
        this.culprit = culprit;
    }



    public CodeParts(int granularity, String partName) {
        this.granularity = granularity;
        this.partName = partName;
        this.processing = false;

    }
    public CodeParts(int granularity, String partName, String stmttype) {
        this.granularity = granularity;
        this.partName = partName;
        this.processing = false;
        this.statementType = stmttype;
    }


    public int getGranularity() {
        return granularity;
    }

    public void setGranularity(int granularity) {
        this.granularity = granularity;
    }


    public boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(boolean necessary) {
        this.necessary = necessary;
    }

    public boolean isSufficient() {
        return sufficient;
    }

    public void setSufficient(boolean sufficient) {
        this.sufficient = sufficient;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }
}
