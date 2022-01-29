import java.util.HashMap;

public class Main {
    public static int[] solution(String[] enroll, String[] referral,
                             String[] seller,
                          int[] amount) {
        int[] answer = new int[enroll.length];
        HashMap<String, int[]> hm = new HashMap<>();
        for (int i = 0; i < enroll.length; i++) {
            hm.put(enroll[i], new int[] {i, 0});
        }

        for (int i = 0; i < seller.length; i++) {
            int[] arr = hm.get(seller[i]);
            int fee = amount[i] * 100;
            hm.put(seller[i],
                    new int[] {arr[0], (int) (arr[1] + fee * 0.9)});
            fee = (int) (fee * 0.1);
            while(!referral[arr[0]].equals("-")) {
                arr = hm.get(referral[arr[0]]);

                if (fee >= 10) {
                    hm.put(enroll[arr[0]],
                            new int[] {arr[0],
                                    (int) (arr[1] + Math.ceil(fee * 0.9))});
                    fee = (int) Math.floor(fee * 0.1);
                } else {
                    hm.put(enroll[arr[0]],
                            new int[] {arr[0], (arr[1] + fee)});
                    break;
                }
            }
        }

        for (int[] arr : hm.values()) {
            answer[arr[0]] = arr[1];
        }
        return answer;
    }

    public static void main(String[] args) {
        String[] enroll = {"john", "mary", "edward", "sam", "emily", "jaimie"
                , "tod", "young"};
        String[] referral = {"-", "-", "mary", "edward", "mary", "mary",
                "jaimie", "edward"};
        String[] seller = {"young", "john", "tod", "emily", "mary"};
        int[] amount = {12, 4, 2, 5, 10};
        int[] res = solution(enroll, referral, seller, amount);
        for (int i : res) {
            System.out.println(i);
        }
    }
}
