package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.ShowDto;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;



@RequiredArgsConstructor
@Service
public class OcrService {

    @Value("${clova.api.url}")
    private String apiURL;

    @Value("${clova.api.key}")
    private String secretKey;

    private final UserRepository userRepository;

    public StringBuffer detectStudentNum(String s) throws IOException {

        String imageFile = "./" + s;

        try
        { URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println(response);
            br.close();

            return response;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Transactional
    public String jsonParsingNum(StringBuffer response) throws ParseException {

        String result = "";
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());


        JSONArray objectImage = (JSONArray) jsonObject.get("images");

        JSONObject objectImageIn = (JSONObject) objectImage.get(0);
        JSONArray objectField = (JSONArray) objectImageIn.get("fields");

        for (int i = 0; i < objectField.size(); i++) {
            JSONObject object = (JSONObject) objectField.get(i);

            String text = (String) object.get("inferText");

            result = result + text;
        }

        return result;
    }

    public StringBuffer detectTextLeft(String s) throws IOException {
        String imageFile = s;

        try
        { URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setReadTimeout(30000);
        con.setRequestMethod("POST");
        String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-OCR-SECRET", secretKey);

        JSONObject json = new JSONObject();
        json.put("version", "V2");
        json.put("requestId", UUID.randomUUID().toString());
        json.put("timestamp", System.currentTimeMillis());

        JSONObject image = new JSONObject();
        image.put("format", "jpg");
        image.put("name", "demo");
        JSONArray images = new JSONArray();
        images.add(image);
        json.put("images", images);
        String postParams = json.toString();

        con.connect();
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        long start = System.currentTimeMillis();
        File file = new File(imageFile);
        writeMultiPart(wr, postParams, file, boundary);
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        return response;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public StringBuffer detectTextRight(String s) throws IOException {
        String imageFile = s;
        try
        { URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

    public String jsonParsing(StringBuffer response) throws ParseException {

        String result = "";
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());


        JSONArray objectImage = (JSONArray) jsonObject.get("images");

        JSONObject objectImageIn = (JSONObject) objectImage.get(0);
        JSONArray objectField = (JSONArray) objectImageIn.get("fields");

        for (int i = 0; i < objectField.size(); i++) {
            JSONObject object = (JSONObject) objectField.get(i);

            String text = (String) object.get("inferText");

            result = result + text + " ";
        }

        return result;
    }

    public List<ShowDto> requireMajorList(String s){
        System.out.println(s);

        String[] str_list = s.split(" ");
        List<String> score_list = Arrays.asList("A+", "B+", "C+", "D+", "P", "F", "RE", "A0", "B0", "C0", "D0", "-");
        int trash_cnt = 0;

        // 쓰레기값 제거
        for(int i=0; i<str_list.length; i++) {
            if(i+7 < str_list.length && str_list[i].equals(":") && str_list[i+7].equals("금학기")) {
                for(int j=0; j<13 && i+j < str_list.length; j++) {
                    str_list[i+j] = "";
                }
            }
            if(str_list[i].equals(":") && str_list[i+2].contains("취득")) {
                for(int j=0; j<14 && i+j < str_list.length; j++) {
                    str_list[i+j] = "";
                }
            }
        }

        // 잘못 인식 된 학수번호 수정
        for(int i=0; i<str_list.length; i++) {
            if(str_list[i].length() == 7 && !str_list[i].matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                for(int j=0; j<3; j++) {
                    if(str_list[i].charAt(j) == '0') str_list[i] = str_list[i].substring(0, j) + 'O' + str_list[i].substring(j+1);
                }
                for(int j=3; j<7; j++) {
                    if(str_list[i].charAt(j) == 'O') str_list[i] = str_list[i].substring(0, j) + '0' + str_list[i].substring(j+1);
                }

            }
        }


        // 학수번호 붙이는거 - 한글 전까지
        for(int i=0; i<str_list.length; i++) {
            int j=1;
            if(str_list[i].matches("^[A-Z][A-Z][A-Z][0-9]*")) {
                while((str_list[i]+str_list[i+j]).length() <= 7 && i+j < str_list.length && !str_list[i+j].matches(".*[A-Zㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                    str_list[i] += str_list[i+j];
                    str_list[i+j] = "";
                    j++;
                }
            }
        }

        // 학수번호 뒤에 있는 것들 죄다 붙이기
        for(int i=1; i<str_list.length; i++) {
            if(str_list[i-1].matches("^[A-Z][A-Z][A-Z][0-9]*")) {
                int j=1;
                while(i+j < str_list.length && (!str_list[i+j].contains(".0"))) {
                    str_list[i] += str_list[i+j];
                    str_list[i+j] = "";
                    j++;
                }
            }
        }



        List<String> list = new ArrayList<>(Arrays.asList(str_list));
        list.removeIf((str) -> str == null || str.equals(""));

        for(int i=0; i<list.size(); i++) {
            if(list.get(i).equals("AO")) list.set(i, "A0");
            else if(list.get(i).equals("BO")) list.set(i, "B0");
            else if(list.get(i).equals("CO")) list.set(i, "C0");
            else if(list.get(i).equals("DO")) list.set(i, "D0");
            else if(list.get(i).equals("At")) list.set(i, "A+");
            else if(list.get(i).equals("Bt")) list.set(i, "B+");
            else if(list.get(i).equals("Ct")) list.set(i, "C+");
            else if(list.get(i).equals("Dt")) list.set(i, "D+");
        }


        // 금학기 수강에 학점 - 붙이기
        for(int i=0; i<list.size()-2; i++) {
            if(list.get(i).contains(".0") && !score_list.contains(list.get(i+1))) {
                list.add(i+1, "-");
                trash_cnt++;
            }
        }

        // 맨 마지막에 - 붙이기
        System.out.println(list.get(list.size()-1));
        if(trash_cnt != 0){
            list.add("-");
        }


        // 과목명 당기기전에 뒤에꺼 전부 통합
        for(int i=0; i<list.size()-1; i++) {
            if(score_list.contains(list.get(i)) && !list.get(i+1).matches("^[A-Z][A-Z][A-Z][0-9]*")) {
                int j=2;
                String plus_s = "";
                while(i+j < list.size() && !list.get(i+j).matches("^[A-Z][A-Z][A-Z][0-9]*")) {
                    plus_s += list.get(i+j);
                    list.set(i+j, "");
                    j++;
                }
                list.set(i+1, list.get(i+1)+plus_s);
            }
        }

        // 과목명 안 맞는거 당겨오기
        for(int i=0; i<=list.size()-2; i++) {
            if(score_list.contains(list.get(i))) {
                if(list.get(i+1).matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                    if(list.get(i+1).equals("-")) {
                        list.set(i-2, list.get(i-2)+list.get(i+1).substring(1));
                    }else{
                        list.set(i-2, list.get(i-2)+list.get(i+1));
                    }

                    list.set(i+1, "");
                }
            }
        }

        list.removeIf((str) -> str == null || str.equals(""));

        for(int i=0; i<list.size(); i++) {
            System.out.println(list.get(i));
        }

        List<ShowDto> showDtoList = new ArrayList<>();
            for(int i=0; i<list.size(); i+=4){
                ShowDto showDto = ShowDto.builder()
                    .index(i/4 + 1)
                        .subjectNum(list.get(i))
                    .subjectName(list.get(i+1))
                    .subjectScore(list.get(i+2))
                    .studentScore(list.get(i+3))
                    .build();
            showDtoList.add(showDto);
        }

        return showDtoList;
    }
}