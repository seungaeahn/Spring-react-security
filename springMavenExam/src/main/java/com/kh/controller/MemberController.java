package com.kh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.service.MemberService;
import com.kh.vo.MemberVO;

@Controller
@RequestMapping("/")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@PostMapping("login")
	public String memberLogin(String memberId, String memberPwd) {
		// 로그인할 때 필요한 코드 작성
		memberService.loginMember(memberId, memberPwd);
		
		return "redirect:/";
	}


}
