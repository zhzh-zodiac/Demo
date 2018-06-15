package example;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *
 *
 * @author zhangzhen
 * @version C10 2018年06月15日 
 * @since SDP V300R003C10
 */
public class ForkJoinMergeSort{

    public String[] sort(String[] rawArray) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSort(rawArray));
        return rawArray;
    }

    /**
     * 使用Fork/join的方式进行归并排序，充分利用cpu
     *
     * @author zhangwensha
     *
     */
    private static class MergeSort extends RecursiveAction
    {

        private static final long serialVersionUID = 425572392953885545L;
        private String[] intArr;

        public MergeSort(String[] intArr) {
            this.intArr = intArr;
        }

        @Override
        protected void compute() {
            if (intArr.length > 1) {
                // 如果数组长度大于1就分解称两份
                String[] leftArray = Arrays.copyOfRange(intArr, 0, intArr.length / 2);
                String[] rightArray = Arrays.copyOfRange(intArr, intArr.length / 2, intArr.length);

                // 这里分成两份执行
                invokeAll(new MergeSort(leftArray), new MergeSort(rightArray));

                // 合并且排序
                merge(leftArray, rightArray, intArr);
            }
        }

        /**
         * 合并排序
         *
         * @param leftArray
         * @param rightArray
         * @param intArr
         */
        private void merge(String[] leftArray, String[] rightArray, String[] intArr) {

            // i：leftArray数组索引，j：rightArray数组索引，k：intArr数组索引
            int i = 0, j = 0, k = 0;
            while (i < leftArray.length && j < rightArray.length) {
                // 当两个数组中都有值的时候，比较当前元素进行选择
                if (leftArray[i].compareTo(rightArray[j])<0) {
                    intArr[k] = leftArray[i];
                    i++;
                } else {
                    intArr[k] = rightArray[j];
                    j++;
                }
                k++;
            }

            // 将还剩余元素没有遍历完的数组直接追加到intArr后面
            if (i == leftArray.length) {
                for (; j < rightArray.length; j++, k++) {
                    intArr[k] = rightArray[j];
                }
            } else {
                for (; i < leftArray.length; i++, k++) {
                    intArr[k] = leftArray[i];
                }
            }
        }

    }
}
