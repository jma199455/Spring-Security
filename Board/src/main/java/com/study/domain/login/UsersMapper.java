package com.study.domain.login;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    
    public UsersDto usersLogin(UsersDto dto) throws Exception;
    public int insert(UsersDto dto) throws Exception;

    // 시큐리티 로그인 정보 가져오기
    public UserEntity getUser(String loginUserId);  
	public List<UserRoleEntity> getUserRoles(int userSeq);


}
