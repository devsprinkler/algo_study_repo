import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m, cntSet;
	static int[][]parent;
    
    static void init() {
		parent = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				parent[i][j] = m * i + j;
			}
		}
		cntSet = n * m;
	}
    
    static int find(int x) {
		if(parent[x / m][x % m] != x)
            return parent[x / m][x % m] = find(parent[x / m][x % m]);
		return x;
	}
	
	static void union(int x, int y) {
		int rx = find(x);
        int ry = find(y);
        
		if(rx != ry) {
			parent[rx / m][rx % m] = ry;
			cntSet--;
		}
	}
    
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
        
		init();
		
		for (int i = 0; i < n; i++) {
			String s =br.readLine();
			for (int j = 0; j < m; j++) {
                if (s.charAt(j) == 'U')
                    union(i * m + j, (i - 1) * m + j);
                else if (s.charAt(j) == 'D')
                    union(i * m + j, (i + 1) * m + j);
                else if (s.charAt(j) == 'L')
                    union(i * m + j, i * m + j - 1);
                else if (s.charAt(j) == 'R')
                    union(i * m + j, i * m + j + 1);
			}
		}
		
		System.out.println(cntSet);
		
	}
}
