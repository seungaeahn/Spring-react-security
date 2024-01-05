package com.kh.springchap4.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//CORS(Cross-Origin Resource Sharing)
	//Cors는 웹 브라우저에서 실행되는 자바스크립트가 다른 도메인에 접근할 수 있도록 해주는 보안 기술
	//주로 코딩에서나(웹에서나) API서버에서 다른 도메인으로부터 HTTP 요청을 허용하도록 설정하는데 사용
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		//CorsConfigurationSource 객체 생성 
		CorsConfiguration configuration = new CorsConfiguration();
		//허용할 Origin(주소)목록 설정 (여기서는 http://localhost:3000 만 허용해준것)
		//만약에 주소가 2가지 이상이면 콤마,를 찍어서 주소를 추가할 수 있음 
		//configuration.setAllowedOrigins(Arrays.asList("http://localfost:3000", "http://localfost:3001"));
		configuration.setAllowedOrigins(Arrays.asList("http://localfost:3000"));
		
		//허용할 HTTP 메서드 목록 설정
		/* configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH"));
		 * "GET" : 데이터 조회하기 위한 용도로 사용
		 * "POST" : 서버에 데이터를 제출하기 위한 용도
		 * "DELETE" : 서버에서 데이터를 삭제하기 위한 용도
		 * "PUT" : 서버에서 데이터를 업데이트하기 위한 용도로 클라이언트가 수정한 내용을 데이터베이스에 업데이트 하기위해 사용
		 * "OPTIONS" : 실제로 요청하기 전에 브라우저가 서버한테 해당 데이터에 대해 어떤 메서드와 헤더들을 사용할 수 있는지 확인하기 위한 용도
		 * 				CORS에서 사전 검사를 요청하는데 많이 사용
		 * "PATCH" : 데이터에 일부만 업데이트하기 위한 용도
		 * 			 PUT과 비슷하지만 전체 데이터를 업데이트하는 대신 일부만 업데이트할 때 사용
		 * */
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
		
		//허용할 HTTP 헤더 목록 설정
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origins", "Content-type", "Accept", "Authorization"));
		
		//자격 증명(cookie, 헤더 등)을 허용할지 여부 결정 
		//주로 로그인 상태를 유지하거나 사용자 관련 정보를 요청과 함께 전송할 때 많이 활용
		configuration.setAllowCredentials(true);
		
		//UrlBasedCorsConfigurationSource 객체 생성 등록 
		//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
		//경로별로 다른 CORS 구성을 관리할 수 있도록 도와줌
		//경로를 설정해서 설정한 경로에만 CORS 설정을 동일하게 적용하겠다는 의미
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		// CORS 구성이 적용되도록 설정된 source를 반환 
		return source;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		//활성화 
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		//비활성화 .cors(cors -> cors.disable())
		
		/**
		 * 만약에 CSRF 사용하게 되면 SNS(Google, Kakao, Naver 등에서 로그인을 위한 토큰) 
		 * CSRF(Cross-Site RequestFogery) 공격을 방지하기 위한 방법 중 하나
		 * CSRF 토큰이 있으며, 이 토큰은 사용자의 세션과 관련된 고유한 값으로 각 요청에 포함되어있는지 토큰을 통해 확인하고 유효한지를 검증
		 * 사용자가 로그인할 때마다 서버는 특별한 1회성 CSRF 토큰을 생성하고 이 토큰을 안전하게 쿠키에 저장
		 * 웹 페이지의 폼이나 Ajax 요청에서 토큰을 포함시켜야 함
		 * 보통은 input hidden 필더나 헤더에 토큰을 넣어서 전송
		 * 1회성이기 때문에 데이터베이스에는 저장하지 않음 
		 * 토큰이 일치하지 않으면 해당 요청은 거부되거나 무시됨 
		 * .csrf(csrf -> csrf.disable())
		 	
		 */
		.authorizeHttpRequests(authorizeHttpRequests ->
		authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		.oauth2Login(oauth2Login ->
		oauth2Login
				.successHandler(new SimpleUrlAuthenticationSuccessHandler("/loginSuccess")));
		return http.build();
	}
	
	//추후 네이버 등록한 정보를 저장 
	@Bean
	//네이버 클라이언트의 등록 정보를 생성하는 메서드
	//클라이언트 아이디와 시크릿, 인증 후 리다이렉트 URI 설정
	//네이버에서 인증하고 나서 OAuth2 엔드포인트 설정
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(naverClientRegistration(), googleClientRegistration(), kakaoClientRegistration());
	}
	
	public ClientRegistration naverClientRegistration() {
		return ClientRegistration.withRegistrationId("naver")
				.clientId("Z4rTscYPETXXWJp5C4Jm")
				.clientSecret("pQFFq_2etr")
				.redirectUri("http://localhost:8080/login/oauth2/code/naver")
				.clientName("Naver")
				.authorizationUri("http://nid.naver.com/oauth2.0/authorize")
				.tokenUri("https://nid.naver.com/oauth2.0/token")
				.userInfoUri("https://openapi.naver.com/v1/nid/me")
				.userNameAttributeName("response")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.build();
	}
	
	//카카오
	public ClientRegistration kakaoClientRegistration() {
		return ClientRegistration.withRegistrationId("kakao")
				.clientId("4bec4086e3499d1d7569601f8c987f8d")
				.clientSecret("z0bNunQsk71ZbErkPJuEqgXuLit4F5br")
				.redirectUri("http://localhost:8080/login/oauth2/code/kakao")
                .clientName("Kakao")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname", "profile_image", "account_email") 
                .build();
	}
	
	//구글 로그인
	public ClientRegistration googleClientRegistration() {
		return ClientRegistration.withRegistrationId("google")
				.clientId("484985427980-4c5dv46g2gubu65mjura2387oslnbd0j.apps.googleusercontent.com")
				.clientSecret("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")
				.redirectUri("http://localhost:8080/login/oauth2/code/google")
				.clientName("Google")
				.authorizationUri("http://accounts.google.com/o/oauth2/auth")
				.tokenUri("https://www.googleapis.com/oauth2/v4/token")
				.userInfoUri("https://www.googleapis.com.oauth2/v3/userinfo")
				.userNameAttributeName("sub")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.scope("openid", "profile", "email")
				.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
				.build();
	}
	
	
	

	//인증 통합 관리하는 매니저
	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
	        ClientRegistrationRepository clientRegistrationRepository,
	        OAuth2AuthorizedClientRepository authorizedClientRepository) {
		//클라이언트 인증 처리
	    OAuth2AuthorizedClientProvider authorizedClientProvider =
	            OAuth2AuthorizedClientProviderBuilder.builder()
	                    .authorizationCode()
	                    .build();
	  //클라이언트 등록 정보와 인증된 클라이언트 저장소를 설정 
	    DefaultOAuth2AuthorizedClientManager authorizedClientManager =
	            new DefaultOAuth2AuthorizedClientManager(
	                    clientRegistrationRepository, authorizedClientRepository);
	    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
	
	    return authorizedClientManager;
	}
}