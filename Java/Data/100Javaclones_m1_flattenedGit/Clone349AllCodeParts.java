public class Clone349AllCodeParts { 
public void actionPerformed (ActionEvent e) {
OutputStream os=new BufferedOutputStream(new FileOutputStream(file));
XMLEncoder xe=new XMLEncoder(os);
xe.setPersistenceDelegate(DefaultTableModel.class,new DefaultTableModelPersistenceDelegate());
xe.writeObject(model);
xe.close();
Reader r=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
textArea.read(r,null);
ex.printStackTrace();
}
 
 public void actionPerformed (ActionEvent e) { 
     try { 
         InputStream is = new BufferedInputStream (new FileInputStream (file)); 
         XMLDecoder xd = new XMLDecoder (is); 
         model = (DefaultTableModel) xd.readObject (); 
         table.setModel (model); 
     } catch (IOException ex) { 
         ex.printStackTrace (); 
     } 
 }

}