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
public class Free extends Subscription {

    private int nrFreeReq;

    public Free() {

    }

    public Free(String name) {
        super(name);
        this.nrFreeReq = 0;
    }

    public int getNFR() {
        return this.nrFreeReq;
    }
}
