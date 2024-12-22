package com.sahilkamat.utils;

import com.sahilkamat.services.ArticleScrapperService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleTranslateUtil {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTranslateUtil.class);

    private static final String API_URL = "https://google-translator9.p.rapidapi.com/v2";
    private static final String API_KEY = "d13316f8femsh0756541f5a74c24p1ef07fjsn9950aba11f8e";
    private static final String API_HOST = "google-translator9.p.rapidapi.com";
    private static String sourceLang="es";
    private static String targetLang="en";




    public  static String translateText(String text) {

        try{

            String payload = String.format(
                    "{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\",\"format\":\"text\"}",
                    text, sourceLang, targetLang
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("x-rapidapi-key", API_KEY)
                    .header("x-rapidapi-host", API_HOST)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            logger.info("Calling Google Translate API to translate String : " + text);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("API returned response with status code: " + response.statusCode());
            logger.info(" API Response Body" + response.body());

            if (response.statusCode() == 200) {
                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.body());
                return jsonResponse.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText");
            } else {
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }




    }


}
