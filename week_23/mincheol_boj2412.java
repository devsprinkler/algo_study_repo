import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class boj2412 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        ArrayList<Integer>[] p = new ArrayList[T + 1];
        for (int i = 0; i <= T; i++) {
            p[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            p[y].add(x);
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] {0, 0, 0});
        while(!q.isEmpty()) {
            int x = q.peek()[0];
            int y = q.peek()[1];
            int c = q.poll()[2];
            if (y == T) {
                System.out.println(c);
                return;
            }

            if (y < T - 1) {
                if (p[y + 2].contains(x)) {
                    p[y + 2].remove((Integer)x);
                    q.offer(new int[]{x, y + 2, c + 1});
                }
                if (p[y + 2].contains(x + 1)) {
                    p[y + 2].remove((Integer)(x + 1));
                    q.offer(new int[]{x + 1, y + 2, c + 1});
                }
                if (p[y + 2].contains(x + 2)) {
                    p[y + 2].remove((Integer)(x + 2));
                    q.offer(new int[]{x + 2, y + 2, c + 1});
                }
                if (p[y + 2].contains(x - 1)) {
                    p[y + 2].remove((Integer)(x - 1));
                    q.offer(new int[]{x - 1, y + 2, c + 1});
                }
                if (p[y + 2].contains(x - 2)) {
                    p[y + 2].remove((Integer)(x - 2));
                    q.offer(new int[]{x - 2, y + 2, c + 1});
                }
            }
            if (y < T) {
                if (p[y + 1].contains(x)) {
                    p[y + 1].remove((Integer)x);
                    q.offer(new int[]{x, y + 1, c + 1});
                }
                if (p[y + 1].contains(x + 1)) {
                    p[y + 1].remove((Integer)(x + 1));
                    q.offer(new int[]{x + 1, y + 1, c + 1});
                }
                if (p[y + 1].contains(x + 2)) {
                    p[y + 1].remove((Integer)(x + 2));
                    q.offer(new int[]{x + 2, y + 1, c + 1});
                }
                if (p[y + 1].contains(x - 1)) {
                    p[y + 1].remove((Integer)(x - 1));
                    q.offer(new int[]{x - 1, y + 1, c + 1});
                }
                if (p[y + 1].contains(x - 2)) {
                    p[y + 1].remove((Integer)(x - 2));
                    q.offer(new int[]{x - 2, y + 1, c + 1});
                }
            }
            if (p[y].contains(x + 1)) {
                p[y].remove((Integer)(x + 1));
                q.offer(new int[] {x + 1, y, c + 1});
            }
            if (p[y].contains(x + 2)) {
                p[y].remove((Integer)(x + 2));
                q.offer(new int[] {x + 2, y, c + 1});
            }
            if (p[y].contains(x - 1)) {
                p[y].remove((Integer)(x - 1));
                q.offer(new int[] {x - 1, y, c + 1});
            }
            if (p[y].contains(x - 2)) {
                p[y].remove((Integer)(x - 2));
                q.offer(new int[] {x - 2, y, c + 1});
            }
            if (y > 0) {
                if (p[y - 1].contains(x)) {
                    p[y - 1].remove((Integer)x);
                    q.offer(new int[]{x, y - 1, c + 1});
                }
                if (p[y - 1].contains(x + 1)) {
                    p[y - 1].remove((Integer)(x + 1));
                    q.offer(new int[]{x + 1, y - 1, c + 1});
                }
                if (p[y - 1].contains(x + 2)) {
                    p[y - 1].remove((Integer)(x + 2));
                    q.offer(new int[]{x + 2, y - 1, c + 1});
                }
                if (p[y - 1].contains(x - 1)) {
                    p[y - 1].remove((Integer)(x - 1));
                    q.offer(new int[]{x - 1, y - 1, c + 1});
                }
                if (p[y - 1].contains(x - 2)) {
                    p[y - 1].remove((Integer)(x - 2));
                    q.offer(new int[]{x - 2, y - 1, c + 1});
                }
            }
            if (y > 1) {
                if (p[y - 2].contains(x)) {
                    p[y - 2].remove((Integer)x);
                    q.offer(new int[]{x, y - 2, c + 1});
                }
                if (p[y - 2].contains(x + 1)) {
                    p[y - 2].remove((Integer)(x + 1));
                    q.offer(new int[]{x + 1, y - 2, c + 1});
                }
                if (p[y - 2].contains(x + 2)) {
                    p[y - 2].remove((Integer)(x + 2));
                    q.offer(new int[]{x + 2, y - 2, c + 1});
                }
                if (p[y - 2].contains(x - 1)) {
                    p[y - 2].remove((Integer)(x - 1));
                    q.offer(new int[]{x - 1, y - 2, c + 1});
                }
                if (p[y - 2].contains(x - 2)) {
                    p[y - 2].remove((Integer)(x - 2));
                    q.offer(new int[]{x - 2, y - 2, c + 1});
                }
            }
        }
        System.out.println("-1");
    }
}
