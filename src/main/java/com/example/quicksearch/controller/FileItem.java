package com.example.quicksearch.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lin Rongchen
 * @create 2023-03-01-21:42:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileItem {
    String fileName;
    String content;
}
