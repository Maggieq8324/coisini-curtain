package com.coisini.curtain.controller.v1;

import com.coisini.curtain.dto.SpecValueDTO;
import com.coisini.curtain.model.SpecValueDO;
import com.coisini.curtain.service.SpecValueService;
import com.coisini.curtain.vo.CreatedVO;
import com.coisini.curtain.vo.DeletedVO;
import com.coisini.curtain.vo.UpdatedVO;
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
    public CreatedVO create(@Validated @RequestBody SpecValueDTO dto) {
        specValueService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新规格值")
    @GroupRequired
    public UpdatedVO update(
            @Validated @RequestBody SpecValueDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除规格值")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public SpecValueDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        SpecValueDO specValue = specValueService.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        return specValue;
    }


    @GetMapping("/by/spec-key/{id}")
    public List<SpecValueDO> getBySpecKeyId(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return this.specValueService.lambdaQuery().eq(SpecValueDO::getSpecId, id).list();
    }

}
