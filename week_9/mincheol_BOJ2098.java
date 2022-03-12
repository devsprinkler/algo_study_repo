import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[][] w, d;
    static final int max = 16000000;

    static int find(int node, int path){
        if(path == (1 << n) - 1) {
            if(w[node][0] == 0) {
                return max;
            }
            return w[node][0];
        }

        if(d[node][path] != max) {
            return d[node][path];
        }

        for(int i = 0 ; i < n; i++) {
            int next = path | (1 << i);
            if(w[node][i] == 0 || (path & (1 << i)) != 0) continue;
            d[node][path] = Math.min(d[node][path], find(i, next) + w[node][i]);
        }

        return d[node][path];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        w = new int[n][n];
        d = new int[n][(1 << n) - 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], max);
        }

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                w[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(find(0, 1));
    }
}
