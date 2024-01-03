package springChap3googleAPI.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springChap3googleAPI.model.MemberGoogle;
import springChap3googleAPI.service.MemberGoogleService;

@Controller
@RequestMapping("/oauth")
public class OAuthController {
    @Autowired
    private MemberGoogleService memberGoogleService;
    
    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
        String email = oauthUser.getAttribute("email");

        Optional<MemberGoogle> optionalUser = memberGoogleService.findByUsername(email);

        if (optionalUser.isPresent()) {
            // 사용자 정보가 존재하는 경우의 처리
        } else {
            MemberGoogle user = new MemberGoogle();
            user.setUsername(email);
            user.setEmail(email);
            memberGoogleService.saveMember(user);

            model.addAttribute("newUser", true);
        }

        return "loginSuccess";
    }
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }
//    
//    
    
}
   
   