import java.util.Objects;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

// Interfaz para la Pila
interface MyStack<T> {
    void push(T x);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    boolean delete(T n);
}

// Implementación con Arreglo Dinámico
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
        data[size] = x;
        size++;
    }

    public T pop() {
        if (isEmpty()) return null;
        size--;
        T value = (T) data[size];
        data[size] = null;
        return value;
    }

    public T peek() {
        if (isEmpty()) return null;
        return (T) data[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean delete(T n) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(data[i], n)) {
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }
                size--;
                data[size] = null;
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

// Ejecutable y Medición de Tiempos
public class Stack_Dynamic {

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
            DynamicArrayStack<Integer> stack = new DynamicArrayStack<>();
            exec(size, "push", stack::push);
        }
    }
}