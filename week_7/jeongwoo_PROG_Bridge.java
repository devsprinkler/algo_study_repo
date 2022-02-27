class Solution {
    boolean binarySearch(int[] stones, int k, int mid) {
        int count = 0;

        for (int stone : stones) {
            if (stone < mid) {
                count += 1;
                if (count >= k) {
                    return false;
                }
            } else {
                count = 0;
            }
        }

        return true;
    }
    
    public int solution(int[] stones, int k) {
        int answer = 0;

        int left = 0;
        int right = 200000000;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (binarySearch(stones, k, mid)) {
                left = mid + 1;

                answer = Math.max(answer, mid);
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }
}
