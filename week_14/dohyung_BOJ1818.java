import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 가장 긴 증가하는 부분 수열을 구함
    // 수열에 포함되지 않는 수는 감소하는 숫자 -> 무조건 옮겨야 함

    static int N;
    static int[] books;
    static ArrayList<Integer> lis = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        books = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            books[i] = Integer.parseInt(st.nextToken());

            findLis(books[i]);
        }

        System.out.println(N - lis.size());
    }

    static void findLis(int book) {
        if (lis.isEmpty() || book > lis.get(lis.size() - 1)) {
            lis.add(book);
        }
        else {
            int idx = lowerBound(book);
            lis.set(idx, book);
        }
    }

    // 타겟과 같거나 큰 첫 위치 반환
    static int lowerBound(int num) {
        int left = 0;
        int right = lis.size();

        while (left < right) {
            int mid = (left + right) / 2;

            if (lis.get(mid) >= num) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return right;
    }
}
