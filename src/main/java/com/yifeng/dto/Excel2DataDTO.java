package com.yifeng.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @Author niyf
 * @Date 2022-7-27 15:27
 * @Description
 **/
@Data
// @Accessors(chain = true)
public class Excel2DataDTO {

    @ExcelProperty(value = "CAR_TYPE")
    @JSONField(name = "CAR_TYPE")
    private String carType;

    @ExcelProperty(value = "FACTORY_CODE")
    @JSONField(name = "FACTORY_CODE")
    private String factoryCode;

    @ExcelProperty(value = "PRODUCE_PLACE")
    @JSONField(name = "PRODUCE_PLACE")
    private String producePlace;

    @ExcelProperty(value = "CERTIFICATE_NO")
    @JSONField(name = "CERTIFICATE_NO")
    private String certificateNo;

    @ExcelProperty(value = "ENGINE_TYPE")
    @JSONField(name = "ENGINE_TYPE")
    private String engineType;

    @ExcelProperty(value = "ENGINE_NO")
    @JSONField(name = "ENGINE_NO")
    private String engineNo;

    @ExcelProperty(value = "ENGINE_CODE")
    @JSONField(name = "ENGINE_CODE")
    private String engineCode;

    @ExcelProperty(value = "ENGINE_NO2")
    @JSONField(name = "ENGINE_NO2")
    private String engineNo2;

    @ExcelProperty(value = "ENGINE_CODE2")
    @JSONField(name = "ENGINE_CODE2")
    private String engineCode2;

    @ExcelProperty(value = "BATTERY_NO")
    @JSONField(name = "BATTERY_NO")
    private String batteryNo;

    @ExcelProperty(value = "BATTERY_CODE")
    @JSONField(name = "BATTERY_CODE")
    private String batteryCode;

    @ExcelProperty(value = "OFFLINE_DATE")
    @JSONField(name = "OFFLINE_DATE", format = "yyyy-MM-dd HH:mm:ss")
    private Date offlineDate;

    @ExcelProperty(value = "PRODUCE_DATE")
    @JSONField(name = "PRODUCE_DATE", format = "yyyy-MM-dd HH:mm:ss")
    private Date produceDate;

    @ExcelProperty(value = "BATCH_NO")
    @JSONField(name = "BATCH_NO")
    private String batchNo;

    @ExcelProperty(value = "VEMATERIEL_CODE")
    @JSONField(name = "VEMATERIEL_CODE")
    private String vematerielCode;

    @ExcelProperty(value = "CAR_COLOR_CODE")
    @JSONField(name = "CAR_COLOR_CODE")
    private String carColorCode;

    @ExcelProperty(value = "CAR_CONFIG_CODE")
    @JSONField(name = "CAR_CONFIG_CODE")
    private String carConfigCode;

    @ExcelProperty(value = "VIN")
    @JSONField(name = "VIN")
    private String vin;

    @ExcelProperty(value = "CDU")
    @JSONField(name = "CDU")
    private String cdu;

    @ExcelProperty(value = "RATED_POWER")
    @JSONField(name = "RATED_POWER")
    private String ratedPower;

    @ExcelProperty(value = "ELECTRICAL_COMPANY")
    @JSONField(name = "ELECTRICAL_COMPANY")
    private String electricalCompany;

    @ExcelProperty(value = "CONTROLLER_TYPE")
    @JSONField(name = "CONTROLLER_TYPE")
    private String controllerType;

    @ExcelProperty(value = "ELECTRICAL_COMP")
    @JSONField(name = "ELECTRICAL_COMP")
    private String electricalComp;

    @ExcelProperty(value = "DRIVING_MOTOR")
    @JSONField(name = "DRIVING_MOTOR")
    private String drivingMotor;

}
