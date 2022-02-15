import java.util.*;

class Solution {
    class User {
        String action, id;
        
        User(String action, String id) {
            this.action = action;
            this.id = id;
        }
    }
    
    public String[] solution(String[] record) {
        List<String> answer = new ArrayList<>();
        Map<String, String> userInfo = new HashMap<>();
        StringTokenizer st = new StringTokenizer("");
        
        int length = record.length;
        List<User> chatInfo = new ArrayList<>();
        
        for(String r : record) {
            st = new StringTokenizer(r);
            
            String action = st.nextToken();
            String id = st.nextToken();
            
            if(!"Leave".equals(action)) {
                String name = st.nextToken();
                
                userInfo.put(id, name);
            }
            
            if(!"Change".equals(action)) {
                chatInfo.add(new User(action, id));
            }
        }
        
        length = chatInfo.size();
        
        for(User user : chatInfo) {
            String action = user.action.equals("Enter") ? "들어왔습니다." : "나갔습니다.";
            String id = user.id;
            String name = userInfo.get(id);
            
            answer.add(String.format("%s님이 %s", name, action));
        }
        
        length = answer.size();
        
        return answer.toArray(new String[length]);
    }
}
