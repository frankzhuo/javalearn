package t6_Algo;

public class Topic1 {

    /*
        递归思想：
            函数内部调用函数自身

            用 栈 + while循环 可以模拟递归（相当于把递归的线程栈，放到堆中存储）

        数组 & 链表
            数组：连续内存，预先分配，固定长度，随机查询性能较高。
            链表：离散存储，插入删除性能较高。

        八大排序算法（重点）：
            https://www.cnblogs.com/hokky/p/8529042.html

            空间复杂度、时间复杂度、算法简要描述、实现思路、优缺点 -> 使用场景

            O(n) 的含义
                O(n)表示时间复杂度
                复杂度可一个表示为一个多项式f(n)，n是数据集大小，O(n)就是这个多项式的最高次，如O(n^2), O(logn), O(n)等

            最重要
                快速排序：
                    快速排序的基本思想是，选定一个中间值，通过一轮的排序将序列分割成独立的两部分，其中一部分序列的关键字（这里主要用值来表示）均比中间值小；
                    另一部分均比中间值大；
                    继续对长度较短的序列进行同样的分割，最后到达整体有序。
                    在排序过程中，由于已经分开的两部分的元素不需要进行比较，故减少了比较次数，降低了排序时间。

                    https://blog.csdn.net/adusts/article/details/80882649（有图）
                    https://www.cnblogs.com/surgewong/p/3381438.html（描述）


        查找算法：
            二分查找：要求有序
                // 查找第一个相等的元素
                static int findFirstEqual(int[] array, int key) {
                    int left = 0;
                    int right = array.length - 1;

                    // 这里必须是 <=
                    while (left <= right) {
                        int mid = (left + right) / 2;
                        if (array[mid] >= key) {
                            right = mid - 1;
                        }
                        else {
                            left = mid + 1;
                        }
                    }
                    if (left < array.length && array[left] == key) {
                        return left;
                    }

                    return -1;
                }

        树：
            树的表示
                class Node<T>{
                    Node left;
                    Node right;
                    T val;
                }
            二叉树的遍历
                https://blog.csdn.net/qq_40772692/article/details/79343914
                前（先）序遍历：
                    （1）访问根节点；（2）采用先序递归遍历左子树；（3）采用先序递归遍历右子树；

                中序遍历：
                    （1）按照左子树；（2） 根节点； （3）右子树的顺序访问
                后续遍历：
                    （1）采用后序递归遍历左子树；（2）采用后序递归遍历右子树；（3）访问根节点；

                三种遍历实现：递归调用即可
                    https://www.cnblogs.com/ky415/p/7745273.html

                https://blog.csdn.net/m0_37698652/article/details/79218014
                （二叉树，相互求法，笔试爱考，算法题也可能考）

            最小生成树（了解吧）
                https://blog.csdn.net/qq_35644234/article/details/59106779
                Prim算法
                    首先就是从图中的一个起点a开始，把a加入U集合，然后，寻找从与a有关联的边中，权重最小的那条边并且该边的终点b在顶点集合：
                    （V-U）中，我们也把b加入到集合U中，并且输出边（a，b）的信息，这样我们的集合U就有：{a,b}，然后，我们寻找与a关联和b关联的边中，
                    权重最小的那条边并且该边的终点在集合：（V-U）中，我们把c加入到集合U中，并且输出对应的那条边的信息，这
                    样我们的集合U就有：{a,b,c}这三个元素了，依此类推，直到所有顶点都加入到了集合U。

                Kruskal算法
                    （1）将图中的所有边都去掉。
                    （2）将边按权值从小到大的顺序添加到图中，保证添加的过程中不会形成环
                    （3）重复上一步直到连接所有顶点，此时就生成了最小生成树。这是一种贪心策略。


                Prim算法的时间复杂度都是和边无关的，都是O(n*n)，所以它适合用于边稠密的网建立最小生成树。
                Kruskal算法恰恰相反，它的时间复杂度为：O(eloge)，其中e为边的条数，因此它相对Prim算法而言，更适用于边稀疏的网。

        图（了解吧）：
            图的表示：
                Map<String, List<String>> graph = new HashMap<String, List<String>>();
                邻接图等
                关键方法：获取相邻节点

            图的遍历：
                https://www.cnblogs.com/honeybee/p/8557159.html
                深度优先遍历：
                    借助栈

                广度优先遍历：
                    借助队列


        大数据算法（算是重点，要理解）：
            归并排序：
                https://blog.csdn.net/michellechouu/article/details/47002393

            布隆过滤器：
                算法：
                    1. 首先需要k个hash函数，每个函数可以把key散列成为1个整数
                    2. 初始化时，需要一个长度为n比特的数组，每个比特位初始化为0
                    3. 某个key加入集合时，用k个hash函数计算出k个散列值，并把数组中对应的比特位置为1
                    4. 判断某个key是否在集合时，用k个hash函数计算出k个散列值，并查询数组中对应的比特位，如果所有的比特位都是1，认为在集合中。

                优点：不需要存储key，节省空间

                缺点：
                    1. 算法判断key在集合中时，有一定的概率key其实不在集合中
                    2. 无法删除

                guava提供了布隆过滤器实现
                    网页URL的去重（爬虫使用？），垃圾邮件的判别（实现白名单、黑名单？），集合重复元素的判别，
                    防止缓存穿透

            HashMap

            Map Reduce



     */

}
