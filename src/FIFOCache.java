/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachememory;

import java.util.Queue;
import java.util.LinkedList;

/**
 *
 * @author nick
 */
public class FIFOCache implements Cache {

    Queue<Premium> queue;
    private int size, capacity;

    public FIFOCache() {

    }

    public FIFOCache(int capacity) {
        queue = new LinkedList<>();
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
    public int existElementFifo(Queue<Premium> queue, Premium obj) {

        boolean existCache = contains(queue, obj);  //get exist cache

        //check where object exists
        if (!existCache) {
            return 1;   //if it exists in main memory but not in cache return 1;
        } else {
            return 0;   //if it exists both in main and cache memory return 0;
        }
    }

    //remove obj with name name from cache memory
    public void removeSubsFifo(String name) {

        int sizeQ = size;   //get size
        //search through cache memory
        while (sizeQ != 0) {
            Premium auxSubs = queue.remove();       //get first in queue
            if (!auxSubs.getName().equals(name)) {
                queue.add(auxSubs);     //if not name, put back in queue
            }
            sizeQ -= 1;     //decrement size
        }
    }

    //GET operation for FIFO cache memory
    public String makeGetFifo(MainMemory mainMem, Premium obj) {

        String subsName = null;     //initialize subscription name
        int exist = existElementFifo(queue, obj);     //get boolean statement

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
        } else if (exist == 0) {

            //if yes, find obj in cache memory
            int sizeQ = getSize();     //get memory size

            //search obj in cache memory
            while (sizeQ != 0) {
                Premium auxSubs = queue.remove();  //take first in queue

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
                }
                queue.add(auxSubs);        //put back in queue
                sizeQ -= 1;                     //decrement contor
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
        queue.add(sub); //add element
        size += 1;      //increment size
    }

    //remove element from queue
    @Override
    public void remove() {

        //check if queue is empty
        if (isEmpty()) {
            return;     //if yes, exit function
        }
        queue.remove(); //remove element
        size -= 1;      //decrement size
    }
}
