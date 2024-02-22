package ru.nsu.salina;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Counter {
    //private final static int N = 1024;
    private int wordCount;
    private Map<String, Integer> wordMap;

    private static String[] findWords(String line) {
        return line.split("[^A-Za-z0-9]+");
        /*String[] words = new String[N];
        String word = "";
        int len = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                word = word + c;
            } else {
                if (!word.isEmpty()) {
                    words[len] = word;
                    ++len;
                }
                word = "";
            }
        }
        if (!word.isEmpty()) words[len] = word;
        return words;*/
    }
    private void initializeWordMap() {
        this.wordMap = new HashMap<>();
    }
    public void readInput(Scanner scanner) {
        String line = scanner.nextLine();
        if (wordMap == null) initializeWordMap();

        while(line != null) {
            String[] words = findWords(line);
            for (int i = 0; i < words.length; ++i) {
                if (words[i] == null) break;
                wordCount++;
                if (wordMap.containsKey(words[i])) {

                    wordMap.put(words[i], wordMap.get(words[i]) + 1);
                } else {
                    wordMap.put(words[i], 1);
                }
            }
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException ex) {
                break;
            }
        }
    }
    public int getWordCount() {
        return wordCount;
    }
    public Map<String, Integer> getWorldMap() {
        return wordMap;
    }



}
