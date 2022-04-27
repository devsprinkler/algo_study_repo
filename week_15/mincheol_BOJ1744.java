import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        LinkedList<Integer> pos = new LinkedList<>();
        LinkedList<Integer> nag = new LinkedList<>();
        int ans = 0;
        boolean isZero = false;
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());
            if (num == 1) ans++;
            else {
                if (num > 0) pos.offer(num);
                else if (num < 0) nag.offer(num);
                else if (num == 0) isZero = true;
            }
        }

        pos.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });
        while(pos.size() > 1) {
            ans += pos.poll() * pos.poll();
        }
        if (!pos.isEmpty()) ans += pos.poll();

        nag.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });
        while(nag.size() > 1) {
            ans += nag.poll() * nag.poll();
        }
        if (!nag.isEmpty() && !isZero) ans += nag.poll();

        System.out.println(ans);
    }
}

/*  
 *  1. 양수는 큰 수부터 묶기
 *  2. 음수가 짝수개면 작은 수부터 묶기
 *  3. 음수가 홀수개면 한개 남을 때 까지 작은 수부터 묶기, 하나남은 음수는 묶지 않기
 *  4. 0, 1은 묶지 않기
 */ 