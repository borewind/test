package com.zxtechai.Contract;

import java.math.BigInteger;

public class OrderDetailContractDTO {
        private BigInteger id;           // 订单项ID
        private String name;             // 名称
        private BigInteger orderId;      // 订单ID
        private BigInteger dishId;       // 菜品ID
        private BigInteger setmealId;    // 套餐ID
        private BigInteger number;       // 数量
        private BigInteger amount;       // 金额

        public OrderDetailContractDTO() {
        }

        public OrderDetailContractDTO(BigInteger id, String name, BigInteger orderId, BigInteger dishId, BigInteger setmealId, BigInteger number, BigInteger amount) {
            this.id = id;
            this.name = name;
            this.orderId = orderId;
            this.dishId = dishId;
            this.setmealId = setmealId;
            this.number = number;
            this.amount = amount;
        }
        // Getters and Setters
        public BigInteger getId() {
            return id;
        }

        public void setId(BigInteger id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigInteger getOrderId() {
            return orderId;
        }

        public void setOrderId(BigInteger orderId) {
            this.orderId = orderId;
        }

        public BigInteger getDishId() {
            return dishId;
        }

        public void setDishId(BigInteger dishId) {
            this.dishId = dishId;
        }

        public BigInteger getSetmealId() {
            return setmealId;
        }

        public void setSetmealId(BigInteger setmealId) {
            this.setmealId = setmealId;
        }
        

        public BigInteger getNumber() {
            return number;
        }

        public void setNumber(BigInteger number) {
            this.number = number;
        }

        public BigInteger getAmount() {
            return amount;
        }

        public void setAmount(BigInteger amount) {
            this.amount = amount;
        }


    }

