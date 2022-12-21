public class Clone16AllCodeParts { 
public static void main (String [] args) { 
     ObjectMapper mapper = new ObjectMapper (); 
     try { 
         File json = new File ("test.json"); 
         CollectionType tweetListType = mapper.getTypeFactory ().constructCollectionType (ArrayList.class, Tweet.class); 
         List < Tweet > tweets = mapper.readValue (json, tweetListType); 
         System.out.println ("Java objects created from JSON String:"); 
         tweets.forEach (System.out :: println); 
     } catch (IOException ex) { 
         ex.printStackTrace (); 
     } 
 } 
 
public static void main (String [] args) throws Exception {
Tweet[] tweets;
ObjectMapper mapper=new ObjectMapper();
File json=new File("test.json");
tweets=mapper.readValue(json,Tweet[].class);
System.out.println("Java object created from JSON String :");
Arrays.asList(tweets).forEach(null);
ex.printStackTrace();
}

}