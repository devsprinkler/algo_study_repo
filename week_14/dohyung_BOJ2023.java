import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        StringBuilder num = new StringBuilder();

        backTrack(num, sb, 0);

        System.out.println(sb);
    }

    //
    static void backTrack(StringBuilder num, StringBuilder sb, int cnt) {
        if (cnt == N) {
            sb.append(num).append("\n");
        }
        else {
            for (int i = 1; i <= 9; i++) {
                num.append(i);
                if (prime(Integer.parseInt(num.toString())))
                    backTrack(num, sb, cnt + 1);
                num.deleteCharAt(num.length() -1);
            }
        }
    }

    static boolean prime(int n) {
        int end = (int) Math.sqrt(n);

        if (n == 1)
            return false;

        for (int i = 2; i <= end; i++) {
            if (n % i == 0) return false;
        }

        return true;
    }
}
