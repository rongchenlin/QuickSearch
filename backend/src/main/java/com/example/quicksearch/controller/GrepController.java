package com.example.quicksearch.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
public class GrepController {

    @RequestMapping("/grep")
    public Result grep(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String keywords = jsonObject.getStr("keywords");
        String path = jsonObject.getStr("path");
        String optionStr = jsonObject.getStr("optionStr");
        path = windowsPathToLinuxPath(path);

        String[] k = keywords.split("\\|");
        String[] o = new String[]{"r", optionStr};
        String cmd = "";
        if (k.length > 1) {
            cmd = fuzzyGrepCmd(path,k,o);
        }
        String[] k2 = keywords.split(";");
        if (k2.length > 1) {
            cmd = accurateGrepCmd(path,k2,o);
        }
        if (k.length <= 1 && k2.length <= 1) {
            cmd = accurateGrepCmd(path,k,o);
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


    @RequestMapping("/fuzzyGrep")
    public Result fuzzyGrep(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String keywords = jsonObject.getStr("keywords");
        String path = jsonObject.getStr("path");
        path = windowsPathToLinuxPath(path);

        String[] k = keywords.split("\\|");
        String[] o = new String[]{"i", "r", "E"};
        String cmd = fuzzyGrepCmd(path,k,o);

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

    @RequestMapping("/accurateGrep")
    public Result accurateGrep(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String keywords = jsonObject.getStr("keywords");
        String path = jsonObject.getStr("path");
        path = windowsPathToLinuxPath(path);

        String[] k = keywords.split(";");
        String[] o = new String[]{"i", "r", "E"};
        String cmd = accurateGrepCmd(path,k,o);

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

    @RequestMapping("/openFile")
    public Result openFiles(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String fileName = jsonObject.getStr("fileName");
        String path = convertPath(fileName);
        openFile(path);
        return Result.success();
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


    public void openFile(String filePath) {
        File file = new File(filePath);
        //if (file.exists()) {
            try {
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start", "\"\"", file.getAbsolutePath());
                pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
    }

    public String fuzzyGrepCmd(String path, String[] keyWords, String[] options) {
        String keyJoin = String.join("|", keyWords);
        keyJoin = "'" + keyJoin + "' ";
        String optionJoin = String.join("", options);
        optionJoin = "-" + optionJoin + " ";
        String other = " 2>/dev/null | grep -v 'Binary file' | grep -v '(img'";
        String cmd = "grep " + keyJoin + optionJoin + path + other;
        return cmd;
    }

    public String accurateGrepCmd(String path, String[] keyWords, String[] options) {

        String optionJoin = String.join("", options);
        optionJoin = "-" + optionJoin + " ";
        String other = " 2>/dev/null | grep -v 'Binary file' | grep -v '(img'";
        String cmd = "";
        for (int i = 0; i < keyWords.length; i++) {
            String k = keyWords[i];
            if (i == 0) {
                cmd += "grep " + "'" + k + "' " +  optionJoin + path;
            } else {
                cmd += " | " + "grep " + "'" + k + "'";
            }
        }
        cmd += other;
        return cmd;
    }
}
