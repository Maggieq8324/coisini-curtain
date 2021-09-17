/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-05-23 10:05
 */
package com.coisini.curtain.model;

import lombok.Data;

import java.util.List;

@Data
public class SpuDetailDO extends SpuDO {

    private String categoryName;

    private String sketchSpecKeyName;

    private String defaultSkuTitle;

    private List<String> spuImgList;

    private List<String> spuDetailImgList;
}

