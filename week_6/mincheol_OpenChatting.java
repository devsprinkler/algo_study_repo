import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static String[] solution(String[] record) {
        HashMap<String, String> hm = new HashMap<>();
        String[] cur;
        for (int i = 0; i < record.length; i++) {
            cur = record[i].split(" ");
            if (cur.length > 2) {
                hm.put(cur[1], cur[2]);
            }
        }

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < record.length; i++) {
            cur = record[i].split(" ");
            if (cur[0].charAt(0) == 'E') {
                list.add(hm.get(cur[1]) + "님이 들어왔습니다.");
            } else if (cur[0].charAt(0) == 'L') {
                list.add(hm.get(cur[1]) + "님이 나갔습니다.");
            }
        }
        String[] answer = list.toArray(new String[list.size()]);
        return answer;
    }

    public static void main(String[] args) {
        String[] record = {"Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan"};
        String[] answer = solution(record);
        for (int i = 0; i < answer.length; i++) {
            System.out.println(answer[i]);
        }
    }
}
