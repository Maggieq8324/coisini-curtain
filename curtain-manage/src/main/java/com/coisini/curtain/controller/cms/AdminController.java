package com.coisini.curtain.controller.cms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.bo.GroupPermissionBo;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.model.Permission;
import com.coisini.curtain.model.User;
import com.coisini.curtain.service.AdminService;
import io.github.talelin.core.annotation.AdminRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.evt.admin.DispatchPermissionEvt;
import com.coisini.curtain.evt.admin.DispatchPermissionsEvt;
import com.coisini.curtain.evt.admin.NewGroupEvt;
import com.coisini.curtain.evt.admin.RemovePermissionsEvt;
import com.coisini.curtain.evt.admin.ResetPasswordEvt;
import com.coisini.curtain.evt.admin.UpdateGroupEvt;
import com.coisini.curtain.evt.admin.UpdateUserInfoEvt;
import com.coisini.curtain.model.Group;
import com.coisini.curtain.service.GroupService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.PageResponseVo;
import com.coisini.curtain.vo.UpdatedVo;
import com.coisini.curtain.vo.UserInfoVo;
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
    public Map<String, List<Permission>> getAllPermissions() {
        return adminService.getAllStructualPermissions();
    }


    @GetMapping("/users")
    @AdminRequired
    @PermissionMeta(value = "查询所有用户", mount = false)
    public PageResponseVo getUsers(
            @RequestParam(name = "group_id", required = false)
            @Min(value = 1, message = "{group.id.positive}") Integer groupId,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page) {
        IPage<User> iPage = adminService.getUserPageByGroupId(groupId, count, page);
        List<UserInfoVo> userInfos = iPage.getRecords().stream().map(user -> {
            List<Group> groups = groupService.getUserGroupsByUserId(user.getId());
            return new UserInfoVo(user, groups);
        }).collect(Collectors.toList());
        return PageUtil.build(iPage, userInfos);
    }

    @PutMapping("/user/{id}/password")
    @AdminRequired
    @PermissionMeta(value = "修改用户密码", mount = false)
    public UpdatedVo changeUserPassword(@PathVariable @Positive(message = "{id.positive}") Integer id, @RequestBody @Validated ResetPasswordEvt validator) {
        adminService.changeUserPassword(id, validator);
        return new UpdatedVo(4);
    }

    @DeleteMapping("/user/{id}")
    @AdminRequired
    @PermissionMeta(value = "删除用户", mount = false)
    public DeletedVo deleteUser(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        adminService.deleteUser(id);
        return new DeletedVo(5);
    }

    @PutMapping("/user/{id}")
    @AdminRequired
    @PermissionMeta(value = "管理员更新用户信息", mount = false)
    public UpdatedVo updateUser(@PathVariable @Positive(message = "{id.positive}") Integer id, @RequestBody @Validated UpdateUserInfoEvt validator) {
        adminService.updateUserInfo(id, validator);
        return new UpdatedVo(6);
    }

    @GetMapping("/group")
    @AdminRequired
    @PermissionMeta(value = "查询所有权限组及其权限", mount = false)
    public PageResponseVo getGroups(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page) {
        IPage<Group> iPage = adminService.getGroupPage(page, count);
        return PageUtil.build(iPage);
    }

    @GetMapping("/group/all")
    @AdminRequired
    @PermissionMeta(value = "查询所有权限组", mount = false)
    public List<Group> getAllGroup() {
        List<Group> groups = adminService.getAllGroups();
        return groups;
    }

    @GetMapping("/group/{id}")
    @AdminRequired
    @PermissionMeta(value = "查询一个权限组及其权限", mount = false)
    public GroupPermissionBo getGroup(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        GroupPermissionBo groupPermissions = adminService.getGroup(id);
        return groupPermissions;
    }

    @PostMapping("/group")
    @AdminRequired
    @PermissionMeta(value = "新建权限组", mount = false)
    public CreatedVo createGroup(@RequestBody @Validated NewGroupEvt validator) {
        adminService.createGroup(validator);
        return new CreatedVo(15);
    }

    @PutMapping("/group/{id}")
    @AdminRequired
    @PermissionMeta(value = "更新一个权限组", mount = false)
    public UpdatedVo updateGroup(@PathVariable @Positive(message = "{id.positive}") Integer id,
                                 @RequestBody @Validated UpdateGroupEvt validator) {
        adminService.updateGroup(id, validator);
        return new UpdatedVo(7);
    }

    @DeleteMapping("/group/{id}")
    @AdminRequired
    @PermissionMeta(value = "删除一个权限组", mount = false)
    public DeletedVo deleteGroup(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        adminService.deleteGroup(id);
        return new DeletedVo(8);
    }

    @PostMapping("/permission/dispatch")
    @AdminRequired
    @PermissionMeta(value = "分配单个权限", mount = false)
    public CreatedVo dispatchPermission(@RequestBody @Validated DispatchPermissionEvt validator) {
        adminService.dispatchPermission(validator);
        return new CreatedVo(9);
    }

    @PostMapping("/permission/dispatch/batch")
    @AdminRequired
    @PermissionMeta(value = "分配多个权限", mount = false)
    public CreatedVo dispatchPermissions(@RequestBody @Validated DispatchPermissionsEvt validator) {
        adminService.dispatchPermissions(validator);
        return new CreatedVo(9);
    }

    @PostMapping("/permission/remove")
    @AdminRequired
    @PermissionMeta(value = "删除多个权限", mount = false)
    public DeletedVo removePermissions(@RequestBody @Validated RemovePermissionsEvt validator) {
        adminService.removePermissions(validator);
        return new DeletedVo(10);
    }

}
