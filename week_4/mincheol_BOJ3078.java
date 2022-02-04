package algorithm.BOJ.P3078;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String str = br.readLine();
            int n = Integer.parseInt(str.split(" ")[0]);
            int k = Integer.parseInt(str.split(" ")[1]);

            int[] length = new int[n];
            int[][] cumsum = new int[21][n];
            str = br.readLine();
            int l = str.length();
            length[0] = l;
            cumsum[l][0] = 1;
            for (int i = 1; i < n; i++) {
                str = br.readLine();
                l = str.length();
                for (int j = 1; j <= 20; j++) {
                    cumsum[j][i] = cumsum[j][i-1];
                }
                length[i] = l;
                cumsum[l][i] = cumsum[l][i-1] + 1;
            }

            long sum = 0;
            for (int i = 0; i < n-1; i++) {
                l = length[i];
                if (i+k < n) {
                    sum += cumsum[l][i+k] - cumsum[l][i];
                } else {
                    sum += cumsum[l][n-1] - cumsum[l][i];
                }
            }
            System.out.println(sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
