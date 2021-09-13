/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachememory;

import java.security.Timestamp;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author nick
 */
public class LRUCache implements Cache {

    Queue<Premium> queue;
    Queue<Double> ts;
    private int size, capacity;

    public LRUCache() {

    }

    public LRUCache(int capacity) {
        queue = new LinkedList<>();
        ts = new LinkedList<>();
        this.capacity = capacity;
        size = 0;

    }

    //get queue size
    public int getSize() {
        return size;
    }

    //set queue size
    public void setSize(int size) {
        this.size = size;
    }

    //get queue capacity
    public int getCapacity() {
        return capacity;
    }

    //check if queue is full
    public boolean isFull() {
        return size == capacity;
    }

    //check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    //check if fifo contains obj
    public boolean contains(Queue<Premium> queue, Premium obj) {

        int sizeQ = size;       //get size
        boolean exist = false;  //initialize exist

        //search through cache memory
        while (sizeQ != 0) {
            Premium auxSubs = queue.remove();   //get first in queue
            if (auxSubs.getName().equals(obj.getName())) {
                exist = true;   //if found, exist becomes true
            }
            queue.add(auxSubs);     //put element back in queue
            sizeQ -= 1;             //update size
        }
        return exist;       //return exist
    }

    //operation check if obj is in fifo
    public int existElementLru(Queue<Premium> queue, Premium obj) {

        boolean existCache = contains(queue, obj);  //get exist cache

        //check where object exists
        if (!existCache) {
            return 1;   //if it exists in main memory but not in cache return 1;
        } else {
            return 0;   //if it exists both in main and cache memory return 0;
        }
    }

    //remove obj with name name from cache memory
    public void removeSubsLru(String name) {

        int sizeQ = size;   //get size
        //search through cache memory
        while (sizeQ != 0) {
            Premium auxSubs = queue.remove();       //get first in queue
            double auxTs = ts.remove();             //get timestamp of first in queue
            if (!auxSubs.getName().equals(name)) {
                queue.add(auxSubs);     //if not name, put back in queue
                ts.add(auxTs);          //put timestamp back in ts
            }
            sizeQ -= 1;     //decrement size
        }
    }

    //get subscription with lowest timestamp
    public Premium getMinTimestamp() {

        int sizeQ = size;               //get size
        Premium minSubs = queue.peek(); //set minSubs
        double minTs = ts.peek();       //set minTs

        //search through cache memory
        while (sizeQ != 0) {
            Premium auxSubs = queue.remove();   //get first in queue
            double auxTs = ts.remove();         //get timestamp of first if queue

            //check if minTs is lower than auxTs
            if (auxTs < minTs) {
                minTs = auxTs;      //if yes, minTs becoms auxTs
                minSubs = auxSubs;  //minSubs becomes auxSubs
            }
            queue.add(auxSubs);     //put back in queue
            ts.add(auxTs);          //put back in ts queue
            sizeQ -= 1;
        }
        return minSubs;
    }

    //GET operation for LRU cache memory
    public String makeGetLru(MainMemory mainMem, Premium obj) {

        String subsName = null;     //initialize subscription name
        int exist = existElementLru(queue, obj);     //get boolean statement

        //check if obj is in cache memory
        if (exist == 1) {

            //if not, check subscription type
            if (obj.getNPR() != 0) {
                obj.setNPR(obj.getNPR() - 1);
                subsName = "Premium";           //if Premium, decrement premium requests, subscription anme becomes Premium
            } else if (obj.getNBR() != 0) {
                obj.setNBR(obj.getNBR() - 1);
                subsName = "Basic";             //if Basic, decrement basic requests, subscription becomes Basic
            } else if (obj.getNFR() == 0) {
                subsName = "Free";              //if Free, subscription becomes Free 
            }
            add(obj);      //add obj to cache memory
            double timestamp = System.nanoTime();
            ts.add(timestamp);
        } else if (exist == 0) {

            //if yes, find obj in cache memory
            int sizeQ = getSize();     //get memory size

            //search obj in cache memory
            while (sizeQ != 0) {
                Premium auxSubs = queue.remove();   //get first in queue
                double auxTs = ts.remove();         //get timestamp of first in queue

                //check if first in queue is obj
                if (auxSubs.getName().equals(obj.getName())) {

                    //if yes, check subscription type
                    if (auxSubs.getNPR() != 0) {
                        auxSubs.setNPR(auxSubs.getNPR() - 1);
                        subsName = "Premium";           //if Premium, decrement premium requests, subscription anme becomes Premium
                    } else if (auxSubs.getNBR() != 0) {
                        auxSubs.setNBR(auxSubs.getNBR() - 1);
                        subsName = "Basic";             //if Basic, decrement basic requests, subscription becomes Basic
                    } else if (auxSubs.getNFR() == 0) {
                        subsName = "Free";              //if Free, subscription becomes Free
                    }
                    auxTs = System.nanoTime();
                }
                queue.add(auxSubs);     //put back in queue
                ts.add(auxTs);          //put timestamp back in ts    
                sizeQ -= 1;             //decrement contor
            }
        }
        return subsName;    //return subscription name
    }

    //add element in queue
    @Override
    public void add(Premium sub) {

        //check if queue is full
        if (isFull()) {
            remove();   //if yes, remove element
        }
        queue.add(sub); //add element to queue
        size += 1;          //update size
    }

    //remove element from queue
    @Override
    public void remove() {

        //check if queue is empty
        if (isEmpty()) {
            return;
        }
        int sizeQ = size;   //get size
        Premium minSubs = getMinTimestamp();

        //search through cache memory
        while (sizeQ != 0) {
            Premium auxSubs = queue.remove();   //get first in queue
            double auxTs = ts.remove();         //get timestamp of first in queue

            //check if auxSubs has the lowest timestamp
            if (!auxSubs.equals(minSubs)) {
                queue.add(auxSubs);     //if not, put back in queue
                ts.add(auxTs);          //put back in ts queue
            }
            sizeQ -= 1;     //decrement sizeQ
        }
        size -= 1;      //update size
    }

}
