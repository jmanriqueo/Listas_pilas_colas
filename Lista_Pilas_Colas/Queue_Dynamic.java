import java.util.Objects;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

// Interfaz para la Cola
interface MyQueue<T> {
    void enqueue(T x);
    T dequeue();
    T front();
    boolean isEmpty();
    int size();
    boolean delete(T n);
}

// Implementación con Arreglo Circular Dinámico
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
        T value = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    public T front() {
        if (isEmpty()) return null;
        return (T) data[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

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

// Ejecutable y Medición de Tiempos
public class Queue_Dynamic {

    public static void exec(int size, String method, Operation operation) {
        long start = System.nanoTime();

        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }

        long finish = System.nanoTime();
        double timeElapsedUs = (finish - start) / 1000.0;
        System.out.printf("Se ejecutó %s con %d elementos en: %.2f µs\n", method, size, timeElapsedUs);
    }

    public static void main(String[] args) {
        final int start = 100;
        final int end = 10000;

        for (int size = start; size <= end; size *= 10) {
            System.out.println("\n--- Tamaño del contenedor: " + size + " ---");
            DynamicArrayQueue<Integer> queue = new DynamicArrayQueue<>();
            exec(size, "enqueue", queue::enqueue);
        }
    }
}