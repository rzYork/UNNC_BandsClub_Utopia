package com.unncbandsclub.utopia.utlis;

import java.util.HashMap;

public class UDataMapBuilder {
    public static HashMap<String,Object> build(Object[][] data){
        HashMap<String, Object> m = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            if(data[i].length!=2){
                new IllegalArgumentException().printStackTrace();
                m.clear();
                return m;
            }
           Object s= data[i][0];
           if(!(s instanceof String)){
               new IllegalArgumentException("无效字符串").printStackTrace();
               m.clear();
               return m;
           }
           m.put((String)s,data[i][1]);
        }

        return m;
    }

    public static HashMap<String,Object> build(Object... objects){
        HashMap<String,Object> m=new HashMap<>();
        for (int i = 0; i < objects.length; i++) {
            m.put(String.valueOf(i),objects[i]);
        }
        return m;
    }
}
