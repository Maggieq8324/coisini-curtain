package com.coisini.curtain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description SpecKeyValue
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecKeyValue {

    @JsonProperty(value = "key_id")
    private Integer keyId;

    @JsonProperty(value = "value_id")
    private Integer valueId;

    private String value;

    private String key;
}
