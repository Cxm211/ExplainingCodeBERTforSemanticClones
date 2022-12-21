public class Clone815AllCodeParts { 
private static void countString (String str, Character character) {
int[] counts=new int[MAX_CHAR];
char[] chars=str.toCharArray();
if(character != null && character != ch)
counts[ch]++;
for([int i=0] i < counts.length [i++])
if(counts[i] > 0)
System.out.println("Character " + (char)i + " appeared "+ counts[i]+ " times");
}
 
 public static void countString (String str, char value) { 
     String [] arr = str.split (""); 
     StringBuffer tempString = new StringBuffer (); 
     for (String s : arr) { 
         tempString.append (s); 
         for (char ch : s.toCharArray ()) { 
             System.out.println ("Number of Occurrence of " + ch + " is:" + tempString.chars ().filter (i -> i == ch).count ()); 
         } 
     } 
     if (! (Character.toString (value).isEmpty ())) { 
         StringBuffer tempString2 = new StringBuffer (); 
         for (String s : arr) { 
             tempString2.append (s); 
             for (char ch : s.toCharArray ()) { 
                 if (ch == value) { 
                     System.out.println ("Number of Occurrence of " + ch + " is:" + tempString2.chars ().filter (i -> i == ch).count ()); 
                 } 
             } 
         } 
     } 
 }

}