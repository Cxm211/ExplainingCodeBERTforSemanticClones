public class Clone712AllCodeParts { 
public static void main (String [] args) {
int n=7;
for([int i=0] i < 7 [i++])
for([int j=0] j < 4 [j++])
if(i + j <= n - 4 || j == 0 || i == n - 1)
System.out.print("*");
if(i - j >= n - 4)
System.out.print("*");
System.out.print(" ");
System.out.println();
}
 
 public static void main (String [] args) { 
     int n = 7; 
     int mid = n / 2; 
     for (int i = 0; 
     i < n; i ++) { 
         for (int j = 0; 
         j < Math.abs (mid - i) + 1; j ++) { 
             System.out.print ("*"); 
         } 
         System.out.println (); 
     } 
 }

}