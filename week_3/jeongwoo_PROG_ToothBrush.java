import java.util.*;

class Solution {
    class Person {
        String parent;
        int money;

        Person(String parent) {
            this.parent = parent;
        }

        String getParent() {
            return parent;
        }

        int getMoney() {
            return money;
        }

        void setMoney(int money) {
            this.money = money;
        }
    }

    void calcMoney(Map<String, Person> map, String name, int money) {
        if(money == 0 || "-".equals(name))
            return;

        int fee = (int)(money * 0.1);

        Person cur = map.get(name);
        int curMoney = cur.getMoney();
        cur.setMoney(curMoney + money - fee);

        calcMoney(map, cur.getParent(), fee);
    }

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = new int[enroll.length];

        Map<String, Person> map = new HashMap<>();
        map.put("-", new Person("-"));

        for(int i = 0; i < enroll.length; i++) {
            map.put(enroll[i], new Person(referral[i]));
        }

        for(int i = 0; i < seller.length; i++) {
            calcMoney(map, seller[i], amount[i] * 100);
            System.out.println();
        }

        for(int i = 0; i < enroll.length; i++) {
            answer[i] = map.get(enroll[i]).getMoney();
        }

        return answer;
    }
}