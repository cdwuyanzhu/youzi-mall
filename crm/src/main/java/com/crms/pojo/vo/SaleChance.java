package com.crms.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author bruce
 * @since 2022-12-28
 */
@ApiModel(value="SaleChance对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleChance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "机会来源")
    private String chanceSource;

    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "客户id")
    private Integer customerId;

    @ApiModelProperty(value = "成功几率")
    private Integer successProbability;

    @ApiModelProperty(value = "概要")
    private String outline;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "手机号")
    private String linkTel;

    @ApiModelProperty(value = "详情")
    private String details;

    @ApiModelProperty(value = "创建人")
    private String createMan;

    @ApiModelProperty(value = "分配人")
    private String assignMan;

    @ApiModelProperty(value = "分配时间")
    private String assignTime;

    @ApiModelProperty(value = "分配状态")
    private Integer state;

    @ApiModelProperty(value = "开发状态")
    private String devResult;

    @ApiModelProperty(value = "有效状态")
    private Integer isValid;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "更新时间")
    private String updateDate;

    @ApiModelProperty(value = "0表示删除1表示未删除")
    @TableLogic
    private Integer isdel;
}
