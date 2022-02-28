import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] inorder, postorder;
    static int[] inorderIdx;
    static StringBuilder sb = new StringBuilder();
    public static void getPreorder(int inStart, int inEnd, int postStart, int postEnd) {
        if (inStart > inEnd || postStart > postEnd) {
            return;
        }

        int root = inorderIdx[postorder[postEnd]];
        int leftLen = root - inStart;
        sb.append(inorder[root]).append(" ");

        getPreorder(inStart, root - 1, postStart, postStart + leftLen - 1);
        getPreorder(root + 1, inEnd, postStart + leftLen,postEnd - 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        inorder = new int[100001];
        inorderIdx = new int[100001];
        for (int i = 1; i <= n; i++) {
            inorder[i] = Integer.parseInt(st.nextToken());
            inorderIdx[inorder[i]] = i;
        }
        st = new StringTokenizer(br.readLine());
        postorder = new int[100001];
        for (int i = 1; i <= n; i++) {
            postorder[i] = Integer.parseInt(st.nextToken());
        }
        getPreorder(1, n, 1, n);
        System.out.println(sb);
    }
}
