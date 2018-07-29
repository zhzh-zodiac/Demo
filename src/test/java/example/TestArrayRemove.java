package example;

import java.util.ArrayList;
import java.util.List;

public class TestArrayRemove {
    public static void main(String[] args) {
        List<String> arr = new ArrayList<>();
        arr.add("aaa");
        arr.add("bbb");
        arr.add("ccc");
        arr.add("ddd");
        arr.add("eee");
        arr.add("fff");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
            if ("bbb".equals(arr.get(i))) {
                arr.remove(arr.get(i));
                i--;
            }
        }
        /*for (String str : arr) {
            System.out.println(str);
            if ("bbb".equals(str)) {
                arr.remove(str);
            }
        }*/
        System.out.println(arr);
    }
}
