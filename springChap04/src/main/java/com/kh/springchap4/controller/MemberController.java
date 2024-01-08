package com.kh.springchap4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.springchap4.model.Member;
import com.kh.springchap4.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("members", new Member());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerMember(Member member) {
		memberService.signUpMember(member);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("members", new Member());
		return "login";
	}

	@PostMapping("/login")
	public String loginMember(Member member) {
		memberService.signUpMember(member);
		return "redirect:/";
	}
	
	//update
	@GetMapping("/update/{memberId}")
	public String updateMember(@PathVariable Long memberId, Model model) {
		Optional<Member> member = memberService.getMemberById(memberId);
		member.ifPresent(value -> model.addAttribute("member", value));
		return "register";
	}

}
