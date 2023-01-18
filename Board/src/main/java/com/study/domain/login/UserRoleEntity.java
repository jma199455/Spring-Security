package com.study.domain.login;

import lombok.Data;

@Data
public class UserRoleEntity {
   
    // private String userLoginId; // 제거..?

    private int userId;
    private String roleName;
    
}
