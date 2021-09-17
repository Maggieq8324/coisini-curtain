package com.coisini.curtain.bo;

import com.coisini.curtain.model.SpecKeyDO;
import com.coisini.curtain.model.SpecValueDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author TaleLin
 */
@Data
public class SpecKeyAndItemsBO {

    private Integer id;

    private String name;

    private String unit;

    private Integer standard;

    private String description;

    private List<SpecValueDO> items;

    public SpecKeyAndItemsBO(SpecKeyDO specKey, List<SpecValueDO> items) {
        BeanUtils.copyProperties(specKey, this);
        this.setItems(items);
    }

}
