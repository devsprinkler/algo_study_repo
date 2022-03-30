import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 스티커 모눈종이 위에 인쇄. 상하좌우로 모두 연결돼있다.
    // 모눈종이의 크기는 스티커의 크기에 꼭 맞아서, 상하좌우에 스티커가 포함되지 않는 불필요한 행이나 열이 존재하지 않는다.
    
    // 먼저 받은 스티커부터 차례대로 붙인다.
    // 1. 회전 시키지않고 떼어낸다
    // 2. 다른 스티커와 겹치지 않고, 노트북을 벗어나지 않으면서 스티커를 붙일 수 있는 위치를 찾는다.
    // -> 위쪽부터 채우려해서 가장 위쪽 선택. 가장 위쪽 위치 여러곳이면 가장 왼쪽 위치
    // 3. 스티커를 붙인다. 위치가 전혀 없으면 스티커를 시계 방향으로 90도 회전한 뒤 2번 과정을 반복
    // 4. 위의 과정을 4번 반복 해서 0, 90, 180, 270 도 회전시켜도 없으면 버린다.

    // 노트북에서 최종적으로 몇 개의 칸이 채워졌는지
    
    // 노트북 가로,세로 최대 40
    // 스티커 최대 100개
    // K 개의 스티커 정보 가로, 세로 최대 10
    // 4 * 100 * 100 * 1600 = 64,000,000 6400만

    static int N, M;
    static int K;
    static int[][] laptop;
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        laptop = new int[N][M];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            Sticker temp = new Sticker(r, c);
            for (int j = 0; j < r; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < c; k++) {
                    temp.paper[j][k] = Integer.parseInt(st.nextToken());
                }
            }

            // sticker
            putSticker(temp);
        }

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < M; j++) {
//                System.out.print(laptop[i][j] + " ");
//            }
//            System.out.println();
//        }

        System.out.println(answer);
    }

    static void putSticker(Sticker s) {
        // rotate
        for (int k = 0; k < 4; k++) {
            if (k != 0) {
                // rotate
                s.rotate();
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (put(i, j, s)) {
                        // sticker 적용이미 해줌
                        return;
                    }
                }
            }
        }
    }

    static boolean put(int r, int c, Sticker s) {
        class Pair {
            int y,x;

            public Pair(int y, int x) {
                this.y = y;
                this.x = x;
            }
        }

        int[][] arr = s.paper;
        ArrayList<Pair> list = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 1) {
                    int ny = r + i;
                    int nx = c + j;
                    if (ny >= 0 && ny < N && nx >= 0 && nx < M) {
                        if (laptop[ny][nx] != 1) {
                            list.add(new Pair(ny, nx));
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Pair p = list.get(i);

            laptop[p.y][p.x] = 1;
        }
        answer += list.size();

        return true;
    }

    static class Sticker {
        int r, c;
        int[][] paper;

        public Sticker(int r, int c) {
            this.r = r;
            this.c = c;
            paper = new int[r][c];
        }

        void rotate() {
            // 시계방향 90도

            int[][] tArr = new int[this.c][this.r];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    tArr[j][r - i - 1] = paper[i][j];
                }
            }

            int temp = this.r;
            this.r = this.c;
            this.c = temp;

            paper = tArr;
        }
    }
}
