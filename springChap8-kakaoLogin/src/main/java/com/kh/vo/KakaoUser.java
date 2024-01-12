package com.kh.vo;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class KakaoUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="kakaoUser_seq")
	private Long id;
	private String email;
	private String nickname;
	private String name;
	private String birthdate;

}
