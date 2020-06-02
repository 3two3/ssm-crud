package com.inspur.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inspur.bean.Employee;
import com.inspur.bean.Msg;
import com.inspur.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理员工CRUD请求
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 删除员工信息
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单条删除：1
     *
     * @param ids
     * @return
     */
    @CrossOrigin // 允许所有ip跨域
    @ResponseBody
    @RequestMapping(value = "/emp/{empIds}", method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("empIds") String ids) {
        //批量删除
        if (ids.contains("-")) {
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id的集合
            for (String str : str_ids) {
                del_ids.add(Integer.parseInt(str));
            }
            employeeService.deleteBatch(del_ids);
        } else {//单个删除
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmpById(id);
        }

        return Msg.success();
    }

    /**
     * 修改员工信息
     *
     * @param employee
     * @return
     */
    @CrossOrigin // 允许所有ip跨域
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    public Msg saveEmp(@RequestBody Employee employee) {
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 获取单个员工信息
     *
     * @param id
     * @return
     */
    @CrossOrigin // 允许所有ip跨域
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("employee", employee);
    }

    /**
     * 员工姓名重复校验
     *
     * @param empName
     * @return
     */
    @CrossOrigin // 允许所有ip跨域
    @ResponseBody
    @RequestMapping("/checkEmpName")
    public Msg checkEmpName(@RequestParam String empName) {
        boolean result = employeeService.checkEmpName(empName);
        if (result) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

    /**
     * 员工保存
     * 1、支持JSR303校验
     * 2、导入Hibernate-Validator
     *
     * @return
     */
    @CrossOrigin // 允许所有ip跨域
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@RequestBody @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            //校验失败
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("fieldErrors", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * 导入jackson包
     *
     * @param pageNum
     * @return
     */
    @CrossOrigin // 允许所有ip跨域
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        //引入PageHelper分页插件
        //在查询之前只需要调用,传入页码，以及每页的大小
        PageHelper.startPage(pageNum, pageSize);
        //startPage后面紧跟的查询是分页查询
        List<Employee> emps = employeeService.getEmps();
        //使用pageInfo包装查询后的结果,只需要将pageInfo交给页面就行了
        //封装了详细的分页信息，包括我们查询出来的所有数据,传入连续显示的页数
        PageInfo pageInfo = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 查询员工数据
     *
     * @return
     */
    //@RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        //这不是分页查询
        //引入PageHelper分页插件
        //在查询之前只需要调用,传入页码，以及每页的大小
        PageHelper.startPage(pageNum, 5);
        //startPage后面紧跟的查询是分页查询
        List<Employee> emps = employeeService.getEmps();

        //使用pageInfo包装查询后的结果,只需要将pageInfo交给页面就行了
        //封装了详细的分页信息，包括我们查询出来的所有数据,传入连续显示的页数
        PageInfo pageInfo = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", pageInfo);
        pageInfo.getNavigatepageNums();
        return "list";
    }

}
