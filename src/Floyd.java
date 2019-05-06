import java.util.Arrays;

public class Floyd {

    private final static int m = Integer.MAX_VALUE; // 用极大值表示不能到达的边

    public static void main(String[] args) {
        int[][] g = initGraph();
        int len = g.length;
        int[][] r = floyd(g);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int d = r[i][j];
                if (m == d)
                    System.out.println(i + " -> " + j + " 不可达" );
                else
                    System.out.println(i + " -> " + j + " " + d);

            }
        }
    }

    /**
     * 初始化图 二维数组
     *
     * @return
     */
    private static int[][] initGraph() {
        int[][] g = new int[][]{
                new int[]{0, 12, m, m, m, 16, 14},
                new int[]{12, 0, 10, m, m, 7, m},
                new int[]{m, 10, 0, 3, 5, 6, m},
                new int[]{m, m, 3, 0, 4, m, m},
                new int[]{m, m, 5, 4, 0, 2, 8},
                new int[]{16, 7, 6, m, 2, 0, 9},
                new int[]{14, m, m, m, 8, 9, 0}
        };
        return g;
    }

    private static int[][] floyd(int[][] g) {
        int len = g.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                for (int k = 0; k < len; k++) {
//                    因为使用最大数来表示不可达 因此这里要判断是否其中一边不可达
                    int tmp = (g[j][i] == m || g[i][k] == m) ? m : g[j][i] + g[i][k];
                    g[j][k] = Math.min(tmp, g[j][k]);
                }
            }
        }
        return g;
    }
}
