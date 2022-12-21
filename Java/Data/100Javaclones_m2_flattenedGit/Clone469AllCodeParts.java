public class Clone469AllCodeParts { 
public static void main (String [] args) { 
     try { 
         InetAddress address = InetAddress.getByName ("192.168.1.103"); 
         boolean reachable = address.isReachable (10000); 
         System.out.println ("Is host reachable? " + reachable); 
     } catch (Exception e) { 
         e.printStackTrace (); 
     } 
 } 
 
public static void main (String [] args) throws UnknownHostException, IOException {
InetAddress inet;
inet=InetAddress.getByAddress(new byte[]{127,0,0,1});
System.out.println("Sending Ping Request to " + inet);
System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
inet=InetAddress.getByAddress(new byte[]{(byte)173,(byte)194,32,38});
System.out.println("Sending Ping Request to " + inet);
System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
}

}