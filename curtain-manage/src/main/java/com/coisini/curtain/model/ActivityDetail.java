package com.coisini.curtain.model;

import lombok.Data;
import java.util.List;

/**
 * @Description ActivityDetail
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class ActivityDetail extends Activity {

    private List<Integer> couponIds;

}
