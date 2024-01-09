package com.kh.springchap4.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kh.springchap4.model.Member;

@Mapper
public interface MembersMapper {
	void insertMember(Member member);

	Optional<Member> findMemberBtId(Long memberId);

	List<Member> getAllMembers();

	void deleteById(Long memberId);
	
	void updateMember(Member member);
	
	void saveMember(Member member);

	Member getMemberById(Long memberId);
	 
	
//	Member loginByNameAndPassword(String username,String password);
}
