import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ2638 {
    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int N, M;
    static int[][] map;
    static Queue<Pair> cheeses;
    static int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new int[N][M];
            cheeses = new LinkedList<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());

                    if (map[i][j] == 1) {
                        cheeses.offer(new Pair(j, i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void meltCheese(Queue<Pair> q) {
        while (!q.isEmpty()) {
            Pair p = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = p.x + direction[i][0];
                int ny = p.y + direction[i][1];

                if (0 <= nx && nx < M && 0 <= ny && ny < N && map[ny][nx] == 0) {
                    map[ny][nx] = -1;
                    q.offer(new Pair(nx, ny));
                }
            }
        }
    }

    static void solve() {
        Queue<Pair> melted = new LinkedList<>();
        melted.offer(new Pair(0,0));
        meltCheese(melted);

        int answer = 0;

        while (!cheeses.isEmpty()) {
            melted.clear();

            int[][] tempMap = new int[N][M];

            int size = cheeses.size();

            for (int i = 0; i < size; i++) {
                Pair cheese = cheeses.poll();

                int count = 0;

                for (int j = 0; j < 4; j++) {
                    int nx = cheese.x + direction[j][0];
                    int ny = cheese.y + direction[j][1];

                    if (0 <= nx && nx < M && 0 <= ny && ny < N && map[ny][nx] == -1) {
                        count += 1;
                    }
                }

                if (count >= 2) {
                    tempMap[cheese.y][cheese.x] = -1;
                    melted.offer(cheese);
                } else {
                    cheeses.offer(cheese);
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (tempMap[i][j] == -1) {
                        map[i][j] = -1;
                    }
                }
            }

            meltCheese(melted);

            answer += 1;
        }

        System.out.println(answer);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
