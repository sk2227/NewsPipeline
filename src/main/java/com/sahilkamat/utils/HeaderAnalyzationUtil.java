package com.sahilkamat.utils;

import com.sahilkamat.model.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderAnalyzationUtil {


    public void AnalyzeHeader(List<Article> articleList){

        Map<String, Integer> wordCountMap = new HashMap<>();

        for (Article article : articleList) {
            // Get the translated header from the Article model
            String translatedHeader = article.getTranslatedTitle();
            // Split the header into words, handling spaces and punctuation
            String[] words = translatedHeader.toLowerCase().split("\\s+");

            // Update word count in the map
            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z]", ""); // Remove any non-alphabetic characters (e.g., punctuation)
                if (!word.isEmpty()) {
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
        }

        long count = wordCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .peek(entry -> System.out.println(entry.getKey() + ": " + entry.getValue())) // Print the words and counts
                .count();


        if (count == 0) {
            System.out.println("No words found with count greater than 2 in the header.");
        }

    }



}
