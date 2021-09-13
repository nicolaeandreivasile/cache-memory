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
public class MainMemory {

    private Premium[] array;     //memory array

    public MainMemory() {
        array = new Premium[1];
    }

    public MainMemory(int length) {
        array = new Premium[length];
    }

    //function to get array[index];
    public Premium getMainArray(int index) {
        return array[index];
    }

    //function to set array[index]
    public void setMainArray(int index, Premium o) {
        array[index] = o;
    }

    //function to get array length
    public int getLengthMainArray() {
        return array.length;
    }

    //function to get name of array[i]
    public String getNameMainArray(int index) {
        return array[index].getName();
    }

    //add function for main memory
    public void add(Premium o) {

        //put Subscription o in array
        int index = 0;
        for (; index < array.length; index++) {
            if (array[index] == null) {
                break;
            }
        }
        this.array[index] = o;
    }

    //remove function for main memory
    public void remove(String name) {

        //check if object o is in array
        for (int i = 0; i < array.length; i++) {
            if ((array[i].getName()).equals(name)) {
                array[i] = null;   //erase object o from array leaving blank
            }
        }
    }

    //check if memory is full, resize main memory
    public void checkMemory() {
        boolean full = true;

        //check if array is full
        for (int index = 0; index < array.length; index++) {
            if (array[index] == null) {
                full = false;           //if array isn't full, set full to false
                break;
            }
        }

        //resize the array if it's full
        if (full == true) {
            Object[] tempArray = new Object[array.length];                 //create new array
            System.arraycopy(array, 0, tempArray, 0, array.length);   //copy original array into new array
            array = new Premium[2 * array.length];               //resize original array
            System.arraycopy(tempArray, 0, array, 0, tempArray.length);    //copy new aray into original array
        }
    }

    //check if element is in main memory
    public boolean isInMainMem(String name) {

        int index = 0;      //initialize index

        //search through main memory
        while (this.array[index] != null
                && index < this.array.length) {

            if ((this.array[index].getName()).equals(name)) {
                return true;        //if found, return true
            }
        }
        return false;       //else, return false
    }

    //get from main memory 
    public Premium getSubs(String name) {
        int index = 0;

        //search through main memory
        while (index < array.length
                && array[index] != null) {

            if ((array[index].getName()).equals(name)) {
                return array[index];        //if found, return subscription
            }
            index += 1;
        }
        return null;
    }
}
