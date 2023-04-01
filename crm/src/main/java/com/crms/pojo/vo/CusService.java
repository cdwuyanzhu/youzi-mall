package com.crms.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-12-29
 */
@ApiModel(value="CusService对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CusService implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "服务类型")
    private String serviceType;

    @ApiModelProperty(value = "概要")
    private String outline;

    @ApiModelProperty(value = "客户id")
    private Integer cusId;
    @ApiModelProperty(value = "客户Name")
    @TableField(exist = false)
    private String cusName;
    @ApiModelProperty(value = "服务状态")
    private String state;

    @ApiModelProperty(value = "服务请求")
    private String request;

    @ApiModelProperty(value = "服务创建人")
    private String createPeople;

    @ApiModelProperty(value = "服务分配人")
    private String assigner;

    @ApiModelProperty(value = "分配时间")
    private String assignTime;

    @ApiModelProperty(value = "服务处理")
    private String serviceProce;

    @ApiModelProperty(value = "服务处理人")
    private String serviceProcePeople;

    @ApiModelProperty(value = "服务处理时间")
    private String serviceProceTime;

    @ApiModelProperty(value = "服务处理结果")
    private String serviceProceResult;

    @ApiModelProperty(value = "满意度")
    private String satisfaction;

    @ApiModelProperty(value = "是否有效")
    private Integer isValid;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "修改时间")
    private String updateDate;

    @ApiModelProperty(value = "0表示没删1表示已删除")
    @TableLogic
    private Integer isdel;
}
