import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 4변 중 적어도 2변 이상이 접촉한 것은 정확히 1시간만에 녹아 없어짐
    // 치즈 내부에 있는 공간은 외부 접촉하지 않은 것으로 가정
    // 모눈종이 맨 가장자리에는 치즈가 놓이지 않는 것으로 가정

    // 치즈가 모두 녹아 없업지는데 걸리는 정확한 시간

    // 행, 열 최대 100
    // 치즈는 1, 공백 0
    // 0,1 은 하나의 공백으로 분리

    // 치즈 별로 접촉한 변의 갯수를 알아야 함
    // -> arrayList 관리
    // 접촉한 변의 공간이 외부인지, 내부인지 구분 필요
    // -> 빈 공간 마다 BFS, 번호를 부여
    // -> 범위 밖을 접하는 공간이면 외부 공기라 판단

    final static int C = 1, S = 0;
    final static int OUT = 1, IN = 0;

    // 위, 오른, 아래, 왼
    final static int[] dy = {-1, 0, 1, 0};
    final static int[] dx = {0, 1, 0, -1};
    static int N, M;
    static int[][] map;
    static ArrayList<Pair> cheeses = new ArrayList<>();
    static ArrayList<Integer> paints = new ArrayList<>(); // 0: 내부, 1: 외부공기

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == C)
                    cheeses.add(new Pair(i, j));
            }
        }
        process();

    }

    static void process() {
        int time = 0;

        while (!cheeses.isEmpty()) {
            // 빈 공간 유형 결정
            int[][] space = new int[N][M];
            spacePaint(space);

//            printSpace(space);
            
            // 치즈 탐색, 녹이기(arrayList 제거, map 0으로 표기)
            cheeseMelt(space);

            time++;

//            printMap();
        }

        System.out.println(time);
    }

    static void printMap() {
        System.out.println();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    static void printSpace(int[][] space) {
        System.out.println();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(space[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    static void cheeseMelt(int[][] space) {
        ArrayList<Pair> remove = new ArrayList<>();

        for (int i = 0; i < cheeses.size(); i++) {
            Pair c = cheeses.get(i);

            int cnt = 0;
            for (int j = 0; j < 4; j++) {
                int y = c.y + dy[j];
                int x = c.x + dx[j];

                // 경계는 어차피 안닿음?
                // 외부와 닿은 것만 cnt
                if (y < 0 || y >= N || x < 0 || x >= M) continue;

                if (map[y][x] == S && paints.get(space[y][x]) == OUT) {
                    cnt++;
                }
            }

            // 나중에 map 에서 지워야함
            if (cnt >= 2) {
                remove.add(new Pair(c.y, c.x));
                cheeses.remove(i);
                i--;
            }
        }

        for (Pair p : remove) {
            map[p.y][p.x] = S;
        }
    }

    static void spacePaint(int[][] space) {
        int num = 2;
        boolean[][] visit = new boolean[N][M];
        paints = new ArrayList<>();
        // dummy
        paints.add(IN);
        paints.add(IN);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visit[i][j] == false && map[i][j] == S) {
                    if (bfs(space, visit, num, i, j))
                        paints.add(OUT); // paints.get(num) == 외부 공기 여부
                    else
                        paints.add(IN);
                    num++;
                }
            }
        }
    }

    static boolean bfs(int[][] space, boolean[][] visit, int num, int y, int x) {
        Queue<Pair> q = new LinkedList<>();
        boolean outAir = false; // 범위밖 접촉하면 true 로 리턴

        q.add(new Pair(y, x));
        visit[y][x] = true;

        while (!q.isEmpty()) {
            Pair p = q.poll();

            // paint
            space[p.y][p.x] = num;

            for (int i = 0; i < 4; i++) {
                int ny = p.y + dy[i];
                int nx = p.x + dx[i];

                if (ny < 0 || ny >= N || nx < 0 || nx >= M) {
                    // out Air
                    outAir = true;
                }
                else {
                    if (visit[ny][nx] == false && map[ny][nx] == S) {
                        q.add(new Pair(ny, nx));
                        visit[ny][nx] = true;
                    }
                }
            }
        }

        return outAir;
    }

    static class Pair {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
