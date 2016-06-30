package com.jdjr.datasolution.util;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by xiaodengliang on 2016/5/20.
 */
public class TestUtil {
    public static void main(String[] args) throws Exception {
        String filename = "星巴克(乐颂坊店) 其它386家分店";
        filename = filename.substring(0,filename.indexOf(" "));
        System.out.println(filename);
    }
}
