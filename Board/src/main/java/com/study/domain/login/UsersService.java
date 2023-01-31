package com.study.domain.login;

import java.util.HashMap;
import java.util.Map;

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

    // ChartController에서 사용 유저 정보
    public UserEntity getUserA(HashMap<String, Object> map) throws Exception{

        System.out.println(map);
        UserEntity result = usersMapper.getUserA(map);

        return result;

    }

    // CustomUserDetailService 에서 처리
    public int insert(UserEntity customUser) throws Exception{

        return usersMapper.insert(customUser);

    }


    /*  시큐리티 사용안하고 그냥 /loginCheck 사용할 때 주석제거
    public int insert(UsersDto usersDto) throws Exception{

        return usersMapper.insert(usersDto);

    }
    */


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
