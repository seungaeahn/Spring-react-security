package com.kh.springchap4.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.springchap4.mapper.MembersMapper;

import lombok.RequiredArgsConstructor;

/*

데이터베이스나 외부에서 로그인하여 인증을 하기 위해서는 인증 처리를 해야함

UserDetailsService : 사용자 정보를 인증

*/
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{
	private final MembersMapper memberMapper;
	
	// 유저에 대한 정보를 로그인 할 때 userDetails를 사용해서 로그인할 수 있는 유저가 있는지 확인할 것
	// 사용자명을 기준으로 사용자 정보를 가져오게 할 것
	
	// UserDetails = 스프링 시큐리티가 사용자의 인증과 권한 부여를 처리하는데 필요한 정보를 제공
	// 인터페이스로 다양한 종류의 메서드가 있음
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<UserSercurity> _siteUser = memberMapper.findByusername(username);
		if(_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		UserSercurity user = _siteUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		// 만약에 admin user로 로그인이 된다면 로그인 분류를 role에 따라 추가로 작성
		if(UserRole.ADMIN.equals(user.getIsRole())) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new User	(user.getUsername(), user.getPassword(), authorities);
	}
}