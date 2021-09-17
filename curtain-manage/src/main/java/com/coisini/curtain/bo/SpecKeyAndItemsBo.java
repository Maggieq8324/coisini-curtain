package com.coisini.curtain.bo;

import com.coisini.curtain.model.SpecKey;
import com.coisini.curtain.model.SpecValue;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author TaleLin
 */
@Data
public class SpecKeyAndItemsBo {

    private Integer id;

    private String name;

    private String unit;

    private Integer standard;

    private String description;

    private List<SpecValue> items;

    public SpecKeyAndItemsBo(SpecKey specKey, List<SpecValue> items) {
        BeanUtils.copyProperties(specKey, this);
        this.setItems(items);
    }

}
