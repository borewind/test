package com.Contract;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxtechai.Contract.DishContractDTO;
import com.zxtechai.utils.DishUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DishContract {
    /**
     * 添加菜品信息到智能合约
     *
     * 该方法通过调用智能合约的setDish方法来添加菜品信息
     * 它从DishContractDTO对象中提取菜品的相关信息，并将这些信息转换为必要的格式或单位，
     * 然后将这些信息作为参数传递给智能合约方法
     *
     * @param dishContractDTO 包含菜品信息的数据传输对象
     * @return 返回执行智能合约方法后的结果，通常是一个包含交易信息的JSONObject
     */
    public JSONObject add(DishContractDTO dishContractDTO) {
        // 创建参数列表
        ArrayList<Object> funcParam = new ArrayList<>();

        // 从 DishContractDTO 对象中获取信息并添加到参数列表
        funcParam.add(dishContractDTO.getName());         // 菜品名称
        funcParam.add(dishContractDTO.getCategoryId());   // 菜品分类ID
        // 菜品价格转为最小单位 Wei，以适应区块链网络中的价值传输要求
        funcParam.add(dishContractDTO.getPrice().multiply(BigDecimal.valueOf(Math.pow(10, 18))).toBigInteger());
        funcParam.add(dishContractDTO.getDescription());  // 菜品描述
        funcParam.add(dishContractDTO.getStatus());       // 菜品状态（上架或下架）

        // 调用 DishUtils 的 writeContract 方法，将菜品信息传递给智能合约
        return DishUtils.writeContract("addDish", funcParam);
    }

    /**
     * 获取菜品信息
     *
     * 本方法通过调用DishUtils的readContract方法，来读取菜品智能合约中的信息
     * 没有输入参数，因为funcParam数组为空
     * 返回一个JSONArray对象，包含从合约中读取的菜品信息
     */
    public JSONArray get() {
        // 初始化功能参数列表，此处为空，因为get方法不需要额外参数
        ArrayList<Object> funcParam = new ArrayList<>();

        // 调用DishUtils类的静态方法readContract，传入方法名"get"和空参数列表
        // 读取菜品合约中的信息，并将结果存储在result变量中
        JSONArray result = DishUtils.readContract("getDish", funcParam);

        // 返回读取到的菜品信息JSONArray对象
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
        return DishUtils.writeContract("updateDocument", funcParam);
    }

    public JSONObject deleteDocument(int id) {
        ArrayList<Object> funcParam = new ArrayList<>();
        funcParam.add(id);
        JSONObject result = DishUtils.writeContract("deleteDocument", funcParam);
        return result;
    }

    public JSONArray getAllDocuments() {
        JSONArray result = DishUtils.readContract("getAllDocuments", null);
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
