import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// 최대 50개 노드.. 에지의 길이는 양방향 1으로
// 고정이므로 에지 최대 개수는 (50 * 51 / 2) = 1275개
// 1..50까지 합
// dijkstra -> elog(v) -> 1275 * 1.7 = 2166
// 모든 노드에서 한번씩 -> 2166 * 50 = 108309
// 다익스트라로 해결 가능할듯?

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] graph = new int[n+1][n+1];

        for (;;) {
            String[] in = br.readLine().split(" ");
            int x = Integer.parseInt(in[0]);
            int y = Integer.parseInt(in[1]);
            if (x == -1) break;
            graph[x][y] = 1;
            graph[y][x] = 1;
        }

        int[] cost = new int[n+1];
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            Arrays.fill(cost, -1);
            cost[i] = 0;
            queue.offer(i);
            while(!queue.isEmpty()) {
                int cur = queue.poll();
                for (int j = 1; j <= n; j++) {
                    if (cost[j] == -1 && graph[cur][j] == 1) {
                        cost[j] = cost[cur] + 1;
                        queue.offer(j);
                    }
                }
            }
            for (int j = 1; j <= n; j++) {
                if (cost[j] > 0 && graph[i][j] == 0) {
                    graph[i][j] = graph[j][i] = cost[j];
                }
            }
        }
        
        ArrayList<Integer> list = new ArrayList<>();
        int min = 50;
        for (int i = 1; i <= n; i++) {
            int tmp = 0;
            for (int j = 1; j <= n; j++) {
                tmp = Math.max(graph[i][j], tmp);
            }
            if (min == tmp) {
                list.add(i);
            } else if (min > tmp) {
                list.clear();
                list.add(i);
                min = tmp;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(min).append(" ").append(list.size()).append("\n");
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(" ");
        }
        System.out.println(sb);
    }
}