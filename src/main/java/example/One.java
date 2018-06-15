package example;

import java.awt.image.BufferedImage;
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

        String[] arr = new String[listSize];
        for(int i =0;i<listSize;i++)
        {
            arr[i] = list.get(i);
        }


        List<List<String>> tempList = new ArrayList<>();
        for (int i = 0; i < threadCount; i++)
        {
            tempList.add(new ArrayList<>());
        }
        int len = listSize/threadCount+1;
        for(int i=0;i<threadCount-1;i++)
        {
            tempList.get(i).add("");
        }




        for (int i = 0; i < listSize - 1; i++)
        {
            for (int j = 0; j < listSize - 1; j++)
            {
                int index = j % threadCount;
                List<String> secList = new ArrayList<>();
                if (index == i)
                {
                    String str = list.get(j);
                    tempList.get(index).add(str);
                    //secList.add(str);
                }
                /*if(0!=secList.size())
                {
                    tempList.add(index, secList);
                }*/
            }
        }

        List<FutureTask<List<String>>> taskList = new ArrayList<>();
        for(List<String> sList : tempList)
        {
            FutureTask<List<String>> task = new FutureTask<>(() -> {
                return sort(sList);
            });
            taskList.add(task);
            exec.submit(task);
        }

        List<String> otempList = new ArrayList<>();
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
            otempList.addAll(olist);
        }




        List outList = sort(otempList);
        try (PrintWriter pw = new PrintWriter (new FileWriter("out.txt")))
        {
            for (int i = 0; i < outList.size()-1; i++)
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