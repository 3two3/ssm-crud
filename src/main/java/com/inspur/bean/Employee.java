package com.inspur.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class Employee implements Serializable {
    private Integer empId;

    @Length(min = 2, max = 10, message = "员工姓名的长度在3~10个字符之间！")
    private String empName;

    private String gender;

    @Email(message = "邮箱格式不正确！")
    private String email;

    private Integer deptId;

    //希望查询员工的同时也查询部门
    private Department department;

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Employee(Integer empId, String empName, String gender, String email, Integer deptId) {
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.email = email;
        this.deptId = deptId;
    }

    public Employee() {
        super();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", deptId=" + deptId +
                ", department=" + department +
                '}';
    }
}