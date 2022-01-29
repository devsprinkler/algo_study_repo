import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    // 보물섬 지도

    // 각 칸은 육지(L) 또는 바다(W)로 되어있다.
    // 지도에서 이동은 상하좌우로 이웃한 육지로만 가능
    // 한 칸 이동하는데 1시간이 걸림
    // 보물은 서로 간에 최단 거리로 이동하는데 있어, 가장 긴 시간이 걸리는 육지 2곳에 나눠 묻혀있다.
    // 육지를 나타내는 두 곳 사이를 최단거리로 이동하려면 같은 곳을 두번이상 지나거나, 멀리 돌아가서는 안된다.

    // 지도 문자 사이 빈칸 없다.
    // 세로크기, 가로크기 주어짐
    // 세로, 가로는 각각 50이하이다.

    // 최단거리 -> BFS 사용하면 됨
    // 각 육지 구역 안에서 가장 먼 2곳을 구하려면?
    // 가장 끝 (4방향중 연결된 육지가 1군데 인곳)들을 후보로 전부 확인?
    // -> 직사각형이면 없는 경우도 있음
    // -> 1군데 연결 없으면, 2군데 연결을 후보로 확인 -> 직사각형의 모서리
    // -> 2군데 연결이 없는 경우는?
    // 한칸짜리 육지인 경우? --> 답이 없는 경우는 명시 안했으므로 무시

    // 최대 2500개의 칸
    // 1 x 2 만 있을때 -> 2 x 3칸 필요 -> 1250 * 830 = 약 100만 * BFS(2)

    // 위, 오른, 아래, 왼
    final static int[] dy = {-1, 0, 1, 0};
    final static int[] dx = {0, 1, 0, -1};


    static int R, C;
    static char[][] map;
    static ArrayList<Pair> list = new ArrayList<>();
    static ArrayList<Pair> candid = new ArrayList<>();
    static int answer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            for (int j = 0; j < C; j++) {
                map[i][j] = str.charAt(j);
                if (map[i][j] == 'L') {
                    list.add(new Pair(i, j));
                }
            }
        }

        // 후보 위치
        // 1군데 연결
        addCandid(1);

        // 2군데 연결
        if (candid.size() == 0) {
            addCandid(2);
            for (int i = 0; i < candid.size(); i++) {
                findMax(candid.get(i).y, candid.get(i).x);
            }
        }
        else {
            for (int i = 0; i < candid.size(); i++) {
                findMax(candid.get(i).y, candid.get(i).x);
            }
        }

        System.out.println(answer - 1); // 시작점을 1로 설정했으므로
    }

    static void addCandid(int std) {
        for (int i = 0; i < list.size(); i++) {
            if (countEdge(list.get(i).y, list.get(i).x) == std)
                candid.add(list.get(i));
        }
    }

    static int countEdge(int y, int x) {
        int count = 0;

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (ny >= 0 && ny < R && nx>= 0 && nx < C) {
                if (map[ny][nx] == 'L')
                    count++;
            }
        }

        return count;
    }

    static void findMax(int y, int x) {
        int[][] visit = new int[R][C];
        Queue<Pair> q = new LinkedList<>();

        q.add(new Pair(y, x));
        visit[y][x] = 1; // 시작점을 0으로 하면 다시 되돌아 갈 수 있음

        while (!q.isEmpty()) {
            Pair p = q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = p.y + dy[i];
                int nx = p.x + dx[i];
                if (ny >= 0 && ny < R && nx >= 0 && nx < C) {
                    if (map[ny][nx] == 'L' && visit[ny][nx] == 0) {
                        q.add(new Pair(ny, nx));
                        visit[ny][nx] = visit[p.y][p.x] + 1;
                        answer = Math.max(answer, visit[ny][nx]);
                    }
                }
            }
        }
    }

    static class Pair {
        int y,x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

}
