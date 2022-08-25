import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    // n 명
    // 집과 사무실은 수평선 상 서로 다른 점
    
    // 서로 다른 사람의 집 혹은 사무실의 위치가 겹칠 수 있다.
    // 일직선 상의 어떤 두 점을 잇는 철로를 건설하여, 기차를 운행하려 한다.
    // 철로의 길이는 d로 정해져 있다.
    
    // 집, 사무실의 위치 모두 철로 선분에 포함되는 사람들의 수가 최대가 되도록
    // n개의 정수 쌍 = 집, 사무실 위치
    
    // 사람수 10만
    // 좌표 = -1억 ~ 1억이하 서로 다른 정수
    // 철로의 길이 최대 2억

    // 가장 많이 포함되는 지점을 찾음 - -> +
    // 집인지 사무실인지는 중요하지 않음
    // 다른 사람끼리의 집, 사무실은 겹칠 수 있음
    // 좌표 순서대로 정렬하여 추가
    // -> 같은 좌표일 경우 같은 숫자에 추가 
    // -> 그냥 클래스로 만들어서 정렬
    
    // 라인 스위핑 정렬된 자료에서 선을 한번만 훑으면서 최종 결과를 찾는 방식
    // 우선순위큐가 자주 사용
    
    // 정수 쌍을 도착 지점 기준 오름차순으로 정렬.
    // -> 도착 지점이 같을 경우, 출발 지점의 오름차순으로 정렬
    // 도착 지점 기준으로 선분과 비교 pq로 관리
    // -> 옮기면서 빠지는 걸 pq에서 pop
    // -> 만약 선분의 길이보다 긴 구간 이면 해당 도착지점은 skip

    static int N;
    static int D;
    static ArrayList<Point> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int h, o;

            h = Integer.parseInt(st.nextToken());
            o = Integer.parseInt(st.nextToken());

            if (h < o)
                list.add(new Point(h, o));
            else
                list.add(new Point(o, h));
        }

        st = new StringTokenizer(br.readLine());
        D = Integer.parseInt(st.nextToken());

        Collections.sort(list);

        // 선분 비교
        int answer = 0;
        PriorityQueue<Point> pq = new PriorityQueue<>(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Integer.compare(o1.start, o2.start);
            }
        }); // pq는 출발점 기준 sorting 인데 실수함

        for (int i = 0; i < list.size(); i++) {
            Point p = list.get(i);
            
            if (p.dest - p.start > D) continue;

            // 선분에 포함함
            pq.add(p);
            
            // 제외되는 점 제외
            while (!pq.isEmpty()) {
                Point t = pq.peek();

                if (p.dest - t.start > D)
                    pq.poll();
                else
                    break;
            }
            
            // 최대 수 기록
            answer = Math.max(answer, pq.size());
        }

        System.out.println(answer);
    }
    
    static class Point implements Comparable<Point> {
        int start, dest;

        public Point(int start, int dest) {
            this.start = start;
            this.dest = dest;
        }

        @Override
        public int compareTo(Point o) {
            if (this.dest == o.dest) {
                return Integer.compare(this.start, o.start);
            }
            else {
                return Integer.compare(this.dest, o.dest);
            }
        }
    }
}
