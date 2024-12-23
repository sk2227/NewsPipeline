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

    private static final String API_URL = ConfigLoader.get("googleTranslate.api_url");
    private static final String API_KEY = ConfigLoader.get("googleTranslate.api_key");
    private static final String API_HOST = ConfigLoader.get("googleTranslate.api_host");
    private static String sourceLang="es";
    private static String targetLang="en";




    public  static String translateText(String text) {

        try{

            String payload = String.format(
                    "{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\"}",
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
                // Parse the JSON response eg: {"data":{"translations":{"translatedText":"'The three borders'"}}}
                JSONObject jsonResponse = new JSONObject(response.body());

                return jsonResponse.getJSONObject("data")
                        .getJSONObject("translations")
                        .getString("translatedText");
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
