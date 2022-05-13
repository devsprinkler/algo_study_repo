import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        int n = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);
        int max = 0;

        long[] table = new long[n];
        for (int i = 0; i < n; i++) {
            table[i] = Long.parseLong(br.readLine(), 2);
        }
        int k = Integer.parseInt(br.readLine());
        HashMap<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (map.containsKey(table[i])) {
                map.put(table[i], map.get(table[i]) + 1);
            } else {
                map.put(table[i], 1);
            }
        }

        for (long key : map.keySet()) {
            int bits = m - Long.bitCount(key);
            if (bits <= k && bits % 2 == k % 2) {
                max = Math.max(max, map.get(key));
            }
        }

        System.out.println(max);
    }
}
