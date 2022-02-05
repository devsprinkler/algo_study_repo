import java.util.*;

class Solution {
    int N;
    int[][][][] visited;

    class Robot {
        int x1, x2, y1, y2;
        int time;

        Robot(int x1, int y1, int x2, int y2, int time) {
            if (x1 == x2) {
                this.x1 = x1;
                this.x2 = x2;
                this.y1 = Math.min(y1, y2);
                this.y2 = Math.max(y1, y2);
            } else {
                this.x1 = Math.min(x1, x2);
                this.x2 = Math.max(x1, x2);
                this.y1 = y1;
                this.y2 = y2;
            }

            this.time = time;
        }

        boolean isArrived() {
            return (x1 == N - 1 && y1 == N - 1) || (x2 == N - 1 && y2 == N - 1);
        }

        boolean isVertical() {
            return x1 == x2;
        }
    }

    void init(int[][] board) {
        N = board.length;

        visited = new int[N][N][N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    Arrays.fill(visited[i][j][k], 9999999);
                }
            }
        }

        visited[0][0][0][1] = 0;
    }

    public int solution(int[][] board) {
        int answer = 9999999;

        init(board);

        Queue<Robot> q = new LinkedList<>();
        q.offer(new Robot(0, 0, 1, 0, 0));

        while (!q.isEmpty()) {
            Robot cur = q.poll();
            int x1 = cur.x1;
            int y1 = cur.y1;
            int x2 = cur.x2;
            int y2 = cur.y2;
            int time = cur.time;

            if (cur.isArrived()) {
                answer = Math.min(answer, cur.time);
            }

            // 왼쪽으로 한칸 이동
            if (x1 - 1 >= 0 && board[y1][x1 - 1] == 0 && board[y2][x2 - 1] == 0 && visited[y1][x1 - 1][y2][x2 - 1] > time + 1) {
                visited[y1][x1 - 1][y2][x2 - 1] = time + 1;
                q.offer(new Robot(x1 - 1, y1, x2 - 1, y2, time + 1));
            }

            // 오른쪽으로 한칸 이동
            if (x2 + 1 < N && board[y1][x2 + 1] == 0 && board[y2][x2 + 1] == 0 && visited[y1][x1 + 1][y2][x2 + 1] > time + 1) {
                visited[y1][x1 + 1][y2][x2 + 1] = time + 1;
                q.offer(new Robot(x1 + 1, y1, x2 + 1, y2, time + 1));
            }

            // 위쪽으로 한칸 이동
            if (y1 - 1 >= 0 && board[y1 - 1][x1] == 0 && board[y2 - 1][x2] == 0 && visited[y1 - 1][x1][y2 - 1][x2] > time + 1) {
                visited[y1 - 1][x1][y2 - 1][x2] = time + 1;
                q.offer(new Robot(x1, y1 - 1, x2, y2 - 1, time + 1));
            }

            // 아래쪽으로 한칸 이동
            if (y2 + 1 < N && board[y1 + 1][x1] == 0 && board[y2 + 1][x2] == 0 && visited[y1 + 1][x1][y2 + 1][x2] > time + 1) {
                visited[y1 + 1][x1][y2 + 1][x2] = time + 1;
                q.offer(new Robot(x1, y1 + 1, x2, y2 + 1, time + 1));
            }

            // rotate
            if (cur.isVertical()) {
                if (x1 - 1 >= 0 && board[y1][x1 - 1] == 0 && board[y2][x1 - 1] == 0) {
                    if (visited[y1][x1 - 1][y1][x1] > time + 1) {
                        visited[y1][x1 - 1][y1][x1] = time + 1;
                        q.offer(new Robot(x1 - 1, y1, x1, y1, time + 1));
                    }

                    if (visited[y2][x2 - 1][y2][x2] > time + 1) {
                        visited[y2][x2 - 1][y2][x2] = time + 1;
                        q.offer(new Robot(x2 - 1, y2, x2, y2, time + 1));
                    }
                }

                if (x1 + 1 < N && board[y1][x1 + 1] == 0 && board[y2][x2 + 1] == 0) {
                    if (visited[y1][x1][y1][x1 + 1] > time + 1) {
                        visited[y1][x1][y1][x1 + 1] = time + 1;
                        q.offer(new Robot(x1, y1, x1 + 1, y1, time + 1));
                    }

                    if (visited[y2][x2][y2][x2 + 1] > time + 1) {
                        visited[y2][x2][y2][x2 + 1] = time + 1;
                        q.offer(new Robot(x2, y2, x2 + 1, y2, time + 1));
                    }
                }
            } else {
                if (y1 - 1 >= 0 && board[y1 - 1][x1] == 0 && board[y2 - 1][x2] == 0) {
                    if (visited[y1 - 1][x1][y1][x1] > time + 1) {
                        visited[y1 - 1][x1][y1][x1] = time + 1;
                        q.offer(new Robot(x1, y1 - 1, x1, y1, time + 1));
                    }

                    if (visited[y2 - 1][x2][y2][x2] > time + 1) {
                        visited[y2 - 1][x2][y2][x2] = time + 1;
                        q.offer(new Robot(x2, y2 - 1, x2, y2, time + 1));
                    }
                }

                if (y1 + 1 < N && board[y1 + 1][x1] == 0 && board[y2 + 1][x2] == 0) {
                    if (visited[y1][x1][y1 + 1][x1] > time + 1) {
                        visited[y1][x1][y1 + 1][x1] = time + 1;
                        q.offer(new Robot(x1, y1, x1, y1 + 1, time + 1));
                    }

                    if (visited[y2][x2][y2 + 1][x2] > time + 1) {
                        visited[y2][x2][y2 + 1][x2] = time + 1;
                        q.offer(new Robot(x2, y2, x2, y2 + 1, time + 1));
                    }
                }
            }
        }

        return answer;
    }
}
