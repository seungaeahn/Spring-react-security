package com.kh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//xml로 된 파일을 가져왔을 때 
//최종적으로 리턴하는 종류가 html = @Controller
//최종적으로 리턴하는 종류가 backend api 주소라면 @RestController
@RestController
public class ApiXmlController {
	
	//메서드를 작성해줬으면 = 메서드를 최종적으로 반환하는 값도 같아야 한다.
	@GetMapping("/api_explorer")
	public ResponseEntity<?> xmlApi() {
		//파일을 가지고 올 때 항상 String Builder
		//파일 내용이 들어갈 Builder를 미리 세팅해서 비어있는 Builder 안에 가져온 내용을 채울 예정
		StringBuilder result = new StringBuilder();
		
		// 1. apiUrl 2. key 3. xml 형식으로 가지고 올 것
		// 만약에 xml 파일이 url 주소값으로 가지고 오는 것이 아니라
		// 파일 자체를 다운받아서 가지고 있다면 1. apiUrl 2. key 를 넣어줄 의무가 없다.
		// 파일의 위치와 파일을 변환해주는 작업을 해주면 됨 
		// 파일을 가지고 올 때는 Buffer와 Stream을 사용한다.
		// 최종적으로 json 형식으로 xml 파일이 변환해서 출력을 할 것
		//mapList에서 받아오는 형식과 =을 써서 가져오는 형식이 같아야 한다.
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>(); 
		
		// return data key 목록을 담을 공간 만들기
		List<String> headerList = new ArrayList<String>();
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get("isgi.d_0001.xml"));
			String line = "";
			//readLine() : 한줄씩 읽어서 문자열을 리턴해주는 메서드
			while((line = br.readLine()) != null) {
				List<String> stringList = new ArrayList<String>();
				// split() : 입력받은 형식을 정규 표현식이나 특정 문자를 기준으로 문자열을 나누어 배열에 저장해서 리턴 
				String stringArray[] = line.split("xml 형식에 맞춰서 다듬기");
				stringList = Arrays.asList(stringArray);
				
				//맨 위가 어디인지 확인 후 header 인식
				//꼭대기를 찾아주고 나서 데이터를 json 형식으로 출력 
				if(headerList.size() == 0) {
					headerList = stringList;
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					
					for(int i = 0; i < headerList.size(); i++) {
						map.put(headerList.get(i), headerList.get(i));
					}
					mapList.add(map);
				}
			}
			System.out.println(mapList);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(mapList, HttpStatus.OK);
		
	}

}
