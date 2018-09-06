package util;

/**
 * @author JIE WU
 * @create 2018-04-10
 * @desc 插入排序
 **/
public class InsertSort {
    /**
     * arry 的0-(index-1)之间是有序的。从小到大排序
     *
     * @param arry  排序的数组
     * @param index 要插入的下标
     * @return
     */
    public static int[] sort(int[] arry, int index) {
        int indexNum = arry[index];
        for (int i = index - 1; i >= 0; i--) {
            // 若数组大于等于indexNum则往右移动一个
            // 1 - 3 - 5- 9 插入4
            if (arry[i] >= indexNum) {
                arry[i + 1] = arry[i];
                continue;
            }
            arry[i + 1] = indexNum;
            break;
        }
        return arry;
    }

    public static void main(String[] args) {
        int[] arry = {1, 3, 5, 9, 4};
        sort(arry, 4);
        for (int i = 0; i < arry.length; i++)
            System.out.println(arry[i]);
    }
}
