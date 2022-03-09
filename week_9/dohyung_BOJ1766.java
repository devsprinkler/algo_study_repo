import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    // 문제 풀 순서
    // 1. N개의 문제는 모두 풀어야 한다.
    // 2. 먼저 푸는 것이 좋은 문제가 있는 문제는, 먼저 푸는 것이 좋은 문제를 반드시 먼저 풀어야 한다.
    // 3. 가능하면 쉬운 문제부터 (숫자가 작은 문제)

    // 문제 N개 최대 3만2천
    // 먼저 푸는 것이 좋은 문제에 대한 정보의 개수 M 최대 10만개
    // A B : A를 B보다 먼저 푸는 것이 좋다.

    // topological?
    // 어떤 문제를 기준으로 in 이 모두 해결돼야 out 또는 다른 문제 가능
    // N * logN * N (X) + N
    // 정렬 대신 맞는 위치를 직접 넣어줘야 함

    static int N, M;
    static int[] in;
    static ArrayList<Integer> candid = new ArrayList<>();
    static ArrayList<Integer>[] out;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        in = new int[N + 1];
        out = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            out[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int from, to;

            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());

            in[to]++;
            out[from].add(to);
        }

        // 0인 것 체크해서 분리
        for (int i = 1; i <= N; i++) {
            if (in[i] == 0)
                candid.add(i);
        }

        // N 개 출력
        for (int i = 0; i < N; i++) {
            int num = candid.get(0);
            candid.remove(0);

            System.out.print(num + " ");

            // out 들의 in 숫자 감소
            for (int j = 0; j < out[num].size(); j++) {
                int t = out[num].get(j);

                in[t] -= 1;
                if (in[t] == 0) {
                    // 정렬 자리에 넣어줌
                    int idx = findUpper(t);
                    candid.add(idx, t);
                }
            }
        }
    }

    static int findUpper(int target) {
        int left = 0;
        int right = candid.size(); // 1 크게

        while (left < right) {
            int mid = (left + right) / 2;

            if (candid.get(mid) >= target) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return left;
    }
}
