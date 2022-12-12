package com.yifeng.lottery.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClasName TicketDTO
 * @Author niyf
 * @Date 2022/12/12 23:33
 * @Decription
 */
@Data
public class TicketDTO {

    private List<Integer> redBallList;

    private List<Integer> blueBallList;


}
