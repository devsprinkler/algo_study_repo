import java.util.*;

public class Solution {

    // 최대 2억명 가능 2^32
    // 이분 탐색 -> 최대 32

    // 32 * 20만 = 640만
    // N 타임에 확인 -> 지정한 값 이하인 구간이 K개 이상인 구간이 있는지
    // -> 가능한 것 중 최소 숫자를 구하면 됨

    public static int solution(int[] stones, int k) {
        int answer;

        answer = findUpper(stones, 200000000, k) - 1;
        System.out.println(answer);

        return answer;
    }

    // 가능한 것 중 최대 숫자 + 1
    static int findUpper (int[] stones, int bound, int k) {
        int left = 1; // -1?
        int right = bound + 1; // 2억
        
        while (left < right) {
            int mid = (left + right) / 2;

            if (checkJump(stones, mid, k)) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }

        return left;
    }

    // 건널 수 있으면 true
    static boolean checkJump(int[] stones, int x, int k) {
        int count = 0;

        for (int i = 0; i < stones.length; i++) {
            if (count >= k) return false;
            if (stones[i] < x) {
                count++;
            }
            else {
                count = 0;
            }
        }
        if (count >= k) return false; // 끝부분에 빈 공간 생길때의 예외

        return true;
    }
}
