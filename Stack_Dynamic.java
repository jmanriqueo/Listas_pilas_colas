import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

interface MyStack<T> {
    void push(T x);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    boolean delete(T n);
}

class DynamicArrayStack<T> implements MyStack<T> {
    private Object[] data;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public DynamicArrayStack() {
        this.data = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    public void push(T x) {
        if (size == data.length) {
            resize(data.length * 2);
        }
        data[size++] = x;
    }

    public T pop() {
        if (isEmpty()) return null;
        T val = (T) data[--size];
        data[size] = null;
        return val;
    }

    public T peek() {
        if (isEmpty()) return null;
        return (T) data[size - 1];
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    public boolean delete(T n) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(data[i], n)) {
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }
                data[--size] = null;
                return true;
            }
        }
        return false;
    }

    private void resize(int newCapacity) {
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }
}

public class Stack_Dynamic {

    public static void exec(long size, String method, Operation operation) {
        Instant start = Instant.now();
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }
        Instant finish = Instant.now();
        double timeElapsedMs = Duration.between(start, finish).toNanos() / 1_000_000.0;
        System.out.printf("Se ejecutó %-10s con %-7d elementos en: %10.4f ms\n", method, size, timeElapsedMs);
    }

    private static DynamicArrayStack<Integer> llenarStack(long size) {
        DynamicArrayStack<Integer> s = new DynamicArrayStack<>();
        for (int i = 0; i < size; i++) {
            s.push(i);
        }
        return s;
    }

    public static void main(String[] args) {
        final long start = 10;
        final long end = 100000;

        for (long size = start; size <= end; size *= 10) {
            System.out.println("\n--- EVALUACIÓN DYNAMIC ARRAY STACK - N = " + size + " ---");

            exec(size, "push", new DynamicArrayStack<Integer>()::push);

            DynamicArrayStack<Integer> sPop = llenarStack(size);
            exec(size, "pop", (i) -> sPop.pop());

            DynamicArrayStack<Integer> sPeek = llenarStack(size);
            exec(size, "peek", (i) -> sPeek.peek());

            DynamicArrayStack<Integer> sDel = llenarStack(size);
            exec(size, "delete", (i) -> sDel.delete(i));
        }
    }
}