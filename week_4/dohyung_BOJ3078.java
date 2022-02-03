import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    // 모든 학생은 자신과 반 등수의 차이가 K를 넘으면 친구가 X
    // 좋은 친구: 이름의 길이가 같아야한다.
    
    // N명의 학생 이름이 성적순으로 주어짐.
    // 좋은 친구가 몇쌍인지? 등수 차이 K이하 이면서 이름의 길이가 같은 친구

    // N 최대 30만
    // 이름 최대 20글자

    // k = 2
    // iva 1
    // ivo 2
    // ana 3
    // tom 4
    // 3글자만 30만개, k=30만 -> 900억
    // M + N or M logN or M
    // 산술연산으로 ? 이진 탐색?
    // 이진탐색-> 등수 + k와 같거나 작은 idx 찾아서 idx로 갯수 계산

    // k = 3
    // cynthia(7) 1
    // malcolm(7) 5
    // lloyd(5) 2
    // kevin(5) 4
    // stevie(6) 3
    // dabney(6) 6
    // 글자수 별로 따로 관리?
    // -> 30만개 관리?
    // 각자 k이하 차이 인지 어떻게 계산?
    // 길이 별로 HashMap 저장 <Integer, ArrayList>

    static int N, K;
    static HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
    static long answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();

            int len = str.length();

            if (map.containsKey(len)) {
                map.get(len).add(i);
            }
            else {
                map.put(len, new ArrayList<Integer>());
                map.get(len).add(i);
            }
        }

        List<Integer> keys = new ArrayList<>(map.keySet());

        for (int i = 0; i < keys.size(); i++) {
            int key = keys.get(i);

            calcFellas(key);
        }

        System.out.println(answer);
    }

    static void calcFellas(int key) {
        ArrayList<Integer> arr = map.get(key);

        for (int i = 0; i < arr.size() - 1; i++) {
            int target = arr.get(i) + K;

            int bound = upperBound(i, arr, target);
            int sum = bound - 1 - i;
            if (sum > 0)
                answer += sum;
        }
    }

    // target 보다 처음으로 큰 값이 나오는 idx 를 return
    static int upperBound(int left, ArrayList<Integer> arr, int target) {
        int right = arr.size(); // upperBound(size() - 1)인 경우 size()를 리턴

        while (left < right) {
            int mid = (left + right) / 2;

            if (arr.get(mid) > target) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return right;
    }
}
