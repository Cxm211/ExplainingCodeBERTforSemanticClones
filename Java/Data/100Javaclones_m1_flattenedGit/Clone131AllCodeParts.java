public class Clone131AllCodeParts { 
public static void main (String [] args) {
Scanner myScanner;
myScanner=new Scanner(System.in);
double mon, tues, wed, thurs, fri, sat, sun;
System.out.print("Expenses for Monday: ");
mon=myScanner.nextDouble();
mon=-1;
myScanner.nextLine();
System.out.println(mon);
}
 
 public static void main (String args []) { 
     Scanner myScanner = new Scanner (System.in); 
     double mon = getExpensesForDay (myScanner, "Monday"); 
     double tues = getExpensesForDay (myScanner, "Tuesday"); 
     double wed = getExpensesForDay (myScanner, "Wednesday"); 
     double thurs = getExpensesForDay (myScanner, "Thursday"); 
     double fri = getExpensesForDay (myScanner, "Friday"); 
     double sat = getExpensesForDay (myScanner, "Saturday"); 
     double sun = getExpensesForDay (myScanner, "Sunday"); 
 }

}