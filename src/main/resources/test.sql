SELECT T.ORDER_TYPE AS orderType,
    /*订单类型*/
       T.ORDER_CODE AS saleOrderCode,
    /*OAS订单号*/
       T.ORDER_ID AS saleOrderId,
    /*OAS订单id*/
       T.UPDATE_CONTROL_ID AS updateControlId,
    /*并发控制id*/
       T.VIN AS vin,
    /*VIN码*/
       T.ORDER_DATE AS orderDate,
    /*订单时间*/
       CF.SMALL_CAR_TYPE_CN AS carTypeName,
    /*车型名称*/
       CF.CAR_SERIES_CN AS carSeriesCn,
    /*车系中文名称*/
       CF.CAR_COLOR_NAME AS appearance,
    /*车身颜色(外观)*/
       CF.CAR_INCOLOR_NAME AS interior,
    /*内饰颜色*/
       T.CUST_LX_NAME AS clientName,
    /*客户姓名*/
       T.CUST_LX_PHONE AS clientPhone,
    /*客户手机号*/
       DD.DLR_SHORT_NAME AS deliveryStore, /*交付门店*/
       DD.DLR_CODE AS deliveryStoreCode, /*交付门店code*/
       DD.DLR_ID AS deliveryDlrId, /*交付门店id*/
       SD.DLR_SHORT_NAME AS saleStore, /*销售门店*/
       SD.DLR_CODE AS saleStoreCode, /*销售门店code*/
       T.VIP_ORDER_CODE,/*批次编号*/
       T.HOST_LIMITR_EASON_ID,
       CF.CAR_CONFIG_CODE,/*车型配置编码*/
       CF.CAR_CONFIG_CN,/*车型配置名称*/
       C.BATTERY_CODE,
       C.BATTERY_NO,
       C.QUALIFIED_CERT_NO,
       H.CAR_HOUSE_NAME,/*仓库名称*/
       C.BRAND_MODEL,
       TM.car_num, /*车牌号*/
       T.CAR_STOCK_HOUSE_ID, /*整车仓库id*/
       C.CAR_ID,


       CF.std_car_cn, /*车型名称*/
       T.move_type, /*移库类型*/
       T.SEND_PERSION_DATE /*发运时间*/
FROM (
         SELECT '3' AS ORDER_TYPE,
                A.SALE_ORDER_CODE ORDER_CODE,
                A.SALE_ORDER_ID ORDER_ID,
                B.UPDATE_CONTROL_ID,
                A.VIP_ORDER_CODE VIP_ORDER_CODE,
                A.SALE_ORDER_DATE ORDER_DATE,

                SS.VIN,
                SS.HOST_LIMITR_EASON_ID,
                B.CAR_CONFIG_ID,
                A.DLR_ID,
                B.DELIVERY_DLR_ID,
                A.SALE_ORDER_DATE ISSUE_DATE,
                SS.CAR_STOCK_HOUSE_ID,
                A.CUST_LX_NAME,
                A.CUST_LX_PHONE,

                B.PRE_DELIVER_DATE,
                '' move_type,
                B.SEND_PERSION_DATE /*发运时间*/
         FROM T_VE_BU_SALE_ORDER A
                  JOIN T_VE_BU_SALE_ORDER_D B ON A.SALE_ORDER_ID = B.SALE_ORDER_ID
                  inner join dlr_move_car_record mcr on A.SALE_ORDER_CODE = mcr.sale_order_code
                  INNER JOIN T_MDM_DLR_CAR_STORE SS ON SS.VIN = mcr.vin
         WHERE 1 = 1
           and mcr.move_car_status = 3
           AND B.SALE_ORDER_STATE IN ('400')
           AND SS.MOVE_STATE in ('PL042')
           AND A.SALE_TYPE_ID not in ('10')
     ) T
         INNER JOIN T_MDM_DLR_CAR C ON T.VIN = C.VIN
         INNER JOIN T_BASE_CAR_INFO CF ON T.CAR_CONFIG_ID = CF.CAR_CONFIG_ID
         INNER JOIN T_MDM_ORG_DLR DD ON T.DELIVERY_DLR_ID = DD.DLR_ID
         INNER JOIN t_ve_db_car_stock_house H ON T.CAR_STOCK_HOUSE_ID = H.CAR_STOCK_HOUSE_ID
         LEFT JOIN T_MDM_ORG_DLR SD ON T.DLR_ID = SD.DLR_ID
         LEFT JOIN T_VE_BU_VIN_TMS_INFO TM ON T.VIN = TM.VIN
where
        1 = 1


  and T.VIN = 'L1NSPGHB9MA066947'

order by T.ORDER_DATE desc
limit 1