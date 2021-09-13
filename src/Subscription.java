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
public abstract class Subscription {

    private String name;        //subcription name

    //constructor without parameters
    public Subscription() {

    }

    //constructor with parameters
    public Subscription(String name) {
        this.name = name;
    }

    //function to get subscription name
    public String getName() {
        return name;
    }

    //function to set subscription name
    public void setName(String name) {
        this.name = name;
    }
}
