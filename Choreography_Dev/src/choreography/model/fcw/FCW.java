/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.model.fcw;

import choreography.io.FCWLib;
import java.util.Arrays;

/**
 *
 * @author elementsking
 */
public class FCW {
    private int addr;
    private int data;
    private boolean isWater;

    /**
     *
     * @param addr the value of addr
     * @param data the value of data
     */
    public FCW(int addr, int data){
        this.addr = addr;
        this.data = data;
        FCWLib.getInstance().reverseIsWater(this);
    }

    /**
     * @return the addr
     */
    public int getAddr() {
        return addr;
    }

    /**
     * @param addr the addr to set
     */
    public void setAddr(int addr) {
        this.addr = addr;
    }

    /**
     * @return the data
     */
    public int getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%1$03d%2$s%3$03d", addr, "-", data);
    }
    
    public synchronized void setIsWater(boolean b) {
        this.isWater = b;
    }
    
    public synchronized boolean getIsWater() {
        return isWater;
    }
    
    public synchronized String getPrettyString() {
        String name = FCWLib.getInstance().reverseLookupAddress(this);
        String[] actions = FCWLib.getInstance().reverseLookupData(this);
        return name + "=" + Arrays.toString(actions);
    }
    
    @Override
    public synchronized boolean equals(Object o) {
        if(o instanceof FCW) {
            FCW obj = (FCW)o;
            if(obj.getData() == data && obj.getAddr() == addr) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.addr;
        hash = 43 * hash + this.data;
        return hash;
    }
}
