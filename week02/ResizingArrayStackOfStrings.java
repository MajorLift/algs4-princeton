import java.util.NoSuchElementException;

public class ResizingArrayStackOfStrings {
    private String[] s;
    private int n;

    public ResizingArrayStackOfStrings() {
        s = new String[1];
        n = 0;    
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        if (capacity < n) throw new IllegalArgumentExcption("Array size must be larger than its number of elements: " + n + ".");
        String[] copy = new String[capacity];
        for (i = 0; i < n; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    public void push(String item) {
        if (n == s.length) {
            resize(2*s.length);
        }
        s[n++] = item;
    }

    public String pop() {
        if(isEmpty()) throw new NoSuchElementException();
        String item = s[n-1];
        s[n-1] = null;
        n--;

        if (n > 0 && n == s.length / 4) resize(s.length/2);
        return item;
    }

}