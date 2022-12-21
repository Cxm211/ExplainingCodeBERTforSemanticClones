public class Clone815AllCodeParts { 
private static void countString (String str, Character character) { 
     int [] counts = new int [MAX_CHAR]; 
     char [] chars = str.toCharArray (); 
     for (char ch : chars) { 
         if (character != null && character != ch) { 
             continue; 
         } 
         counts [ch] ++; 
     } 
     for (int i = 0; 
     i < counts.length; i ++) { 
         if (counts [i] > 0) { 
             System.out.println ("Character " + (char) i + " appeared " + counts [i] + " times"); 
         } 
     } 
 } 
 
public static void countString (String str, char value) {
String[] arr=str.split("");
StringBuffer tempString=new StringBuffer();
tempString.append(s);
System.out.println("Number of Occurrence of " + ch + " is:"+ tempString.chars().filter(null).count());
if(!(Character.toString(value).isEmpty()))
StringBuffer tempString2=new StringBuffer();
tempString2.append(s);
if(ch == value)
System.out.println("Number of Occurrence of " + ch + " is:"+ tempString2.chars().filter(null).count());
}

}