public class Clone138AllCodeParts { 
public synchronized void add (M msg) {
Queue<M> queue=threadQueue.get();
if(queue == null)
queue=new LinkedList<>();
queues.add(queue);
threadQueue.set(queue);
queue.add(msg);
notify();
}
 
 public boolean add (P producer, E item) { 
     lock.lock (); 
     try { 
         if (! queues.containsKey (producer)) { 
             queues.put (producer, new PriorityBlockingQueue < > ()); 
         } 
         added.signalAll (); 
         return queues.get (producer).add (item); 
     } finally { 
         lock.unlock (); 
     } 
 }

}