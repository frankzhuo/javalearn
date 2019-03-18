package t9_other;

public class Topic2 {
    /*
        智力题 & 开放题：
            1.老鼠吃毒药
                1000瓶药，一瓶是毒药，一毫克就死，七天死亡；10只老鼠，七天找出那瓶毒药。

            2.超大数据排序问题
                内存不够用 --> 归并排序

            3.短URL问题
                https://www.zhihu.com/question/29270034/answer/46446911

            4.赛马问题
                http://tieba.baidu.com/p/2169941931
                http://www.cnblogs.com/louffy/archive/2012/04/09/2994100.html

            5.句子倒置
                快速的将一个句子倒置，如I am a boy  --> boy a am I

            6.找中位数
                怎么快速的找出一组数字中的中位数

            7.一百层楼扔鸡蛋
                https://blog.csdn.net/qq_38316721/article/details/81351297

            8.链表问题
                https://www.cnblogs.com/dancingrain/p/3405197.html
                1.给一个单链表，判断其中是否有环的存在；
                    快慢指针 是否相遇

                2.如果存在环，找出环的入口点；
                    我们就可以分别用一个指针（ptr1, prt2），同时从head与slow和fast的相遇点出发，每一次操作走一步，
                    直到ptr1 == ptr2，此时的位置也就是入口点！

                3.如果存在环，求出环上节点的个数；
                    从相遇点开始slow和fast继续按照原来的方式向前走slow = slow -> next;
                    fast = fast -> next -> next；直到二者再次项目，此时经过的步数就是环上节点的个数 。

                4.如果存在环，求出链表的长度；
                    链表长度L = 起点到入口点的距离 + 环的长度r;

                5.如果存在环，求出环上距离任意一个节点最远的点（对面节点）；
                    同样使用上面的快慢指针的方法，让slow和fast都指向ptr0，每一步都执行与上面相同的操作（slow每次跳一步，fast每次跳两步），
                    当fast = ptr0或者fast = prt0->next的时候slow所指向的节点就是ptr0的”对面节点“。

                6.（扩展）如何判断两个无环链表是否相交；
                7.（扩展）如果相交，求出第一个相交的节点
                    假设有连个链表listA和listB，如果两个链表都无环，并且有交点，
                    那么我们可以让其中一个链表（不妨设是listA）的为节点连接到其头部，这样在listB中就一定会出现一个环。

                    因此我们将问题6和7分别转化成了问题1和2.

     */
}
