package com.kh.springchap4.service;

import java.util.List;
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
	
	//전체회원가져오기
	public List<Member> getAllMembers(){
		return membersMapper.getAllMembers();
	}
	
	public void updateMember(Member member) {
		membersMapper.updateMember(member);
	}

	public Optional<Member> getMemberById(Long memberId) {
		return membersMapper.findMemberBtId(memberId);
	}
	
	//하나의 회원 정보 가져오기
		public Member getUserById(Long memberId) {
			return membersMapper.getMemberById(memberId);
		}
		
	    public void saveMember(Member member) {
	    	membersMapper.saveMember(member);
	    }

	public void deleteMemberById(Long memberId) {
		membersMapper.deleteById(memberId);
	}
	
//	public Member login(String username, String password) {
//		return membersMapper.loginByNameAndPassword(username, password);
//	}
}
