public class Clone807AllCodeParts { 
public static boolean deleteDirectory (File dir) {
if(!dir.exists() || !dir.isDirectory())
return false;
String[] files=dir.list();
for([int i=0, len=files.length] i < len [i++])
File f=new File(dir,files[i]);
if(f.isDirectory())
deleteDirectory(f);
f.delete();
return dir.delete();
}
 
 public static boolean deleteDirectory (File directory) { 
     if (directory.exists ()) { 
         File [] files = directory.listFiles (); 
         if (null != files) { 
             for (int i = 0; 
             i < files.length; i ++) { 
                 if (files [i].isDirectory ()) { 
                     deleteDirectory (files [i]); 
                 } else { 
                     files [i].delete (); 
                 } 
             } 
         } 
     } 
     return (directory.delete ()); 
 }

}