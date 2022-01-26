import java.util.*;

class Solution {
    // 다단계 조직

    // 칫솔을 판매하면, 그 이익이 피라미드를 타고 조금씩 분배
    // 이익의 10%를 자신을 조직에 참여시킨 추천인에게 배분, 90%는 자신이 가짐

    // 모든 판매원은 자신의 이익 90 % + 추천하여가입시킨판매원의 10%들
    // 10% 계산시 원 단위에서 절사
    // -> 10% 계산이 1원 미만인 경우 이득을 분배하지 않고 자신이 모두 가짐

    // enroll : 각 판매원의 이름
    // referral: 각 판매원을 다단계조직에 참여시킨 다른 판매원의 이름
    // seller: 판매량 집계 데이터의 판매원 이름
    // amount: 판매량 집계 데이터의 판매 수량
    // 각 판매원에게 배분된 이익금의 총합을 계산하여(정수), enroll 이름 순서대로 나열

    // 직원 수 최대 1만, 민호의 이름은 없음 민호를 제외한 구성원의 총 수
    // referral 길이 == enroll 길이
    // -> referral[i] = enroll[i] 번째를 조직에 참여시킨 사람의 이름
    // -> 추천 없이 참여 : "-" (민호 바로 밑)
    // -> enroll 이름은 조직 참여 순서에 따름. 즉, 어느 판매원 enroll[i]를 참여 시킨 사람은
    // -> referral[i]는 enroll[j] (j < i) 에 등장했음이 보장
    // --> (추천 시킨 사람은 이미 조직에 있는 사람이라는 보장)

    // seller 길이는 10만이하
    // seller 같은 이름 중복 가능

    // amount 길이 == seller 길이
    // amount 범위는 1이상 100이하

    // 1. 추천 관계 구현
    // -> ArrayList?
    // -> 자신이 추천한 대상 필요 X?, 추천 해준 대상 필요
    // 클래스 구현?
    // HashMap<String, String> 구현 <"key", "parent"> 구현
    // 2. 10% 정수 절사 구현
    // -> parent 없으면 10% 더해주고 남은 것 계산만 하고 실제로 더 하지는 않음

    static HashMap<String, String> parentMap = new HashMap<>();
    static HashMap<String, Integer> result = new HashMap<>();  // 순서 보장 HashMap?

    static public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer;

        for (int i = 0; i < enroll.length; i++) {
            parentMap.put(enroll[i], referral[i]); // key, parent
        }

        for (int i = 0; i < seller.length; i++) {
            addResult(seller[i], amount[i] * 100);
        }

        answer = new int[enroll.length];

        for (int i = 0; i < enroll.length; i++) {
            answer[i] = result.getOrDefault(enroll[i], 0);
        }

        return answer;
    }

    static void addResult(String key, int amount) {
        String parent;

        while (true) {

            int parentResult = (int) (amount * 0.1);
            int left = amount - parentResult;

            result.put(key, result.getOrDefault(key, 0) + left);

            parent = parentMap.get(key);

            if (parentResult <= 0 || parent.equals("-"))
                break;

            key = parent;
            amount = parentResult;
        }

    }

}
