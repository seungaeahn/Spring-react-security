package com.kh.springchap4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap4.service.UserService;

//@Controller

/**
 * @CrossOrigin : 각 컨트롤러마다 바라보는 url이 다를 수 있기 때문에 어떤 도메인을 허용해줄지 작성해주는 공간
 * 	origins : 하나의 도메인이 아니라 다수의 도메인을 넣을 수 있음 
 * 			  작성해준 도메인의 요청을 허용
 * allowCredentials : 인증된 사용자의 쿠키를 요청에 포함할 수 있도록 허용할지에 대한 여부 
 * allowedHeader : 허용할 수 있는 헤더를 지정
 * 				   * 는 모든 헤더 허용 
 * methods : 원하는 메서드만 설정해서 받을 수 있도록 한번 더 처리할 수 있음 
 * {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
 * 
 */
@RestController
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true", allowedHeaders="*")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "index";
	}
	//구글 로그인을 위한 URL 추가
	@GetMapping("/oauth2/authorization/google")
	public String googleLogin() {
		return "redirect:/oauth2/authorization/google";
	}
	
	//네이버 로그인을 위한 URL 추가
	@GetMapping("/oauth2/authorization/naver")
	public String naverLogin() {
		return "redirect:/oauth2/authorization/naver";
	}
	
	//네이버 로그인을 위한 URL 추가
	@GetMapping("/oauth2/authorization/kakao")
	public String kakaoLogin() {
		return "redirect:/oauth2/authorization/kakao";
	}
	
	@GetMapping("/loginSuccess")
	public String loginSuccess(
			@AuthenticationPrincipal OAuth2User principal, Model model) {
		
		model.addAttribute("name", principal.getAttribute("name"));
		model.addAttribute("email", principal.getAttribute("email"));
		
		return "loginSuccess";
	}
	
//	@GetMapping("/loginSuccess")
//	public String loginSuccess(
//			@AuthenticationPrincipal OAuth2User principal, 
//			@RequestParam(value="naverResponse", required = false) String naverResponse,
//			Model model) {
//		
//		userService.naverLoginService(principal, naverResponse, model);
//		
//		return "loginSuccess";
//	}

}





		