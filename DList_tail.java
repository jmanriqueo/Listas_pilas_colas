import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

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

class Double_LinkedList_tail<T> {
    Nodo<T> head;
    Nodo<T> tail;
    private int size;

    public Double_LinkedList_tail() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() { return head == null; }
    public int size() { return size; }

    public void pushFront(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        if (isEmpty()) {
            head = nuevo;
            tail = nuevo;
        } else {
            nuevo.next = head;
            head.prev = nuevo;
            head = nuevo;
        }
        size++;
    }

    public void pushBack(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        if (isEmpty()) {
            head = nuevo;
            tail = nuevo;
        } else {
            nuevo.prev = tail;
            tail.next = nuevo;
            tail = nuevo;
        }
        size++;
    }

    public T popFront() {
        if (isEmpty()) return null;
        T eliminado = head.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return eliminado;
    }

    public T popBack() {
        if (isEmpty()) return null;
        T eliminado = tail.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return eliminado;
    }

    public Nodo<T> find(T data) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (Objects.equals(actual.data, data)) return actual;
            actual = actual.next;
        }
        return null;
    }

    public void addAfter(Nodo<T> target, T data) {
        if (target == null) return;
        if (target == tail) {
            pushBack(data);
            return;
        }
        Nodo<T> nuevo = new Nodo<>(data);
        nuevo.next = target.next;
        nuevo.prev = target;
        target.next.prev = nuevo;
        target.next = nuevo;
        size++;
    }

    public void addBefore(Nodo<T> target, T data) {
        if (target == null || isEmpty()) return;
        if (target == head) {
            pushFront(data);
            return;
        }
        Nodo<T> nuevo = new Nodo<>(data);
        nuevo.prev = target.prev;
        nuevo.next = target;
        target.prev.next = nuevo;
        target.prev = nuevo;
        size++;
    }

    public T erase(Nodo<T> target) {
        if (target == null || isEmpty()) return null;
        if (target == head) return popFront();
        if (target == tail) return popBack();

        target.prev.next = target.next;
        target.next.prev = target.prev;

        T eliminado = target.data;
        target.next = null;
        target.prev = null;
        size--;
        return eliminado;
    }
}

public class DList_tail {

    public static void exec(long size, String method, Operation operation) {
        Instant start = Instant.now();
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }
        Instant finish = Instant.now();
        double timeElapsedMs = Duration.between(start, finish).toNanos() / 1_000_000.0;
        System.out.printf("Se ejecutó %-10s con %-7d elementos en: %10.4f ms\n", method, size, timeElapsedMs);
    }

    private static Double_LinkedList_tail<Integer> llenarLista(long size) {
        Double_LinkedList_tail<Integer> lista = new Double_LinkedList_tail<>();
        for (int i = 0; i < size; i++) {
            lista.pushBack(i);
        }
        return lista;
    }

    public static void main(String[] args) {
        final long start = 10;
        final long end = 100000;

        for (long size = start; size <= end; size *= 10) {
            System.out.println("\n--- EVALUACIÓN DOUBLE LINKED LIST (WITH TAIL) - N = " + size + " ---");

            exec(size, "pushFront", new Double_LinkedList_tail<Integer>()::pushFront);
            exec(size, "pushBack", new Double_LinkedList_tail<Integer>()::pushBack);

            Double_LinkedList_tail<Integer> lPopF = llenarLista(size);
            exec(size, "popFront", (i) -> lPopF.popFront());

            Double_LinkedList_tail<Integer> lPopB = llenarLista(size);
            exec(size, "popBack", (i) -> lPopB.popBack());

            Double_LinkedList_tail<Integer> lFind = llenarLista(size);
            exec(size, "find", (i) -> lFind.find(i));

            Double_LinkedList_tail<Integer> lAddA = llenarLista(size);
            Nodo<Integer> targetAddA = lAddA.find((int) (size / 2));
            if (targetAddA != null) {
                exec(size, "addAfter", (i) -> lAddA.addAfter(targetAddA, i));
            }

            Double_LinkedList_tail<Integer> lAddB = llenarLista(size);
            Nodo<Integer> targetAddB = lAddB.find((int) (size / 2));
            if (targetAddB != null) {
                exec(size, "addBefore", (i) -> lAddB.addBefore(targetAddB, i));
            }

            Double_LinkedList_tail<Integer> lErase = llenarLista(size);
            exec(size, "erase", (i) -> {
                if (lErase.head != null) lErase.erase(lErase.head);
            });
        }
    }
}