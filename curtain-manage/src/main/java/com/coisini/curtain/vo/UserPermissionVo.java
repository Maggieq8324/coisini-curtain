package com.coisini.curtain.vo;

import com.coisini.curtain.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

/**
 * 用户 + 权限 view object
 *
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Data
public class UserPermissionVo {

    private Long id;

    private String nickname;

    private String avatar;

    private Boolean admin;

    private String email;

    private List<Map<String, List<Map<String, String>>>> permissions;

    public UserPermissionVo() {
    }

    public UserPermissionVo(User user, List<Map<String, List<Map<String, String>>>> permissions) {
        BeanUtils.copyProperties(user, this);
        this.permissions = permissions;
    }

    public UserPermissionVo(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
