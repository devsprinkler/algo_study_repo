import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // N개의 피자 반죽
    // 지름이 제각각

    // 피자 반죽은 완성되는 순서대로 오븐에 들어간다.
    // N개의 피자가 오븐에 모두 들어가고 나면,
    // 맨 위의 피자(마지막으로 들어간 피자)가 얼마나 깊이 들어가 있는지
    // 깊이 최대 30만, 반죽 최대 30만개 -> 30 * 30만 = 900억 N^2 X
    // N log N -> 18 * 30만 540만

    // 10억: 1,000,000,000 -> int 범위

    // 오븐 지름, 피자 반죽 지름 10억이하 자연수

    // 오븐    : 5 6 4 3 6 2 3
    // 최대지름 : 5 5 4 3 3 2 2 (이전값과 현재값중 최소) 이진탐색?

    static int D, N;
    static int answer = 0;
    static int[] oven;
    static int[] pizza;
    static int[] maxDiameter;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        D = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        oven = new int[D + 1];
        maxDiameter = new int[D + 1];
        pizza = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= D; i++) {
            oven[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            pizza[i] = Integer.parseInt(st.nextToken());
        }

        getMaxDiameter();
        int left = 1, right = D;
        for (int i = 0; i < N; i++) {
            int idx = binarySearch(left, right, pizza[i]);
            if (idx == -1) {
                answer = 0;
                break;
            }
            else {
                maxDiameter[idx] = 0;
                answer = idx;
                right = idx - 1;
            }
        }

        System.out.println(answer);
    }

    static void getMaxDiameter() {
        maxDiameter[1] = oven[1];
        for (int i = 2; i <= D; i++) {
            maxDiameter[i] = Math.min(maxDiameter[i - 1], oven[i]);
        }
    }

    // right 범위는 마지막 반죽을 놓은 곳 - 1
    static int binarySearch(int left, int right, int target) {
        int ret = -1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (target <= maxDiameter[mid]) {
                ret = mid;
                left = mid + 1;
            }
            else {
                // target > maxDiameter[mid]
                right = mid - 1;
            }
        }

        return ret;
    }
}
