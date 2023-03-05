package com.example.quicksearch.controller;

/**
 * @author Lin Rongchen
 * @create 2023-03-03-19:17:42
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileController {

    @RequestMapping("/files")
    public List<FileInfo> getFiles(@RequestBody String params) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String path = jsonObject.getStr("path");
        List<FileInfo> fileInfos = new ArrayList<>();
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                fileInfos.add(new FileInfo(f.getName(), f.isDirectory(), f.getAbsolutePath()));
            }
        }
        return fileInfos;
    }

    public static class FileInfo {
        private String name;
        private boolean isFile;
        private String path;

        public FileInfo(String name, boolean isFile, String path) {
            this.name = name;
            this.isFile = isFile;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public boolean getIsFile() {
            return isFile;
        }

        public String getPath() {
            return path;
        }
    }

}

