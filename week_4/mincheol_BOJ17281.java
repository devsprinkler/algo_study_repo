package algorithm.BOJ.P17281;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[][] player;
    static int maxScore = 0;

    static int calcScore(int[] order) {
        int cur = 0;
        int out = 0;
        int inning = 0;
        int score = 0;
        boolean[] base = new boolean[3];
        while (inning < n) {
            while (out < 3) {
                if (player[order[cur]][inning] == 0) {
                    out++;
                } else if (player[order[cur]][inning] == 1) {
                    score += base[2] ? 1 : 0;
                    base[2] = base[1];
                    base[1] = base[0];
                    base[0] = true;
                } else if (player[order[cur]][inning] == 2) {
                    score += base[2] ? 1 : 0;
                    score += base[1] ? 1 : 0;
                    base[2] = base[0];
                    base[1] = true;
                    base[0] = false;
                } else if (player[order[cur]][inning] == 3) {
                    score += base[2] ? 1 : 0;
                    score += base[1] ? 1 : 0;
                    score += base[0] ? 1 : 0;
                    base[2] = true;
                    base[1] = false;
                    base[0] = false;
                } else {
                    score += base[2] ? 1 : 0;
                    score += base[1] ? 1 : 0;
                    score += base[0] ? 1 : 0;
                    score++;
                    base[0] = false;
                    base[1] = false;
                    base[2] = false;
                }
                cur = (cur + 1) % 9;
            }
            out = 0;
            inning++;
            base[0] = base[1] = base[2] = false;
        }
        return score;
    }

    static void bruteforce(int idx, int[] order, boolean[] visited) {
        if (idx == 3) {
            bruteforce(idx+1, order.clone(), visited.clone());
            return;
        }
        if (idx == 9) {
            maxScore = Math.max(maxScore, calcScore(order));
            return;
        }
        for (int i = 1; i < 9; i++) {
            if (!visited[i]) {
                order[idx] = i;
                visited[i] = true;
                bruteforce(idx+1, order.clone(), visited.clone());
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            n = Integer.parseInt(br.readLine());
            player = new int[9][n];
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 9; j++) {
                    player[j][i] = Integer.parseInt(st.nextToken());
                }
            }
            int[] order = new int[9];
            boolean[] visited = new boolean[9];
            visited[0] = true;
            bruteforce(0, order.clone(), visited.clone());
            System.out.println(maxScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
