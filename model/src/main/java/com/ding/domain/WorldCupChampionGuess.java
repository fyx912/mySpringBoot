package com.ding.domain;


import lombok.Data;

import java.util.Date;

/**
 * 世界杯冠军猜想
 * @author yaqiangding
 * @version 1.0
 * @create 2018-5-14
 */
@Data
public class WorldCupChampionGuess {
    private Integer id;
    private Integer wId;//编号
    private String ballTeam;//球队
    private Integer status;//状态:0开售,1停售
    private Double bonus;//奖金
    private Double probability;//概率需要追加%
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
}
