package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.dto.ActivityDTO;
import com.coisini.curtain.service.ActivityService;
import com.coisini.curtain.vo.CreatedVO;
import com.coisini.curtain.vo.DeletedVO;
import com.coisini.curtain.vo.PageResponseVO;
import com.coisini.curtain.vo.UpdatedVO;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.ActivityDO;
import com.coisini.curtain.model.ActivityDetailDO;
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
    public CreatedVO create(@RequestBody @Validated ActivityDTO dto) {
        activityService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新活动")
    @GroupRequired
    public UpdatedVO update(
            @RequestBody @Validated ActivityDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        activityService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除活动")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        ActivityDO activity = activityService.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        activityService.getBaseMapper().deleteById(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ActivityDetailDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        ActivityDetailDO activityDetail = activityService.getDetailById(id);
        return activityDetail;
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<ActivityDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<ActivityDO> pager = new Page<>(page, count);
        IPage<ActivityDO> paging = activityService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

}
