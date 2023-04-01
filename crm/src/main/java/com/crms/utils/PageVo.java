package com.crms.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo implements Serializable {

    private static final long serialVersionUID = -6384482662377700536L;

    /**
     * 当前页数
     */
    private int page;
    /**
     * 每页显示的条数
     */
    private int limit;
    /**
     * 查询关键字
     */
    private String key;
}
