package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.vo.DeletedVo;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.common.enumeration.CategoryRootOrNotEnum;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.evt.CategoryEvt;
import com.coisini.curtain.model.Category;
import com.coisini.curtain.service.CategoryService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.PageResponseVo;
import com.coisini.curtain.vo.UpdatedVo;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/category")
@Validated
@PermissionModule("分类")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    @PermissionMeta(value = "创建分类")
    @GroupRequired
    public CreatedVo create(@Validated @RequestBody CategoryEvt evt) {
        Category category = new Category();
        BeanUtils.copyProperties(evt, category);
        categoryService.save(category);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新分类")
    public UpdatedVo update(
            @RequestBody @Validated CategoryEvt evt,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        categoryService.updateCategory(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除分类")
    @GroupRequired
    public DeletedVo delete(
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        categoryService.deleteCategory(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public Category get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVo<Category> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @Min(value = 0) @Max(value = 1) Integer root
    ) {
        IPage<Category> paging = categoryService.getCategoriesByPage(count, page, root);
        return PageUtil.build(paging);
    }

    @GetMapping("/sub-page")
    @LoginRequired
    public PageResponseVo<Category> subPage(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @RequestParam(name = "id") @Positive(message = "{id}") Integer id
    ) {
        IPage<Category> paging = categoryService.getSubCategoriesByPage(count, page, id);
        return PageUtil.build(paging);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<Category> getList() {
        val notRoot = CategoryRootOrNotEnum.NOT_ROOT;
        return this.categoryService.lambdaQuery().eq(Category::getIsRoot, notRoot).list();
    }

}
