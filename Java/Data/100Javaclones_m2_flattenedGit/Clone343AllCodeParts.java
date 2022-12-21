public class Clone343AllCodeParts { 
public static void main (String [] args) { 
     HashMap < Character, Integer > hm = new HashMap < Character, Integer > (); 
     System.out.println ("Enter an String:"); 
     Scanner sc = new Scanner (System.in); 
     String s1 = sc.nextLine (); 
     for (int i = 0; 
     i < s1.length (); i ++) { 
         if (! hm.containsKey (s1.charAt (i))) { 
             hm.put (s1.charAt (i), (Integer) 1); 
         } else { 
             hm.put (s1.charAt (i), hm.get (s1.charAt (i)) + 1); 
         } 
     } 
     System.out.println ("The Charecters are:" + hm); 
 } 
 
public static void main (String [] args) {
String test="The quick brown fox jumped over the lazy dog.";
int countA=0, countO=0, countSpace=0, countDot=0;
for([int i=0] i < test.length() [i++])
countA++;
countO++;
countSpace++;
countDot++;
System.out.printf("%s%d%n%s%d%n%s%d%n%s%d","A: ",countA,"O: ",countO,"Space: ",countSpace,"Dot: ",countDot);
}

}