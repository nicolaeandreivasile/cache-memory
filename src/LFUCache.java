/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachememory;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author nick
 */
public class LFUCache implements Cache {

    Queue<Premium> queue;
    Queue<Double> ts;
    Queue<Integer> used;
    private int size, capacity;

    public LFUCache() {

    }

    public LFUCache(int capacity) {
        queue = new LinkedList<>();
        ts = new LinkedList<>();
        used = new LinkedList<>();
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
    public int existElementLfu(Queue<Premium> queue, Premium obj) {

        boolean existCache = contains(queue, obj);  //get exist cache

        //check where object exists
        if (!existCache) {
            return 1;   //if it exists in main memory but not in cache return 1;
        } else {
            return 0;   //if it exists both in main and cache memory return 0;
        }
    }

    //remove obj with name name from cache memory
    public void removeSubsLfu(String name) {

        int sizeQ = size;   //get size
        //search through cache memory
        while (sizeQ != 0) {
            Premium auxSubs = queue.remove();       //get first in queue
            double auxTs = ts.remove();             //get timestamp of first in queue
            int auxUsed = used.remove();            //get used of first in queue

            if (!auxSubs.getName().equals(name)) {
                queue.add(auxSubs);     //if not name, put back in queue
                ts.add(auxTs);          //put timestamp back in ts
                used.add(auxUsed);      //put used back in used queue
            }
            sizeQ -= 1;     //decrement size
        }
    }

    //get lowest used
    public int getMinUsed() {

        int sizeQ = size;           //get size
        int minUsed = used.peek();  //set minUsed

        //search through cache memory
        while (sizeQ != 0) {
            int auxUsed = used.remove();    //get used of first in queue

            //check if auxUsed is lower than minUsed
            if (auxUsed < minUsed) {
                minUsed = auxUsed;  //if yes, minUsed becomes auxUsed
            }
            used.add(auxUsed);  //put auxUsed back in used memory
            sizeQ -= 1;         //decrement sizeQ
        }
        return minUsed;     //return minUsed
    }

    //get number of subscription with lowest used
    public int getNrMinUsed(int minUsed) {

        int nrMinUsed = 0;  //initialize number of subscriptions with lowest used
        int sizeQ = size;   //get size

        //search through memory
        while (sizeQ != 0) {
            int auxUsed = used.remove();    //get used of first in queue

            //check if auxUsed is minUsed
            if (auxUsed == minUsed) {
                nrMinUsed += 1;     //if yes, increase number of subscriptions
            }
            used.add(auxUsed);      //put auxUsed bakc in used queue
            sizeQ -= 1;             //decrement sizeQ
        }

        return nrMinUsed;
    }

    //get oldest subscription with lowest used
    public Premium getOldestMinUsed(int minUsed) {

        Premium memSubs = queue.peek(); //set memSubs
        double memTs = ts.peek();       //set memTs

        int sizeQ1 = size;      //get size

        //seach through cache memory
        while (sizeQ1 != 0) {
            Premium auxSubs1 = queue.remove();      //get first in queue
            int auxUsed1 = used.remove();           //get used of first in queue
            double auxTs1 = ts.remove();            //get timestamp of first in queue

            //check if subscription has lowest used
            if (auxUsed1 == minUsed) {
                memTs = auxTs1;         //if yes, memTs becomes auxTs1
                memSubs = auxSubs1;     //memSubsbewcomes auxSubs1
            }
            queue.add(auxSubs1);    //put back int queue
            used.add(auxUsed1);     //put used back in queue
            ts.add(auxTs1);         //put timestamp back in queue
            sizeQ1 -= 1;        //decrement sizeQ1
        }

        int sizeQ2 = size;      //get size

        //search through cache memory
        while (sizeQ2 != 0) {
            Premium auxSubs2 = queue.remove();      //get first in queue
            double auxTs2 = ts.remove();            //get timestamp of first in queue
            int auxUsed2 = used.remove();           //get used of first in queue

            //check if older subscription with lowest used exists
            if (auxUsed2 == minUsed && auxTs2 < memTs) {
                memTs = auxTs2;         //if yes, memTs becomes auxTs2
                memSubs = auxSubs2;     //memSubs becomes auxSubs2
            }
            queue.add(auxSubs2);;   //put back int queue
            ts.add(auxTs2);         //put timestamp back in queue
            used.add(auxUsed2);     //put used back in queue
            sizeQ2 -= 1;    //decrement sizeQ2
        }
        return memSubs;     //return memSubs
    }

    //GET operation for LRU cache memory
    public String makeGetLfu(MainMemory mainMem, Premium obj) {

        String subsName = null;     //initialize subscription name
        int exist = existElementLfu(queue, obj);     //get boolean statement

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
            int used = 0;
            ts.add(timestamp);
            this.used.add(used);
        } else if (exist == 0) {

            //if yes, find obj in cache memory
            int sizeQ = getSize();     //get memory size

            //search obj in cache memory
            while (sizeQ != 0) {
                Premium auxSubs = queue.remove();   //get first in queue
                double auxTs = ts.remove();         //get timestamp of first in queue
                int auxUsed = used.remove();        //get used of first in queue

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
                    auxTs = System.nanoTime();  //update timestamp
                    auxUsed += 1;               //update used
                }
                queue.add(auxSubs);     //put back in queue
                ts.add(auxTs);          //put timestamp back in ts    
                used.add(auxUsed);      //put used back in used queue
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
        int minUsed = getMinUsed();
        int nrMinUsed = getNrMinUsed(minUsed);

        if (nrMinUsed == 1) {
            int sizeQ = size;   //get size

            //search through cache memory
            while (sizeQ != 0) {
                Premium auxSubs = queue.remove();   //get first in queue
                double auxTs = ts.remove();         //get timestamp of first in queue
                int auxUsed = used.remove();        //get used of first in queue

                //check if auxSubs has the lowest timestamp
                if (auxUsed != minUsed) {
                    queue.add(auxSubs);     //if not, put back in queue
                    ts.add(auxTs);          //put back in ts queue
                    used.add(auxUsed);      //put used back in used queue
                }
                sizeQ -= 1;     //decrement sizeQ
            }
        } else if (nrMinUsed > 1) {
            int sizeQ = size;   //get size
            Premium minSubs = getOldestMinUsed(minUsed);

            //search through cache memory
            while (sizeQ != 0) {
                Premium auxSubs = queue.remove();   //get first in queue
                double auxTs = ts.remove();         //get timestamp of first in queue
                int auxUsed = used.remove();        //get used of first in queue

                //check if auxSubs has the lowest timestamp
                if (!auxSubs.equals(minSubs)) {
                    queue.add(auxSubs);     //if not, put back in queue
                    ts.add(auxTs);          //put back in ts queue
                    used.add(auxUsed);      //put used back in used queue
                }
                sizeQ -= 1;     //decrement sizeQ
            }
        }
        size -= 1;      //update size
    }

}
