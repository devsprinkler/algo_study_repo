import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 역에 소형 기관차 3대 배치
    // 적은 수의 객차만을 끌 수 있다.

    // 1. 소형의 최대 객차 수를 미리 3개다 같이 정해서 그보다 많을 수를 끌게 하지 않는다
    // 2. 최대한 많은 손님. 타고있는 손님의 수는 미리 알고 있음. 다른 객차로 손님 이동 x
    // 3. 각 소형 기관차는 번호가 연속적으로 이어진 객차를 끌게 한다.

    // 객차의 수 5만이하
    // 손님의 수. 한 객차당 100명 이하 -> 최대 500만명 int
    // 최대로 끌 수 있는 객차의 수가 입력. 전체의 1/3 보다 적다.

    // 연속된 집합 3개를 구해야 함
    // 2차원 배열을 만들어 구간 합을 저장 -> 객차 수 5만개

    // 누적합
    // -> sum[i] = i 번째까지 누적합
    // dp[i][j] // i번째 소형 기관차가 객차 j번째가지 보았을 때, 최대로 운송할 수 있는 승객 수

    static int N, M;
    static int[] customers;
    static int[] sum;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        customers = new int[N + 1];
        sum = new int[N + 1];
        dp = new int[3 + 1][N + 1];
        sum[0] = 0;

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            customers[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());

        // 누적합
        for (int i = 1; i < sum.length; i++) {
            sum[i] += sum[i - 1] + customers[i];
        }

        // dp
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= N; j++) {
                if (j - M < 0) continue;

                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j - M] + (sum[j] - sum[j - M]));
            }
        }

        System.out.println(dp[3][N]);
    }
}
