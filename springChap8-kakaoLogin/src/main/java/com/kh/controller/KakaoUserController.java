package com.kh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.common.MsgEntity;
import com.kh.dto.KakaoDTO;
import com.kh.repository.KakaoUserRepository;
import com.kh.service.KakaoUserService;
import com.kh.vo.KakaoUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("kakao")
@RequiredArgsConstructor
public class KakaoUserController {
	private final KakaoUserService kakaoUserService;
	private final KakaoUserRepository kakaoUserRepository;
	@GetMapping("/callback")
	public String callback(HttpServletRequest request, 
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) String birthdate, Model model,
			HttpSession session)
					throws Exception{
		// kakao/callback을 작성하면 JSON 형식으로 이동했지만 register html 파일로 이동하게 해줄 것 
		KakaoDTO kakaoInfo = kakaoUserService.getKakaoInfo(request.getParameter("code"), name, birthdate);
		
		//카카오톡에서 인증한 이메일을 가지고 오는 것이지 DB에서 존재하는 email을 가지고 오는 게 아님 
        KakaoUser existingUser = kakaoUserRepository.findByEmail(kakaoInfo.getEmail());
        if (existingUser != null) {
            session.setAttribute("InUser", existingUser);
            return "redirect:/main";
        }
			
		
		model.addAttribute("kakaoInfo", kakaoInfo);
		return "register";
	}
	
	// 프론트엔드에서 가지고 오는 회원가입에 대한 결과를 전달해주는 PostMapping
	@PostMapping("/register")
	public ResponseEntity<MsgEntity> registerUser(@RequestParam String email, 
			@RequestParam String nickname, @RequestParam String name, @RequestParam String birthdate) {
		
		KakaoDTO kakaoDTO = KakaoDTO.builder()
									.email(email)
									.nickname(nickname)
									.name(name)
									.birthdate(birthdate)
									.build();
		KakaoUser registerdUser = kakaoUserService.registerUser(kakaoDTO);
		
		return ResponseEntity.ok()
				.body(new MsgEntity("Success", registerdUser));
	}
}
