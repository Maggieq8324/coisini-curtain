package com.coisini.curtain.controller.v1;

import com.coisini.curtain.evt.SpecValueEvt;
import com.coisini.curtain.model.SpecValue;
import com.coisini.curtain.service.SpecValueService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/spec-value")
@Validated
@PermissionModule("规格值")
public class SpecValueController {

    @Autowired
    private SpecValueService specValueService;

    @PostMapping("")
    @PermissionMeta("创建规格值")
    @GroupRequired
    public CreatedVo create(@Validated @RequestBody SpecValueEvt evt) {
        specValueService.create(evt);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新规格值")
    @GroupRequired
    public UpdatedVo update(
            @Validated @RequestBody SpecValueEvt evt,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.update(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除规格值")
    @GroupRequired
    public DeletedVo delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.delete(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public SpecValue get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        SpecValue specValue = specValueService.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        return specValue;
    }


    @GetMapping("/by/spec-key/{id}")
    public List<SpecValue> getBySpecKeyId(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return this.specValueService.lambdaQuery().eq(SpecValue::getSpecId, id).list();
    }

}
