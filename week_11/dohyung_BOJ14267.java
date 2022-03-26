import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    // 상사가 한 직속부하를 칭찬하면 그 부하의 모든 부하들이 칭찬을 받는다.
    // 모든 칭찬에는 칭찬의 정도를 의미하는 수치가 있음. 이 수치 또한 부하들에게 똑같이 칭찬

    // 직원최대 10만명, 최초 칭찬 횟수 최대 10만번
    // 직속 상사의 번호는 자신의 번호보다 작으며, 사장이 1번
    // 사장은 상사가 없으므로 -1이 입력

    // 칭찬 수치 최대 1000
    // 직원 번호 최대 N
    // 직속 상사로부터 칭찬을 받은 직원 번호 i, 칭찬 수치 w 가 주어짐

    // 전체 탐색 (adj List) -> 최대 10만명에게 10만번
    // 어차피 반복적으로 더할 것을 반복해서 하게 됨 -> dp 반복 줄임
    // 상위 노드 부터 (번호가 작은 것 부터) 칭찬 반영을 부하들에게 먼저 다 함 -> 그 다음 번호 반영

    static int N, M;
    static ArrayList<Integer>[] boss;
    static int[] praise;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        boss = new ArrayList[N + 1];
        for (int i = 0; i < boss.length; i++) {
            boss[i] = new ArrayList<>();
        }
        praise = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            int tempBoss = Integer.parseInt(st.nextToken());

            if (tempBoss == -1)
                continue;

            boss[tempBoss].add(i);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int emp, w;
            emp = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            praise[emp] += w;
        }

        recurPraise();

        for (int i = 1; i <= N; i++) {
            System.out.print(praise[i] + " ");
        }
    }

    static void recurPraise() {
        for (int i = 1; i <= N; i++) {
            if (praise[i] == 0) continue;

            for (int j = 0; j < boss[i].size(); j++) {
                int idx = boss[i].get(j);
                praise[idx] += praise[i];
            }
        }
    }
}
