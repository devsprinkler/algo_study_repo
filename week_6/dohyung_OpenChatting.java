import java.util.*;

class Solution {
    // 가상 닉네임
    // 채팅방 들어오고 나갈 때 메시지 출력
    
    // 닉네임 변경
    // 1. 채팅방을 나간 후, 새로운 닉네임
    // 2. 채팅방에서 닉네임 변경
    // 닉네임을 변경할 대는 기존에 채팅방에 출력되어 있던 메시지의 닉네임도 전부 변경
    // 중복 닉네임 허용

    // 모든 기록이 처리된 후, 최종적으로 방을 개설한 사람이 보게되는 메시지를 문자열 배열 형태로 return
    
    // record 길이 최대 10만
    // user id로 구분
    // [Enter uid nickname]
    // Enter, Leave, Change
    
    // uid, nickname 은 대문자, 소문자를 구별.
    // -> 길이 최대 10
    
    // enter
    // 1. 메시지 추가
    // 2. 기존에 같은 uid 있는 지 확인
    // change
    // 1. 기존에 같은 uid 확인
    // leave
    // 1. 메시지 추가
    
    // record 파싱
    // 같은 uid 의 위치? -> hashMap

    // uid, index
    static HashMap<String, ArrayList<Integer>> map = new HashMap<>();
    static ArrayList<Result> result = new ArrayList<>();

    public static String[] solution(String[] record) {
        StringTokenizer st;

        for (int i = 0; i < record.length; i++) {
            st = new StringTokenizer(record[i]);

            String op = st.nextToken();
            String uid = st.nextToken();

            if (op.equals("Enter")) {
                String nickname = st.nextToken();
                addMessage(op, uid, nickname);
                changeNickname(uid, nickname);
            }
            else if (op.equals("Change")) {
                String nickname = st.nextToken();
                changeNickname(uid, nickname);
            }
            else {
                // Leave
                String nickname = result.get(map.get(uid).get(0)).nickname;
                addMessage(op, uid, nickname);
            }
        }
        
        return returnAnswer();
    }

    static String[] returnAnswer() {
        String[] answer = new String[result.size()];

        for (int i = 0; i < answer.length; i++) {
            answer[i] = result.get(i).nickname + result.get(i).message;
        }

        return answer;
    }

    static void addMessage(String op, String uid, String nickname) {
        if (op.equals("Enter")) {
            result.add(new Result(nickname, "님이 들어왔습니다."));
        }
        else {
            result.add(new Result(nickname, "님이 나갔습니다."));
        }

        if (!map.containsKey(uid))
            map.put(uid, new ArrayList<>());
        // 새로운 위치 추가
        map.get(uid).add(result.size() - 1);
    }

    static void changeNickname(String uid, String nickname) {
        // 기존 uid 닉네임 변경
        if (map.containsKey(uid)) {
            ArrayList<Integer> arr = map.get(uid);

            for (int i = 0; i < arr.size(); i++) {
                int idx = arr.get(i);

                result.get(idx).nickname = nickname;
            }
        }
    }

    static class Result {
        String nickname;
        String message;

        public Result(String nickname, String message) {
            this.nickname = nickname;
            this.message = message;
        }
    }
}
