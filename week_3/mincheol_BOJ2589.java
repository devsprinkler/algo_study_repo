import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] map;
    static boolean[][] visited;

    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());

            map = new boolean[row][col];
            for (int i = 0; i < row; i++) {
                String str = br.readLine();
                char[] str2 = str.toCharArray();
                for (int j = 0; j < col; j++) {
                    if (str2[j] == 'L') {
                        map[i][j] = true;
                    }
                }
            }

            int max = 0;
            visited = new boolean[row][col];

            LinkedList<int[]> queue = new LinkedList<>();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (map[i][j]) {
                        for (int k = 0; k < row; k++) {
                            Arrays.fill(visited[k], false);
                        }
                        queue.offer(new int[] {i, j, 0});
                        visited[i][j] = true;
                        while(!queue.isEmpty()) {
                            int x = queue.peek()[0];
                            int y = queue.peek()[1];
                            int c = queue.poll()[2];
                            boolean next = false;
                            if (x < row-1 && map[x+1][y] && !visited[x+1][y]) {
                                visited[x+1][y] = true;
                                next = true;
                                queue.offer(new int[] {x+1, y, c+1});
                            }
                            if (x > 0 && map[x-1][y] && !visited[x-1][y]) {
                                visited[x-1][y] = true;
                                next = true;
                                queue.offer(new int[] {x-1, y, c+1});
                            }
                            if (y < col-1 && map[x][y+1] && !visited[x][y+1]) {
                                visited[x][y+1] = true;
                                next = true;
                                queue.offer(new int[] {x, y+1, c+1});
                            }
                            if (y > 0 && map[x][y-1] && !visited[x][y-1]) {
                                visited[x][y-1] = true;
                                next = true;
                                queue.offer(new int[] {x, y-1, c+1});
                            }
                            if (!next) {
                                max = Math.max(max, c);
                            }
                        }
                    }
                }
            }
            System.out.println(max);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
