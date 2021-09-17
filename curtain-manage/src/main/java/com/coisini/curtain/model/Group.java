package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;

/**
 * @Description Group
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lin_group")
@EqualsAndHashCode(callSuper = true)
public class Group extends BaseModel implements Serializable {

    private static final long serialVersionUID = -8994898895671436007L;

    /**
     * 分组名称，例如：搬砖者
     */
    private String name;

    /**
     * 分组信息：例如：搬砖的人
     */
    private String info;

    /**
     * 分组级别（root、guest、user，其中 root、guest 分组只能存在一个）
     */
    @TableField(value = "`level`")
    private String level;

}
