import java.util.ArrayDeque;
import java.util.Queue;

public class BellmenFord {

    private final static int m = Integer.MAX_VALUE; // 用极大值表示不能到达的边

    public static void main(String[] args) {

        int[][] g = initGraph();

        int startPoint = 2;

        int[] dis = bellmenFord(g, startPoint);

//        输出结果
        System.out.println("起始点：" + ++startPoint);
        for (int i = 0; i < dis.length; i++) {
            int item = dis[i];
            if (item == m)
                System.out.printf("%d:无法到达 ", i);
            else
                System.out.printf("%d:%d ", i, item);
        }
    }

    /**
     * 初始化图 二维数组
     *
     * @return
     */
    private static int[][] initGraph() {
        int[][] g = new int[][]{
                new int[]{0, 1, 12, m, m, m},
                new int[]{m, 0, 9, 3, m, m},
                new int[]{m, m, 0, m, 5, m},
                new int[]{m, m, 4, 0, 13, 15},
                new int[]{m, m, m, m, 0, 4},
                new int[]{m, m, m, m, m, 0},
        };
        return g;
    }

    /**
     * @param startPoint 起始的点
     * @param graph
     * @return
     */
    private static int[] bellmenFord(int[][] graph, int startPoint) {
        int graphSize = graph.length;
        int[] dis = new int[graphSize];
        for (int i = 0; i < dis.length; i++) {
            dis[i] = i == startPoint ? 0 : m;
        }
        if (startPoint >= graphSize)
            return dis;
        Queue<Node> q = new ArrayDeque<>();
        q.add(new Node(startPoint, startPoint));
        boolean[] visit = new boolean[graphSize];
        while (!q.isEmpty()) {
            Node i = q.remove();
            int self = i.self;
            int prePoint = i.prePoint;
            int[] iLink = graph[self];
//            松弛操作
            dis[i.self] = Math.min(graph[prePoint][self] + dis[prePoint], dis[self]);
//            将当前节点后继节点加入队列
            for (int j = 0; j < iLink.length; j++) {
//                如果是自己则不加入队列
                if (j == self)
                    continue;
                int d = iLink[j];
                if (d != m) {
                    if (!visit[j]) { // 没有访问过的节点添加入队列中
                        visit[j] = true;
                        q.add(new Node(j, self));
                    }else { // 已经访问过的节点进行一次松弛操作
                        dis[j] = Math.min(graph[self][j] + dis[self], dis[j]);
                    }
                }
            }
        }
        return dis;
    }

    private static class Node{
        public int prePoint;
        public int self;

        Node(int self, int prePoint){
            this.self = self;
            this.prePoint = prePoint;
        }
    }
}
