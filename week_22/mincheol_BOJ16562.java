import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Student implements Comparable<Student> {
    int number, cost;

    Student(int number, int cost) {
        this.number = number;
        this.cost = cost;
    }

    @Override
    public int compareTo(Student o) {
        return this.cost - o.cost;
    }
}

public class BOJ16562 {
    static int[] parent;

    static boolean union(int x, int y) {
        if (x == y) return false;
        if (parent[x] == -1) {
            parent[y] = x;
        } else {
            parent[x] = y;
        }
        return true;
    }

    static int find(int t) {
        if (parent[t] == -1) return t;
        return parent[t] = find(parent[t]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        Student[] students = new Student[n];
        for (int i = 1; i <= n; i++) {
            students[i - 1] = new Student(i, Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(students);

        parent = new int[n + 1];
        Arrays.fill(parent, -1);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            union(find(x), find(y));
        }

        int totalCost = 0;
        for (int i = 0; i < n; i++) {
            if (union(0, find(students[i].number))) {
                totalCost += students[i].cost;
            }
        }

        if (k < totalCost) {
            System.out.println("Oh no");
        } else {
            System.out.println(totalCost);
        }
    }
}
