public class Clone869AllCodeParts { 
public V remove (Object key) {
readWriteLock.writeLock().lock();
V value;
value=super.remove(key);
readWriteLock.writeLock().unlock();
return value;
}
 
 public K remove () { 
     if (head == null) return null; 
     K val = head.value; 
     if (head.next == null) { 
         head = null; 
         tail = null; 
     } else { 
         head = head.next; 
         head.prev = null; 
     } 
     return val; 
 }

}