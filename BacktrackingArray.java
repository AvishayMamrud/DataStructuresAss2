
public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int nextEmpty;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        nextEmpty = 0;
    }

    @Override
    public Integer get(int index){
        if(index>=nextEmpty | index<0)
            return null;
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        for(int i =0;i<nextEmpty;i++) {
            if(arr[i]==x)
                return i;
        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
        arr[nextEmpty]=x;
        stack.push(nextEmpty);
        stack.push(-1);
        nextEmpty++;

    }

    @Override
    public void delete(Integer index) {
        if(index>0 & index<nextEmpty) {
            int x = arr[index];
            arr[index]=arr[nextEmpty-1];
            arr[nextEmpty-1]=0;
            nextEmpty--;
            stack.push(x);
            stack.push(index);
        }
    }

    @Override
    public Integer minimum() {
        Integer min = 0;
        for (int i =1;i<nextEmpty;i++) {
            if(arr[i]<arr[min])
                min = i;
        }
        return min;
    }

    @Override
    public Integer maximum() {
        Integer max = 0;
        if(nextEmpty==0)
            return -1;
        for (int i =1;i<nextEmpty;i++) {
            if(arr[i]>arr[max])
                max = i;
        }
        return max;
    }

    @Override
    public Integer successor(Integer index) {
        if(index>=0 & index<nextEmpty) {
            Integer ans = maximum();
            int i = 0;
            while(i<nextEmpty) {
                if(arr[i]>arr[index] & arr[i]<=arr[ans])
                    ans = i;
                i++;
            }
            if(arr[ans]!=arr[index])
                return ans;
        }
        return -1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if(index>=0 & index<nextEmpty) {
            Integer ans = minimum();
            int i = 0;
            while(i<nextEmpty) {
                if(arr[i]<arr[index] & arr[i]>=arr[ans])
                    ans = i;
                i++;
            }
            if(arr[ans]!=arr[index])
                return ans;
        }
        return -1;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()) {
            int myPop = (int)stack.pop();
            if(myPop==-1) {// if the action was insertion and now it needs to be deleted
                int index = (int)stack.pop();
                arr[index]=arr[nextEmpty-1];
                nextEmpty--;
            }else {// if the action was deletion and now it needs to be inserted
                arr[nextEmpty]=arr[myPop];
                arr[myPop]=(int)stack.pop();
                nextEmpty++;
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
        String str = "";
        for(int i = 0;i<nextEmpty;i++) {
            str+=arr[i] + " ";
        }
        if(str.length()>0)
            str = str.substring(0, str.length()-1);
        System.out.print(str);
    }
}
