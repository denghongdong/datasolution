package com.jdjr.datasolution.helper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiaodengliang on 2016/5/24.
 * 功能：删除相同shopId数据，包括删除文件
 */
public class DeleteSameCityIdPage {
    public static void main(String[] args) {
        Collection<File> collection;
        Set<String> set = new HashSet<String>();
        String [] lfix = {"html"};
        try {
            File file = new File("E:\\爬虫数据\\dp_kfc_data\\kfc_shop_page");
            collection = FileUtils.listFiles(file,lfix,true);//取出所有以.huml为后缀的文件
            my:for (File htmlFile : collection){
                if (!set.add(htmlFile.getName())){
                    htmlFile.delete();
                    htmlFile.getParentFile().delete();
                }else {
                    continue my;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
