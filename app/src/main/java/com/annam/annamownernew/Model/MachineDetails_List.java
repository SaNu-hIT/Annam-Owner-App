package com.annam.annamownernew.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SFT on 10/10/2016.
 */
public class MachineDetails_List {

    public static List<MachineDetails> serverData = new ArrayList<MachineDetails>();

    public static List<MachineDetails> getServerData() {
        return serverData;
    }

    public static void setServerData(List<MachineDetails> serverData) {
        MachineDetails_List.serverData = serverData;
    }
}
