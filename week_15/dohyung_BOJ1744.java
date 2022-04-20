import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {


    // 수열의 2개의 수를 묶으려 함
    // 위치에 상관없이 묶을 수 있다. 자기 자신은 불가능

    // 어떤 수를 묶게 되면, 묶은 수는 서로 곱한 후에 더한다.
    // 수열의 모든 수는 단 1번만 묶거나, 묶지 않아야 한다.
    // 수열의 각 수를 적절히 묶었을 때, 그 합이 최대가 되게

    // 수열의 크기 N 최대 49
    // 수열의 수는 -1000 이상 1000 이하
    // 답은 int 범위

    // 브루트 포스 -> 49C2 * 47C2 * ...

    // 우선 정렬 -> -는 -끼리 최대한 곱해야 이득.
    // 최대한 절대값이 큰 것끼리 곱해야
    // 그리디 문제인가?
    
    // 0이 있는 경우 -> 음수는 곱하는게 이득
    // 모두 같은 수 (모두 0)
    // 1이 있는 경우 -> 따로 더하는게 이득
    // 숫자가 1개인 경우

    static int N;
    static ArrayList<Integer> arr = new ArrayList<>();
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            arr.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(arr);

        // 음수 처리
        for (int i = 0; i < arr.size();) {
            if (i + 1 >= arr.size()) break;
            
            if (arr.get(i) < 0 && arr.get(i + 1) <= 0) {
                answer += arr.get(i) * arr.get(i + 1);
                arr.remove(i);
                arr.remove(i); // i + 1
            }
            else {
                break;
            }
        }

        // 양수 처리
        for (int i = arr.size() - 1; i >= 0; i -= 2){
            if (i - 1 < 0) break;

            if (arr.get(i) > 1 && arr.get(i - 1) > 1) {
                answer += arr.get(i - 1) * arr.get(i);
                arr.remove(i - 1);
                arr.remove(i - 1); // i
            }
            else
                break;
        }

        for (int i = 0; i < arr.size(); i++) {
            answer += arr.get(i);
        }

        System.out.println(answer);
    }
}
