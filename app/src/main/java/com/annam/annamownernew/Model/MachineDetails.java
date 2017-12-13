package com.annam.annamownernew.Model;

/**
 * Created by SFT on 9/10/2016.
 */
public class MachineDetails {
    public String MACHINE_ID;
    public String MACHINE_NO;
    public String MACHINE_NAME;
    public String MACHINE_DESC;
    public String MAVAILABLE;
    public String IMAGE;

    public String getMachineId() {
        return MACHINE_ID;
    }

    public void setMachineId(String machineId) {
        MACHINE_ID = machineId;
    }

    public  String getMachineNo() {
        return MACHINE_NO;
    }

    public void setMachineNo(String machineNo) {
        MACHINE_NO = machineNo;
    }

    public String getMachineName() {
        return MACHINE_NAME;
    }

    public void setMachineName(String machineName) {
        MACHINE_NAME = machineName;
    }

    public String getMachineDesc() {
        return MACHINE_DESC;
    }

    public void setMachineDesc(String machineDesc) {
        MACHINE_DESC = machineDesc;
    }

    public String getMAVAILABLE() {
        return MAVAILABLE;
    }

    public void setMAVAILABLE(String AVAILABLE) { MAVAILABLE = AVAILABLE; }

    public String getIMAGE() { return IMAGE; }

    public void setIMAGE(String IMAGE) { this.IMAGE = IMAGE; }
}