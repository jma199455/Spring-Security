package com.study.domain.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    
    @Autowired
    UsersMapper usersMapper;

    public UsersDto getUser(UsersDto usersDto) throws Exception{

        return usersMapper.usersLogin(usersDto);

    }

    public int insert(UsersDto usersDto) throws Exception{

        return usersMapper.insert(usersDto);

    }

    
}
