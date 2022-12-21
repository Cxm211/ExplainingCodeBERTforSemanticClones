public class Clone819AllCodeParts { 
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
 
 public static void countString (String str, char searchKey) { 
     String count = ""; 
     for (int i = 0; 
     i < str.length (); i ++) { 
         if (str.charAt (i) == searchKey) count += str.charAt (i) + "\n"; 
     } 
     System.out.println (count + "\nNumber of Occurrence of " + searchKey + " is " + count.length () + " in string " + str); 
 }

}