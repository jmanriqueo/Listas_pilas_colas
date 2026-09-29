import java.util.*;
import java.time.Instant;
import java.time.Duration;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

class Nodo<T> {
    public T data;
    public Nodo<T> next;

    Nodo(T data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedListTail<T> {
    Nodo<T> head;
    Nodo<T> tail;
    int size;

    public LinkedListTail() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void pushFront(T data) {
        Nodo<T> Nnodo = new Nodo<>(data);
        Nnodo.next = head;
        head = Nnodo;
        
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void pushBack(T data) {
        Nodo<T> Nnodo = new Nodo<>(data);
        if (isEmpty()) {
            head = Nnodo;
            tail = Nnodo;
        } else {
            tail.next = Nnodo;
            tail = Nnodo;
        }
        size++;
    }

    public T popFront() {
        if (isEmpty()) return null;

        T eliminado = head.data;
        head = head.next;
        
        if (head == null) {
            tail = null;
        }
        size--;
        return eliminado;
    }

    public T popBack() {
        if (isEmpty()) return null;

        T eliminado;
        if (head.next == null) { 
            eliminado = head.data;
            head = null;
            tail = null;
        } else {
            Nodo<T> actual = head;
            while (actual.next.next != null) {
                actual = actual.next;
            }
            eliminado = actual.next.data;
            actual.next = null;
            tail = actual; // Actualiza el puntero tail al penúltimo
        }
        size--;
        return eliminado;
    }

    public Nodo<T> find(T data) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (actual.data==data) {
                return actual;
            }
            actual = actual.next;
        }
        return null;
    }

    public void addAfter(Nodo<T> target, T data) {
        if (target == null) return;

        Nodo<T> nuevo = new Nodo<>(data);
        nuevo.next = target.next;
        target.next = nuevo;
        
        if (target == tail) {
            tail = nuevo;
        }
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
            Nodo<T> Nnodo = new Nodo<>(data);
            Nnodo.next = target;
            actual.next = Nnodo;
            size++;
        }
    }

    public T erase(Nodo<T> target) {
        if (target == null || isEmpty()) return null;

        if (target == head) {
            return popFront();
        }

        Nodo<T> actual = head;
        while (actual != null && actual.next != target) {
            actual = actual.next;
        }

        if (actual != null) {
            actual.next = target.next;
            
            if (target == tail) {
                tail = actual;
            }
            
            target.next = null;
            size--;
            return target.data;
        }

        return null;
    }
}

public class List_tail {
    public static void exec(int size, String method, Operation operation) {
        Instant start = Instant.now();        
        
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.printf("Se ejecutó %s de %d elementos en: %d milisegundos\n", method, size, timeElapsed);
    }

    public static void main(String[] args) {
        final int start = 100;
        final int end = 10000;

        for (int size = start; size <= end; size *= 10) {
            for (int i = 0; i < 5; i++) {
                LinkedListTail<Integer> lista = new LinkedListTail<>();
                exec(size, "pushBack", lista::pushBack);
            }
        }
    }
}