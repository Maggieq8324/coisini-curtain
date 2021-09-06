package com.coisini.curtain.entity;

import com.coisini.curtain.util.GenericAndJson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description Sku 模型
 * @author coisini
 * @date Aug 15, 2021
 * @Version 1.0
 */
@Entity
@Getter
@Setter
@Where(clause = "delete_time is null and online = 1")
public class Sku extends BaseEntity{

    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private String code;
    private String specs;
    private Long stock;
    private Long categoryId;
    private Long rootCategoryId;

    /**
     * 获取真实的价格
     * @return
     */
    public BigDecimal getActualPrice() {
        return discountPrice == null ? this.price : this.discountPrice;
    }

    public List<Spec> getSpecs() {
        if (StringUtils.isEmpty(this.specs)) {
            return Collections.emptyList();
        }
        return GenericAndJson.jsonToList(this.specs, new TypeReference<List<Spec>>() {
        });
    }

    public void setSpecs(List<Spec> specs) {
        if (specs.isEmpty()) {
            return;
        }

        this.specs = GenericAndJson.objectToJson(specs);
    }

    @JsonIgnore
    public List<String> getSpecValueList() {
        return getSpecs().stream()
                         .map(Spec::getValue)
                         .collect(Collectors.toList());
    }

}
