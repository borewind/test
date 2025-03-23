package com.zxtechai.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class SetmealUtil {
    private static final String contractAddress = "0x27cec1d748b1cf14c1c5a5bc3bcf503044cfcec8";
    private static final String contractName = "Setmeal";

    private static final String contractAbi = "[{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"setmeals\",\"outputs\":[{\"name\":\"id\",\"type\":\"uint256\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"price\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"setmealCount\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_id\",\"type\":\"uint256\"}],\"name\":\"getSetmeal\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_name\",\"type\":\"string\"},{\"name\":\"_price\",\"type\":\"uint256\"}],\"name\":\"addSetmeal\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    private static final String transUrl = "http://192.168.65.132:5002/WeBASE-Front/trans/handle";
    private static final String ownerAddress = "0xffc4b604dbfcd9c97d2cfe2626b792d23fe87652";


    private static OkHttpClient client = new OkHttpClient();
    private static String CommonReq(String funcName, List funcParam){
        JSONArray abiJOSN = JSON.parseArray(contractAbi);
        JSONObject data = new JSONObject();
        data.put("groupId","1");
        data.put("user",ownerAddress);
        data.put("contractName",contractName);
        //data.put("version","");
        data.put("funcName",funcName);
        data.put("funcParam",funcParam);
        data.put("contractAddress",contractAddress);
        data.put("contractAbi",abiJOSN);
        //data.put("useAes",false);
        data.put("useCns",false);
        //data.put("cnsNAme","");

        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), data.toJSONString());
            Request request = new Request.Builder()
                    .url(transUrl)
                    .post(requestBody)
                    .build();
            final Call call = client.newCall(request);
            Response response = call.execute();
//            System.out.println(data.toJSONString());
            return response.body().string();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 写合约
     * @param funcName 合约方法名
     * @param funcParam 合约方法参数列表
     * @return 合约回执
     */
    public static JSONObject writeContract(String funcName,List funcParam){
        String result = com.zxtechai.utils.SetmealUtil.CommonReq(funcName,funcParam);
        JSONObject _obj = JSON.parseObject(result);
//        if (_obj.getIntValue("code") > 0 || !_obj.get("status").equals("0x0")){
//            return null;
//        }
        return _obj;
    }

    /**
     * 读合约
     * @param funcName 合约方法名
     * @return JSON格式数组
     */
    public static JSONArray readContract(String funcName,List funcParam){
        String result = com.zxtechai.utils.SetmealUtil.CommonReq(funcName,funcParam);
        return JSON.parseArray(result);
    }
}
