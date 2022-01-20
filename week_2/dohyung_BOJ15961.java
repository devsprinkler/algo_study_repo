import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    // 여러 종류 초밥
    // 종류를 번호로 표현

    // 같은 종류의 초밥이 2이상 있을 수 있다.

    // 2가지 행사
    // 1. 원래는 먹음만큼 계산. 임의의 한 위치부터 k개의 접시를 연속해서 먹을 경우 할인된 정액 가격으로 제공
    // 2. 각 고객에게 초밥의 종류 하나가 쓰인 쿠폰 발행
    // -> 1번 행사에 참가할 경우 이 쿠폰에 적혀진 종류의 초밥 하나를 추가로 무료 제공
    // -> 벨트위에 없을 경우 요리사가 새로 만들어 제공

    // 행사에 참여하여 가능한 한 다양한 종류의 초밥
    // k = 4, 30 쿠폰
    // 손님이 먹을 수 있는 초밥 가짓수의 최댓값

    // N: 벨트에 접시의 수, 최대 3백만
    // d: 초밥의 가짓수, 최대 3천
    // k: 연속 접시수, 최대 3천
    // c: 쿠폰 번호, 최대 3천

    // idx 원형으로 할 수 있게
    // 포인터 2개로 k개 포함 되도록
    // -> k개를 유지하면서 슬라이드. Map 이용 -> map의 size가 갯수 <종류, 갯수>. 개수가 0이되면 삭제
    // -> c 포함 여부 확인? c가 여러개 있을 수도. 추가할때 k를 확인?

    static int N, d, k, c;
    static HashMap<Integer, Integer> map = new HashMap<>();
    static int[] belt;
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        belt = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            belt[i] = Integer.parseInt(st.nextToken());
        }
        
        map.put(c, 1); // coupon

        getMax();

        System.out.println(answer);
    }

    static void getMax() {
        int f, b;

        f = 0;
        b = k - 1;
        for (int i = f; i <= b; i++) {
            map.put(belt[i], map.getOrDefault(belt[i], 0) + 1);
        }

        answer = Math.max(answer, map.size());

        // K == N인 경우도 있음
        if (k == N) return;
        do {
            map.put(belt[f], map.get(belt[f]) - 1);
            if (map.get(belt[f]) == 0) map.remove(belt[f]);
            f = getIdx(f + 1);

            b = getIdx(b + 1);
            map.put(belt[b], map.getOrDefault(belt[b], 0) + 1);

            answer = Math.max(answer, map.size());

        }
        while (f != N - 1);
    }

    static int getIdx(int idx) {
        return (idx + N) % N;
    }
}
