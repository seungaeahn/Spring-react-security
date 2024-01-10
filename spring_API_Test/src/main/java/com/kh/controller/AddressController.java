package com.kh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AddressController {

    @GetMapping(value = "/map", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getKakaoApplicationFromAddress(@RequestParam("address") String roadFullAddr) {
    	
    	 System.out.println("Received address parameter: " + roadFullAddr);
    	
        String apiUrl = "http://dapi.kakao.com/v2/local/search/address.json";
        String apiKey = "c1c604d6b7fec95cfc7740c669ff935c";
        String jsonString = null;

        try {
            roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");
            String addr = apiUrl + "?query=" + roadFullAddr;
            URL url = new URL(addr);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuffer docJson = new StringBuffer();
            String line;

            while ((line = rd.readLine()) != null) {
                docJson.append(line);
            }
            jsonString = docJson.toString();
            rd.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("addressResult", jsonString);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
