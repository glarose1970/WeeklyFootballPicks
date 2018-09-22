package com.inkedapparelonline.weeklyfootballpicks.helpers;

import java.util.Random;

public class PlayerHelper {

    public static final String Generate_Player_Id(int size) {
        String id = "";
        String preId = "10-";
        String nums = "1234567890";
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        int min = 0;
        int max = 9;

        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(max + 1 - min) + min;
            Character num = nums.charAt(index);
            builder.append(num);
        }
        id = builder.toString();
        return preId + id;
    }
}
