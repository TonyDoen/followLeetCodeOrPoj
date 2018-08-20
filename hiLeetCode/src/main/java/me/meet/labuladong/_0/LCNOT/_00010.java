package me.meet.labuladong._0.LCNOT;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class _00010 {
    private _00010() {
    }
    /*
     * Rapidly-exploring Random Tree (RRT) 算法简介
     * Rapidly-exploring Random Tree（RRT）是一种用于解决路径规划问题的算法，尤其适合处理高维空间和复杂约束的情景。
     * 该算法由Steven M. LaValle在1998年提出，并迅速成为了机器人运动规划领域的一个重要工具。
     *
     * RRT基本原理：
     * 随机采样：RRT通过在搜索空间中随机采样点来逐步构建一个树形结构。
     * 树的增长：每次采样后，都会选择树中离采样点最近的节点作为父节点，并在这个方向上进行延伸，创建一个新的节点。
     * 连续探索：通过不断重复这个过程，RRT能够快速探索整个搜索空间。
     * 碰撞检测：构建过程中需要进行碰撞检测，确保生成的路径避开障碍物。
     *
     * RRT的特点：
     * 快速性：由于采样的随机性，RRT可以迅速探索出大范围的可行路径。
     * 适应性：能够很好地处理复杂的环境和非凸障碍物。
     * 非最优性：标准的RRT算法生成的路径通常不是最短的，但是变种如RRT*可以对路径进行优化，寻求更优解。
     * RRT在无人驾驶车辆中的应用
     * 在无人驾驶车辆的路径规划中，RRT可以用来高效地搜索从起点到目标点的可行路径，并且能够处理动态变化的环境和各种约束条件。
     *
     * 应用步骤：
     * 定义搜索空间：这包括车辆周围的环境，以及可能存在的动静态障碍物。
     * 初始化RRT：从无人车当前位置作为根节点开始构建RRT。
     * 随机采样：在搜索空间内随机采样新的点。
     * 找到最近节点：在树中找到距离新采样点最近的节点。
     * 路径延展：在最近节点和采样点之间按照某个步长延展新节点，并进行碰撞检测。
     * 插入新节点：如果新节点没有与障碍物发生碰撞，则将其作为子节点加入到树中。
     * 重复执行：重复步骤3至6，直到找到满足目标条件的路径或达到预设的迭代次数。
     * 路径提取：从树中提取从起始点到目标点的路径。
     * 路径优化（可选）：使用RRT*或其他算法对找到的路径进行优化，以使路径更加平滑、安全或更短等。
     * 
     * 处理复杂环境：
     * 动态环境：在动态环境中，RRT可以实时更新树，以响应环境的变化。
     * 多目标规划：RRT可以被扩展为同时寻找多个目标的路径，例如，在停车场中规划一系列的停车位。
     * 车辆动力学约束：通过考虑车辆的转向角度限制、加速度等动力学特性，RRT能够生成实际可行的路径。
     *
     * 总结：
     * RRT及其变体因其简单、灵活且有效，常被用于无人驾驶车辆的路径规划。然而，RRT算法在实际应用中还需要针对具体场景和车辆特性进行调整和优化，以满足实时性、安全性和高效性的要求。
     *
     * 参考地址：
     * https://medium.com/geekculture/rapidly-exploring-random-trees-rrt-and-their-much-nicer-properties-7b5d983e5b18
     * https://en.wikipedia.org/wiki/Rapidly_exploring_random_tree
     * https://www.youtube.com/watch?v=Ob3BIJkQJEw
     */

    // 节点类
    private static class Node {
        Point2D point;
        Node parent;

        public Node(Point2D point) {
            this.point = point;
            this.parent = null;
        }
    }

    // RRT算法类
    public static class RRT {
        private static final double STEP_SIZE = 10.0;
        private static final int MAX_ITERATIONS = 1000;

        private List<Node> tree;
        private Point2D start;
        private Point2D goal;
        private Random random;
        private double maxX;
        private double maxY;

        public RRT(Point2D start, Point2D goal, double maxX, double maxY) {
            this.tree = new ArrayList<>();
            this.start = start;
            this.goal = goal;
            this.random = new Random();
            this.maxX = maxX;
            this.maxY = maxY;
            this.tree.add(new Node(start)); // 将起始点作为第一个节点加入树中
        }

        // 执行RRT算法
        public void run() {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                Point2D randPoint = getRandomPoint();
                Node nearestNode = getNearestNode(randPoint);

                if (nearestNode != null) {
                    Node newNode = extendTree(nearestNode, randPoint);
                    if (newNode != null && newNode.point.distance(goal) < STEP_SIZE) {
                        System.out.println("Goal reached!");
                        return;
                    }
                }
            }
            System.out.println("Unable to reach goal after " + MAX_ITERATIONS + " iterations.");
        }

        // 在规定范围内随机生成一个点
        private Point2D getRandomPoint() {
            double x = random.nextDouble() * maxX;
            double y = random.nextDouble() * maxY;
            return new Point2D.Double(x, y);
        }

        // 找到树中距离给定点最近的节点
        private Node getNearestNode(Point2D point) {
            Node nearestNode = null;
            double nearestDistance = Double.MAX_VALUE;
            for (Node node : tree) {
                double distance = node.point.distance(point);
                if (distance < nearestDistance) {
                    nearestNode = node;
                    nearestDistance = distance;
                }
            }
            return nearestNode;
        }

        // 向树中增加新节点，步长受STEP_SIZE限制
        private Node extendTree(Node nearestNode, Point2D randPoint) {
            double dx = randPoint.getX() - nearestNode.point.getX();
            double dy = randPoint.getY() - nearestNode.point.getY();
            double norm = Math.sqrt(dx * dx + dy * dy);

            if (norm < STEP_SIZE) return null;

            double newX = nearestNode.point.getX() + (dx * STEP_SIZE / norm);
            double newY = nearestNode.point.getY() + (dy * STEP_SIZE / norm);
            Point2D newPoint = new Point2D.Double(newX, newY);

            // 简化版的碰撞检测（这里省略真正的碰撞检测）
            if (!isCollision(newPoint)) {
                Node newNode = new Node(newPoint);
                newNode.parent = nearestNode;
                tree.add(newNode);
                return newNode;
            }
            return null;
        }

        // 检查是否发生碰撞（这里仅提供方法框架，并未实现实际碰撞检测逻辑）
        private boolean isCollision(Point2D point) {
            // 实际应用中需要实现判断点是否与环境中的障碍物发生碰撞
            return false;
        }
    }

    // 主函数，运行RRT算法示例
    public static void main(String[] args) {
        // 注意事项：
        // 这个例子仅展示了RRT算法的框架结构，实际应用中需要添加碰撞检测逻辑以确保路径不会穿过障碍物。
        // isCollision 方法需要根据具体环境进行实现，这里未提供具体实现。
        // 在真实的无人驾驶场景中，RRT的实现会更复杂，包括处理车辆动力学模型、考虑环境变化、路径后处理等。
        // 为了实现更优的路径规划，可以使用RRT的变种，比如RRT*算法，它能够回溯并优化路径得到接近最短路径的解。
        Point2D start = new Point2D.Double(0, 0); // 起始点
        Point2D goal = new Point2D.Double(100, 100); // 目标点
        double maxX = 200; // X最大值
        double maxY = 200; // Y最大值

        RRT rrt = new RRT(start, goal, maxX, maxY);
        rrt.run();
    }
}
