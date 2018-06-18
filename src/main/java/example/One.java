package example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class One {
    String message = "foo";

    private static int threadCount = 5;

    private static final ExecutorService exec = Executors.newCachedThreadPool();

    public String foo() {
        File file = new File("in.txt");
        FileInputStream is;
        List<String> list = new ArrayList<String>();
        try {
            if (file.length() != 0) {
                is = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
                reader.close();
                is.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        int listSize = list.size();
        if(threadCount>listSize)
        {
            threadCount=listSize;
        }

        List<List<String>> tempList = averageAssign(list,threadCount);

        List<FutureTask<List<String>>> taskList = new ArrayList<>();
        for(List<String> sList : tempList)
        {
            FutureTask<List<String>> task = new FutureTask<>(() -> {
                return sort(sList);
            });
            taskList.add(task);
            exec.submit(task);
        }

        List<List<String>> otempList = new ArrayList<>();
        for (FutureTask<List<String>> task : taskList)
        {
            List<String> olist = null;
            try
            {
                olist = task.get(25000, TimeUnit.MILLISECONDS);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            otempList.add(olist);
        }

        List<String> resultList = mergeSort(otempList);




        List outList = resultList;
        try (PrintWriter pw = new PrintWriter (new FileWriter("out.txt")))
        {
            for (int i = 0; i < outList.size(); i++)
            {
                System.out.println(outList.get(i));
                pw.println(outList.get(i));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return message;
    }

    public static List<String> mergeSort(List<List<String>> listList)
    {
        List<String> tlist = null;
        int low=0;
        int high=listList.size();
        if(listList.size()>1)
        {
            int mid = (low+high)/2;
            if(low<high){
                List<List<String>> leftLists = listList.subList(low,mid);
                List<List<String>> rightLists = listList.subList(mid,high);
                List<String> leftList = mergeSort(leftLists);
                List<String> rightList = mergeSort(rightLists);
                //左右归并
                tlist = merge(leftList,rightList);
            }
        }
        else
        {
            if(listList.size()>0)
            tlist = listList.get(0);
        }
        return tlist;
    }

    public static List<String> merge(List<String> leftList ,List<String> rightList) {
        //int[] temp = new int[high-low+1];
        List<String> resultList = new ArrayList<>();
        int leftSize= leftList!=null?leftList.size():-1;
        int rightSize = rightList!=null? rightList.size():-1;
        //int k=0;
        int i=0;
        int j=0;
        // 把较小的数先移到新数组中
        while(i<leftSize && j<rightSize){
            if(leftList.get(i).compareTo(rightList.get(j))>0){
                resultList.add(rightList.get(j++));
            }else{
                resultList.add(leftList.get(i++));
            }
        }
        // 把左边剩余的数移入数组
        while(i<leftSize){
            resultList.add(leftList.get(i++));
        }
        // 把右边边剩余的数移入数组
        while(j<rightSize){
            resultList.add(rightList.get(j++));
        }
        return resultList;
        // 把新数组中的数覆盖nums数组
        /*for(int x=0;x<temp.length;x++){
            a[x+low] = temp[x];
        }*/
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    private List sort(List<String> list)
    {
        if(null == list)
        {
            return null;
        }
        int size = list.size();
        for(int i=0;i<size-1;i++)
        {
            for(int j=0;j<size-1;j++)
            {
                int flag = list.get(j).compareTo(list.get(j+1));
                if(flag>0)
                {
                    String temp = list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                }
            }
        }
        return list;
    }


}