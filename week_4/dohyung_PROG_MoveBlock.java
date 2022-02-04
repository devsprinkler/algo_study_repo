import java.util.*;

public class Solution {
    
    // 로봇 2 x 1 크기
    
    // 0, 1 로 이루어진 N x N 크기의 지도에서 로봇을 움직여,
    // (N, N) 위치까지 이동할 수 있도록 프로그래밍하려고 한다.
    
    // (1, 1) ~ (N, N)
    // 0: 빈칸
    // 1: 벽
    
    // 벽이 있는 칸, 지도 밖으로는 이동할 수 없다.
    // (1, 1) (1, 2) 에 놓여있는 상태로 시작
    // 앞뒤 구분 없이 이동 가능

    // 이동할 때는 현재 놓여 있는 상태를 유지하면서 이동
    // 아래, 위, 오른, 왼 ..

    // 조건에 따라 회전 가능
    // 두 칸중, 어느 칸이든 축이 될 수 있다.
    // 회전하는 방향에는 벽이 없어야 한다. (축이 되는 칸으로부터 대각선 방향에 있는 칸)

    // 로봇이 한칸 이동 or 회전하는 데는 1초 걸린다.
    // 최소 시간을 return

    // board의 한 변의 길이는 5이상 100이하. 최대 100 x 100
    // 항상 목적지에 도착 가능
    
    // bfs 사용
    // 회전 구현 필요
    // 상, 하, 좌, 우, 반시계(1축), 반시계(2축), 시계(1축), 시계(2축) 8가지 방향
    // -> 시간복잡도? 2칸마다 상하좌우 축별 회전 포함해서 총 10가지 경우의수. 2칸짜리는 최대 25 x 25개 있음 6250 * log625
    // 2칸이 모두 이동하는 것 주의
    // visit 구현? -> 두 좌표의 위치로 구현 (두 좌표가 바뀌어도 visit 은 같음)
    // -> 좌표 2개로 구현 하되, 확인할때 바꿔가면서 교차로 확인 or 정렬 순서대로 확인?
    // -> 각 좌표의 4방향으로의 visit 있음, 범위밖인 경우도 있을 것
    // -> [4][N][N]. 교착확인 필요! (visit 표시할 때 두번 표기?)

    // 반시계 확인 (벽인지, 범위 밖인지 + 이동할 지점도 범위밖인지, 벽인지)
    // |
    // 0  (-1, -1) 위
    // 0- (-1, 1)  오른
    // 0
    // |  (1, 1)   아래
    // -o (1, -1)  왼

    // 시계 확인
    // |
    // 0  (-1, 1)  위
    // 0- (1, 1)   오른
    // 0
    // |  (1, -1)  아래
    // -o (-1, -1) 왼


    // 위, 오른, 아래, 왼, 반시계(2), 시계(3)
    final static int[] dy = {-1, 0, 1, 0};
    final static int[] dx = {0, 1, 0, -1};
    // 피벗에서 대상 dir : 위, 오른, 아래, 왼 기준. 체크해야할 대각선 좌표
    final static int[] antiY = {-1, -1, 1, 1};
    final static int[] antiX = {-1, 1, 1, -1};
    // 피벗에서 대상 dir : 위, 오른, 아래, 왼 기준
    final static int[] clockY = {-1, 1, 1, -1};
    final static int[] clockX = {1, 1, -1, -1};
    static int answer = 0;

    public static int solution(int[][] board) {
        bfs(new Robot(0, 0, 0, 1, 0), board);

        System.out.println(answer);
        return answer;
    }

    static void bfs(Robot robot, int[][] board) {
        int N = board.length;
        boolean[][][] visit = new boolean[4][N][N];

        Queue<Robot> q = new LinkedList<>();
        q.add(robot);
        visitPoint(visit, robot);

        while (!q.isEmpty()) {
            Robot r = q.poll();

            if (checkDest(r, N)) {
                answer = r.cnt;
                break;
            }
            // 상하좌우 이동
            for (int i = 0; i < 4; i++) {
                Robot t = new Robot(r.y1 + dy[i], r.x1 + dx[i], r.y2 + dy[i], r.x2 + dx[i], r.cnt + 1);
                if (boundCheck(t, N) && wallCheck(t, board)) {
                    int dir = getDir(t);
                    if (!visit[dir][t.y1][t.x1]) {
                        q.add(t);
                        visitPoint(visit, t); // visit
                    }
                }
            }
            // y1, x1 피벗
            int py = r.y1;
            int px = r.x1;
            int ty = r.y2;
            int tx = r.x2;
            addAnti(py, px, ty, tx, N, board, q, visit, r.cnt);
            addClock(py, px, ty, tx, N, board, q, visit, r.cnt);
            // y2, x2 피벗
            py = r.y2;
            px = r.x2;
            ty = r.y1;
            tx = r.x1;
            addAnti(py, px, ty, tx, N, board, q, visit, r.cnt);
            addClock(py, px, ty, tx, N, board, q, visit, r.cnt);
        }
    }

    static boolean checkDest(Robot r, int N) {
        if ((r.y1 == N - 1 && r.x1 == N - 1) || (r.y2 == N - 1 && r.x2 == N - 1))
            return true;
        else
            return false;
    }

    static void addAnti(int py, int px, int ty, int tx, int N, int[][] board, Queue<Robot> q, boolean[][][] visit, int cnt) {
        int dir = getSpecificDir(py, px, ty, tx);
        // 반시계 회전 ~: dir (y, x) -> (-x, y)
        ty = py - dx[dir];
        tx = px + dy[dir];
        int diagY = py + antiY[dir];
        int diagX = px + antiX[dir];
        if (diagY >= 0 && diagY < N && diagX >= 0 && diagX < N) {
            if (board[diagY][diagX] != 1) {
                Robot t = new Robot(py, px, ty, tx, cnt + 1);
                if (boundCheck(t, N) && wallCheck(t, board)) {
                    int newDir = getDir(t);
                    if (!visit[newDir][py][px]) {
                        q.add(t);
                        visitPoint(visit, t);
                    }
                }
            }
        }
    }

    static void addClock(int py, int px, int ty, int tx, int N, int[][] board, Queue<Robot> q, boolean[][][] visit, int cnt) {
        int dir = getSpecificDir(py, px, ty, tx);
        // 시계 회전   : dir (y, x) -> (x, -y)
        ty = py + dx[dir];
        tx = px - dy[dir];
        int diagY = py + clockY[dir];
        int diagX = px + clockX[dir];
        if (diagY >= 0 && diagY < N && diagX >= 0 && diagX < N) {
            if (board[diagY][diagX] != 1) {
                Robot t = new Robot(py, px, ty, tx, cnt + 1);
                if (boundCheck(t, N) && wallCheck(t, board)) {
                    int newDir = getDir(t);
                    if (!visit[newDir][py][px]) {
                        q.add(t);
                        visitPoint(visit, t);
                    }
                }
            }
        }
    }


    static boolean wallCheck(Robot r, int[][] board) {
        if (board[r.y1][r.x1] == 1 || board[r.y2][r.x2] == 1)
            return false;
        else
            return true;
    }

    static boolean boundCheck(Robot r, int N) {
        if (r.y1 < 0 || r.y1 >= N || r.x1 < 0 || r.x1 >= N
            || r.y2 < 0 || r.y2 >= N || r.x2 < 0 || r.x2 >= N)
            return false;
        else
            return true;
    }

    static int getDir(Robot r) {
        // y1, x1 기준 방향 구하기
        int dir = -1;
        int dirY = r.y2 - r.y1;
        int dirX = r.x2 - r.x1;
        for (int i = 0; i < 4; i++) {
            if (dirY == dy[i] && dirX == dx[i]) {
                dir = i;
                break;
            }
        }

        return dir;
    }

    static int getSpecificDir(int py, int px, int ty, int tx) {
        // y1, x1 기준 방향 구하기
        int dir = -1;
        int dirY = ty - py;
        int dirX = tx - px;
        for (int i = 0; i < 4; i++) {
            if (dirY == dy[i] && dirX == dx[i]) {
                dir = i;
                break;
            }
        }

        return dir;
    }

    // 기준 좌표의 방향 별 방문 여부 확인
    // -> 위 방향이면 아래 방향 좌표의 visit 도 처리
    static void visitPoint(boolean[][][] visit, Robot robot) {
        int dir = getDir(robot);

        visit[dir][robot.y1][robot.x1] = true;

        // 0 -> 2 1 -> 3 2 -> 0 3 -> 1
        int oppoDir = (dir + 2) % 4;
        visit[oppoDir][robot.y2][robot.x2] = true;
    }

    static class Robot {
        int y1, x1;
        int y2, x2;
        int cnt = 0;

        public Robot(int y1, int x1, int y2, int x2, int cnt) {
            this.y1 = y1;
            this.x1 = x1;
            this.y2 = y2;
            this.x2 = x2;
            this.cnt = cnt;
        }
    }
}
