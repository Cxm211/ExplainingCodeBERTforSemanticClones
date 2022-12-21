public class Clone869AllCodeParts { 
public V remove (Object key) { 
     readWriteLock.writeLock ().lock (); 
     V value; 
     try { 
         value = super.remove (key); 
     } finally { 
         readWriteLock.writeLock ().unlock (); 
     } 
     return value; 
 } 
 
public K remove () {
if(head == null)
return null;
K val=head.value;
if(head.next == null)
head=null;
tail=null;
head=head.next;
head.prev=null;
return val;
}

}