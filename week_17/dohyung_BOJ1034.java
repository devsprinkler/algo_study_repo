import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 각 칸마다 램프 직사각형 탁자
    // 각 열의 아래에 스위치가 하나씩
    // -> 이 스위치를 누를 때마다 그 열에 있는 램프의 상태가 바뀐다.
    // -> 꺼>켜 켜>꺼

    // 어떤 행에 램프가 모두 켜져있을 때, 그 행이 켜져있다
    // 스위치 K번, 서로다른 스위치 누르지 않아도 됨
    // 켜져있는 행을 최대로 하려고 한다.

    // 행, 열 최대 50이하
    // 최대 1000번 스위치 누름
    // -> 0번 할수도
    
    // 짝수번 누르면 똑같은 상태임
    // 홀수번은 고르는 경우의 수
    // k가 0번 누르면?
    // 브루트 포스 : 각 열이 누르고/안누를 경우 2^50
    // 조합 : 50C1, 50C2 ...
    // k가 열의 수보다 같거나 크면 의미 없음
    // k가 열의 수보다 작으면 k이하 개만 누를 수 있음
    
    // k가 짝수이고 열의 갯수가 홀수 이면
    // -> 짝 + 짝 + 짝 = 짝
    // -> 짝 + 짝 + 홀 = 홀
    // -> 짝 + 홀 + 홀 = 짝 
    // -> 홀수가 짝수개 있어야 함
    
    // k가 짝수이고 열의 갯수가 짝수 이면
    // -> 짝 + 짝 = 짝
    // -> 홀 + 홀 = 짝
    // -> 홀 + 홀 + 짝 + 짝 = 짝 
    // -> 홀수가 짝수개 있어야 함
    
    // k가 홀수이고 열의 갯수가 홀수 이면
    // -> 홀
    // -> 홀 + 짝 + 짝 = 홀
    // 홀의 갯수가 홀수개
    
    // k가 홀수, 열 짝수
    // 홀의 갯수가 홀수개
    
    // k가 짝수이면 홀이 짝수개
    // k가 홀수이면 홀이 홀수개

    // 뒤집는 것이 1이 더 많으면 뒤집음?

    // 1. 꺼진 갯수가 k이하
    // 2. k짝이면 꺼진 갯수도 짝/ k홀이면 꺼진 갯수도 홀
    // 조건을 만족하는 행이면 같이 켜지는 다른 행 찾음 -> 꺼진 곳이 모두 포함되는 곳
    
    static int N, M;
    static int K;
    static int[][] lamps;
    static ArrayList<Integer>[] black;
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        lamps = new int[N][M];
        black = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            black[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            for (int j = 0; j < M; j++) {
                lamps[i][j] = s.charAt(j) - '0';
                if (lamps[i][j] == 0)
                    black[i].add(j);
            }
        }

        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            if (black[i].size() <= K && black[i].size() % 2 == K % 2) {
                int count = 1;

                for (int j = 0; j < N; j++) {
                    if (i == j) continue;

                    if(isLightOn(black[i], black[j]))
                        count++;
                }

                answer = Math.max(answer, count);
            }
        }

        System.out.println(answer);
    }

    static boolean isLightOn(ArrayList<Integer> origin, ArrayList<Integer> target) {
        if (target.size() != origin.size())
            return false;

        for (int i = 0; i < origin.size(); i++) {
            if (origin.get(i) != target.get(i))
                return false;
        }
        return true;
    }
}
