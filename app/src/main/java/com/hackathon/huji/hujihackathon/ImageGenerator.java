package com.hackathon.huji.hujihackathon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ImageGenerator {

    private static List<String> urls = Arrays.asList(
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group1.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group2.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group3.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group4.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group5.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group6.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group7.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group8.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group9.jpg",
            "http://imrisbucket.s3-website-us-west-2.amazonaws.com/group10.jpg");


    public static String get(){
        Random rand = new Random();
        int n = rand.nextInt(urls.size());
        return urls.get(n);
    }

}
