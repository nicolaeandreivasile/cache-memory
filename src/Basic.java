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
public class Basic extends Free {

    private int nrBasicReq;     //basic requests

    public Basic() {

    }

    public Basic(String name, int nrBasicReq) {
        super(name);
        this.nrBasicReq = nrBasicReq;
    }

    //fucntion to get number of basic requests
    public int getNBR() {
        return nrBasicReq;
    }

    //function to set number of basic requests
    public void setNBR(int nrBasicReq) {
        this.nrBasicReq = nrBasicReq;
    }
}
