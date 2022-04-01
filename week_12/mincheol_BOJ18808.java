import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] turn90(boolean[][] origin) {
        int row = origin.length;
        int col = origin[0].length;
        boolean[][] transformed = new boolean[col][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                transformed[j][row - i - 1] = origin[i][j];
            }
        }
        return transformed;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        boolean[][] laptop = new boolean[n][m];
        for (int i = 0; i < k; i++) {
            st  = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            boolean[][] sticker = new boolean[r][c];

            for (int j = 0; j < r; j++) {
                st = new StringTokenizer(br.readLine());
                for (int l = 0; l < c; l++) {
                    int block = Integer.parseInt(st.nextToken());
                    if (block == 1) sticker[j][l] = true;
                }
            }

            boolean isFitted = true;
            for (int round = 0; round < 4; round++) {
                int row = 0, col = 0;
                do {
                    isFitted = true;
                    for (int j = 0; j < r; j++) {
                        for (int l = 0; l < c; l++) {
                            if (row + j >= n || col + l >= m || (laptop[row + j][col + l] && sticker[j][l])) {
                                isFitted = false;
                                j = r;
                                break;
                            }
                        }
                    }
                    if (isFitted) break;
                    col++;
                    if (col >= m) {
                        row++;
                        col = 0;
                    }
                    if (row >= n) break;
                } while(!isFitted);

                
                if (isFitted) {
                    for (int j = 0; j < r; j++) {
                        for (int l = 0; l < c; l++) {
                            if (sticker[j][l]) laptop[row + j][col + l] = true;
                        }
                    }
                    break;
                }
                sticker = turn90(sticker);
                int tmp = r;
                r = c;
                c = tmp;
            }
        }

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (laptop[i][j]) cnt++;
            }
        }
        System.out.println(cnt);
    }
}