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

    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList_tail<T> {
    Nodo<T> head;
    Nodo<T> tail;
    private int size;

    public LinkedList_tail() {
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
            tail.next = nuevo;
            tail = nuevo;
        }
        size++;
    }

    public T popFront() {
        if (isEmpty()) return null;
        T eliminado = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return eliminado;
    }

    public T popBack() {
        if (isEmpty()) return null;
        if (head == tail) {
            T eliminado = head.data;
            head = null;
            tail = null;
            size--;
            return eliminado;
        }
        Nodo<T> actual = head;
        while (actual.next != tail) {
            actual = actual.next;
        }
        T eliminado = tail.data;
        actual.next = null;
        tail = actual;
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
        target.next = nuevo;
        size++;
    }

    public void addBefore(Nodo<T> target, T data) {
        if (target == null || isEmpty()) return;
        if (target == head) {
            pushFront(data);
            return;
        }
        Nodo<T> actual = head;
        while (actual != null && actual.next != target) {
            actual = actual.next;
        }
        if (actual != null) {
            Nodo<T> nuevo = new Nodo<>(data);
            nuevo.next = target;
            actual.next = nuevo;
            size++;
        }
    }

    public T erase(Nodo<T> target) {
        if (target == null || isEmpty()) return null;
        if (target == head) return popFront();

        Nodo<T> actual = head;
        while (actual != null && actual.next != target) {
            actual = actual.next;
        }
        if (actual != null) {
            if (target == tail) tail = actual;
            actual.next = target.next;
            T eliminado = target.data;
            target.next = null;
            size--;
            return eliminado;
        }
        return null;
    }
}

public class List_tail {

    public static void exec(long size, String method, Operation operation) {
        Instant start = Instant.now();
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }
        Instant finish = Instant.now();
        double timeElapsedMs = Duration.between(start, finish).toNanos() / 1_000_000.0;
        System.out.printf("Se ejecutó %-10s con %-7d elementos en: %10.4f ms\n", method, size, timeElapsedMs);
    }

    private static LinkedList_tail<Integer> llenarLista(long size) {
        LinkedList_tail<Integer> lista = new LinkedList_tail<>();
        for (int i = 0; i < size; i++) {
            lista.pushBack(i);
        }
        return lista;
    }

    public static void main(String[] args) {
        final long start = 10;
        final long end = 100000;

        for (long size = start; size <= end; size *= 10) {
            System.out.println("\n--- EVALUACIÓN SINGLE LINKED LIST (WITH TAIL) - N = " + size + " ---");

            exec(size, "pushFront", new LinkedList_tail<Integer>()::pushFront);
            exec(size, "pushBack", new LinkedList_tail<Integer>()::pushBack);

            LinkedList_tail<Integer> lPopF = llenarLista(size);
            exec(size, "popFront", (i) -> lPopF.popFront());

            if (size <= 10000) {
                LinkedList_tail<Integer> lPopB = llenarLista(size);
                exec(size, "popBack", (i) -> lPopB.popBack());
            }

            LinkedList_tail<Integer> lFind = llenarLista(size);
            exec(size, "find", (i) -> lFind.find(i));

            LinkedList_tail<Integer> lAddA = llenarLista(size);
            Nodo<Integer> targetAddA = lAddA.find((int) (size / 2));
            if (targetAddA != null) {
                exec(size, "addAfter", (i) -> lAddA.addAfter(targetAddA, i));
            }

            LinkedList_tail<Integer> lAddB = llenarLista(size);
            Nodo<Integer> targetAddB = lAddB.find((int) (size / 2));
            if (targetAddB != null) {
                exec(size, "addBefore", (i) -> lAddB.addBefore(targetAddB, i));
            }

            LinkedList_tail<Integer> lErase = llenarLista(size);
            exec(size, "erase", (i) -> {
                if (lErase.head != null) lErase.erase(lErase.head);
            });
        }
    }
}