package com.crms.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;

/**
 * SpringSecurity三套权限对应注解：
 * prePostEnabled = true------>@PostAuthorize("hasAnyRole('ADMIN')")
 * jsr250Enabled = true------>@RolesAllowed("ADMIN")
 * securedEnabled = true------>@Secured("ROLE_ADMIN")
 */

/**
 * 跳转模板页面
 */
@Controller
public class CommonController {

    @GetMapping("index.html")
    public String toindexHtml() {
        return "index";
    }
    @GetMapping("frame.html")
    public String toframeHtml() {
        return "frame";
    }
    //@PostAuthorize("hasAnyRole('USER')")
    //@RolesAllowed("ADMIN")
    //@Secured("ROLE_超级管理员")
    @GetMapping("customer.html")
    public String toCustomerHtml() {
        return "customer/customer";
    }
    //跳转添加页面
    @GetMapping("customerForm.html")
    public String tocustomerFormHtml() {
        return "customer/customerForm";
    }
    //客户页面
    @GetMapping("customerContact.html")
    public String tocustomerContactHtml() {
        return "customer/customerContact";
    }
    //跳转查看联系人页面
    @GetMapping("customerLinkMan.html")
    public String tocustomerLinkManHtml() {
        return "customer/customerLinkMan";
    }
    //跳转查看联系人修改页面
    @GetMapping("customerLinkManForm.html")
    public String toCustomerLinkManFormHtml() {
        return "customer/customerLinkManForm";
    }

    //跳转查看联系人新增页面
    @GetMapping("customerContactForm.html")
    public String tocustomerContactFormHtml() {
        return "customer/customerContactForm";
    }

    //跳转查看联系人新增页面
    @GetMapping("customerLossForm.html")
    public String tocustomerLossFormHtml() {
        return "customer/customerLossForm";
    }

    //跳转客户流失详情页面
   // @Secured({"ROLE_超级管理员","ROLE_营销部"})//超级管理员,营销部才有权限访问该页面
    @GetMapping("customerLoss.html")
    public String tocustomerLossHtml() {
        return "customer/customerLoss";
    }

    //跳转订单页面
    @GetMapping("customerOrder.html")
    public String tocustomerOrderHtml() {
        return "customer/customerOrder";
    }

    //跳转添加订单页面
    @GetMapping("customerOrderForm.html")
    public String tocustomerOrderFormHtml(@RequestParam(defaultValue = "add") String type,Model model) {
        model.addAttribute("type",type);
        return "customer/customerOrderForm";
    }

    //跳转营销机会页面
   // @Secured({"ROLE_营销部"})
    @GetMapping("saleChance.html")
    public String tosaleChanceHtml() {
        return "marketing/saleChance";
    }

    //跳转营销机会详情页面
    @GetMapping("saleChanceForm.html")
    public String tosaleChanceFormHtml(@RequestParam(defaultValue = "-1") Integer customerId,
                                       @RequestParam(defaultValue = "-1") Integer assignMan,
                                       @RequestParam(defaultValue = "0") Integer state,
                                       @RequestParam(defaultValue = "add") String type,
                                       Model model) {

        model.addAttribute("customerId",customerId);
        model.addAttribute("assignMan",assignMan);
        model.addAttribute("state",state);
        model.addAttribute("type",type);
        return "marketing/saleChanceForm";
    }

    //跳转客户开发页面
    @GetMapping("developmentPlan.html")
    public String todevelopmentPlanHtml() {
        return "marketing/developmentPlan";
    }
    //跳转客户开发详情页面

    @GetMapping("developmentPlanForm.html")
    public String todevelopmentPlanFormHtml(@RequestParam(defaultValue = "-1")Integer saleChanceId,Model model) {
        model.addAttribute("saleChanceId",saleChanceId);
        return "marketing/developmentPlanForm";
    }

    //跳转服务管理页面
    @GetMapping("establish.html")
    public String toestablishHtml() {
        return "services/establish";
    }
    //跳转服务管理详情页面
    @GetMapping("establishForm.html")
    public String toestablishFormHtml(@RequestParam(defaultValue = "-1")Integer cusId,Model model) {
        model.addAttribute("cusId",cusId);
        return "services/establishForm";
    }
    @GetMapping("permission.html")
    public String topermissionhHtml() {
        return "permission/permission";
    }

    @GetMapping("permissionForm.html")
    public String topermissionFormHtml() {
        return "permission/permissionForm";
    }

    @GetMapping("permissionForm2.html")
    public String topermissionForm2Html(Integer parentId,Model model) {
        model.addAttribute("parentId",parentId);
        return "permission/permissionForm2";
    }

    @GetMapping("role.html")
    public String toroleHtml() {
        return "permission/role";
    }

    @GetMapping("roleForm.html")
    public String toroleFormHtml() {
        return "permission/roleForm";
    }

    @GetMapping("rolePermissionForm.html")
    public String torolePermissionFormHtml(Integer roleId, Integer permissionId, Model model) {
       model.addAttribute("permissionId",permissionId);
       model.addAttribute("roleId",roleId);
        return "permission/rolePermissionForm";
    }

    @GetMapping("rolePermission.html")
    public String torolePermissionHtml() {
        return "permission/rolePermission";
    }

    @GetMapping("user.html")
    public String touserHtml() {
        return "permission/user";
    }

    @GetMapping("userForm.html")
    public String touserFormHtml() {
        return "permission/userForm";
    }

    @GetMapping("userRole.html")
    public String touserRoleHtml() {
        return "permission/userRole";
    }

    @GetMapping("userRoleForm.html")
    public String touserRoleFormHtml(Integer userId,Integer roleId,Model model) {
        model.addAttribute("roleId",roleId);
        model.addAttribute("userId",userId);
        return "permission/userRoleForm";
    }
    @GetMapping("403.html")
    public String to403Html() {
        return "403";
    }

    @GetMapping("404.html")
    public String to404Html() {
        return "404";
    }

    @GetMapping("500.html")
    public String to500Html() {
        return "500";
    }

    @GetMapping("login.html")
    public String touloginHtml() {
        return "login";
    }

    @GetMapping("personalData.html")
    public String topersonalDataHtml() {
        return "personalcenter/personalData";
    }

    @GetMapping("personalPwd.html")
    public String topersonalPwdHtml() {
        return "personalcenter/personalPwd";
    }

    @GetMapping("systemLogging.html")
    public String tosystemLoggingData() {
        return "logging/systemLogging";
    }


    @GetMapping("loginLogging.html")
    public String tologinLoggingData() {
        return "logging/loginLogging";
    }

}
