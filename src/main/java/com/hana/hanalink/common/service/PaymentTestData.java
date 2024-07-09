package com.hana.hanalink.common.service;

import java.util.Random;

public class PaymentTestData {

    private static final String[] restaurantNames = {
            "Burger King", "McDonald's", "서브웨이", "명륜 진사갈비", "Pizza Hut", "하남돼지 집","역전 할머니 맥주" ,
            "bhc","신촌 황소 부추 곱창","춘천 닭갈비","소문난 성수 감자탕","호랑이 초밥","고래 사랑 횟집", "달콤함 삼겹살", "MEGA Coffee"
            ,"맘스터치" ,"왕십리 곱창집", "신당동 떡볶이" ,"바운스 카페" ,"Twosome Place" ,"compose coffee", "성수 대취"
    };

    // 랜덤으로 지출 금액을 생성하는 메서드 (최소 30,000원에서 100,000원 사이)
    public static Long getRandomAmount() {
        Random random = new Random();
        int randomInt = random.nextInt(201);
        return (long) 30000 + (randomInt * 100);
    }

    // 랜덤으로 음식점 이름을 선택하는 메서드
    public static String getRandomTransTo() {
        Random random = new Random();
        // restaurantNames 배열에서 랜덤하게 선택
        return restaurantNames[random.nextInt(restaurantNames.length)];
    }

}
