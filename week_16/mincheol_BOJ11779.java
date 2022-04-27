import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

class Bus implements Comparable<Bus> {
    int goal;
    int cost;

    public Bus(int goal, int cost) {
        this.goal = goal;
        this.cost = cost;
    }

    @Override
    public int compareTo(Bus o) {
        return this.cost - o.cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        String[] in;
        ArrayList<Bus>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < m ; i++) {
            in = br.readLine().split(" ");
            int start = Integer.parseInt(in[0]);
            int end = Integer.parseInt(in[1]);
            int cost = Integer.parseInt(in[2]);

            graph[start].add(new Bus(end, cost));
        }

        in = br.readLine().split(" ");
        int start = Integer.parseInt(in[0]);
        int end = Integer.parseInt(in[1]);

        int[] cost = new int[n + 1];
        int[] parent = new int[n + 1];

        final int MAX = 100_000_001;
        
        for (int i = 1; i <= n; i++) {
            cost[i] = MAX;
            parent[i] = -1;
        }
        PriorityQueue<Bus> pq = new PriorityQueue<>();
        pq.offer(new Bus(start, 0));
        while(!pq.isEmpty()) {
            Bus bus = pq.poll();
            if (cost[bus.goal] < bus.cost) continue;
            for (Bus b : graph[bus.goal]) {
                if (cost[b.goal] > bus.cost + b.cost) {
                    cost[b.goal] = bus.cost + b.cost;
                    parent[b.goal] = bus.goal;
                    pq.offer(new Bus(b.goal, cost[b.goal]));
                }
            }
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = end; i != start; i = parent[i]) {
            stack.push(i);
        }
        stack.push(start);

        StringBuilder sb = new StringBuilder();
        sb.append(cost[end]).append("\n").append(stack.size()).append("\n");
        while(!stack.isEmpty()) {
            sb.append(stack.pop()).append(" ");
        }
        System.out.println(sb);
    }
}