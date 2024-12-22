package com.sahilkamat.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class TranslationUtil {

    private static final int REQUEST_DELAY_MS = 10000;

    public  static String translateText(String text) {
        try {
            // Prepare the JSON payload for the translation request
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("from", "es");  // English
            jsonPayload.put("to", "en");    // Arabic
            jsonPayload.put("q", text);     // Text to translate

            // Build the HttpRequest using HttpClient
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://rapid-translate-multi-traduction.p.rapidapi.com/t"))
                    .header("Content-Type", "application/json")
                    .header("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com")
                    .header("x-rapidapi-key", "d13316f8femsh0756541f5a74c24p1ef07fjsn9950aba11f8e")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload.toString()))  // Send the JSON as string
                    .build();

            // Create the HttpClient and send the request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

           //System.out.println("Response Body: " + response.body());


            // Check the response status code
            if (response.statusCode() == 200) {
                // Parse and return the translated text from the response
                JSONArray jsonResponse = new JSONArray(response.body());  // Use JSONArray instead of JSONObject
                return jsonResponse.getString(0);  // Get the first element from the array (translated text)
            } else {
                System.out.println("Error: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            // Delay after each request to avoid hitting rate limit
            try {
                Thread.sleep(REQUEST_DELAY_MS);  // Delay for a specified time between requests
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore the interrupted state
            }
        }
    }

//    public static void main(String[] args) {
//        TranslationUtil tl= new TranslationUtil();
//        tl.translateText("Una Europa en dos frentes");
//    }
}