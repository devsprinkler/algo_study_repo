import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static boolean isPrime(int n) {
        if (n % 2 == 0) return false;
        for (int div = 3; div <= Math.sqrt(n); div += 2) {
            if (n % div == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer>[] list = new ArrayList[n+1];
        for (int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }
        list[1].add(2);
        list[1].add(3);
        list[1].add(5);
        list[1].add(7);

        for (int i = 2; i <= n; i++) {
            for (int p : list[i - 1]) {
                for (int j = 0; j < 10; j++) {
                    int t = p * 10 + j;
                    if (isPrime(t)) {
                        list[i].add(t);
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int p : list[n]) {
            sb.append(p).append("\n");
        }
        System.out.println(sb);
    }
}
