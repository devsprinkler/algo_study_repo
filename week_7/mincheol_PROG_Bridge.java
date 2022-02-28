public class Main {
    public static int binarySearch(int[] stones, int k, int min, int max) {
        if (min == max) return min;
        int mid = Math.floorDiv(max + min, 2);
        int cnt = 0;
        for (int i = 0; i < stones.length; i++) {
            if (cnt == k) break;
            if (stones[i] - mid > 0) cnt = 0;
            else cnt++;
        }
        if (cnt == k) return binarySearch(stones, k, min, mid);
        else return binarySearch(stones, k, mid + 1, max);
    }

    public int solution(int[] stones, int k) {
        return binarySearch(stones, k, 1, 200000000);
    }
}
