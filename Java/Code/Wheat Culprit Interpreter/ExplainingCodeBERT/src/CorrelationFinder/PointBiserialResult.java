package CorrelationFinder;

public class PointBiserialResult {

    public float getCorrelation() {
        return correlation;
    }

    public void setCorrelation(float correlation) {
        this.correlation = correlation;
    }

    float correlation;

    public float getPvalue() {
        return pvalue;
    }

    public void setPvalue(float pvalue) {
        this.pvalue = pvalue;
    }

    float pvalue;

    public static void printResult(String cloneFileName, PointBiserialResult result)
    {
        String correlationStatus="";
        String significanceStatus="";
        if(result.correlation > 0)
            correlationStatus = "positive correlation";
        else
            correlationStatus = "0 or no correlation";
        if(result.pvalue < 0.01)
            significanceStatus = "significant";
        if(result.pvalue < 0.005)
            significanceStatus = "highly significant";
        if(result.pvalue >= 0.01)
            significanceStatus = "not significant";

        System.out.println(cloneFileName + ","+ result.correlation+","+result.pvalue + "," + correlationStatus + "," + significanceStatus);

    }


}
