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
public class Premium extends Basic {

    private int nrPremiumReq;       //premium requests

    //constructor without parameters
    public Premium() {

    }

    //construcotr with parameters
    public Premium(String name, int nrBasicReq, int nrPremiumReq) {
        super(name, nrBasicReq);
        this.nrPremiumReq = nrPremiumReq;
    }

    //function to get number of premium requests
    public int getNPR() {
        return nrPremiumReq;
    }

    //function to set number of premium requests
    public void setNPR(int nrPremiumReq) {
        this.nrPremiumReq = nrPremiumReq;
    }
}
