import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 1 ~ N 섬
    // 1번 섬 구명보트만, 다른 섬에는 양 또는 늑대들

    // 늑대가 없는 나라로 이주
    // 각 섬에서 1번 섬으로 가는 경로는 유일
    // i 번 섬에는 pi 번 섬으로 가는 다리가 있다

    // 늑대는 원래 섬에서 안 움직임
    // -> 섬으로 들어온 양을 늑대 1마리당 최대 양 1마리. 항상 잡음
    // 양들은 1번 섬으로 가는 경로로 이동

    // 얼마나 많은 양이 1번 섬?

    // 섬의 개수 2 ~ 12만
    // 마리수 최대 10억

    // 가장 리프 노드? 에서 각각 양의 마리수와 늑대 마리수를 더해나가서 0초과이면 구출 가능
    // 반대로 1번에서 dfs로 탐색?

    static int N;
    static Node[] nodes;
    static ArrayList<Integer>[] adj;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        nodes = new Node[N + 1];
        adj = new ArrayList[N + 1];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }

        nodes[1] = new Node(0, 0);
        for (int i = 2; i <= N; i++) {
            st = new StringTokenizer(br.readLine());

            String type = st.nextToken();
            int cnt, dest;
            cnt = Integer.parseInt(st.nextToken());
            dest = Integer.parseInt(st.nextToken());

            int numType = ("S".equals(type)) ? 1 : 2;
            nodes[i] = new Node(numType, cnt);
            adj[dest].add(i); // 도로 반대로
        }

        System.out.println(dfs(1));
    }

    static long dfs(int n) {
        long subSum = 0;

        if (nodes[n].type == 1)
            subSum += nodes[n].count;
        if (adj[n].size() == 0)
            return subSum;
        else {
            for (int i = 0; i < adj[n].size(); i++) {
                subSum += dfs(adj[n].get(i));
            }
            if (nodes[n].type == 2) {
                subSum -= nodes[n].count;
                if (subSum < 0)
                    subSum = 0;
                return subSum;
            }
            return subSum;
        }
    }

    static class Node {
        // type s: 1,
        // type w: 2
        int type, count;

        public Node(int type, int count) {
            this.type = type;
            this.count = count;
        }
    }
}
