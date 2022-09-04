import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static char[][] map;
    static int answer = 0;

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

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

    static Pair getNextPos(Pair p, char dir) {
        switch (dir) {
            case 'U':
                return new Pair(p.x, p.y - 1);
            case 'D':
                return new Pair(p.x, p.y + 1);
            case 'L':
                return new Pair(p.x - 1, p.y);
            case 'R':
                return new Pair(p.x + 1, p.y);
        }

        return new Pair(0, 0);
    }

    static int dfs(Pair p, int[][] visited, int safety) {
        if (visited[p.y][p.x] != 0) {
            return visited[p.y][p.x];
        }

        visited[p.y][p.x] = safety;

        return visited[p.y][p.x] = dfs(getNextPos(p, map[p.y][p.x]), visited, safety);
    }

    static void solve() {
        int[][] visited = new int[N][M];
        int answer = 1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int cur = dfs(new Pair(j, i), visited, answer);

                if (cur == answer)
                    answer += 1;
            }
        }

        System.out.println(answer - 1);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
