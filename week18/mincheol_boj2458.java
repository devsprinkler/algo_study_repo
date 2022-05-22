import java.util.*;
import java.io.*;

class Main {
  
    static ArrayList<Integer>[] big, small;
    static int n, m;
    static int cnt;
    static boolean[] visited;

    static void bigDfs(int idx) {
        if (visited[idx]) return;
        cnt++;
        visited[idx] = true;
        for (int i = 0; i < big[idx].size(); i++) {
            bigDfs(big[idx].get(i));
        }
    }

    static void smallDfs(int idx) {
        if (visited[idx]) return;
        cnt++;
        visited[idx] = true;
        for (int i = 0; i < small[idx].size(); i++) {
            smallDfs(small[idx].get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        m = Integer.parseInt(input[1]);

        big = new ArrayList[n + 1];
        small = new ArrayList[n + 1];
        visited = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            big[i] = new ArrayList<>();
            small[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            input = br.readLine().split(" ");

            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            big[a].add(b);
            small[b].add(a);
        }

        int ans = 0;

        for (int i = 1; i <= n; i++) {
            cnt = -2;
            Arrays.fill(visited, false);
            bigDfs(i);
            visited[i] = false;
            smallDfs(i);
            if (cnt == n - 1) ans++;
        }

        System.out.println(ans);
    }
}import java.util.*;
import java.io.*;

class Main {
  
    static ArrayList<Integer>[] big, small;
    static int n, m;
    static int cnt;
    static boolean[] visited;

    static void bigDfs(int idx) {
        if (visited[idx]) return;
        cnt++;
        visited[idx] = true;
        for (int i = 0; i < big[idx].size(); i++) {
            bigDfs(big[idx].get(i));
        }
    }

    static void smallDfs(int idx) {
        if (visited[idx]) return;
        cnt++;
        visited[idx] = true;
        for (int i = 0; i < small[idx].size(); i++) {
            smallDfs(small[idx].get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        m = Integer.parseInt(input[1]);

        big = new ArrayList[n + 1];
        small = new ArrayList[n + 1];
        visited = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            big[i] = new ArrayList<>();
            small[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            input = br.readLine().split(" ");

            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            big[a].add(b);
            small[b].add(a);
        }

        int ans = 0;

        for (int i = 1; i <= n; i++) {
            cnt = -2;
            Arrays.fill(visited, false);
            bigDfs(i);
            visited[i] = false;
            smallDfs(i);
            if (cnt == n - 1) ans++;
        }

        System.out.println(ans);
    }
}
