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
 * @since 2022-12-24
 */
@ApiModel(value="Customer对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "客户所属地区")
    private String area;

    @ApiModelProperty(value = "客户经理")
    private String cusManager;

    @ApiModelProperty(value = "客户级别")
    private String level;

    @ApiModelProperty(value = "满意度")
    private String satisfaction;

    @ApiModelProperty(value = "信用度")
    private String creditLine;

    @ApiModelProperty(value = "客户地址")
    private String address;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "网址")
    private String webSite;

    @ApiModelProperty(value = "注册资金")
    private String regCaptial;

    @ApiModelProperty(value = "开户银行")
    private String depBank;

    @ApiModelProperty(value = "开户账号")
    private String depAccount;

    @ApiModelProperty(value = "年营业额")
    private String yearlyTurnover;

    @ApiModelProperty(value = "流失状态")
    private Integer state;

    @ApiModelProperty(value = "有效状态")
    private Integer isValid;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "更新时间")
    private String updateDate;

    @ApiModelProperty(value = "0表示没删1表示已删除")
    @TableLogic
    private Integer isdel;
    @TableField(exist = false)
    private String  province;
    @TableField(exist = false)
    private String  city;
    @TableField(exist = false)
    private String district;

}
