package com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.vo.KakaoUser;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, Long>{
	KakaoUser findByEmail(String email);
}
