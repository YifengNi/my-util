package com.yifeng.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Author niyf
 * @Date 2022-1-21 15:18
 * @Description
 **/
public class StrTest {

    public static void main(String[] args) {
        // imageSql();

        // codeList();

        testAsync();
    }

    /**
     * 测试异步的用法
     */
    public static void testAsync() {
        System.out.println("begin");
        // CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
        //     System.out.println("normal");
        //     int a = 1 / 0;
        // }).exceptionally(e -> {
        //     // e.printStackTrace();
        //     System.out.println("e：" + e.getMessage());
        //     System.out.println("after e");
        //     return null;
        // });
        // future.join();

        // CompletableFuture<?> future = CompletableFuture.runAsync(() -> {
        //     System.out.println("normal");
        //     int a = 1 / 0;
        // }).whenComplete((res, e) -> {
        //     // 如果上个阶段抛出异常，此处就会抛出该异常或者抛出在本阶段生成的异常
        //     // e.printStackTrace();
        //     System.out.println("res：" + res);
        //     System.out.println("e：" + e);
        //     System.out.println("e：" + e.getMessage());
        //     System.out.println("after e");
        // });
        // future.join();

        CompletableFuture<?> future = CompletableFuture.runAsync(() -> {
            System.out.println("normal");
            int a = 1 / 0;
        }).handle((res, e) -> {
            System.out.println("res：" + res);
            System.out.println("e：" + e);
            // System.out.println("e：" + e.getMessage());
            System.out.println("after e");
            return res;
        });
        future.join();

        System.out.println("end");
    }

    public static void codeList() {
        String s = "1\t\n" +
                "XP18664306\n" +
                "2\t\n" +
                "XP18247450\n" +
                "XP12962926";


        Set<String> codeList = Arrays.stream(s.split("\\s")).filter(item -> item.length() > 6)
                .distinct()
                // .map(item -> "'" + item + "'")
                // .map(item -> "\"" + item + "\"")
                .map(item -> "'" + item + "-REPLACE'")
                // .map(item -> item.replaceAll("\\-REPLACE", ""))
                // .map(item -> item.replaceAll("REPLACE", ""))
                // .map(item -> "'" + item + "REPLACE'")
                .sorted()
                .collect(Collectors.toSet());
        // System.out.println("codeList size = " + codeList.size());
        System.out.println(String.join(", ", codeList));

        // codeList.forEach(System.out::println);


        // Arrays.stream(s.split("\\s")).filter(item -> item.length() > 6)
        //         .distinct()
        //         .map(item -> item.replaceAll("\\-REPLACE", ""))
        //         .forEach(System.out::println);


        // String s1 = "{\"saleOrderCode\":\"XP19820328\",\"replaceOrderCode\":\"KE2022_0411_2\",\"auditStatus\":\"0\"}";
        // System.out.println(JSONObject.parseObject(s1));


        // String s2 = "1\t\n" +
        //         "{\"key\":\"1511598719600955392\",\"event\":\"UPDATE_ORDER_SIGNATORY_EVENT\",\"body\":\"{\\\"replaceInfoSyncReq\\\":{\\\"saleOrderCode\\\":\\\"XP19369770\\\",\\\"replaceOrderCode\\\":\\\"KE2204060050\\\",\\\"discountPrice\\\":\\\"6000.0000\\\",\\\"auditStatus\\\":\\\"3\\\",\\\"custTypeCode\\\":\\\"0\\\",\\\"custName\\\":\\\"蔡欣\\\",\\\"credCode\\\":\\\"1\\\",\\\"custCard\\\":\\\"510216197208090820\\\",\\\"isCash\\\":\\\"0\\\"},\\\"buyTypeEnum\\\":\\\"FULL_PAYMENT\\\",\\\"operateType\\\":1}\"}\n" +
        //         "20\t\n" +
        //         "{\"key\":\"1511598594904297472\",\"event\":\"UPDATE_ORDER_SIGNATORY_EVENT\",\"body\":\"{\\\"replaceInfoSyncReq\\\":{\\\"saleOrderCode\\\":\\\"XP13110332\\\",\\\"replaceOrderCode\\\":\\\"KE2204060041\\\",\\\"discountPrice\\\":\\\"18000.0000\\\",\\\"auditStatus\\\":\\\"3\\\",\\\"custTypeCode\\\":\\\"0\\\",\\\"custName\\\":\\\"张厚成\\\",\\\"credCode\\\":\\\"1\\\",\\\"custCard\\\":\\\"110223195107114619\\\",\\\"isCash\\\":\\\"0\\\"},\\\"buyTypeEnum\\\":\\\"FULL_PAYMENT\\\",\\\"operateType\\\":1}\"}\n" +
        //         "21\t\n" +
        //         "{\"key\":\"1511598343795511296\",\"event\":\"UPDATE_ORDER_SIGNATORY_EVENT\",\"body\":\"{\\\"replaceInfoSyncReq\\\":{\\\"saleOrderCode\\\":\\\"XP10865867\\\",\\\"replaceOrderCode\\\":\\\"KE2204060048\\\",\\\"discountPrice\\\":\\\"6000.0000\\\",\\\"auditStatus\\\":\\\"3\\\",\\\"custTypeCode\\\":\\\"0\\\",\\\"custName\\\":\\\"叶惠明\\\",\\\"credCode\\\":\\\"1\\\",\\\"custCard\\\":\\\"510102196510102636\\\",\\\"isCash\\\":\\\"0\\\"},\\\"buyTypeEnum\\\":\\\"FULL_PAYMENT\\\",\\\"operateType\\\":1}\"}\n" +
        //         "22\t\n" +
        //         "{\"key\":\"1511595070816649216\",\"event\":\"UPDATE_ORDER_SIGNATORY_EVENT\",\"body\":\"{\\\"replaceInfoSyncReq\\\":{\\\"saleOrderCode\\\":\\\"XP14885966\\\",\\\"replaceOrderCode\\\":\\\"KE2204060044\\\",\\\"discountPrice\\\":\\\"6000.0000\\\",\\\"auditStatus\\\":\\\"3\\\",\\\"custTypeCode\\\":\\\"0\\\",\\\"custName\\\":\\\"卢伟东\\\",\\\"credCode\\\":\\\"1\\\",\\\"custCard\\\":\\\"441900198705096179\\\",\\\"isCash\\\":\\\"0\\\"},\\\"buyTypeEnum\\\":\\\"FULL_PAYMENT\\\",\\\"operateType\\\":1}\"}\n";
        //
        // String codes = Arrays.stream(s2.split("\\s")).filter(item -> item.length() > 6)
        //         // .distinct()
        //         // .map(item -> "'" + item + "'")
        //         // .map(item -> "'" + item + "-REPLACE'")
        //         // .map(item -> item.replaceAll("\\-REPLACE", ""))
        //         // .map(item -> "'" + item + "REPLACE'")
        //         .map(item -> {
        //             // System.out.println("item = " + item);
        //             JSONObject json = JSONObject.parseObject(item);
        //             JSONObject body = json.getJSONObject("body");
        //             JSONObject replaceInfoSyncReq = body.getJSONObject("replaceInfoSyncReq");
        //             return replaceInfoSyncReq.getString("saleOrderCode");
        //         })
        //         .distinct()
        //         .map(item -> "'" + item + "'")
        //         .collect(Collectors.joining(", "));
        // System.out.println(codes);

    }

    public static void imageSql() {
        String insertSql = "INSERT INTO `file_data_record` (`file_service_table_name`, `data_id`, `file_name`" +
                ", `file_path`, `file_type`, `is_enable`, `create_by`, `update_by`, `file_cat`) " +
                "VALUES ('t_mdm_ve_car_series', '%s'" +
                ", '%s', '%s'" +
                ", 'png', 1, 'System', 'System', 'car-image');";


        String url = "https://xps01.xiaopeng.com/car/img/carImage-%E5%B0%8F%E9%B9%8FG3.png\n" +
                "https://xps01.xiaopeng.com/car/img/carImage-%E5%B0%8F%E9%B9%8FG3i.png\n" +
                "https://xps01.xiaopeng.com/car/img/carImage-%E5%B0%8F%E9%B9%8FP5.png\n" +
                "https://xps01.xiaopeng.com/car/img/carImage-%E5%B0%8F%E9%B9%8FP7.png\n" +
                "https://xps01.xiaopeng.com/car/img/carImage-%E5%B0%8F%E9%B9%8FP7%E9%B9%8F%E7%BF%BC%E7%89%88.png";


        for (String item : url.split("\\s")) {
            String[] ss = item.split("/");
            String fileName = ss[ss.length - 1];
            try {
                fileName = URLDecoder.decode(fileName, "UTF-8");
                int endIdx = fileName.indexOf('.');
                String dataId = fileName.substring(0, endIdx);
                String sql = String.format(insertSql, dataId, fileName, item);
                System.out.println(sql);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

}
