/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachememory;

/**
 *
 * @author nick
 */
public class Operations {

    //read operation type
    public String readOp(String line) {

        String[] tokensArray = line.split(" ");
        return tokensArray[0];
    }

    //read ADD operation
    public void readADD(String line, Premium obj) {

        String[] tokensArray = line.split(" ");     //break line into tokens

        obj.setName(tokensArray[1]);                        //read name of subscription
        obj.setNBR(Integer.parseInt(tokensArray[2]));       //read number of basic requests

        //check if subscription has premium requests
        if (tokensArray.length == 4) {
            obj.setNPR(Integer.parseInt(tokensArray[3]));     //if yes, read munber of premium requests
        } else {
            obj.setNPR(0);          //if not, 0 premium requests
        }
    }

    //read GET operation
    public String readGET(String line) {

        String[] tokensArray = line.split(" ");     //break line into tokens

        return tokensArray[1];      //return name of subscription
    }

    //insert in main memory, and erase from cache if necesary
    public void makeAdd(MainMemory mainMem, Premium obj) {

        //check if main memory is empty        
        if (mainMem.getLengthMainArray() == 1) {

            //if yes, add obj
            mainMem.checkMemory();      //check if main memory is full
            mainMem.add(obj);           //add obj
        } else {

            //if not, check if obj exists, then add or overwrite it
            mainMem.checkMemory();      //check if main memory is full
            boolean exist = false;      //set exist contor to false
            int index = 0;
            while (mainMem.getMainArray(index) != null
                    && index < mainMem.getLengthMainArray()) {

                //check if obj exists in main memory 
                if ((obj.getName()).equals(mainMem.getNameMainArray(index))) {
                    exist = true;       //if yes set exist contor to true
                    break;
                }
                index++;                //increment index
            }

            //check if obj exists in main memory
            if (exist == true) {
                mainMem.setMainArray(index, null);      //erase obj from main memory
            }

            mainMem.checkMemory();  //check if main memory is full
            mainMem.add(obj);       //insert it
        }
    }

}
