package playground;

import java.util.*;
import java.util.stream.Collectors;

public class MainPlayground {

    public static void main(String[] args) {
//        Recursion re = new Recursion();
//        System.out.println("Palindrome: " + re.isPalindrome("nate"));
//        System.out.println("Multiply: " + re.multiply(7,7));

        testSetIterator();
    }


    /**
     * Starting with an instance of {@link TreeSet} What is faster, creating an arrayList and
     * accessing via a convential for loop or using the Set's iterator
     *
     */

    public static void testSetIterator() {
        Set<Integer> s = new TreeSet<>();
        Set<Integer> r = new TreeSet<>();
        for (int i = 0; i < 10000; i++) {
            s.add(i);
        }
        List<Integer> l = s.stream().collect(Collectors.toList());
        long start = System.currentTimeMillis();
        testArrayList(l, r);
        long stop = System.currentTimeMillis();
        long duration = stop - start;

        long start_two = System.currentTimeMillis();
        testIterator(s, r);
        long stop_two = System.currentTimeMillis();
        long dur = stop_two - start_two;
        System.out.println("ArrayList: " + duration + "ms ::: And Iterator: " + dur + "ms");
    }

    public static Set<Integer> testArrayList(List<Integer> s, Set<Integer> r) {
        for (int i = 0; i < s.size()-1; i++) {
            int test = s.get(i);
            r.add((test + 10000) / 2);
        }
        return r;
    }

    public static Set<Integer> testIterator(Set<Integer> s, Set<Integer> r) {
        Iterator it = s.iterator();
        while (it.hasNext()) {
            int test = (int)it.next();
            r.add((test + 10000) / 2);
        }
        return r;
    }
}
