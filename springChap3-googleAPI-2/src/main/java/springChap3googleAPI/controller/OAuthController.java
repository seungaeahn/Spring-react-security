package springChap3googleAPI.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import springChap3googleAPI.model.UserGoogle;
import springChap3googleAPI.service.UserService;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

	 @Autowired
	 private UserService userService;
	 
	 @GetMapping("/loginSuccess")
	    public String loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
	        String email = oauthUser.getAttribute("email");

	        Optional<UserGoogle> optionalUser = userService.findByUsername(email);

	        if (optionalUser.isPresent()) {
	            // 사용자 정보가 존재하는 경우의 처리
	        	
	        } else {
	        	UserGoogle user = new UserGoogle();
	            user.setUsername(email);
	            user.setEmail(email);
	            userService.saveUser(user);

	            model.addAttribute("newUser", true);
	        }

	        return "loginSuccess";
	    }
	     
     @GetMapping("/logout")
     public String logout(HttpServletRequest request, HttpServletResponse response) {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null) {
             new SecurityContextLogoutHandler().logout(request, response, auth);
         }
         return "redirect:/";
     }
 }
   
   