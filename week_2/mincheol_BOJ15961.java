import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String[] str = br.readLine().split(" ");
            int n = Integer.parseInt(str[0]);
            int d = Integer.parseInt(str[1]);
            int k = Integer.parseInt(str[2]);
            int c = Integer.parseInt(str[3]);

            ArrayList<Integer> sushi = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int num = Integer.parseInt(br.readLine());
                sushi.add(num);
            }
            for (int i = 0; i < k; i++) {
                sushi.add(sushi.get(i));
            }

            int[] cnt = new int[d+1];
            LinkedList<Integer> list = new LinkedList<>();
            int max = 2;
            int size = 0;
            for (int i = 0; i < k; i++) {
                int cur = sushi.get(i);
                if (cnt[cur]++ == 0) {
                    size++;
                }
                list.offer(cur);
            }
            for (int i = k; i < n + k; i++) {
                int cur = sushi.get(i);
                int older = list.poll();
                if (--cnt[older] == 0) {
                    size--;
                }
                if (cnt[cur]++ == 0) {
                    size++;
                }
                list.offer(cur);
                if (cnt[c] == 0) {
                    max = Math.max(max, size+1);
                } else {
                    max = Math.max(max, size);
                }
            }
            System.out.println(max);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
