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
 * @since 2022-12-27
 */
@ApiModel(value="CustomerLoss对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerLoss implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户编号")
    private Integer cusId;

    @ApiModelProperty(value = "客户姓名")
    private String cusName;

    @ApiModelProperty(value = "客户经理")
    private String cusManger;

    @ApiModelProperty(value = "最后下单时间")
    private String lastOrderTime;

    @ApiModelProperty(value = "确认流失时间")
    private String confirmLossTime;

    @ApiModelProperty(value = "流失状态")
    private Integer state;

    @ApiModelProperty(value = "流失原因")
    private String lossReason;

    @ApiModelProperty(value = "有效状态")
    private Integer isValid;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "逻辑删除状态")
    @TableLogic
    private Integer isdel;

}
