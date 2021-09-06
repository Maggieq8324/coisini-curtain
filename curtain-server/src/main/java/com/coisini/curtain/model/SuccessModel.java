package com.coisini.curtain.model;

import com.coisini.curtain.util.HttpRequestProxy;
import lombok.*;

/**
 * @Description
 * @author coisini
 * @date
 * @Version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessModel {

    private Integer code = 0;
    private String message = "ok";
    private String request = HttpRequestProxy.getRequestUrl();

}
