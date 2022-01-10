import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static int[] weightedUnion(int a, int b, int[] p) {
        if (p[a] < p[b]) {
            p[a] += p[b];
            p[b] = a;
        } else if (a != b) {
            p[b] += p[a];
            p[a] = b;
        }
        return p;
    }

    public static int collapsingFind(int a, int[] p) {
        if (p[a] < 0) {
            return a;
        } else {
            return p[a] = collapsingFind(p[a], p);
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String[] str = br.readLine().split(" ");
            int n = Integer.parseInt(str[0]);
            int m = Integer.parseInt(str[1]);
            int[] p = new int[n+1];
            Arrays.fill(p, -1);
            StringBuilder sb = new StringBuilder();
            while(m-- > 0) {
                str = br.readLine().split(" ");
                int op = Integer.parseInt(str[0]);
                int a = Integer.parseInt(str[1]);
                int b = Integer.parseInt(str[2]);
                if (op == 0) {
                    p = weightedUnion(collapsingFind(a, p),
                            collapsingFind(b, p), p);
                } else {
                    if (collapsingFind(a, p) == collapsingFind(b, p)) {
                        sb.append("YES").append("\n");
                    } else {
                        sb.append("NO").append("\n");
                    }
                }
            }
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}