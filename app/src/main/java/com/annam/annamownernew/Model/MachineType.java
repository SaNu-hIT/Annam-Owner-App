package com.annam.annamownernew.Model;

/**
 * Created by SFT on 9/10/2016.
 */
public class MachineType {
    public String MACHINE_TYPE_ID;
    public String NAME;
    public String PICKUP_REQ;

    public String getMachineTypeId() {
        return MACHINE_TYPE_ID;
    }

    public void setMachineTypeId(String machineTypeId) {
        MACHINE_TYPE_ID = machineTypeId;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPickupReq() {
        return PICKUP_REQ;
    }

    public void setPickupReq(String pickupReq) { PICKUP_REQ = pickupReq; }
}
