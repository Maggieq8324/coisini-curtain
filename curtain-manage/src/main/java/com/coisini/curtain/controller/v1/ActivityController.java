package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.evt.ActivityEvt;
import com.coisini.curtain.service.ActivityService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.PageResponseVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.Activity;
import com.coisini.curtain.model.ActivityDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
* @author generator@TaleLin
* @since 2020-05-20
*/
@RestController
@RequestMapping("/v1/activity")
@Validated
@PermissionModule("活动")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("")
    @PermissionMeta("创建活动")
    @GroupRequired
    public CreatedVo create(@RequestBody @Validated ActivityEvt evt) {
        activityService.create(evt);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新活动")
    @GroupRequired
    public UpdatedVo update(
            @RequestBody @Validated ActivityEvt evt,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        activityService.update(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除活动")
    @GroupRequired
    public DeletedVo delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        Activity activity = activityService.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        activityService.getBaseMapper().deleteById(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ActivityDetail get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        ActivityDetail activityDetail = activityService.getDetailById(id);
        return activityDetail;
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVo<Activity> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Activity> pager = new Page<>(page, count);
        IPage<Activity> paging = activityService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

}
