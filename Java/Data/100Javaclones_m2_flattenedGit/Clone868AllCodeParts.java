public class Clone868AllCodeParts { 
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
 
public VV remove (String key) {
Item<VV> item=cache.remove(key);
if(item != null)
return item.payload;
return null;
}

}