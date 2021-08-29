public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        int i = 0;
        int j = 0;
        while (i < arr.length) {
            while (i < arr.length & j < fd) {
                myStack.push(arr[i]);
                if (arr[i] == x) {
                    myStack.push(x);
                    return i;
                } else {
                    i++;
                    j++;
                }
            }
            j = 0;

            if (fd <= bk) return -1;
            for (int k = 0; k < bk && !myStack.isEmpty() & i != arr.length; k++) {
                myStack.pop();
                i--;
            }
        }
        return -1;
    }
    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int start = 0, end = arr.length - 1, mid=(start + end) / 2;
        int consist;
        while (end>=start) {
            consist = isConsistent(arr);
            if(consist!=0){
            for (int i = 0; i < consist & !myStack.isEmpty(); i++) {
                myStack.pop();
                myStack.pop();
            }
              if (!myStack.isEmpty()) {
                    end = (int) myStack.pop();
                    start = (int) myStack.pop();
               }
              else  {start=0;
                     end=arr.length-1;
                    }
            }
            myStack.push(start);
            myStack.push(end);
            mid = (start + end) / 2;
             if (x == arr[mid])
                return mid;
             if (x > arr[mid])
                    start = mid+1;
             else end = mid-1;
            }
    return -1;
    }//consistentBinSearch

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int) Math.round(res / 10);
        } else {
            return 0;
        }
    }
}




