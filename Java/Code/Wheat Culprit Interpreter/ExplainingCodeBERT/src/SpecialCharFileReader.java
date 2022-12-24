import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

public class SpecialCharFileReader {
public static void main(String args[])
{
	
	List<String> fullFile = getFileText("E:\\PhD Defense\\TestROSFProjects\\aa\\TimerActivity.java");
	for(String s: fullFile)
	    System.out.println(s);	
	
    
}

public static List<String> getFileText(String path) {
	List<String> fullFile = new ArrayList<String>();
	FileInputStream input;
	try {
        input = new FileInputStream(new File(path));
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE);
        InputStreamReader reader = new InputStreamReader(input, decoder);
        BufferedReader bufferedReader = new BufferedReader( reader );
        
        String line = bufferedReader.readLine();
        while( line != null ) {
            fullFile.add( line );
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
       
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch( IOException e ) {
        e.printStackTrace();
    }
	return fullFile;
}
}
