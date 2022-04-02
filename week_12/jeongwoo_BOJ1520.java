import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] map;
    static PriorityQueue<Cell> pq;

    static class Cell implements Comparable<Cell> {
        int x, y, h;

        Cell(int x, int y, int h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }

        @Override
        public int compareTo(Cell o) {
            return o.h - this.h;
        }
    }

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new int[N][M];
            pq = new PriorityQueue<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    pq.offer(new Cell(j, i, map[i][j]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void solve() {
        int[][] path = new int[N][M];

        path[0][0] = 1;

        while (!pq.isEmpty()) {
            Cell cur = pq.poll();
            int x = cur.x;
            int y = cur.y;
            int h = cur.h;

            if (path[y][x] == 0) {
                continue;
            }

            if (x - 1 >= 0 && h > map[y][x - 1]) {
                path[y][x - 1] += path[y][x];
            }

            if (x + 1 < M && h > map[y][x + 1]) {
                path[y][x + 1] += path[y][x];
            }

            if (y - 1 >= 0 && h > map[y - 1][x]) {
                path[y - 1][x] += path[y][x];
            }

            if (y + 1 < N && h > map[y + 1][x]) {
                path[y + 1][x] += path[y][x];
            }
        }

        System.out.println(path[N - 1][M - 1]);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
