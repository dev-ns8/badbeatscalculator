package playground;

public class Recursion {

    public int recursiveAdd(int[] arr, int index) {
        if (index == arr.length) {
            return 0;
        } else {
            return arr[index] + recursiveAdd(arr, index+1);
        }
    }

    public boolean isPalindrome(String check) {
        String reversed = reverse(check, check.length()-1);
//        System.out.println(reversed);
        return reversed.equals(check);
    }

    public String reverse(String string, int index) {
        if (index == 0) {
            return "" + string.charAt(0);
        } else {
            return new StringBuilder(string.charAt(index) + reverse(string, index-1)).toString();
        }
    }

    public int multiply(int a, int b) {
        if (b == 1) {
            return a;
        }
        return a + multiply(a, b -1);
    }
}
