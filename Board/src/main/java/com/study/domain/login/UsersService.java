package com.study.domain.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersDto getUser(UsersDto usersDto) throws Exception{

        return usersMapper.usersLogin(usersDto);

    }

    public int insert(UsersDto usersDto) throws Exception{

        return usersMapper.insert(usersDto);

    }

    public int save(UsersDto usersDto) throws Exception {

        System.out.println(usersDto.getPw());
        if (usersDto.getPw() != null) {
            usersDto.setPw(bCryptPasswordEncoder.encode(usersDto.getPw())); // DB 암호화 넣기
        }
        int result = usersMapper.save(usersDto);
        return result;
    }

    // 아이디 중복 체크
    public int idCheck(UsersDto params) {
        int result = usersMapper.idCheck(params);
        return result;
    }

}
