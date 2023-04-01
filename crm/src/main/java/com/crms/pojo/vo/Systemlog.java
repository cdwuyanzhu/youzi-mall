package com.crms.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
 * @since 2023-01-08
 */
@ApiModel(value="Systemlog对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Systemlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "0  普通日志  1异常日志")
    private Integer logtype;

    @ApiModelProperty(value = "当前账号")
    private String nowuser;

    @ApiModelProperty(value = "日志时间")
    private String createtime;

    @ApiModelProperty(value = "日志的方法")
    private String methodName;

    @ApiModelProperty(value = "日志描述信息")
    private String logDescription;

    @ApiModelProperty(value = "方法参数信息")
    private String methodParms;

    @ApiModelProperty(value = "方法返回值类型")
    private String methodType;

    @ApiModelProperty(value = "方法返回值信息")
    private String methodReturn;

    @ApiModelProperty(value = "异常信息")
    private String exMessage;


}
