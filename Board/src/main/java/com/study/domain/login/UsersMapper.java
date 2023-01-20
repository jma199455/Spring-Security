package com.study.domain.login;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersMapper {
    
    public UsersDto usersLogin(UsersDto dto) throws Exception;

    public int insert(UserEntity customUser) throws Exception;

    //public int insert(UsersDto dto) throws Exception; // /loginCheck 사용할 때



    // 시큐리티 로그인 정보 가져오기
    public UserEntity getUser(String loginUserId);   // 유저 정보
	public List<UserRoleEntity> getUserRoles(int userSeq); // 유저 권한 정보
    public int save(@Param("param")UsersDto usersDto); // 회원가입 
    public int idCheck(@Param("param") UsersDto usersDto);    // 아이디 중복 체크


}
