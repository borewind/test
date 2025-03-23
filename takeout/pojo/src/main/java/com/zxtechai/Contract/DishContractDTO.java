package com.zxtechai.Contract;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishContractDTO {
        private int id;
        private String name;             // 菜品名称
        private int categoryId;          // 菜品分类ID
        private BigDecimal price;        // 菜品价格
        private String description;      // 菜品描述
        private int status;              // 菜品状态（例如：0 表示下架，1 表示上架）


    public DishContractDTO() {
    }

    public DishContractDTO(String name, int categoryId, BigDecimal price, String description, int status) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.description = description;
        this.status = status;
    }
        // Getter 和 Setter

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

