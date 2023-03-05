package com.example.quicksearch;

import org.junit.Test;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Rongchen
 * @create 2023-02-28-23:03:51
 */
public class MyTest {





    @Test
    public void tt() throws Exception {
        String path = "E:\\Doc\\";
        List<String> folderNames = getFolderNames(path);
        for (String folderName : folderNames) {
            System.out.println(path + folderName);
        }

    }

    public static List<String> getFolderNames(String path) {
        List<String> folderNames = new ArrayList<>();
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    folderNames.add(f.getName());
                }
            }
        }
        return folderNames;
    }



    public List<String> execCmds(String cmd) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            // 创建 ProcessBuilder 对象
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("C:\\Program Files\\Git\\bin\\bash.exe", "-c", cmd);
            //builder.inheritIO();
            // 启动进程
            Process process = builder.start();

            // 获取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

            // 等待进程结束
            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }



    public String convertPath(String path) {
        path = path.substring(1,path.length());
        path = path.substring(0, 1) + ":" + path.substring(1, path.length());
        return path;
    }


    public void openFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start", "", file.getAbsolutePath());
                pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}



