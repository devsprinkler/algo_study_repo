import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // H, W 최대 500
    // 블록이 쌓인 높이를 의미하는 0이상 H이하 정수
    // 맨 왼쪽부터 차례대로 W개
    // 바닥은 항상 막혀있다.
    
    // 양 옆은 막혀있으면서
    // 기둥 높이가 낮아졌다가 높아지면 웅덩이 시작
    // 기둥 높이가 시작 기둥이상이 되면 웅덩이 무조건 끝
    // 가로가 끝나면 웅덩이 끝
    // 양 기둥중 낮은 높이를 기준으로 측정
    
    // 웅덩이가 끝나야 물 카운트

    // 기둥 높이
    // 3  1  2  3  4  1  1  2

    // 증가,감소 (증가: 1, 유지: 0, 감소: -1)
    // 0 -1  1  1 // 1 -1  0  1 //(증가 상태여야 벽이 됨)

    // 100 18
    // 28 100 43 33 37 100 87 15 52 35 54 86 60 24 //99 56 4 40//
    //                                              1   -1 -1 1

    static int H, W;
    static int[] blocks;
    static int[] change;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        blocks = new int[W];
        change = new int[W];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < W; i++) {
            blocks[i] = Integer.parseInt(st.nextToken());

            if (i == 0 || blocks[i - 1] == blocks[i])
                change[i] = 0;
            else if (blocks[i - 1] < blocks[i])
                change[i] = 1;
            else
                change[i] = -1;
        }

        int answer = 0;

        for (int i = 0; i < W; i++) {
            if (change[i] == -1) {
                int start = i - 1;
                int end = -1;

                int idx = i;
                while (idx < W && blocks[idx] < blocks[start]) {
                    if (change[idx] > 0) {
                        if (end == -1)
                            end = idx;
                        else {
                            if (blocks[idx] > blocks[end])
                                end = idx;
                        }
                    }
                    idx++;
                }

                if (idx == W && end < 0) {
                    break;
                }
                else if (idx != W)
                    end = idx;

                idx = start;
                while (idx < end && blocks[idx] >= blocks[end]) {
                    start = idx;
                    idx++;
                }

                int min = Math.min(blocks[start], blocks[end]);

                for (int j = start + 1; j < end; j++) {
                    answer += min - blocks[j];
                }

                i = end;
            }
        }

        System.out.println(answer);
    }
}
