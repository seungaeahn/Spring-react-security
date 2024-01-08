package com.kh.springchap4.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	private Long memberId;
	private String username;
	private String password;
	private String fullName;
	private String email;
	private Date registrationDate;
}
