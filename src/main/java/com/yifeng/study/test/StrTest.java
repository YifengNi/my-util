package com.yifeng.study.test;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

        // testAsync();

        // distinctTableName();
        // System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        // testNull();

        // msgSql();

        System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());

        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // try {
        //     Date date = format.parse("2023-02-07 12:05:36.0");
        //     Date dateN = format.parse("2023-05-08 12:05:36.0");
        //     long between = DateUtil.between(date, dateN, DateUnit.DAY);
        //     System.out.println("between = " + between);
        // } catch (ParseException e) {
        //     throw new RuntimeException(e);
        // }

    }

    public static void msgSql() {
        Map<String, Object> msgMap = new HashMap<>(8);
        String key = IdUtil.randomUUID();
        msgMap.put("key", key);
        msgMap.put("event", "ZQMES_OAS_ZQMES_OAS_R_001");

        Map<String, Object> HEADER = new HashMap<>(8);
        HEADER.put("BUSID", "ZQMES0300");
        HEADER.put("SENDER", "ZQMES");
        HEADER.put("RECID", IdUtil.randomUUID());
        HEADER.put("DTSEND", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        HEADER.put("RECEIVER", "OAS");

        String param = "{\"CAR_TYPE\": \"NHQ7000BEVDQ\", \n" +
                "    \"FACTORY_CODE\": \"肇庆小鹏新能源投资有限公司\", \n" +
                "    \"PRODUCE_PLACE\": \"广东省肇庆市肇庆高新区龙湖大道33号\", \n" +
                "    \"CERTIFICATE_NO\": \"XV1499000182479\",\n" +
                "    \"ENGINE_TYPE\": \"N/A\",  \n" +
                "    \"ENGINE_NO\": \"N/A\",   \n" +
                "    \"ENGINE_CODE\": \"N/A\", \n" +
                "    \"ENGINE_NO2\": \"TZ228XS68H\", \n" +
                "    \"ENGINE_CODE2\": \"B4G7023411010002DC1\",\n" +
                "    \"BATTERY_NO\": \"TPLi0662-380\", \n" +
                "    \"BATTERY_CODE\": \"001PEAFD000002B730100020\",\n" +
                "    \"OFFLINE_DATE\": \"2022-07-13 17:10:53\", \n" +
                "    \"PRODUCE_DATE\": \"2021-08-06 14:07:40\",\n" +
                "    \"BATCH_NO\": \"0000132034\", \n" +
                "    \"VEMATERIEL_CODE\": \"DGKM9011BA2\",\n" +
                "    \"CAR_COLOR_CODE\": \"1B\",\n" +
                "    \"CAR_CONFIG_CODE\": \"DGKM9011BA2\",\n" +
                "    \"VIN\": \"L1NSSGH95MA029258\",\n" +
                "    \"CDU\": \"XPENGD224000130812409C65\",\n" +
                "    \"RATED_POWER\": \"N/A\",\n" +
                "    \"ELECTRICAL_COMPANY\": \"3691\",\n" +
                "    \"CONTROLLER_TYPE\": \"KTZ37X50SXP0\",\n" +
                "    \"ELECTRICAL_COMP\": \"100190\",\n" +
                "    \"DRIVING_MOTOR\": \"TZ228XS68H\"}";
        JSONObject OASMODEL = JSONObject.parseObject(param);
        String vin = OASMODEL.getString("VIN");

        Map<String, Object> bodyMap = new HashMap<>(8);
        // bodyMap.put("HEADER", JSONObject.toJSONString(HEADER));
        // bodyMap.put("OASMODEL", JSONObject.toJSONString(OASMODEL));

        bodyMap.put("HEADER", HEADER);
        bodyMap.put("OASMODEL", OASMODEL);

        // msgMap.put("body", JSONObject.toJSONString(bodyMap));
        msgMap.put("body", bodyMap);

        String sql = String.format("insert\n" +
                "\tinto\n" +
                "\tbiz_message_record\n" +
                "(\n" +
                "\t\tapi_key,\n" +
                "\t\tmessage,\n" +
                "\t\tout_message,\n" +
                "\t\tsource,\n" +
                "\t\t`type`,\n" +
                "\t\tstatus,\n" +
                "\t\tis_async,\n" +
                "\t\tis_retry,\n" +
                "\t\tretry_count,\n" +
                "\t\tbiz_code\n" +
                "\t)\n" +
                "values(\n" +
                "\t'%s',\n" +
                "\t'%s',\n" +
                "\t'',\n" +
                "\t'inner',\n" +
                "\t'ZQMES_OAS_ZQMES_OAS_R_001',\n" +
                "\t0,\n" +
                "\t1,\n" +
                "\t1,\n" +
                "\t0,\n" +
                "\t'CarInfo-%s'\n" +
                ");\n", key, JSONObject.toJSONString(msgMap), vin);

        System.out.println(sql);
    }

    public static void testNull() {
        List<String> list = new ArrayList<>(10);
        // List<String> list = null;
        for (String item : list) {
            System.out.println("item = " + item);
        }

        String[] arr = new String[0];
        for (String item : arr) {
            System.out.println("item = " + item);
        }
    }

    /**
     * 对表名进行去重
     */
    public static void distinctTableName() {
        String s = "t_ve_bu_dlr_move_order\n" +
                "t_ve_bu_dlr_move_demand\n" +
                "t_mdm_org_dlr\n" +
                "t_mdm_org_big_area\n" +
                "t_mdm_org_small_area\n" +
                "t_base_car_info\n" +
                "t_mdm_org_employee\n" +
                "t_ve_db_car_stock_house\n" +
                "t_ve_bu_dlr_move_demand\n" +
                "t_mdm_org_dlr\n" +
                "t_mdm_org_big_area\n" +
                "t_mdm_org_small_area\n" +
                "t_base_car_info\n" +
                "t_mdm_org_employee\n" +
                "t_ve_db_car_stock_house\n" +
                "t_ve_bu_dlr_move_order\n";
        String[] ss = s.split("\\n");

        Arrays.stream(ss)
                .map(String::toLowerCase)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .sorted()
                .forEach(System.out::println);
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
        String s = "L1NSPGH90MA063383\n" +
                "L1NSPGH9XNA133182\n" +
                "L1NSSGH99NA142650\n" +
                "L1NSPGH96NA156216\n" +
                "L1NSSGH93NA168208\n" +
                "L1NSPGHB0NA173015\n" +
                "L1NSPGHB7NA177613\n" +
                "L1NSPGHB5NA178470\n" +
                "L1NSPGHB0NA179543\n" +
                "L1NSPGHB6NA180227\n" +
                "L1NSPGHB8NA180844\n" +
                "L1NSPGHB9NA182408\n" +
                "L1NSPGHB3NA173414\n";


        Set<String> codeList = Arrays.stream(s.split("\\s")).filter(item -> item.length() > 6)
                .distinct()
                // .map(item -> "'CarInfo-" + item + "'")
                .map(item -> "'" + item + "'")
                // .map(item -> "\"" + item + "\"")
                // .map(item -> "'" + item + "-REPLACE'")
                // .map(item -> item.replaceAll("\\-REPLACE", ""))
                // .map(item -> item.replaceAll("REPLACE", ""))
                // .map(item -> "'" + item + "REPLACE'")
                .sorted()
                .collect(Collectors.toSet());
        // System.out.println("codeList size = " + codeList.size());
        // System.out.println(String.join(", ", codeList));


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
