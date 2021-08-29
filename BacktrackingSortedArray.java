public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int nextEmpty;


    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        nextEmpty = 0;
    }

    @Override
    public Integer get(int index) {
        if (index < 0 | index >= nextEmpty)
            return null;
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        int start = 0, end = nextEmpty-1, mid = 0;
        boolean found = false;
        while (end >= start & !found) {
            mid = (start + end) / 2;
            if (arr[mid] == x)
                found = true;
            else {
                if (x > arr[mid])
                    start=mid+1;
                else end=mid-1;
            }
        }
        if (found) return mid;
        else return -1;
    }

    @Override
    public void insert(Integer x) {
        int i;
        if (nextEmpty != arr.length) {
            for (i = nextEmpty - 1; i >= 0 && arr[i] > x; i--) {
                arr[i + 1] = arr[i];
            }
            nextEmpty++;
            arr[i + 1] = x;
            stack.push(i+1);
            stack.push("delete");
        }
    }

    @Override
    public void delete(Integer index) {
        if (!(index < 0 | index >= nextEmpty)) {
            int insert =arr[index];
            for (int i = index; i < nextEmpty; i++) {
                arr[i] = arr[i + 1];
            }
            stack.push(insert);
            stack.push("insert");
            nextEmpty--;
        }
    }

    @Override
    public Integer minimum() {
        if (nextEmpty == 0)
            return -1;
        return 0;
    }

    @Override
    public Integer maximum() {
        if (nextEmpty == 0)
            return -1;
        return nextEmpty - 1;
    }

    @Override
    public Integer successor(Integer index) {
        if(index < 0 | index >= nextEmpty|index==nextEmpty-1)
            return -1;
        else return index+1;
    }

   // @Override
    public Integer predecessor(Integer index) {
       if(index <= 0 | index >= nextEmpty)
           return -1;
       return index-1;
    }

    @Override
    public void backtrack() {
      if(!stack.isEmpty()){
          String st=(String) stack.pop();
          if(st.equals("insert")){
              this.insert((int)stack.pop());
              stack.pop();
              stack.pop();
          }
          else {
                 int index = (int)stack.pop();
                 this.delete(index);
                 stack.pop();
                 stack.pop();
          }
          System.out.println("backtracking performed");
      }
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
    	if(nextEmpty!=0) {
	        for (int i = 0; i < nextEmpty - 1; i++)
	            System.out.print(arr[i] + " ");
	        System.out.print(arr[nextEmpty - 1]);
    	}
    }
}