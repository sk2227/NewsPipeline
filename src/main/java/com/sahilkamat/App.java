package com.sahilkamat;

import com.sahilkamat.services.ArticleScrapperService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ArticleScrapperService asp= new ArticleScrapperService();
        asp.fetchArticles();
    }
}
