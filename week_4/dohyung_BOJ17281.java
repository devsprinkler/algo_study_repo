import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    // 야구 9명

    // 총 N이닝
    // 한 이닝은 공격/수비

    // 타순 정하려 한다.
    // 1번선수: 4번 타자
    // 다른 선수 타순 모두 결정
    // 가장 많은 득점을 하는 타순 -> 그 때의 득점

    // 최대 50이닝
    // N개의 줄에 각 선수가 각 이닝에서 얻는 결과가 1 ~ N이닝 까지 순서대로 주어진다.
    // 각 이닝에는 아웃을 기록하는 타자가 적어도 한 명 존재한다.

    // 점수 계산
    // 1루: 1, 2루: 2, 3루: 3, 홈: 4이상
    // 1번은 4번타자 고정
    // 타순은 다음이닝에도 이어짐
    // 경우의 수 -> 8! (4만가지) * 50 -> 200만 * 27 = 5400만

    static int N;
    static int answer = 0;
    static int[][] play;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        play = new int[N][9];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                play[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] choice = new int[9];
        boolean[] visit = new boolean[9];
        choice[3] = 0;
        visit[0] = true;

        comb(9, 0, choice, visit);

        System.out.println(answer);
    }

    // 조합
    static void comb(int N, int count, int[] choice, boolean[] visit) {
        if (N == count) {
            calcScore(choice);
        }
        else if (count == 3) {
            // 4번 타자
            comb(N, count + 1, choice, visit);
        }
        else {
            for (int i = 1; i < 9; i++) {
                if (visit[i]) continue;

                choice[count] = i;
                visit[i] = true;
                comb(N, count + 1, choice, visit);
                visit[i] = false;
            }
        }
    }

    static void calcScore(int[] choice) {
        ArrayList<Integer> base = new ArrayList<>();
        int score = 0;

        int idx = 0;
        for (int i = 0; i < N; i++) {
            // base 초기화 필요
            base.clear();
            int out = 0;
            while (out != 3) {
                int hit = play[i][choice[idx]];
                if (hit == 0) {
                    out++;
                }
                else {
                    if (hit == 4) {
                        score += base.size() + 1;
                        base.clear();
                    }
                    else {
                        score += calcHit(hit, base);
                    }
                }
                idx = getIdx(idx + 1);
            }
        }

        answer = Math.max(score, answer);
    }

    static int calcHit(int hit, ArrayList<Integer> base) {
        int score = 0;

        for (int i = 0; i < base.size(); i++) {
            int elem = base.get(i) + hit;
            if (elem >= 4) {
                score++;
                base.remove(i);
                i--;
            }
            else {
                base.set(i, elem);
            }
        }
        base.add(hit);

        return score;
    }

    static int getIdx(int idx) {
        return (idx + 9) % 9;
    }
}
