import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 최소 개수의 safe zone 설치

    // 지도의 행 최대 1000
    // 지도의 열 최대 1000
    // 지도 밖으로 나가는 방향의 입력은 주어지지 않음

    // 최대로 묶을 수 있는 그룹의 갯수를 계산
    //

    // 총 100만칸에 대해 경우의 수? X
    // dp?

    final static char[] DIR = {'U', 'D', 'L', 'R'};
    static int N, M;
    static char[][] map;
    static boolean[][] visit;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);
            }
        }

        // 그룹 찾기
        int answer = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visit[i][j]) {
                    findGroup(i, j);
                    answer++;
                }
            }
        }

        System.out.println(answer);
    }

    // 무조건 순환하는 경우만 주어지나?
    static void findGroup(int i, int j) {
        visit[i][j] = true;
        bfs(i, j);

        // 내 방향대로 따라가기
        Pair p = dir(map[i][j]);
        int ny = i + p.dy;
        int nx = j + p.dx;
        while (!visit[ny][nx]) {
            visit[ny][nx] = true;
            bfs(ny, nx);

            p = dir(map[ny][nx]);
            ny = ny + p.dy;
            nx = nx + p.dx;
        }
    }

    static void bfs(int i, int j) {
        // 나한테 들어온 방향 que bsf
        Queue<Point> q = new LinkedList<>();
        Pair p = null;

        q.add(new Point(i, j));
        while (!q.isEmpty()) {
            Point t = q.poll();

            int tny, tnx;
            // 4방향
            for (int k = 0; k < 4; k++) {
                p = dir(DIR[k]);
                tny = t.y + p.dy;
                tnx = t.x + p.dx;

                // 범위 체크, visit 체크
                if (tny >= 0 && tny < N && tnx >= 0 && tnx < M) {
                    if (!visit[tny][tnx]) {
                        // 나한테 온 점인지 체크
                        Pair tp = dir(map[tny][tnx]);

                        if (tny + tp.dy == t.y && tnx + tp.dx == t.x) {
                            q.add(new Point(tny, tnx));
                            visit[tny][tnx] = true; //visit 체크
                        }
                    }
                }
            }
        }
    }

    static Pair dir(char c) {
        switch (c) {
            case 'U': // 위
                return new Pair(-1, 0);
            case 'D': // 아래
                return new Pair(1, 0);
            case 'L':
                return new Pair(0, -1);
            case 'R':
                return new Pair(0, 1);
            default:
                return null;
        }
    }

    static class Pair {
        int dy, dx;

        public Pair(int dy, int dx) {
            this.dy = dy;
            this.dx = dx;
        }
    }

    static class Point {
        int y, x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
