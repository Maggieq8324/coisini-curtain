package com.coisini.curtain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author TaleLin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecKeyValueDO {

    @JsonProperty(value = "key_id")
    private Integer keyId;

    @JsonProperty(value = "value_id")
    private Integer valueId;

    private String value;

    private String key;
}
