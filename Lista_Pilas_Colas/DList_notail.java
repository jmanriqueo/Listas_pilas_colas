import java.util.Objects;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

// 1. Nodo para Lista Doblemente Enlazada
class Nodo<T> {
    public T data;
    public Nodo<T> next;
    public Nodo<T> prev;

    public Nodo(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

// 2. Implementación de Lista Doblemente Enlazada sin Cola (Tail)
class Double_LinkedList_notail<T> {
    Nodo<T> head;
    private int size;

    public Double_LinkedList_notail() {
        this.head = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    // Insertar al inicio - O(1)
    public void pushFront(T data) {
        Nodo<T> Nnodo = new Nodo<>(data);
        Nnodo.next = head;
        Nnodo.prev = null;

        if (head != null) {
            head.prev = Nnodo;
        }
        head = Nnodo;
        size++;
    }

    // Insertar al final - O(N) por no tener cola (tail)
    public void pushBack(T data) {
        Nodo<T> Nnodo = new Nodo<>(data);
        if (isEmpty()) {
            head = Nnodo;
        } else {
            Nodo<T> actual = head;
            while (actual.next != null) {
                actual = actual.next;
            }
            actual.next = Nnodo;
            Nnodo.prev = actual;
        }
        size++;
    }

    // Eliminar el primero - O(1)
    public T popFront() {
        if (isEmpty()) return null;

        T eliminado = head.data;
        head = head.next;

        if (head != null) {
            head.prev = null;
        }
        size--;
        return eliminado;
    }

    // Eliminar el último - O(N) por no tener cola (tail)
    public T popBack() {
        if (isEmpty()) return null;

        if (head.next == null) {
            T eliminado = head.data;
            head = null;
            size--;
            return eliminado;
        }

        Nodo<T> actual = head;
        while (actual.next != null) {
            actual = actual.next;
        }

        T eliminado = actual.data;
        actual.prev.next = null;
        actual.prev = null; // Limpiar puntero
        size--;
        return eliminado;
    }

    // Buscar la referencia de un elemento - O(N)
    public Nodo<T> find(T data) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (Objects.equals(actual.data, data)) {
                return actual;
            }
            actual = actual.next;
        }
        return null;
    }

    // Insertar después del nodo indicado - O(1)
    public void addAfter(Nodo<T> target, T data) {
        if (target == null) return;

        Nodo<T> Nnodo = new Nodo<>(data);
        Nnodo.next = target.next;
        Nnodo.prev = target;

        if (target.next != null) {
            target.next.prev = Nnodo;
        }
        target.next = Nnodo;
        size++;
    }

    // Insertar antes del nodo indicado - O(1) gracias a target.prev
    public void addBefore(Nodo<T> target, T data) {
        if (target == null || isEmpty()) return;

        if (target == head) {
            pushFront(data);
            return;
        }

        Nodo<T> Nnodo = new Nodo<>(data);
        Nnodo.prev = target.prev;
        Nnodo.next = target;

        if (target.prev != null) {
            target.prev.next = Nnodo;
        }
        target.prev = Nnodo;
        size++;
    }

    // Eliminar un nodo específico dada su referencia - O(1)
    public T erase(Nodo<T> target) {
        if (target == null || isEmpty()) return null;

        if (target == head) {
            return popFront();
        }

        if (target.prev != null) {
            target.prev.next = target.next;
        }
        if (target.next != null) {
            target.next.prev = target.prev;
        }

        T eliminado = target.data;
        target.next = null;
        target.prev = null;
        size--;
        return eliminado;
    }
}

// 3. Clase Principal de Benchmarking y Ejecución
public class DList_notail {

    public static void exec(int size, String method, Operation operation) {
        long start = System.nanoTime();

        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }

        long finish = System.nanoTime();
        // Conversión a microsegundos (µs) como sugiere el PDF para máxima precisión
        double timeElapsedUs = (finish - start) / 1000.0;
        System.out.printf("Se ejecutó %s de %d elementos en: %.2f µs\n", method, size, timeElapsedUs);
    }

    public static void main(String[] args) {
        final int start = 10;
        final int end = 100000;

        for (int size = start; size <= end; size *= 10) {
            System.out.println("\n--- Probando con tamaño: " + size + " ---");
            
            // Medición de pushFront
            Double_LinkedList_notail<Integer> listaPushFront = new Double_LinkedList_notail<>();
            exec(size, "pushFront", listaPushFront::pushFront);

            // Medición de pushBack
            Double_LinkedList_notail<Integer> listaPushBack = new Double_LinkedList_notail<>();
            exec(size, "pushBack", listaPushBack::pushBack);
        }
    }
}