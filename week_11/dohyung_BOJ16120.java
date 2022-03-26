package ppap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static Stack<Character> s = new Stack<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        String str = st.nextToken();

        int i = 0;
        for (; i < str.length(); i++) {
            if (str.charAt(i) == 'A') {
                if (s.size() == 0 || s.pop() != 'P')
                    break;
                if (s.size() == 0 || s.pop() != 'P')
                    break;
                if (!(i + 1 < str.length() && str.charAt(i + 1) == 'P'))
                    break;
            }
            else
                s.push(str.charAt(i));
        }

        if (i < str.length())
            System.out.println("NP");
        else {
            if (s.size() == 1 && s.pop() == 'P')
                System.out.println("PPAP");
            else
                System.out.println("NP");
        }
    }
}
