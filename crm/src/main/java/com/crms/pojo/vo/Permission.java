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
 * @since 2022-12-30
 */
@ApiModel(value="Permission对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "权限等级")
    private Integer permissionLevel;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "权限备注")
    private String permissionRemarker;

    @ApiModelProperty(value = "")
    @TableField(exist = false)
    private String roleName;

    @ApiModelProperty(value = "有效状态")
    private Integer isValid;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "更新时间")
    private String updateDate;

    @ApiModelProperty(value = "上级菜单")
    private Integer parentId;

    @ApiModelProperty(value = "")
    private String icon;

    @ApiModelProperty(value = "")
    private String url;


    @ApiModelProperty(value = "0表示删除1表示未删除")
    @TableLogic
    private Integer isdel;

}
