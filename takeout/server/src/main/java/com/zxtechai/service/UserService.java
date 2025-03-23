package com.zxtechai.service;

import com.zxtechai.dto.UserLoginDTO;
import com.zxtechai.entity.User;

public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
