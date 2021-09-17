package com.coisini.curtain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.coisini.curtain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息 view object
 *
 * @author pedro@TaleLin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {

    private Integer id;

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 分组
     */
    private List groups;

    public UserInfoVo(User user, List groups) {
        BeanUtil.copyProperties(user, this);
        this.groups = groups;
    }
}
