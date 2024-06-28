package org.example.module;
/**
 * This code is auto generated by boj-helper-in-java v.0.02 
 * Anyone can join this simple open-source project any time*
 *
 * github : https://github.com/Chwangsky
 */ 

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "C:/PS/boj-helper/formatTest.txt";
        String content = null;
        try {
            // 파일의 모든 내용을 읽어서 String으로 변환
            content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(content);

        assert (content != null);

        // 주석 부분을 일치시키는 정규 표현식
        String regex = "(?s)/\\*.*?\\*/|//.*?(\\R|$)";
        // (?s)는 DOTALL 모드 활성화
        // \\R는 모든 줄 바뀜 문자를 의미

        // 주석을 기준으로 문자열을 분할
        String[] parts = content.split(regex);

        // 주석이 아닌 부분만 리스트에 추가
        List<String> nonCommentParts = new ArrayList<>();
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                nonCommentParts.add(trimmed);
            }
        }

        // 결과 배열로 변환
        String[] result = nonCommentParts.toArray(new String[0]);

        // 결과 출력
        for (String part : result) {
            System.out.println(part);
            System.out.println("---------");
        }

    }
}