import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static int[][] visited;
    public static int[][] map;
    public static int cnt;
    public static int n, m;


    public static void dfs(int ox, int oy, int x, int y) {
        visited[x][y] = ox*10000+oy;
        cnt++;
        if (x > 0 && visited[x-1][y] == -1 && map[x-1][y] == map[x][y]) {
            dfs(ox, oy, x-1, y);
        }
        if (x < n - 1 && visited[x+1][y] == -1 && map[x+1][y] == map[x][y]) {
            dfs(ox, oy, x+1, y);
        }
        if (y > 0 && visited[x][y-1] == -1 && map[x][y-1] == map[x][y]) {
            dfs(ox, oy, x, y-1);
        }
        if (y < m - 1 && visited[x][y+1] == -1 && map[x][y+1] == map[x][y]) {
            dfs(ox, oy, x, y+1);
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String[] str = br.readLine().split(" ");
            n = Integer.parseInt(str[0]);
            m = Integer.parseInt(str[1]);
            int k = Integer.parseInt(str[2]);
            map = new int[n][m];
            visited = new int[n][m];
            int[][] score = new int[n][m];
            for (int i = 0 ; i < n; i++) {
                str = br.readLine().split(" ");
                for (int j = 0; j < m; j++) {
                    map[i][j] = Integer.parseInt(str[j]);
                }
            }

            for (int i = 0; i < n; i++) {
                Arrays.fill(visited[i], -1);
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    cnt = 0;
                    if (visited[i][j] != -1) {
                        int x = visited[i][j] / 10000;
                        int y = visited[i][j] % 10000;
                        score[i][j] = score[x][y];
                    } else {
                        dfs(i, j, i, j);
                        score[i][j] = cnt * map[i][j];
                    }
                }
            }

            int[] pos = {0, 0};
            int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int curDir = 0;
            int sumScore = 0;
            int bottom = 6; int top = 1; int front = 2; int back = 5;
            int right = 3; int left = 4;
            for (int i = 0; i < k; i++) {
                if (pos[0] + dir[curDir][0] < 0
                        || pos[0] + dir[curDir][0] > n - 1) {
                    if (curDir == 1) curDir = 3;
                    else curDir = 1;
                } else if (pos[1] + dir[curDir][1] < 0
                        || pos[1] + dir[curDir][1] > m - 1) {
                    if (curDir == 0) curDir = 2;
                    else curDir = 0;
                }
                pos[0] += dir[curDir][0];
                pos[1] += dir[curDir][1];
                sumScore += score[pos[0]][pos[1]];
                int tmp;
                switch (curDir) {
                    case 0:
                        tmp = left;
                        left = bottom;
                        right = 7 - left;
                        top = tmp;
                        bottom = 7 - top;
                        break;
                    case 1:
                        tmp = front;
                        front = bottom;
                        back = 7 - front;
                        top = tmp;
                        bottom = 7 - top;
                        break;
                    case 2:
                        tmp = right;
                        right = bottom;
                        left = 7 - right;
                        top = tmp;
                        bottom = 7 - top;
                        break;
                    case 3:
                        tmp = back;
                        back = bottom;
                        front = 7 - back;
                        top = tmp;
                        bottom = 7 - top;
                        break;
                    default:
                        return;
                }
                if (bottom > map[pos[0]][pos[1]]) {
                    curDir = (curDir + 1) % 4;
                } else if (bottom < map[pos[0]][pos[1]]) {
                    curDir = curDir - 1;
                    if (curDir == -1) curDir = 3;
                }
            }
            System.out.println(sumScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
