package com.coisini.curtain.controller.cms;

import com.coisini.curtain.common.LocalUser;
import com.coisini.curtain.evt.user.ChangePasswordEvt;
import com.coisini.curtain.evt.user.LoginEvt;
import com.coisini.curtain.service.UserIdentityService;
import com.coisini.curtain.service.UserService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.UpdatedVo;
import com.coisini.curtain.vo.UserInfoVo;
import com.coisini.curtain.vo.UserPermissionVo;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.core.annotation.AdminRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.core.annotation.RefreshRequired;
import io.github.talelin.core.token.DoubleJWT;
import io.github.talelin.core.token.Tokens;
import com.coisini.curtain.evt.user.RegisterEvt;
import com.coisini.curtain.evt.user.UpdateInfoEvt;
import com.coisini.curtain.model.Group;
import com.coisini.curtain.model.User;
import com.coisini.curtain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@RestController
@RequestMapping("/cms/user")
@PermissionModule(value = "用户")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private DoubleJWT jwt;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @AdminRequired
    public CreatedVo<String> register(@RequestBody @Validated RegisterEvt validator) {
        userService.createUser(validator);
        return new CreatedVo(11);
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Tokens login(@RequestBody @Validated LoginEvt validator) {
        User user = userService.getUserByUsername(validator.getUsername());
        if (user == null) {
            throw new NotFoundException("user not found", 10021);
        }
        boolean valid = userIdentityService.verifyUsernamePassword(
                user.getId(),
                user.getUsername(),
                validator.getPassword());
        if (!valid) {
            throw new ParameterException("username or password is fault", 10031);
        }
        return jwt.generateTokens(user.getId());
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    @LoginRequired
    public UpdatedVo update(@RequestBody @Validated UpdateInfoEvt validator) {
        userService.updateUserInfo(validator);
        return new UpdatedVo(6);
    }

    /**
     * 修改密码
     */
    @PutMapping("/change_password")
    @LoginRequired
    public UpdatedVo updatePassword(@RequestBody @Validated ChangePasswordEvt validator) {
        userService.changeUserPassword(validator);
        return new UpdatedVo(4);
    }

    /**
     * 刷新令牌
     */
    @GetMapping("/refresh")
    @RefreshRequired
    public Tokens getRefreshToken() {
        User user = LocalUser.getLocalUser();
        return jwt.generateTokens(user.getId());
    }

    /**
     * 查询拥有权限
     */
    @GetMapping("/permissions")
    @LoginRequired
    public UserPermissionVo getPermissions() {
        User user = LocalUser.getLocalUser();
        boolean admin = groupService.checkIsRootByUserId(user.getId());
        List<Map<String, List<Map<String, String>>>> permissions = userService.getStructualUserPermissions(user.getId());
        UserPermissionVo userPermissions = new UserPermissionVo(user, permissions);
        userPermissions.setAdmin(admin);
        return userPermissions;
    }

    /**
     * 查询自己信息
     */
    @LoginRequired
    @GetMapping("/information")
    public UserInfoVo getInformation() {
        User user = LocalUser.getLocalUser();
        List<Group> groups = groupService.getUserGroupsByUserId(user.getId());
        return new UserInfoVo(user, groups);
    }
}
