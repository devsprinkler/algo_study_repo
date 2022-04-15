import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        int[] lis = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int size = 0;
        lis[size++] = arr[0];

        for (int i = 1; i < n; i++) {
            int idx = Arrays.binarySearch(lis, 0, size, arr[i]);
            if (idx == -(size + 1)) lis[size++] = arr[i];
            else lis[-(idx + 1)] = arr[i];
        }

        System.out.println(n - size);
    }
}
