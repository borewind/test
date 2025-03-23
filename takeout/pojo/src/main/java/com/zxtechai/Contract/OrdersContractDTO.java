package com.zxtechai.Contract;

public class OrdersContractDTO {
    private Long id;              // 订单ID
    private String number;        // 订单号
    private Integer status;       // 订单状态
    private Long userId;          // 下单用户ID
    private Long addressBookId;   // 地址ID
    private String userName;      // 用户名
    private String phone;         // 手机号
    private String shippingAddress;  // 地址
    private String consignee;     // 收货人

    // 无参构造器
    public OrdersContractDTO() {
    }

    // 全参构造器
    public OrdersContractDTO(Long id, String number, Integer status, Long userId, Long addressBookId,
                    String userName, String phone, String shippingAddress, String consignee) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.userId = userId;
        this.addressBookId = addressBookId;
        this.userName = userName;
        this.phone = phone;
        this.shippingAddress = shippingAddress;
        this.consignee = consignee;
    }

    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressBookId() {
        return addressBookId;
    }

    public void setAddressBookId(Long addressBookId) {
        this.addressBookId = addressBookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

}
