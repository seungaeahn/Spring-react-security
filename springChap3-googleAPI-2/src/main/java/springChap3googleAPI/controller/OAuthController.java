package springChap3googleAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import springChap3googleAPI.model.UserGoogle;
import springChap3googleAPI.service.UserService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins="http://localhost:3000")
public class OAuthController {

	 @Autowired
	 private UserService userService;
	 @GetMapping("/loginSuccess")
	 public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser) {
	     String email = oauthUser.getAttribute("email");
	     UserGoogle user = userService.findByUsername(email);
	     System.out.println("OAuth2User: " + oauthUser);
	     System.out.println("이메일 속성: " + email);
	     if (user == null) {
	         user = new UserGoogle();
	         user.setUsername(email);
	         user.setEmail(email);
	         userService.saveUser(user);

//	         model.addAttribute("newUser", true);
	     }

	   
	     return ResponseEntity.ok("loginSuccess");
	 }
	     
     @GetMapping("/logout")
     public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null) {
             new SecurityContextLogoutHandler().logout(request, response, auth);
         }
         return ResponseEntity.ok("로그아웃 됐습니다.");
     }
 }
   
   