package springChap3googleAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springChap3googleAPI.model.MemberGoogle;



public interface MemberGoogleRepository extends JpaRepository<MemberGoogle, Long>{
	//추가적으로 필요한 메서드 작성 
	MemberGoogle findByUsername(String username);

}
