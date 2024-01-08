package com.kh.springchap4.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.springchap4.mapper.MembersMapper;
import com.kh.springchap4.model.Member;

@Service
public class MemberService {
	@Autowired
	private MembersMapper membersMapper;
	
	//회원정보 가지고 오기
	public void signUpMember(Member member) {
		membersMapper.insertMember(member);
	}

	public Optional<Member> getMemberById(Long memberId) {
		return membersMapper.findMemberBtId(memberId);
	}
}
