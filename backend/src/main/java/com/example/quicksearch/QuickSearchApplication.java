package com.example.quicksearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob
 */
@SpringBootApplication
public class QuickSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickSearchApplication.class, args);
    }
}
