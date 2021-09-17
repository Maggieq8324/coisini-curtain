package com.coisini.curtain.bo;

import cn.hutool.core.bean.BeanUtil;
import com.coisini.curtain.model.Permission;
import com.coisini.curtain.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPermissionBo {
    private Integer id;

    private String name;

    private String info;

    private List<Permission> permissions;

    public GroupPermissionBo(Group group, List<Permission> permissions) {
        BeanUtil.copyProperties(group, this);
        this.permissions = permissions;
    }
}
