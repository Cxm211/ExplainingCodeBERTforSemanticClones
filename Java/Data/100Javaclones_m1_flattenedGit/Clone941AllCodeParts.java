public class Clone941AllCodeParts { 
public static String makeStringsEqual (String str, String keyword) {
StringBuilder equalStringBuilder=new StringBuilder();
if(str.length() > keyword.length())
int keywordIndex=0;
for([int i=0] i < str.length() [i++])
if(str.charAt(i) != ' ')
equalStringBuilder.append(keyword.charAt(keywordIndex++));
keywordIndex%=keyword.length();
equalStringBuilder.append(' ');
return equalStringBuilder.toString();
}
 
 public String makeStringsEqual (String str, String keyword) { 
     if (str.length () > keyword.length ()) { 
         string result = ""; 
         for (int i = 0; 
         i < str.length (); i ++) { 
             if (str.charAt (i) != ' ') { 
                 result += keyword.charAt (i % keyword.length ()); 
             } else result += " "; 
         } 
     } 
     return result; 
 }

}