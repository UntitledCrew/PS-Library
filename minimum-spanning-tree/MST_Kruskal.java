import java.util.*;
import java.io.*;

/**
 * @Author by Sangwoo
 * @Part MST using Kruskal
 */
public class MST_Kruskal {
    private static int V, E;
    private static PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.e, o2.e));

    private static class Edge{
        int u, v, e;
        Edge(int u, int v, int e) {
            this.u = u;
            this.v = v;
            this.e = e;
        }
    }

    private static class DisjointSet{
        int[] p;
        DisjointSet() {
            this.init();
        }
        private void init() {
            p = new int[V + 1];
            for (int i = 1; i <= V; i++) {
                p[i] = i;
            }
        }
        private int find(int n) {
            if (n == p[n]) return n;
            return p[n] = find(p[n]);
        }
        private void merge(int n, int m) {
            n = find(n);
            m = find(m);
            p[m] = n;
        }
    }

    public static void main(String[] args) throws IOException {
        input();
        int n = 0;
        int ans = 0;
        DisjointSet ds = new DisjointSet();
        while (!pq.isEmpty() && n <= V - 1) {
            Edge edge = pq.poll();
            if (ds.find(edge.u) != ds.find(edge.v)) {
                ans += edge.e;
                n += 1;
                ds.merge(edge.u, edge.v);
            }
        }
        System.out.println(ans);
    }
    private static void input() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(input.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            pq.add(new Edge(n, m, w));
        }
    }
}