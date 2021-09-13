/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachememory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author nick
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here

        MainMemory mainMem = new MainMemory(1);     //initialize, make main memory
        FIFOCache fifoCache = null;                 //initialize fifo memory
        LRUCache lruCache = null;                   //initialize lru memory
        LFUCache lfuCache = null;                   //initialize lfu memory

        FileReader fileRead = new FileReader(args[0]);  //initialize file reader
        FileWriter fileWrite = new FileWriter(args[1], true);
        Operations operations = new Operations();       //initialize operations

        //read from file
        Scanner input = new Scanner(fileRead);
        String cacheType = input.nextLine();     //read cache type
        int nrMaxCache = input.nextInt();        //read max cache memory
        int nrOp = input.nextInt();              //read number of operations

        //check cache memory type
        if (cacheType.equals("FIFO")) {
            fifoCache = new FIFOCache(nrMaxCache);      //if FIFO, make fifo memory
        } else if (cacheType.equals("LRU")) {
            lruCache = new LRUCache(nrMaxCache);        //if LRU, make lru memory
        } else if (cacheType.equals("LFU")) {
            lfuCache = new LFUCache(nrMaxCache);        //if LRU, make lru memory
        }

        //read operations
        while (input.hasNextLine()) {
            String line = input.nextLine();         //read line
            String op = operations.readOp(line);    //read operation

            //check what operation is being done
            if (op.equals("ADD")) {
                Premium subs = new Premium();       //initialize subscription
                operations.readADD(line, subs);     //read subscription

                //check cache memory type
                if (cacheType.equals("FIFO")) {
                    operations.makeAdd(mainMem, subs);    //if FIFO, use fifo memory
                    if (fifoCache.existElementFifo(fifoCache.queue, subs) == 0) {
                        fifoCache.removeSubsFifo(subs.getName());
                        fifoCache.setSize(fifoCache.getSize() - 1);
                    }
                } else if (cacheType.equals("LRU")) {
                    operations.makeAdd(mainMem, subs);     //if LRU, use lru memory
                    if (lruCache.existElementLru(lruCache.queue, subs) == 0) {
                        lruCache.removeSubsLru(subs.getName());
                        lruCache.setSize(lruCache.getSize() - 1);
                    }
                } else if (cacheType.equals("LFU")) {
                    operations.makeAdd(mainMem, subs);     //if LFU, use lru memory
                    if (lfuCache.existElementLfu(lfuCache.queue, subs) == 0) {
                        lfuCache.removeSubsLfu(subs.getName());
                        lfuCache.setSize(lfuCache.getSize() - 1);
                    }
                }

            } else if (op.equals("GET")) {
                String name = operations.readGET(line);         //read GET operation
                Premium auxSubs = mainMem.getSubs(name);        //get subscription from main memory

                int outputNr = 0;              //initialize output number
                String outputName = null;      //initialize output name

                //check if subscription is in main memory
                if (auxSubs == null) {
                    outputNr = 2;       //if not, return 2;
                } else {
                    //if yes, check if is in cache memory
                    //check cache memory type
                    if (cacheType.equals("FIFO")) {
                        //if fifo, use fifo cache memory
                        outputNr = fifoCache.existElementFifo(fifoCache.queue, auxSubs);    //0 - exists; 1 - doesn't exist
                        outputName = fifoCache.makeGetFifo(mainMem, auxSubs);   //get subscription type
                    } else if (cacheType.equals("LRU")) {
                        //if lru, use lru cache memory
                        outputNr = lruCache.existElementLru(lruCache.queue, auxSubs);       //0 - exists; 1 - doesn't exist
                        outputName = lruCache.makeGetLru(mainMem, auxSubs);     //get subscription type
                    } else if (cacheType.equals("LFU")) {
                        //if lf u, use lru cache memory
                        outputNr = lfuCache.existElementLfu(lfuCache.queue, auxSubs);       //0 - exists; 1 - doesn't exist
                        outputName = lfuCache.makeGetLfu(mainMem, auxSubs);     //get subscription type
                    }
                }
                if (outputName == null) {
                    fileWrite.write(outputNr + "\n");
                } else {
                    fileWrite.write(outputNr + " " + outputName + "\n");
                }
            }
        }

        input.close();
        fileRead.close();
        fileWrite.close();
    }

}
