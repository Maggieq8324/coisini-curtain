package com.coisini.curtain.controller.cms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.bo.GroupPermissionBO;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.model.PermissionDO;
import com.coisini.curtain.model.UserDO;
import com.coisini.curtain.service.AdminService;
import io.github.talelin.core.annotation.AdminRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.dto.admin.DispatchPermissionDTO;
import com.coisini.curtain.dto.admin.DispatchPermissionsDTO;
import com.coisini.curtain.dto.admin.NewGroupDTO;
import com.coisini.curtain.dto.admin.RemovePermissionsDTO;
import com.coisini.curtain.dto.admin.ResetPasswordDTO;
import com.coisini.curtain.dto.admin.UpdateGroupDTO;
import com.coisini.curtain.dto.admin.UpdateUserInfoDTO;
import com.coisini.curtain.model.GroupDO;
import com.coisini.curtain.service.GroupService;
import com.coisini.curtain.vo.CreatedVO;
import com.coisini.curtain.vo.DeletedVO;
import com.coisini.curtain.vo.PageResponseVO;
import com.coisini.curtain.vo.UpdatedVO;
import com.coisini.curtain.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@RestController
@RequestMapping("/cms/admin")
@PermissionModule(value = "管理员")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/permission")
    @AdminRequired
    @PermissionMeta(value = "查询所有可分配的权限", mount = false)
    public Map<String, List<PermissionDO>> getAllPermissions() {
        return adminService.getAllStructualPermissions();
    }


    @GetMapping("/users")
    @AdminRequired
    @PermissionMeta(value = "查询所有用户", mount = false)
    public PageResponseVO getUsers(
            @RequestParam(name = "group_id", required = false)
            @Min(value = 1, message = "{group.id.positive}") Integer groupId,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page) {
        IPage<UserDO> iPage = adminService.getUserPageByGroupId(groupId, count, page);
        List<UserInfoVO> userInfos = iPage.getRecords().stream().map(user -> {
            List<GroupDO> groups = groupService.getUserGroupsByUserId(user.getId());
            return new UserInfoVO(user, groups);
        }).collect(Collectors.toList());
        return PageUtil.build(iPage, userInfos);
    }

    @PutMapping("/user/{id}/password")
    @AdminRequired
    @PermissionMeta(value = "修改用户密码", mount = false)
    public UpdatedVO changeUserPassword(@PathVariable @Positive(message = "{id.positive}") Integer id, @RequestBody @Validated ResetPasswordDTO validator) {
        adminService.changeUserPassword(id, validator);
        return new UpdatedVO(4);
    }

    @DeleteMapping("/user/{id}")
    @AdminRequired
    @PermissionMeta(value = "删除用户", mount = false)
    public DeletedVO deleteUser(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        adminService.deleteUser(id);
        return new DeletedVO(5);
    }

    @PutMapping("/user/{id}")
    @AdminRequired
    @PermissionMeta(value = "管理员更新用户信息", mount = false)
    public UpdatedVO updateUser(@PathVariable @Positive(message = "{id.positive}") Integer id, @RequestBody @Validated UpdateUserInfoDTO validator) {
        adminService.updateUserInfo(id, validator);
        return new UpdatedVO(6);
    }

    @GetMapping("/group")
    @AdminRequired
    @PermissionMeta(value = "查询所有权限组及其权限", mount = false)
    public PageResponseVO getGroups(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page) {
        IPage<GroupDO> iPage = adminService.getGroupPage(page, count);
        return PageUtil.build(iPage);
    }

    @GetMapping("/group/all")
    @AdminRequired
    @PermissionMeta(value = "查询所有权限组", mount = false)
    public List<GroupDO> getAllGroup() {
        List<GroupDO> groups = adminService.getAllGroups();
        return groups;
    }

    @GetMapping("/group/{id}")
    @AdminRequired
    @PermissionMeta(value = "查询一个权限组及其权限", mount = false)
    public GroupPermissionBO getGroup(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        GroupPermissionBO groupPermissions = adminService.getGroup(id);
        return groupPermissions;
    }

    @PostMapping("/group")
    @AdminRequired
    @PermissionMeta(value = "新建权限组", mount = false)
    public CreatedVO createGroup(@RequestBody @Validated NewGroupDTO validator) {
        adminService.createGroup(validator);
        return new CreatedVO(15);
    }

    @PutMapping("/group/{id}")
    @AdminRequired
    @PermissionMeta(value = "更新一个权限组", mount = false)
    public UpdatedVO updateGroup(@PathVariable @Positive(message = "{id.positive}") Integer id,
                                       @RequestBody @Validated UpdateGroupDTO validator) {
        adminService.updateGroup(id, validator);
        return new UpdatedVO(7);
    }

    @DeleteMapping("/group/{id}")
    @AdminRequired
    @PermissionMeta(value = "删除一个权限组", mount = false)
    public DeletedVO deleteGroup(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        adminService.deleteGroup(id);
        return new DeletedVO(8);
    }

    @PostMapping("/permission/dispatch")
    @AdminRequired
    @PermissionMeta(value = "分配单个权限", mount = false)
    public CreatedVO dispatchPermission(@RequestBody @Validated DispatchPermissionDTO validator) {
        adminService.dispatchPermission(validator);
        return new CreatedVO(9);
    }

    @PostMapping("/permission/dispatch/batch")
    @AdminRequired
    @PermissionMeta(value = "分配多个权限", mount = false)
    public CreatedVO dispatchPermissions(@RequestBody @Validated DispatchPermissionsDTO validator) {
        adminService.dispatchPermissions(validator);
        return new CreatedVO(9);
    }

    @PostMapping("/permission/remove")
    @AdminRequired
    @PermissionMeta(value = "删除多个权限", mount = false)
    public DeletedVO removePermissions(@RequestBody @Validated RemovePermissionsDTO validator) {
        adminService.removePermissions(validator);
        return new DeletedVO(10);
    }

}
