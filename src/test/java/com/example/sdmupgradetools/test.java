/**
 * Project Name: sdm-upgradetools-web
 * File Name: test
 * Package Name: com.example.sdmupgradetools
 * Date: 2020/5/12 14:21
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.example.sdmupgradetools;

import com.example.sdmupgradetools.model.VersionInfo;
import com.example.sdmupgradetools.utils.LoadVersionInfo;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 14:21 2020/5/12 
 */
public class test {

    public static void main(String[] args) throws JSONException {

        int j =0;
        for(int i=0;i<1000;i++){
            int temp=++j;
            j=temp;
        }
        System.out.println(j);
    }

}
