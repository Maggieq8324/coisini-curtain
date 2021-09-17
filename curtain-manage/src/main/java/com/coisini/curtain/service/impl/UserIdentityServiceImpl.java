package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.common.constant.IdentityConstant;
import io.github.talelin.core.util.EncryptUtil;
import com.coisini.curtain.mapper.UserIdentityMapper;
import com.coisini.curtain.model.UserIdentity;
import com.coisini.curtain.service.UserIdentityService;
import org.springframework.stereotype.Service;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Service
public class UserIdentityServiceImpl extends ServiceImpl<UserIdentityMapper, UserIdentity> implements UserIdentityService {


    @Override
    public UserIdentity createIdentity(Integer userId, String identityType, String identifier, String credential) {
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(userId);
        userIdentity.setIdentityType(identityType);
        userIdentity.setIdentifier(identifier);
        userIdentity.setCredential(credential);
        return this.createIdentity(userIdentity);
    }

    @Override
    public UserIdentity createIdentity(UserIdentity userIdentity) {
        this.baseMapper.insert(userIdentity);
        return userIdentity;
    }

    @Override
    public UserIdentity createUsernamePasswordIdentity(Integer userId, String identifier, String credential) {
        // 密码加密
        credential = EncryptUtil.encrypt(credential);
        return this.createIdentity(userId, IdentityConstant.USERNAME_PASSWORD_IDENTITY, identifier, credential);
    }

    @Override
    public boolean verifyUsernamePassword(Integer userId, String username, String password) {
        QueryWrapper<UserIdentity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentity::getUserId, userId)
                .eq(UserIdentity::getIdentityType, IdentityConstant.USERNAME_PASSWORD_IDENTITY)
                .eq(UserIdentity::getIdentifier, username);
        UserIdentity userIdentity = this.baseMapper.selectOne(wrapper);
        return EncryptUtil.verify(userIdentity.getCredential(), password);
    }

    @Override
    public boolean changePassword(Integer userId, String password) {
        String encrypted = EncryptUtil.encrypt(password);
        UserIdentity userIdentity = UserIdentity.builder().credential(encrypted).build();
        QueryWrapper<UserIdentity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentity::getUserId, userId);
        return this.baseMapper.update(userIdentity, wrapper) > 0;
    }

    @Override
    public boolean changeUsername(Integer userId, String username) {
        UserIdentity userIdentity = UserIdentity.builder().identifier(username).build();
        QueryWrapper<UserIdentity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentity::getUserId, userId);
        return this.baseMapper.update(userIdentity, wrapper) > 0;
    }

    @Override
    public boolean changeUsernamePassword(Long userId, String username, String password) {
        UserIdentity userIdentity = UserIdentity.builder().identifier(username).credential(password).build();
        QueryWrapper<UserIdentity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentity::getUserId, userId);
        return this.baseMapper.update(userIdentity, wrapper) > 0;
    }
}
