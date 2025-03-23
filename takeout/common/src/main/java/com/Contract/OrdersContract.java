package com.Contract;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.zxtechai.Contract.OrdersContractDTO;
import com.zxtechai.utils.EmployeeUtil;
import com.zxtechai.utils.OrdersUtil;

import java.util.ArrayList;

public class OrdersContract {
    public JSONObject addOrder(OrdersContractDTO ordersContractDTO) {
        // 创建参数列表
        ArrayList<Object> funcParam = new ArrayList<>();

        // 从 OrdersContractDTO 对象中获取信息并添加到参数列表
        funcParam.add(ordersContractDTO.getId());              // 订单ID
        funcParam.add(ordersContractDTO.getNumber());          // 订单号
        funcParam.add(ordersContractDTO.getStatus());          // 订单状态
        funcParam.add(ordersContractDTO.getUserId());          // 下单用户ID
        funcParam.add(ordersContractDTO.getAddressBookId());   // 地址ID
        funcParam.add(ordersContractDTO.getUserName());        // 用户名
        funcParam.add(ordersContractDTO.getPhone());           // 手机号
        funcParam.add(ordersContractDTO.getShippingAddress()); // 地址
        funcParam.add(ordersContractDTO.getConsignee());       // 收货人

        // 调用 EmployeeUtil 的 writeContract 方法，将订单信息传递给智能合约
        return EmployeeUtil.writeContract("createOrder", funcParam);
    }

    public JSONArray get() {
        ArrayList<Object> funcParam = new ArrayList<>();

        JSONArray result = OrdersUtil.readContract("get", funcParam);
        return result;
    }

    public JSONObject updateDocument(int id, String fileName, String size, String uploader, String date, String url) {
        ArrayList<Object> funcParam = new ArrayList<>();
//        String[] strings =  {"\""+fileName+"\"","\""+size+"\"","\""+uploader+"\"","\""+date+"\"","\""+url+"\"",};
        funcParam.add(id);
        funcParam.add(fileName);
        funcParam.add(size);
        funcParam.add(uploader);
        funcParam.add(date);
        funcParam.add(url);
        return OrdersUtil.writeContract("updateDocument", funcParam);
    }

    public JSONObject deleteDocument(int id) {
        ArrayList<Object> funcParam = new ArrayList<>();
        funcParam.add(id);
        JSONObject result = OrdersUtil.writeContract("deleteDocument", funcParam);
        return result;
    }

    public JSONArray getAllDocuments() {
        JSONArray result = OrdersUtil.readContract("getAllDocuments", null);
        return result;
    }


/*    public static void main(String[] args) throws Exception {
        MyContract myContract = new MyContract();
        JSONArray allDocuments = myContract.getDocumentByUser("1");
        JSONArray documents = allDocuments.getJSONArray(0);
        ArrayList<HashMap<String, Object>> documentsList = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            JSONArray document = documents.getJSONArray(i);
            if (document.getInteger(0) != 0) {  // 检查每个文档数组的第一个元素（id）
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", document.get(0));
                map.put("originalFilename", document.get(1));
                map.put("size", document.get(2));
                map.put("username", document.get(3));
                map.put("time", document.get(4));
                map.put("url", document.get(5));
                documentsList.add(map);
            }
        }

        System.out.println(documentsList);

//        MyContract myContract = new MyContract();
//
//
//        JSONArray result1 = myContract.getAllDocuments();
//        System.out.println(result1);
//        JSONArray result2 = myContract.deleteDocument(1);
//        System.out.println(result2);

        //根据id获取链上信息
//        JSONArray result3 = myContract.get(3);
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("id",result3.get(0));
//        map.put("originalFilename",result3.get(1));
//        map.put("size",result3.get(2));
//        map.put("username",result3.get(3));
//        map.put("time",result3.get(4));
//        map.put("url",result3.get(5));
//        System.out.println(map);
        //获取所有链上数据,但不显示id为0的数据
//        JSONArray allDocuments = HttpUtils.readContract("getAllDocuments", null);
//        JSONArray documents = allDocuments.getJSONArray(0);
//        ArrayList<HashMap<String, Object>> documentsList = new ArrayList<>();
//        for (int i = 0; i < documents.size(); i++) {
//            JSONArray document = documents.getJSONArray(i);
//            if (document.getInteger(0) != 0) {  // 检查每个文档数组的第一个元素（id）
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("id", document.get(0));
//                map.put("originalFilename", document.get(1));
//                map.put("size", document.get(2));
//                map.put("username", document.get(3));
//                map.put("time", document.get(4));
//                map.put("url", document.get(5));
//                documentsList.add(map);
//            }
//        }

//        System.out.println(documentsList);
//        String jsonString = "[[0, \"\", \"\", \"\", \"\", \"\"], [0, \"\", \"\", \"\", \"\", \"\"], [3, \"3\", \"3\", \"3\", \"3\", \"3\"]]";
//        JSONArray documents = JSONArray.parseArray(jsonString);
//
//        for (int i = 0; i < documents.size(); i++) {
//            JSONArray document = documents.getJSONArray(i);
//            Integer id = document.getInteger(0); // 获取id
//            String firstField = document.getString(1); // 获取第一个字段
//            String secondField = document.getString(2); // 获取第二个字段
//            // 以此类推，获取其他字段
//
//            // 打印或处理每个文档的数据
//            System.out.println("Document ID: " + id);
//            System.out.println("First Field: " + firstField);
//            System.out.println("Second Field: " + secondField);
//            // 打印其他字段
//
//        }
//    }
    }*/
}
