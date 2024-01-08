package com.kh.springchap4.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kh.springchap4.model.Member;

@Mapper
public interface MembersMapper {
	void insertMember(Member member);

	Optional<Member> findMemberBtId(Long memberId);
}
