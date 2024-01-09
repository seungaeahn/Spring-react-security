package com.kh.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiJsonController {
	
	@GetMapping(value="/api/get", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String DBInsert() {
		//데이터를 시작하기 전에 StringBuilder
		StringBuilder result = new StringBuilder();
		
		try {
			String apiUrl="http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
			String apiKey="ctDYVoX6WcuH7feW4imqDK4V334L9cSBzBHtLA7/QB6zg3YlVuQ45zTjJTJ3EpX7BY4J5e4XBbdBCoKb3ZB5zQ==";
			String numOfRows="1";
			String pageNo="10";
			String dataType="XML";
			String stnId = "108";
			String tmFc="202401090600";
			String encodedApiKey= URLEncoder.encode(apiKey, "UTF-8");
			String encodedUrl= String.format("%s?serviceKey=%s&numOfRows=%s&pageNo=%s&dataType=%s&stnId=%s&tmFc=%s",
                    apiUrl, encodedApiKey, numOfRows, pageNo, dataType, stnId, tmFc);
			URL url = new URL(encodedUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			//화면에 데이터가 출력될 수 있도록 readLine을 써서 출력
			String line;
			while((line = reader.readLine()) !=null) {
				result.append(line);
			}
			reader.close();
			connection.disconnect();
			
			
			
		} catch(Exception err) {
			err.printStackTrace();
		}
		
		//json파일로 변환해서 보여주기
		String jsonResult = convertXmlToJson(result.toString());
		return jsonResult;
		
		
	}
	//xml로 보이는 파일을 json 형식으로 변환해서 화면에 출력하는 메서드
	private String convertXmlToJson(String xmlData) {
		JSONObject jsonObj = XML.toJSONObject(xmlData);
		return jsonObj.toString();
		
	}
	
		
	
}
