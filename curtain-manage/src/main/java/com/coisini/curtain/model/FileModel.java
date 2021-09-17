package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * @Description FileModel
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@TableName("sys_file")
@EqualsAndHashCode(callSuper = true)
public class FileModel extends BaseModel implements Serializable {

    private static final long serialVersionUID = -3203293656352763490L;

    private String path;

    /**
     * LOCAL REMOTE
     */
    private String type;

    private String name;

    private String extension;

    private Integer size;

    /**
     * md5值，防止上传重复文件
     */
    private String md5;
}
