public class Clone738AllCodeParts { 
public int read () throws IOException {
if(currentPos < currentLineEnd)
in.seek(currentPos++);
int readByte=in.readByte();
return readByte;
if(currentPos < 0)
return -1;
findPrevLine();
return read();
}
 
 public int read () throws IOException { 
     if (currentFilePos <= 0 && currentBufferPos < 0 && currentLineReadPos < 0) { 
         return - 1; 
     } 
     if (! lineBuffered) { 
         fillLineBuffer (); 
     } 
     if (lineBuffered) { 
         if (currentLineReadPos == 0) { 
             lineBuffered = false; 
         } 
         return currentLine [currentLineReadPos --]; 
     } 
     return 0; 
 }

}