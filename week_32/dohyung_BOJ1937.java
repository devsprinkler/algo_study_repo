import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // n x n 대나무 숲
    
    // 어떤 지역에서 대나무 먹기 시작
    // 다 먹으면 상,하,좌,우 중 한 곳으로 이동하여 다시 먹음
    // -> 옮기는 지역에 그 전 지역보다 대나무가 많아야함
    
    // 어떤 지점에서 시작하고, 어떤 곳으로 이동해야 최대한 많은 칸 방문?
    
    // n 최대 500 n x n -> 25만칸
    // 각 지역 대나무 양 정수 값
    // -> 100만이하 자연수 -> 합산할 때 long 사용

    // dp[i][j] = [i][j] 에서 갈 수 있는 최대 칸 수 
    // 방문한 적 있으면 visit true, -> dp[i][j] 반환
    // 없으면 visit false->true, -> 재귀적으로 dp[i][j] 구함
    // 아무데도 갈데가 없으면 -> 바닥 : +1칸. 초기화

    // 전체 칸에 대해서 dp 값 탐색
    // max 값 저장
    
    // 값이 큰 쪽으로만 움직이기 때문에 되돌아가지 않을 것을 암
    
    // 위, 아래, 왼, 오른
    static final int[] dy = {-1, 1, 0, 0};
    static final int[] dx = {0, 0, -1, 1};
    static int N;
    static int[][] dp;
    static boolean[][] visit;
    static long[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        dp = new int[N][N];
        map = new long[N][N];
        visit = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Long.parseLong(st.nextToken());
            }
        }

        solution();
    }

    static void solution() {
        int answer = 0;

        // 전체 칸에 대해 dp 값 탐색
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visit[i][j])
                    dp[i][j] = recursiveCount(i, j);
                answer = Math.max(answer, dp[i][j]);
            }
        }

        System.out.println(answer);
    }

    static int recursiveCount(int i, int j) {
        // 방문 처리
        visit[i][j] = true;

        int ny, nx;
        int localMax = 0;

        for (int k = 0; k < 4; k++) {
            ny = i + dy[k];
            nx = j + dx[k];
            
            // 범위검사, visit 검사, 대소 비교
            if (ny >= 0 && ny < N && nx >= 0 && nx < N) {
                if (!visit[ny][nx]) {
                    if (map[ny][nx] > map[i][j]) {
                        localMax = Math.max(localMax, recursiveCount(ny, nx) + 1);
                    }
                }
                else {
                    if (map[ny][nx] > map[i][j])
                        localMax = Math.max(localMax, dp[ny][nx] + 1);
                }
            }
        }

        if (localMax == 0)
            return dp[i][j] = 1; // 아무데도 갈수 없음. 자기 자신 1칸
        else
            return dp[i][j] = localMax;
    }
}
