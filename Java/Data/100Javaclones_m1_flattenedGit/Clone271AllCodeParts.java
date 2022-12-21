public class Clone271AllCodeParts { 
public static boolean hasDateExpired (int days, Instant savedDate, Instant currentDate) {
boolean hasExpired=false;
if(savedDate != null && currentDate != null)
if(savedDate.plus(days,ChronoUnit.DAYS).isBefore(currentDate))
hasExpired=true;
return hasExpired;
}
 
 private static boolean hasDateExpired (int days, java.util.Date savedDate) throws ParseException { 
     SimpleDateFormat dateFormatUtc = new SimpleDateFormat ("yyyy-MMM-dd HH:mm:ss"); 
     dateFormatUtc.setTimeZone (TimeZone.getTimeZone ("UTC")); 
     SimpleDateFormat dateFormatLocal = new SimpleDateFormat ("yyyy-MMM-dd HH:mm:ss"); 
     savedDate = dateFormatLocal.parse (dateFormatUtc.format (savedDate)); 
     Date dateTimeNow = dateFormatLocal.parse (dateFormatUtc.format (new Date ())); 
     long expires = dateTimeNow.getTime () + (DAY_IN_MS * days); 
     Date expiresDate = new Date (expires); 
     System.out.println ("savedDate \t\t" + savedDate + "\nexpiresDate \t" + expiresDate); 
     return savedDate.before (expiresDate); 
 }

}