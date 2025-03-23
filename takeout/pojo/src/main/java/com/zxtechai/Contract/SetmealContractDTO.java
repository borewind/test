package com.zxtechai.Contract;

import java.math.BigInteger;

public class SetmealContractDTO {
    private String name;           // 套餐名称
    private BigInteger price;      // 套餐价格

    public SetmealContractDTO() {
    }

    public SetmealContractDTO(String name, BigInteger price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }
}
