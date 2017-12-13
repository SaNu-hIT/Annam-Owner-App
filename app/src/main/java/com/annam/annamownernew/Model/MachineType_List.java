package com.annam.annamownernew.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SFT on 10/10/2016.
 */
public class MachineType_List {
    public static List<MachineType> serverData = new ArrayList<>();

    public static List<MachineType> getServerData() {
        return serverData;
    }

    public static void setServerData(List<MachineType> serverData) {
        MachineType_List.serverData = serverData;
    }
}
