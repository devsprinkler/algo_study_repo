import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static Sticker[] stickers;

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Sticker {
        int r, c;
        int[][] s;

        Sticker(int r, int c, int[][] s) {
            this.r = r;
            this.c = c;
            this.s = s;
        }
    }

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            stickers = new Sticker[K];

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());

                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int[][] s = new int[r][c];

                for (int j = 0; j < r; j++) {
                    st = new StringTokenizer(br.readLine());

                    for (int k = 0; k < c; k++) {
                        s[j][k] = Integer.parseInt(st.nextToken());
                    }
                }

                stickers[i] = new Sticker(r, c, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int[][] rotate(int[][] sticker) {
        int n = sticker.length;
        int m = sticker[0].length;

        int[][] rotatedSticker = new int[m][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotatedSticker[j][n - i - 1] = sticker[i][j];
            }
        }

        return rotatedSticker;
    }

    static Pair findPosition(int[][] board, int[][] sticker) {
        int r = sticker.length;
        int c = sticker[0].length;

        boolean canAttach;

        for (int i = 0; i < N; i++) {
            // ?????? ROW + ???????????? ??????????????? ???????????? ?????????????????? ??? ??????
            if (i + r > N)
                return null;

            for (int j = 0; j < M; j++) {
                // ?????? COL + ???????????? ??????????????? ???????????? ?????????????????? ??? ??????
                if (j + c > M)
                    break;

                canAttach = true;
                for (int k = 0; k < r; k++) {
                    // (i, j)?????? ??? ????????? ??????, ?????? ???????????? ??????
                    if (!canAttach)
                        break;

                    for (int l = 0; l < c; l++) {
                        // ??? ????????? ??????
                        if (board[i + k][j + l] == 1 && sticker[k][l] == 1) {
                            canAttach = false;
                            break;
                        }
                    }
                }

                if (canAttach) {
                    return new Pair(j, i);
                }
            }
        }

        return null;
    }

    static void attach(int[][] board, int[][] sticker, Pair position) {
        int r = sticker.length;
        int c = sticker[0].length;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (sticker[i][j] == 1) {
                    board[i + position.y][j + position.x] = 1;
                }
            }
        }
    }

    static void solve() {
        int[][] board = new int[N][M];

        for (int i = 0; i < K; i++) {
            int[][] sticker = stickers[i].s;
            Pair position = findPosition(board, sticker);

            if (position != null) {
                attach(board, sticker, position);
            } else {
                for (int j = 0; j < 3; j++) {
                    sticker = rotate(sticker);

                    position = findPosition(board, sticker);
                    if (position != null) {
                        attach(board, sticker, position);
                        break;
                    }
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 1) {
                    answer += 1;
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
