package com.coisini.curtain.evt.admin;

import io.github.talelin.autoconfigure.validator.LongList;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Data
public class DispatchPermissionsEvt {

    @Positive(message = "{group.id.positive}")
    @NotNull(message = "{group.id.not-null}")
    private Integer groupId;

    @LongList(message = "{permission.ids.long-list}")
    private List<Integer> permissionIds;
}
