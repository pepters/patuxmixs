package com.mixay.patuxmix.util;


import java.util.HashMap;
import java.util.UUID;

public class DataManager {
    protected static HashMap <UUID, Integer> clickedChickens = new HashMap<>();
    public static boolean increaseChicken (UUID chickenuuid) {
        if (!alreadyAdded(chickenuuid)) {
            clickedChickens.put(chickenuuid, 1);
            return true;
        } else {
            clickedChickens.replace(chickenuuid, clickedChickens.get(chickenuuid)+1);
            return false;
        }
    }
    public static boolean alreadyAdded (UUID chickenuuid) {
        return clickedChickens.containsKey(chickenuuid);
    }
    public static Integer getClicks (UUID chickenuuid) {
        return clickedChickens.get(chickenuuid);
    }
    public static void removeChicken (UUID chickenuuid) {
        clickedChickens.remove(chickenuuid);
    }
}
