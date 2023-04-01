package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.CusService;
import com.crms.pojo.vo.Customer;
import com.crms.pojo.vo.User;
import com.crms.service.ICusServiceService;
import com.crms.service.ICustomerService;
import com.crms.service.IUserService;
import com.crms.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bruce
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/cus-service/")
@Api(tags = "服务API",description = "提供服务相关的Rest API")
@ApiResponses({
        @ApiResponse(code = 200,message = "请求成功"),
        @ApiResponse(code = 500,message = "服务器内部错误"),
        @ApiResponse(code = 404,message = "请求资源找不到"),
        @ApiResponse(code = 405,message = "请求方式不支持"),
        @ApiResponse(code = 403,message = "请求被拒绝"),
        @ApiResponse(code = 401,message = "没有权限访问"),

})
@Slf4j
public class CusServiceController {
    @Autowired
    ICusServiceService cusServiceService;
    @Autowired
    ICustomerService customerService;
    @Autowired
    IUserService userService;
    @GetMapping("page")
    @ApiOperation(value = "服务管理分页接口", notes = "服务管理分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<Customer> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("isdel",0);
        queryWrapper1.eq("is_valid",1);
        queryWrapper1.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "name", pageVo.getKey());
        List<Customer> customerList = customerService.list(queryWrapper1);
        ArrayList<Integer> idlist = new ArrayList<>();
        for (Customer customer:customerList) {
            idlist.add(customer.getId());
        }
        QueryWrapper<CusService> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(idlist.size()>0,"cus_id",idlist);
        //条件查询
        queryWrapper.eq("isdel",0);
        //分页插件
        IPage<CusService> page = cusServiceService.page(new Page<CusService>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        List<CusService> records = page.getRecords();
        for (CusService cusService:records) {
            Customer customer = customerService.getById(cusService.getCusId());
            cusService.setCusName(customer.getName());
            //创建人（数据库存的id）
            String createPeople = cusService.getCreatePeople();
            User user = userService.getById(createPeople);
            cusService.setCreatePeople(user.getUserName());

        }
        return DataResultsPage.success(ResultCode.PAGESUCCESS,records,"0",page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "服务管理添加接口", notes = "服务管理添加接口")
    public DataResults save(@RequestBody CusService cusService, HttpSession session) {
        User user = (User) session.getAttribute("user");
        cusService.setCreatePeople(String.valueOf(user.getId()));
        cusService.setIsdel(0);
        cusService.setCreateDate(DateTimeUtils.getDateTime());
        return DataResults.success(ResultCode.SUCCESS,cusServiceService.save(cusService));
    }
    @PostMapping("update")
    @ApiOperation(value = "服务管理更新接口", notes = "服务管理更新接口")
    public DataResults update(@RequestBody CusService cusService) {
        cusService.setUpdateDate(DateTimeUtils.getDateTime());
        return DataResults.success(ResultCode.SUCCESS,cusServiceService.updateById(cusService));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "服务管理删除接口", notes = "服务管理删除接口")
    public DataResults delete(Integer id) {
        CusService cusService = new CusService();
        cusService.setIsdel(1);
        cusService.setId(id);
        return DataResults.success(ResultCode.SUCCESS,cusServiceService.removeById(cusService));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "服务管理多选删除接口", notes = "服务管理多选删除接口")
    public DataResults deleteMany(@RequestBody List<CusService> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CusService cusService:list) {
            ids.add(cusService.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,cusServiceService.removeByIds(ids));

    }
}
