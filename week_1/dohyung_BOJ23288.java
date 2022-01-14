import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    // N x M 크기 지도

    // 지도의 오른쪽: 동쪽
    // 위쪽: 북쪽

    // (1, 1) ~ (N, M)

    // 주사위
    // 1 ~ 6

    // 윗면 1
    // 동쪽 3
    //   2
    // 4 1 3
    //   5
    //   6

    // 처음 이동 방향: 동쪽
    // 주사위 이동
    // 1. 이동 방향으로 한 칸 굴러간다. 이동방향에 칸이 없다면 반대로 굴러간다.
    // 2. 도착한 칸 (x, y)에 대한 점수를 획득한다.
    // 3. 아랫면에 있는 정수 A
    // (x, y)에 있는 정수 B를 비교해 이동방향을 정한다.
    // A > B 인경우 이동방향을 90도 시계방향 회전
    // A < B 인경우 이동방향을 90도 반시계방향 회전
    // A = B 인경우 이동방향 그대로

    // 2. 점수 구하기
    // (x,y) 있는 정수 B
    // (x,y)에서 동서남북 방향으로 연속해서 이동할 수 있는 칸의 수 C를 모두 구한다.
    // -> 이때 이동할 수 있는 칸에는 모두 정수 B가 있어야 한다.
    // 점수 : B x C

    // 1.주사위 굴리기 구현
    // 북쪽으로 굴리기 : 행하나씩 감소 (범위 밖 고려)
    // 남쪽으로 굴리기 : 행하나씩 증가 (범위 밖 고려)
    // 동쪽으로 굴리기 : 1행 2열이 3행 1열로, 3행 1열이 1행 0열로 /  1행 1열이 1행 2열로, 1행 0열이 1행 1열로
    // 서쪽으로 굴리기 : 1행 0열이 3행 1열로, 3행 1열이 1행 2열로 /  1행 1열이 1행 0열로, 1행 2열이 1행 1열로
    // 2. 점수 구하기 (BFS)
    // 3. 방향 정하기

    // 북 동 남 서
    final static int[] dy = {-1, 0, 1, 0};
    final static int[] dx = {0, 1, 0, -1};
    static int N, M, K;
    static int[][] dice = {
            {0, 2, 0},
            {4, 1, 3},
            {0, 5, 0},
            {0, 6, 0}
    };
    static int[][] map;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 1; j <= M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        int dir = 1; // 동
        int y = 1, x = 1; // 시작 위치
        for (int i = 0; i < K; i++) {
            int originDir = dir;
            // 굴리기, 칸이 없다면 반대 방향
            int ny = y + dy[dir];
            int nx = x + dx[dir];
            if (ny < 1 || ny > N || nx < 1 || nx > M) {
                dir = (dir + 2) % 4;
                ny = y + dy[dir];
                nx = x + dx[dir];
            }
            switch (dir) {
                case 0:
                    rollNorth();
                    break;
                case 1:
                    rollEast();
                    break;
                case 2:
                    rollSouth();
                    break;
                case 3:
                    rollWest();
                    break;
            }
            y = ny;
            x = nx;

            // 점수 구하기
            answer += getScore(y, x);

            // 방향 정하기
            int A = dice[3][1];
            int B = map[y][x];
            if (A > B) {
                dir = getIdx(dir + 1, 4);
            }
            else if (A < B) {
                dir = getIdx(dir - 1, 4);
            }
        }

        System.out.println(answer);
    }

    static void rollNorth() {
        int temp = dice[dice.length - 1][1];

        for (int i = dice.length - 1; i >= 0; i--) {
            int next = getIdx(i - 1, 4);

            int temp2 = dice[next][1];
            dice[next][1] = temp;
            temp = temp2;
        }
    }

    static void rollSouth() {
        int temp = dice[0][1];

        for (int i = 0; i < dice.length; i++) {
            int next = getIdx(i + 1, 4);

            int temp2 = dice[next][1];
            dice[next][1] = temp;
            temp = temp2;
        }
    }

    static void rollEast() {
        // 1행 2열이 3행 1열로
        int temp = dice[3][1];
        dice[3][1] = dice[1][2];

        // 3행 1열이 1행 0열로
        int temp2 = dice[1][0];
        dice[1][0] = temp;

        // 1행 0열이 1행 1열로
        temp = dice[1][1];
        dice[1][1] = temp2;

        // 1행 1열이 1행 2열로
        dice[1][2] = temp;
    }

    static void rollWest() {
        // 1행 0열이 3행 1열로
        int temp = dice[3][1];
        dice[3][1] = dice[1][0];

        // 3행 1열이 1행 2열로
        int temp2 = dice[1][2];
        dice[1][2] = temp;

        // 1행 2열이 1행 1열로
        temp = dice[1][1];
        dice[1][1] = temp2;

        // 1행 1열이 1행 0열로
        dice[1][0] = temp;
    }

    static int getScore(int y, int x) {
        int count = 0;

        Queue<Pair> q = new LinkedList<>();
        boolean[][] visit = new boolean[N + 1][M + 1];

        q.add(new Pair(y, x));
        visit[y][x] = true;
        int value = map[y][x];
        count++;

        while (!q.isEmpty()) {
            Pair c = q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = c.y + dy[i];
                int nx = c.x + dx[i];
                if (ny >= 1 && ny <= N && nx >= 1 && nx <= M) {
                   if (!visit[ny][nx] && map[ny][nx] == value) {
                       q.add(new Pair(ny, nx));
                       visit[ny][nx] = true;
                       count++;
                   }
                }
            }
        }

        return count * value;
    }

    static int getIdx(int idx, int size) {
        return (idx + size) % size;
    }

    static class Pair {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
