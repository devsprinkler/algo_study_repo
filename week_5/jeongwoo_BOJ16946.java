import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static char[][] map;
    static int[][] count;
    static int[][] answer;
    static int[][] number;

    final static int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

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
            count = new int[N][M];
            answer = new int[N][M];
            number = new int[N][M];

            for (int i = 0; i < N; i++) {
                map[i] = br.readLine().toCharArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean canMove(int x, int y) {
        return 0 <= x && x < M && 0 <= y && y < N && map[y][x] == '0';
    }

    static void bfs(int x, int y, int num) {
        List<Pair> list = new ArrayList<>();
        list.add(new Pair(x, y));
        count[y][x] = 1;

        int size = 1;
        int index = 0;

        while (index < size) {
            Pair cur = list.get(index);

            for (int i = 0; i < 4; i++) {
                int nX = cur.x + dir[i][0];
                int nY = cur.y + dir[i][1];

                if (canMove(nX, nY) && count[nY][nX] == 0) {
                    size++;
                    list.add(new Pair(nX, nY));
                    count[nY][nX] = 1;
                }
            }

            index++;
        }

        for (Pair p : list) {
            number[p.y][p.x] = num;
            count[p.y][p.x] = size;
        }
    }

    static int getMove(int x, int y, int num) {
        int move = 1;
        boolean[] isConnected = new boolean[num + 1];

        for (int i = 0; i < 4; i++) {
            int nX = x + dir[i][0];
            int nY = y + dir[i][1];

            if (canMove(nX, nY) && !isConnected[number[nY][nX]]) {
                move += count[nY][nX];
                isConnected[number[nY][nX]] = true;
            }
        }

        move %= 10;

        return move;
    }

    static void solve() {
        StringBuilder sb = new StringBuilder();
        int num = 1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (canMove(j, i) && count[i][j] == 0) {
                    bfs(j, i, num);
                    num += 1;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '1') {
                    answer[i][j] = getMove(j, i, num);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(answer[i][j]);
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}

