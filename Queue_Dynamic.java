import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

interface MyQueue<T> {
    void enqueue(T x);
    T dequeue();
    T front();
    boolean isEmpty();
    int size();
    boolean delete(T n);
}

class DynamicArrayQueue<T> implements MyQueue<T> {
    private Object[] data;
    private int front;
    private int rear;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public DynamicArrayQueue() {
        this.data = new Object[INITIAL_CAPACITY];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public void enqueue(T x) {
        if (size == data.length) {
            resize(data.length * 2);
        }
        data[rear] = x;
        rear = (rear + 1) % data.length;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T val = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return val;
    }

    public T front() {
        if (isEmpty()) return null;
        return (T) data[front];
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    public boolean delete(T n) {
        if (isEmpty()) return false;
        int targetIndex = -1;
        for (int i = 0; i < size; i++) {
            int currIndex = (front + i) % data.length;
            if (Objects.equals(data[currIndex], n)) {
                targetIndex = currIndex;
                break;
            }
        }
        if (targetIndex == -1) return false;

        int current = targetIndex;
        int lastIndex = (front + size - 1) % data.length;
        while (current != lastIndex) {
            int next = (current + 1) % data.length;
            data[current] = data[next];
            current = next;
        }
        data[lastIndex] = null;
        rear = lastIndex;
        size--;
        return true;
    }

    private void resize(int newCapacity) {
        Object[] newData = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
        rear = size;
    }
}

public class Queue_Dynamic {

    public static void exec(long size, String method, Operation operation) {
        Instant start = Instant.now();
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }
        Instant finish = Instant.now();
        double timeElapsedMs = Duration.between(start, finish).toNanos() / 1_000_000.0;
        System.out.printf("Se ejecutó %-10s con %-7d elementos en: %10.4f ms\n", method, size, timeElapsedMs);
    }

    private static DynamicArrayQueue<Integer> llenarQueue(long size) {
        DynamicArrayQueue<Integer> q = new DynamicArrayQueue<>();
        for (int i = 0; i < size; i++) {
            q.enqueue(i);
        }
        return q;
    }

    public static void main(String[] args) {
        final long start = 10;
        final long end = 100000;

        for (long size = start; size <= end; size *= 10) {
            System.out.println("\n--- EVALUACIÓN DYNAMIC ARRAY QUEUE - N = " + size + " ---");

            exec(size, "enqueue", new DynamicArrayQueue<Integer>()::enqueue);

            DynamicArrayQueue<Integer> qDeq = llenarQueue(size);
            exec(size, "dequeue", (i) -> qDeq.dequeue());

            DynamicArrayQueue<Integer> qFront = llenarQueue(size);
            exec(size, "front", (i) -> qFront.front());

            DynamicArrayQueue<Integer> qDel = llenarQueue(size);
            exec(size, "delete", (i) -> qDel.delete(i));
        }
    }
}