package com.zxtechai.Contract;

import java.math.BigInteger;

public class EmployeeContractDTO {

        private BigInteger id;        // 员工ID
        private String username;      // 员工用户名
        private String phone;         // 员工电话
        private String sex;           // 员工性别
        private String idNumber;      // 员工身份证号码

    public EmployeeContractDTO(BigInteger id, String username, String phone, String sex, String idNumber) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.sex = sex;
        this.idNumber = idNumber;
    }

    public EmployeeContractDTO() {
    }

    // Getter 和 Setter 方法
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

}
