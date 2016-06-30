package com.jdjr.datasolution.helper;

import java.io.File;

/**
 * Created by xiaodengliang on 2016/5/24.
 * 删除空文件夹
 */
public class DeleteNullFileOfDirs {
    public static void main(String[] args) {
        try {
            File file = new File("E:\\爬虫数据\\20160612\\dp_suning_data");
            File[] files = file.listFiles();
                for (File dirFile : files) {
                    if ( dirFile.isDirectory()) {
                        System.out.println("It's a directory");
                        File[] files1 = dirFile.listFiles();
                        if (files1.length == 0) {
                            System.out.println("no files");
                            dirFile.delete();
                        } else {//�ǿ��ļ���
                            System.out.println("have files");
                        }
                    } else {//���ļ�
                        System.out.println("It's a file");
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
