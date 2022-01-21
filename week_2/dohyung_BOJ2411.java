import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    // N x M

    // 왼쪽아래 출발, 오른쪽 위로 가려함
    // 중간에 모든 아이템을 먹으려함 (아이템: 별)
    // 오른쪽, 위쪽으로만 이동가능
    // 장애물 지날 수 없다

    // 이동하는 경로의 개수가 총 몇개인지

    // N, M 1 ~ 100
    // A 아이템, B 장애물 개수
    // 아이템 위치,
    // 장애물 위치

    // 최대 경로 갯수 100 * 100
    // 왼쪽아래가 (1, 1)  : (N, 1)
    // 오른쪽 위가 (N, M) : (1, M)
    // 행
    // 1 -> N
    // 2 -> N - 1
    // ...
    // 4 -> 2
    // 5 -> 1
    // N - (i - 1) = NewN(문제의 좌표)
    // i = N - newN + 1

    // 열 별로 최대 높이 지정
    // 1, 3, 3 , 3, 4, 4, 5, 5

    // 별에서 별 사각형 단위로 구함
    // dp 활용해서 dfs사용(dfs이기 때문에 부분 dp를 사용가능)

    // 위, 오른
//    final static int[] dy = {-1, 0};
//    final static int[] dx = {0, 1};
    // 왼, 아래
    final static int[] dy = {0, 1};
    final static int[] dx = {-1, 0};
    final static int item = 1;
    final static int block = -1;

    static int N, M, A, B;
    static int[][] map;
    //    static int[] maxHeight;
    static Queue<Path> q = new LinkedList<>();
    static ArrayList<Pair> items = new ArrayList<>();
    static int answer = 0;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        map = new int[N + 1][M + 1];
        dp = new int[N + 1][M + 1];

        for (int i = 0; i < A; i++) {
            st = new StringTokenizer(br.readLine());

            int y, x;
            y = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());

            int mY = N - y + 1;

            map[mY][x] = item;
            // 정렬 -> x 작을수록 먼저, y 클수록 먼저
            items.add(new Pair(mY, x));
        }

        Collections.sort(items, (o1, o2) -> {
            if (o1.x == o2.x) {
                return o2.y - o1.y;
            }
            else {
                return o1.x - o2.x;
            }
        });

        for (int i = 0; i < B; i++) {
            st = new StringTokenizer(br.readLine());

            int y, x;
            y = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());

            int mY = N - y + 1;

            map[mY][x] = block;
        }
        int targetY, targetX;
        int idx = 0;

        if (items.size() == 0) {
            targetY = 1;
            targetX = M;
        }
        else {
            targetY = items.get(idx).y;
            targetX = items.get(idx).x;
            idx++;
        }
        findPath(N, 1, targetY, targetX);


        answer += dp[targetY][targetX];

        while (targetX != M || targetY != 1) {
            // 중간에 item 있음
            if (items.size() > idx && targetY < items.get(idx).y) {
                answer = 0;
                break;
            }
            if (items.size() > idx) {
                findPath(targetY, targetX, items.get(idx).y, items.get(idx).x);
                targetY = items.get(idx).y;
                targetX = items.get(idx).x;
                idx++;
            }
            else {
                findPath(targetY, targetX, 1, M);
                answer *= dp[1][M];
                break;
            }

            answer *= dp[targetY][targetX];
        }

        System.out.println(answer);
    }

        static void findPath(int sy, int sx, int destY, int destX) {
            dp[sy][sx] = 1;
            for (int i = sy; i >= destY; i--) {
                for (int j = sx; j <= destX; j++) {
                    if (i == sy && j == sx) continue;
                    int sum = 0;
                    for (int k = 0; k < 2; k++) {
                        int py = i + dy[k];
                        int px = j + dx[k];
                        if (py <= sy && py >= destY && px >= sx && px <= destX) {
                            if (map[py][px] != block) {
                                sum += dp[py][px];
                            }
                        }
                    }
                    dp[i][j] = sum;
                }
            }
        }


    static class Path {
        int y, x;

        public Path(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
    static class Pair {
        int y,x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
