public class Clone868AllCodeParts { 
public V remove (Object key) {
readWriteLock.writeLock().lock();
V value;
value=super.remove(key);
readWriteLock.writeLock().unlock();
return value;
}
 
 public VV remove (String key) { 
     synchronized (lock) { 
         Item < VV > item = cache.remove (key); 
         if (item != null) { 
             return item.payload; 
         } else { 
             return null; 
         } 
     } 
 }

}