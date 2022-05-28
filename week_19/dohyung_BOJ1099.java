import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 단어 N 개가 있다.
    // 이 언어의 문장은 단어를 공백없이 붙여쓴 것이다.
    // 이 문장에서 각 단어는 0번 또는 그 이상 나타날 수 있다.
    // 단어에 쓰여 있는 문자의 순서를 바꿔도 된다.

    // 원래 단어의 위치와 다른 위치에 있는 문자의 개수 만큼이 그 순서를 바꾼 단어를 만드는 비용
    // abc 0
    // acb cba bac 비용2
    // bca cab 비용 3

    // 한 문장을 여러가지 방법으로 해석 가능
    // 비용의 최솟값
    
    // 문장의 최대 길이는 50
    // 단어 최대 50개
    // 단어 길이 최대 50
    // 알파벳 소문자로만 이루어짐

    // 단어의 범위를 찾는 것이 문제
    // 처음부터 뒤로 순서대로?
    // 단어의 길이가 중요 -> 길이 합쳐서 되는 경우만?
    // -> 소문자 갯수도 중요
    // -> 패턴에 안맞는 문자의 갯수가 비용
    // 중복 문자, 갯수 조합으로 구성된 단어는 하나로?

    // 인덱스마다 길이별로 가능한 단어가 있는지를 한번 iter?
    //

    // 1. 문장 앞에서부터 연속적으로 해석
    // 2. dp[idx] = 문장의 idx 까지 해석하기 위해 필요한 비용의 최솟값
    // -> substring 해서 앞의 dp를 활용

    static String sentence;
    static int N;
    static ArrayList<String> words = new ArrayList<>();
    static int[] dp;
    static int cost = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sentence = st.nextToken();
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            words.add(st.nextToken());
        }

        dp = new int[sentence.length()];
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 0; i < sentence.length(); i++) {

            // each substring
            for (int j = 0; j <= i; j++) {
                if (j >= 1 && dp[j - 1] == Integer.MAX_VALUE) continue; // 이전까지가 단어로 안되면 의미 X

                String sub = sentence.substring(j, i + 1);

                // 비용 + dp[j - 1] (j == 0이면?)
                int sum = j == 0 ? 0 : dp[j - 1];
                // 알파벳 조합 일치하면
                if (match(sub)) {
                    dp[i] = Math.min(sum + cost, dp[i]);
                }
            }
        }

        if (dp[sentence.length() - 1] == Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(dp[sentence.length() - 1]);
    }

    public static boolean match(String sub) {
        cost = Integer.MAX_VALUE;
        // 모든 단어와 비교
        for (int i = 0; i < words.size(); i++) {
            if (sub.length() != words.get(i).length()) continue;

            int[] wordCnt = new int['z' - 'a' + 1];
            int[] subCnt = new int['z' - 'a' + 1];

            int tempCost = 0;

            for (int j = 0; j < sub.length(); j++) {
                wordCnt[words.get(i).charAt(j) - 'a'] += 1;
                subCnt[sub.charAt(j) - 'a'] += 1;
                if (words.get(i).charAt(j) != sub.charAt(j))
                    tempCost++;
            }

            int idx = 0;
            for (idx = 0; idx < 'z' - 'a' + 1; idx++) {
                if (wordCnt[idx] != subCnt[idx]) break;
            }

            if (idx == 'z' - 'a' + 1) {
                cost = Math.min(cost, tempCost);
            }
        }

        if (cost == Integer.MAX_VALUE) {
            return false;
        }
        else {
            return true;
        }
    }
}
