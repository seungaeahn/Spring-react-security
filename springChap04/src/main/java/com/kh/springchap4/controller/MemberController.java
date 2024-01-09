package com.kh.springchap4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String showLoginForm() {
		return "login";
	}

//	@PostMapping("/login")
//	public String login(String username, String password, Model model) {
//		Member userInfo = memberService.login(username, password);
//		if(userInfo != null) {
//			model.addAttribute("loginSuccess", true);
//			return "loginSuccess";
//		} else {
//			model.addAttribute("error", "로그인에 실패했습니다");
//			return "loginSuccess";
//		}
//		
//	}
	
	@GetMapping("/memberList")
    public String getAllUsers(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "memberList";
    }
	
	
	@GetMapping("/update/{memberId}")
	public String updateUserForm(@PathVariable Long memberId, Model model) {
		Member member = memberService.getUserById(memberId);
		model.addAttribute("member", member);
		return "update";
	}
	
	@PostMapping("/update/{memberId}")
    public String updateUser(@PathVariable Long memberId, @ModelAttribute("member") @Validated Member member, BindingResult result) {
		member.setMemberId(memberId); 
    	memberService.updateMember(member);
        return "redirect:/memberList";
    }
    
	
	
	
	
	
	
	
	
//	//delete
//	@GetMapping("/delete/{memberId}")
//	public String deleteMember(@PathVariable Long memberId) {
//		memberService.deleteMemberById(memberId);
//		return "redirect:/memberList";
//	}
//	
//	
//	@GetMapping("")
//	public String showMemberList(Model model) {
//		model.addAttribute("", model);
//		return "memberList";
//	}

}
