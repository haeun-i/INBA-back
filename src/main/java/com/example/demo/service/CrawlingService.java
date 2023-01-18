package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    /**
     * 조회할 URL셋팅 및 Document 객체 로드하기
     */
    private static final String url = "https://cse.inha.ac.kr/cse/889/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGY3NlJTJGMjQzJTJGYXJ0Y2xMaXN0LmRvJTNG";
    public List<List<String>> process() {

        List<List<String>> noticeList = new ArrayList<>();
        Connection conn = Jsoup.connect(url);
        //Jsoup 커넥션 생성

        Document document = null;
        try {
            document = conn.get();
            Elements elements = document.select("._artclTdTitle");
            for(Element e : elements){
                List<String> notice = new ArrayList<>();
                String url = "https://cse.inha.ac.kr/" + e.getElementsByAttribute("href").attr("href");
                String text = e.select("strong").first().ownText();
                notice.add(text);
                notice.add(url);
                noticeList.add(notice);
            }
            //url의 내용을 HTML Document 객체로 가져온다.
            //https://jsoup.org/apidocs/org/jsoup/nodes/Document.html 참고
        } catch (IOException e) {
            e.printStackTrace();
        }

        return noticeList;
    }




}