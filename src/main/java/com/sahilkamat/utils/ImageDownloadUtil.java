package com.sahilkamat.utils;

import com.sahilkamat.services.ArticleScrapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;
public class ImageDownloadUtil {

    private static final Logger logger = LoggerFactory.getLogger(ArticleScrapperService.class);


    //final String destinationPath = "C:\\Users\\Admin\\IdeaProjects\\NewsPipeline\\images\\";
    final String destinationPath="images/";
    private  int count = 1;

    public  void downloadImage(List<String> imgURL) throws IOException {


        for (String s : imgURL) {
            URL url = new URL(s);
            InputStream in = url.openStream();

            String randomString = generateRandomString();

            Path path = Paths.get(destinationPath + "image_"+ count+"_" +randomString + ".jpg");
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

            logger.info("Image downloaded successfully to: "+ destinationPath);
            count++;
        }


    }

    private static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }




}
