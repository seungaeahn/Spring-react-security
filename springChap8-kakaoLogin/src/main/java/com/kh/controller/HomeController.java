package com.kh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.service.KakaoUserService;
import com.kh.vo.KakaoUser;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final KakaoUserService kakaoService;
    /*
    public HomeController(KakaoUserService kakaoService) {
		this.kakaoService = kakaoService;
	}
    */
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "index";
    }
    
    //현재는 Kakao 로그인하지만 추후에 SNS 인증을 거치지 않은 회원가입일 경우를 생각해서 GetMapping을 찍어주는 것 
    @GetMapping("/main")
    public String home(Model model, HttpSession session) {
        	KakaoUser InUser = (KakaoUser) session.getAttribute("InUser");
          model.addAttribute("InUser", InUser);
        return "main";
    }

	

}
