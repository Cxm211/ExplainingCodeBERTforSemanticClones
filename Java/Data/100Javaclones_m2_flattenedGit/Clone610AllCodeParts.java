public class Clone610AllCodeParts { 
public ZonedDateTime parseToZonedDateTime (String date, String dateFormat) { 
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern (dateFormat); 
     TemporalAccessor parsed = formatter.parseBest (date, ZonedDateTime :: from, LocalDateTime :: from); 
     if (parsed instanceof ZonedDateTime) { 
         return (ZonedDateTime) parsed; 
     } 
     if (parsed instanceof LocalDateTime) { 
         LocalDateTime dt = (LocalDateTime) parsed; 
         return dt.atZone (ZoneId.systemDefault ()); 
     } 
     return null; 
 } 
 
public ZonedDateTime parseToZonedDateTime (String date, String dateFormat) {
DateTimeFormatter formatter=DateTimeFormatter.ofPattern(dateFormat);
ZonedDateTime zonedDateTime=null;
zonedDateTime=ZonedDateTime.parse(date,formatter);
LocalDateTime localDateTime=LocalDateTime.parse(date,formatter);
zonedDateTime=localDateTime.atZone(ZoneId.systemDefault());
return zonedDateTime;
}

}