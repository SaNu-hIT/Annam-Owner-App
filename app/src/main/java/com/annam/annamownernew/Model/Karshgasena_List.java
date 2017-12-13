package com.annam.annamownernew.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SFT on 10/10/2016.
 */
public class Karshgasena_List {

    public static List<KarshagasenaDetailsBean> serverData = new ArrayList<KarshagasenaDetailsBean>();

    public static List<KarshagasenaDetailsBean> getServerData() {
        return serverData;
    }

    public static void setServerData(List<KarshagasenaDetailsBean> serverData) {
        Karshgasena_List.serverData = serverData;
    }
}
