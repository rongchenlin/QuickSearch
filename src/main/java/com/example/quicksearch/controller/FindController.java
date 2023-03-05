package com.example.quicksearch.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Rongchen
 * @create 2023-03-01-20:16:24
 */
@RestController
public class FindController {

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

    @RequestMapping("/find")
    public Result find(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String keywords = jsonObject.getStr("keywords");
        String path = jsonObject.getStr("path");
        String type = jsonObject.getStr("type");
        String suffix = jsonObject.getStr("suffix");
        String optionStr = jsonObject.getStr("optionStr");
        path = windowsPathToLinuxPath(path);

        String[] o = new String[]{optionStr, "name"};
        String cmd = "";
        String[] k = keywords.split("\\|");
        if (k.length > 1) {
             cmd = fuzzyFindCmd(path,k,o,type,suffix);
        }
        String[] k2 = keywords.split(";");
        if (k2.length > 1) {
             cmd = accurateFindCmd(path,k2,o,type,suffix);
        }
        if (k.length <= 1 && k2.length <= 1) {
            cmd = accurateFindCmd(path,k,o,type,suffix);
        }
        List<String> list = execCmds(cmd);
        List<FileItem> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String[] split = s.split(":");
            String fileName = split[0];
            String content = "";
            for (int j = 1; j < split.length; j++) {
                content += split[j];
            }
            res.add(new FileItem(fileName, content));
        }
        return Result.success(res);
    }

    @RequestMapping("/fuzzyFind")
    public Result fuzzyFind(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String keywords = jsonObject.getStr("keywords");
        String path = jsonObject.getStr("path");
        String type = jsonObject.getStr("type");
        String suffix = jsonObject.getStr("suffix");
        path = windowsPathToLinuxPath(path);

        String[] k = keywords.split("\\|");
        String[] o = new String[]{"i", "name"};
        String cmd = fuzzyFindCmd(path,k,o,type,suffix);

        List<String> list = execCmds(cmd);
        List<FileItem> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String[] split = s.split(":");
            String fileName = split[0];
            String content = "";
            for (int j = 1; j < split.length; j++) {
                content += split[j];
            }
            res.add(new FileItem(fileName, content));
        }
        return Result.success(res);
    }

    @RequestMapping("/accurateFind")
    public Result accurateFind(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String keywords = jsonObject.getStr("keywords");
        String path = jsonObject.getStr("path");
        String type = jsonObject.getStr("type");
        String suffix = jsonObject.getStr("suffix");
        path = windowsPathToLinuxPath(path);

        String[] k = keywords.split(";");
        String[] o = new String[]{"i", "name"};
        String cmd = accurateFindCmd(path,k,o,type,suffix);

        List<String> list = execCmds(cmd);
        List<FileItem> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String[] split = s.split(":");
            String fileName = split[0];
            String content = "";
            for (int j = 1; j < split.length; j++) {
                content += split[j];
            }
            res.add(new FileItem(fileName, content));
        }
        return Result.success(res);
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String windowsPathToLinuxPath(String path) {
        path = path.substring(0, 1) + path.substring(2, path.length());
        path = "\\" + path;
        path =  path.replace("\\", "/");
        return path;
    }

    public String convertPath(String path) {
        path = path.substring(1,path.length());
        path = path.substring(0, 1) + ":" + path.substring(1, path.length());
        return path;
    }


    public String fuzzyFindCmd(String path, String[] keyWords, String[] options, String type, String suffix) {
        if (suffix != null && suffix != "") {
            suffix = " | egrep -i " + "'*." + suffix + "'";
        }
        if (type.equals("d")) {
            suffix = "";
        }
        String optionJoin = String.join("", options);
        optionJoin = "-" + optionJoin + " ";
        type = "-type " + type + " ";
        String other = " 2>/dev/null | grep -v 'Binary file' | grep -v '(img'";
        String cmd = "";
        for (int i = 0; i < keyWords.length; i++) {
            String k = keyWords[i];
            if (i == 0) {
                cmd += "find "+  path + " " + optionJoin  + " '*" + k + "*' " + type + " ";
            } else {
                cmd += " -o " + optionJoin + "'*" + k + "*' " + type + " ";
            }
        }
        cmd += other;
        cmd += suffix;
        return cmd;
    }

    public String accurateFindCmd(String path, String[] keyWords, String[] options, String type, String suffix) {
        if (suffix != null && suffix != "") {
            suffix = " | egrep -i " + "'*." + suffix + "'";
        }
        if (type.equals("d")) {
            suffix = "";
        }
        String optionJoin = String.join("", options);
        optionJoin = "-" + optionJoin + " ";
        type = "-type " + type + " ";
        String other = " 2>/dev/null | grep -v 'Binary file' | grep -v '(img'";
        String cmd = "";
        for (int i = 0; i < keyWords.length; i++) {
            String k = keyWords[i];
            if (i == 0) {
                cmd += "find "+  path + " " + optionJoin  + " '*" + k + "*' " + type + " ";
            } else {
                cmd += " " + optionJoin + "'*" + k + "*' " + type + " ";
            }
        }
        cmd += other;
        cmd += suffix;
        return cmd;
    }
}
