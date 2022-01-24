import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ2589 {
    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static char[][] map;
    static int N, M;

    static final int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new char[N][M];
            for (int i = 0; i < N; i++) {
                map[i] = br.readLine().toCharArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean canMove(int x, int y) {
        if (0 <= x && x < M && 0 <= y && y < N && map[y][x] == 'L')
            return true;

        return false;
    }

    static int bfs(int x, int y) {
        int count = 0;
        int[][] time = new int[N][M];

        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(x, y));

        time[y][x] = 1;

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dir[i][0];
                int ny = cur.y + dir[i][1];

                if (canMove(nx, ny) && time[ny][nx] == 0) {
                    time[ny][nx] = time[cur.y][cur.x] + 1;
                    q.offer(new Pair(nx, ny));

                    count = Integer.max(count, time[ny][nx]);
                }
            }
        }

        return count - 1;
    }

    static void solve() {
        int answer = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 'L') {
                    answer = Math.max(answer, bfs(j, i));
                }
            }
        }

        System.out.println(answer);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
