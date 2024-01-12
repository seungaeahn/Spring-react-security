package com.kh.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.dto.KakaoDTO;
import com.kh.repository.KakaoUserRepository;
import com.kh.vo.KakaoUser;

@Service
public class KakaoUserService {
	@Value("${kakao.client.id}")
	private String KAKAO_CLIENT_ID;
	
	@Value("${kakao.client.secret}")
	private String KAKAO_CLIENT_SECRET;
	
	//value를 썼기 때문에 각 값을 변수에 넣어서 보관하겠다는 의미
	@Value("${kakao.redirect.url}")
	private String KAKAO_REDIRECT_URL;
	
	//URI는 URL보다 큰 범위 URL은 URI 안에 있는 링크일 뿐
	//카카오 자체에서 인증으로 돌아가는 공식 주소
	private final static String KAKAO_AUTH_URI="https://kauth.kakao.com";
	private final static String KAKAO_API_URI="https://kapi.kakao.com";
	

	private final KakaoUserRepository kakaoUserRepository;
	
	public KakaoUserService(KakaoUserRepository UserRepository) {
		this.kakaoUserRepository = UserRepository;
	}
	
	
	public String getKakaoLogin() {
		return KAKAO_AUTH_URI + "/oauth/authorize?client_id=" + KAKAO_CLIENT_ID + "&redirect_uri=" 
					+ KAKAO_REDIRECT_URL + "&response_type=code";
		
	}
	
	public KakaoDTO getKakaoInfo(String code, String name, String birthdate) throws Exception {
		if(code==null) throw new Exception("존재하는 인증 코드가 없습니다.");
		
		//로그인이 허용된 토큰이 들어갈 공간
		String accessToken="";
		
		//http HEADER에 내 정보를 흘려 보내겠다 작성 
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/x-www-form-urlencoded");
			
			/*	
			 * 카카오 로그인 아이디 + 시크릿키 + 코드 + 리다이렉트 url 모두 붙여줄 것
				MultiValueMap Spring에서 제공하는 인터페이스
				여러개의 값을 하나의 키에 연결할 수 있도록 합쳐주는 역할
				http 에서 요청이 여러개일때 자주 사용 
				<String, String> 인 이유는 key, value를 각각 String으로 넣을 것 이기 떄문
				MultiValueMap<String, String> key와 ㅍ미ㅕㄷfmf String으로 설정해서 카카오톡에서 설정한 값을 적고 그 값에 대한 내용들을 갖고 오겠다는 의미
				예를 들어
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				params.add("key1", "value1")
				params.add("key1", "value2")
				params.add("key2", "value3")
				key1 -> [value1, value2]
				key2 -> [value3]
		
			*/
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "authorization_code");
			params.add("client_id", KAKAO_CLIENT_ID);
			params.add("client_secret", KAKAO_CLIENT_SECRET);
			params.add("code", code);
			params.add("redirect_uri", KAKAO_REDIRECT_URL);
			
			//Spring에서 제공하는 것
			//RestTemplate을 사용해서 Http에 요청을 보내고 요청에 대한 응답을 받아오는 템플릿 
			//HTTP 요청을 생성하고 서버를 전달해주는 역할
			RestTemplate restTemplate = new RestTemplate();
			
			//HTTP 요청이나 응답의 헤더 본문 http 메서드를 포함하는 엔티티
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
			
			ResponseEntity<String> response = restTemplate.exchange(
					//요청보낼 URI
					//카카오 OAuth 토큰을 얻기 위해서 /oauth/token POST 요청을 보냄
					KAKAO_AUTH_URI + "/oauth/token",
					HttpMethod.POST,
					httpEntity, //본문에서 요청하는 내용과 헤더 정보를 포함하는 객체
					String.class // 서버에서 오는 응답을 String 형태로 받아오겠다는 의미
					);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
			accessToken = (String) jsonObj.get("access_token");
			
			
		
			
		} catch(Exception err) {
			throw new Exception("api를 불러오지 못했습니다.");
		}
		
		return getUserInfoWithToken(accessToken, name, birthdate);
		
		
	}
	
	//카카오에서 회사에게 로그인 할 수 있도록 허용받은 로그인 허용 토큰을 사용해서 카카오 API에서 사용자 정보를 가져오는 메서드
	
	private KakaoDTO getUserInfoWithToken(String accessToken, String name, String birthdate) throws Exception{
		//토큰용 HttpHeader 생성
		HttpHeaders headers = new HttpHeaders();
		//Bearer : HTTP 요청에서 인증할 때 특정 형태로 변환해서 토큰 타입을 나타내는 것 
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//내용을 담을 템플릿 생성
		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = rt.exchange(
				KAKAO_API_URI + "/v2/user/me",
				HttpMethod.POST,
				httpEntity,
				String.class
			);
		//Response 데이터를 가지고 오기
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
		JSONObject account = (JSONObject) jsonObj.get("kakao_account");
		JSONObject profile = (JSONObject) account.get("profile");
		
		long id = (long) jsonObj.get("id");
		String email = String.valueOf(account.get("email"));
		String nickname = String.valueOf(profile.get("nickname"));
		
		return KakaoDTO.builder()
				.id(id)
				.email(email)
				.nickname(nickname)
				// 이름과 생년월일 추가
				.name(name)
				.birthdate(birthdate)
				.build();
		
	}
	
	//데이터베이스에 저장하는 메서드 생성 
	public KakaoUser registerUser(KakaoDTO kakaoDTO) {
		KakaoUser user = new KakaoUser();
		user.setEmail(kakaoDTO.getEmail());
		user.setNickname(kakaoDTO.getNickname());
		user.setName(kakaoDTO.getName());
		user.setBirthdate(kakaoDTO.getBirthdate());
		
		//사용자를 데이터베이스에 저장
		return kakaoUserRepository.save(user);
	}
	
}

