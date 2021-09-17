package com.coisini.curtain.controller.v1;

import com.coisini.curtain.vo.DeletedVo;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.evt.GridCategoryEvt;
import com.coisini.curtain.model.GridCategory;
import com.coisini.curtain.service.GridCategoryService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.UpdatedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/grid-category")
@Validated
@PermissionModule("六宫格")
public class GridCategoryController {

    @Autowired
    private GridCategoryService gridCategoryService;

    @PostMapping("")
    @PermissionMeta(value = "创建六宫格")
    @GroupRequired
    public CreatedVo create(@Validated @RequestBody GridCategoryEvt evt) {
        gridCategoryService.createGridCategory(evt);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新六宫格")
    @GroupRequired
    public UpdatedVo update(
            @Validated @RequestBody GridCategoryEvt evt,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        gridCategoryService.updateGridCategory(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除六宫格")
    @GroupRequired
    public DeletedVo delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        gridCategoryService.deleteGridCategory(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public GridCategory get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        GridCategory gridCategory = gridCategoryService.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        return gridCategory;
    }

    @GetMapping("/list")
    @LoginRequired
    public List<GridCategory> getList() {
        return gridCategoryService.list();
    }
}
