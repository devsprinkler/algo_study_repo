import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 한번에 갈수 있는 거리가 정해져있음 (연료량)


    // M명
    // N x N
    // 상하좌우 인접칸 이동
    // 항상 최단경로 이동
    // 여러 승객 같이 탑승 X
    // 출발지에서만 택시타서 목적지에서만 택시 내림

    // 태울 승객을 고를 때 현재 위치에서 최단거리가 가장 짧은 고객
    // 여러명이면 그 중 행번호 최소, 그중에서도 열 번호 최소

    // 한 칸이동 1 연료 소모
    // 승객을 목적지로 성공적 이동 -> 이동하면서 소모한 연료 양의 2배 충전
    // 연료 바닥나면 끝 (도착지에서 0되는건  성공)

    // 위, 오른, 아래, 왼
    final static int[] dy = {-1, 0, 1, 0};
    final static int[] dx = {0, 1, 0, -1};
    static int N, M;
    static int F;
    static int startY, startX;
    static int[][] map;
    static Pair[][] dests;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());

        map = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        startY = Integer.parseInt(st.nextToken());
        startX = Integer.parseInt(st.nextToken());

        dests = new Pair[N + 1][N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int sY, sX;
            int dY, dX;

            sY = Integer.parseInt(st.nextToken());
            sX = Integer.parseInt(st.nextToken());
            dY = Integer.parseInt(st.nextToken());
            dX = Integer.parseInt(st.nextToken());

            dests[sY][sX] = new Pair(dY, dX);
        }

        bfs();
    }

    static void bfs() {
        int y = startY;
        int x = startX;

        for (int i = 0; i < M; i++) {
            // find customer
            Pair p = findCustomer(y, x);
            if (p == null) {
                System.out.println(-1);
                return;
            }
            Pair dest = new Pair(dests[p.y][p.x].y, dests[p.y][p.x].x);
            dests[p.y][p.x] = null;
            // find shortest dest
            p = findDest(p.y, p.x, dest);
            if (p == null) {
                System.out.println(-1);
                return;
            }

            y = p.y;
            x = p.x;
        }

        System.out.println(F);
    }

    static Pair findCustomer(int y, int x) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[][] visit = new boolean[N + 1][N + 1];

        int cnt = 0;

        pq.add(new Pair(y, x));
        visit[y][x] = true;
        cnt++;

        while (!pq.isEmpty()) {

            int nextCnt = 0;
            ArrayList<Pair> list = new ArrayList<>();
            for (int i = 0; i < cnt; i++) {
                Pair p = pq.poll();

                if (dests[p.y][p.x] != null) {
                    return p;
                }

                if (F == 0)
                    continue; // 바로 return null 하면 안됨
                else {
                    // add points
                    for (int j = 0; j < 4; j++) {
                        int ny = p.y + dy[j];
                        int nx = p.x + dx[j];

                        if (ny >= 1 && ny <= N && nx >= 1 && nx <= N) {
                            if (map[ny][nx] == 0 && !visit[ny][nx]) {
//                                pq.add(new Pair(ny, nx)); // 바로넣으면 계속 섞임
                                list.add(new Pair(ny, nx));
                                visit[ny][nx] = true;
                                nextCnt++;
                            }
                        }

                    }
                }
            }
            cnt = nextCnt;
            for (int i = 0; i < list.size(); i++) {
                pq.add(list.get(i));
            }
            F -= 1;
        }

        return null;
    }

    static Pair findDest(int y, int x, Pair dest) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[][] visit = new boolean[N + 1][N + 1];

        int cnt = 0;
        int fuel = 0;

        pq.add(new Pair(y, x));
        visit[y][x] = true;
        cnt++;

        while (!pq.isEmpty()) {

            int nextCnt = 0;
            ArrayList<Pair> list = new ArrayList<>();

            for (int i = 0; i < cnt; i++) {
                Pair p = pq.poll();

                if (p.y == dest.y && p.x == dest.x) {
                    F += fuel * 2;
                    return p;
                }

                if (F == 0)
                    continue;
                else {
                    // add points
                    for (int j = 0; j < 4; j++) {
                        int ny = p.y + dy[j];
                        int nx = p.x + dx[j];

                        if (ny >= 1 && ny <= N && nx >= 1 && nx <= N) {
                            if (map[ny][nx] == 0 && !visit[ny][nx]) {
                                list.add(new Pair(ny, nx));
                                visit[ny][nx] = true;
                                nextCnt++;
                            }
                        }

                    }
                }
            }
            cnt = nextCnt;
            for (int i = 0; i < list.size(); i++) {
                pq.add(list.get(i));
            }
            F -= 1;
            fuel += 1;
        }

        return null;
    }

    static class Pair implements Comparable {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Object o) {
            Pair p = (Pair) o;
            if (this.y == p.y) {
                return this.x - p.x;
            } else {
                return this.y - p.y;
            }
        }

    }
}
