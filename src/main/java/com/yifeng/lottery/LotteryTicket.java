package com.yifeng.lottery;

import com.yifeng.lottery.dto.TicketDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClasName LotteryTicket
 * @Author niyf
 * @Date 2022/12/12 23:20
 * @Decription
 */
public class LotteryTicket {

    /**
     * 下注彩票张数限制
     */
    private static final int BET_TICKET_LIMIT = 7;
    private static final int RED_BALL_LIMIT = 5;
    private static final int BLUE_BALL_LIMIT = 2;
    private static final int BLUE_BALL_ALL = 12;


    public static void main(String[] args) {
        generateRandomTickets();
    }


    public static void generateRandomTickets() {
        // 红球（前半区）1-35号
        boolean[] redBalls = new boolean[36];
        // 篮球（后半区）1-12号，考虑到要生成7注，此处下标要达到14
        boolean[] blueBalls = new boolean[15];
        // 默认彩票号，最多7注
        List<List<Integer>> defaultTicketList = new ArrayList<>(7);

        // 去掉0号下标
        redBalls[0] = true;
        blueBalls[0] = true;

        Random redRandom = new Random();
        Random blueRandom = new Random();

        List<TicketDTO> ticketList = new ArrayList<>(BET_TICKET_LIMIT);
        while (ticketList.size() < BET_TICKET_LIMIT) {
            TicketDTO ticketDTO = new TicketDTO();
            List<Integer> redBallList = new ArrayList<>(RED_BALL_LIMIT);
            List<Integer> blueBallList = new ArrayList<>(BLUE_BALL_LIMIT);

            while (redBallList.size() < RED_BALL_LIMIT) {
                int idx = redRandom.nextInt(redBalls.length - 1);
                while (redBalls[idx % redBalls.length]) {
                    idx++;
                }
                idx = idx % redBalls.length;
                redBallList.add(idx);
                redBalls[idx] = true;
            }
            ticketDTO.setRedBallList(redBallList);

            while (blueBallList.size() < BLUE_BALL_LIMIT) {
                int idx = blueRandom.nextInt(blueBalls.length - 2) + 1;
                while (blueBalls[idx % blueBalls.length]) {
                    idx++;
                }
                idx = idx % blueBalls.length;
                int blueBall = idx;
                if (blueBall > BLUE_BALL_ALL) {
                    int r = blueRandom.nextInt(BLUE_BALL_ALL - 2) + 2;
                    blueBall = blueBall - r;
                    System.out.println("重复blueBall：" + blueBall + "-" + r);
                }
                if (blueBallList.contains(blueBall)) {
                    System.out.println("blueBall已存在：" + blueBall);
                    continue;
                }
                blueBallList.add(blueBall);
                blueBalls[idx] = true;
            }
            ticketDTO.setBlueBallList(blueBallList);

            ticketList.add(ticketDTO);
        }

        ticketList.forEach(System.out::println);

    }

}
