import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Main {
    // 위치 별로 타겟의 몇번째 인덱스를 보는중인지에 따라 반복계산 -> dp
    // dp[타겟 idx][angel, devil][다리 위치]. 그 위치의 가능한 갯수를 저장
    // 재귀로 반환해가면서 값을 저장하도록
    // 다리의 idx별로 devil, angel 골라서
    // -> 갯수가 -1 이면 재귀들어감
    // -> 재귀 끝까지 들어가면 갯수를 반환. 반환한 값을 기존 dp 배열에 더함
    // -> 각배열의 위치에서 가능한 idx들을 모두 반환 받아가면서 더해나감


    // [target idx][angel, devil][다리 idx]
    static int[][][] dp;
    static String target;
    static String devil;
    static String angel;
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        target = st.nextToken();
        st = new StringTokenizer(br.readLine());
        devil = st.nextToken();
        st = new StringTokenizer(br.readLine());
        angel = st.nextToken();

        dp = new int[target.length()][2][devil.length()];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        for (int i = 0; i < devil.length(); i++) {
            if (target.charAt(0) == devil.charAt(i))
                answer += recurCount(1, 1, i);
            if (target.charAt(0) == angel.charAt(i)) // else if X
                answer += recurCount(1, 0, i);
        }

        System.out.println(answer);
    }

    // type 0: devil, 1: angel
    static int recurCount(int tgtIdx, int turn, int colIdx) {
        if (tgtIdx == target.length()) {
            if (colIdx < devil.length())
                return 1;
            // else return 안해도 안더해져서 0
//            else
//                return 0;
        }

        if (dp[tgtIdx][turn][colIdx] != -1) {
            return dp[tgtIdx][turn][colIdx];
        }
        else {
            dp[tgtIdx][turn][colIdx] = 0;
        }

        for (int i = colIdx + 1; i < devil.length(); i++) {
            if (turn == 0 && devil.charAt(i) == target.charAt(tgtIdx)) {
                dp[tgtIdx][turn][colIdx] += recurCount(tgtIdx + 1, (turn + 1) % 2, i);
            }
            else if (turn == 1 && angel.charAt(i) == target.charAt(tgtIdx)) {
                dp[tgtIdx][turn][colIdx] += recurCount(tgtIdx + 1, (turn + 1) % 2, i);
            }
        }

        return dp[tgtIdx][turn][colIdx];
    }
}
