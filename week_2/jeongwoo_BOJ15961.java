import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ15961 {
    static int N, D, K, C;
    static int[] sushi;

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            D = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            sushi = new int[N];

            for (int i = 0; i < N; i++) {
                sushi[i] = Integer.parseInt(br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int getNext(int idx) {
        return (idx + 1) % N;
    }

    static void solve() {
        int start = 0;
        int end = K - 1;
        int count = 0;
        int answer = 0;

        int[] eat = new int[D + 1];

        for (int i = 0; i < K; i++) {
            if (eat[sushi[i]] == 0) {
                count += 1;
            }
            eat[sushi[i]] += 1;
        }

        answer = count;
        for (int i = 0; i < N; i++) {
            eat[sushi[start]] -= 1;
            if (eat[sushi[start]] == 0)
                count -= 1;

            start += 1;
            end = getNext(end);

            if (eat[sushi[end]] == 0) {
                count += 1;
            }

            eat[sushi[end]] += 1;

            answer = Math.max(answer, count + (eat[C] == 0 ? 1 : 0));
        }

        System.out.println(answer);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
