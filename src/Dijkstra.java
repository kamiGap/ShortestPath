import java.util.*;

public class Dijkstra {

    private final static int m = Integer.MAX_VALUE; // 用极大值表示不能到达的边

    public static void main(String[] args) {
        int[][] g = initGraph();
        int startPoint = 2;
        int[] dis = dijkstra(g, startPoint);
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
     *
     * @param graph
     * @param startPoint
     * @return
     */
    public static int[] dijkstra(int[][] graph, int startPoint) {
        int graphSize = graph.length;
        int[] dis = new int[graphSize];
        for (int i = 0; i < dis.length; i++) {
            dis[i] = i == startPoint ? 0 : m;
        }
        if (startPoint >= graphSize)
            return dis;
        MinDisComparator comparator = new MinDisComparator();
        PriorityQueue<MinDisNode> minDisQueue = new PriorityQueue<>(comparator);
        minDisQueue.add(new MinDisNode(startPoint, 0)); // 将指定点加入队列
        while (!minDisQueue.isEmpty()) { // 队列中不存在需要探索的点时结束
            // 取出队列中最小的值 并认为此时得到的路径就是它距离指定点的最短路径
            MinDisNode markPoint = minDisQueue.poll();
            int index = markPoint.index;
            int pointDis = dis[index] = markPoint.dis;
            // 依次遍历这个点的邻接点
            int[] iLink = graph[index];
            for (int i = 0; i < iLink.length; i++) {
                int d = iLink[i];
                if (d != m){
                    int tmpD = pointDis + d;
                    if (dis[i] == m) // 如果这个点还未到达就将其加入队列
                        minDisQueue.add(new MinDisNode(i, tmpD));
                    else { // 如果这个点已经到达过 则进行松弛操作更新它距离指定点的距离
                        updateMinDisList(minDisQueue, i, tmpD); // 更新其在队列中的值
                        dis[i] = dis[i] > tmpD ? tmpD : dis[i]; // 更新其在距离数组的值
                    }
                }
            }
        }
        return dis;
    }

    public static void updateMinDisList (PriorityQueue<MinDisNode> minDisQueue, int index, int dis){
        for (MinDisNode node : minDisQueue){
            if (node.index == index) {
                node.dis = node.dis > dis ? dis : node.dis;
                break;
            }
        }
    }

    public static class MinDisNode {
        public int index;
        public int dis;

        public MinDisNode(int index, int dis) {
            this.index = index;
            this.dis = dis;
        }
    }

    public static class MinDisComparator implements Comparator<MinDisNode> {
        @Override
        public int compare(MinDisNode o1, MinDisNode o2) {
            return o1.dis - o2.dis;
        }
    }


}
