package com.coisini.curtain.vo;

import com.coisini.curtain.entity.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @Description Activity Pure Vo
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ActivityPureVo {
    private Long id;
    private String title;
    private String entranceImg;
    private Boolean online;
    private String remark;
    private String startTime;
    private String endTime;

    public ActivityPureVo(Activity activity) {
        BeanUtils.copyProperties(activity,this);
    }

    public ActivityPureVo(Object object){
        BeanUtils.copyProperties(object, this);
    }

}
