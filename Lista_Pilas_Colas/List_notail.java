import java.util.*;
import java.time.Instant;
import java.time.Duration;

// Interfaz funcional necesaria para pasar los métodos a exec()
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

class linkedList_notail<T> {
    Nodo<T> head;
    int capacidad;

    public linkedList_notail() {
        this.head = null;
        this.capacidad = 0;
    }

    public linkedList_notail(int capacidad) {
        this.head = null;
        this.capacidad = capacidad;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void pushFront(T data) {
        Nodo<T> Nnodo = new Nodo<>(data);
        Nnodo.next = head;
        head = Nnodo;
        capacidad++;
    }

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
        }
        capacidad++;
    }

    public T popFront() {
        if (isEmpty()) return null;

        T eliminado = head.data;
        head = head.next;
        capacidad--;
        return eliminado;
    }

    public T popBack() {
        if (isEmpty()) return null;

        if (head.next == null) {
            T eliminado = head.data;
            head = null;
            capacidad--;
            return eliminado;
        }

        Nodo<T> actual = head;
        while (actual.next.next != null) {
            actual = actual.next;
        }
        T eliminado = actual.next.data;
        actual.next = null; // Desconecta el último nodo
        capacidad--;
        return eliminado;
    }

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

    public void addAfter(Nodo<T> target, T data) {
        if (target == null) return;

        Nodo<T> Nnodo = new Nodo<>(data);
        Nnodo.next = target.next;
        target.next = Nnodo;
        capacidad++;
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
            capacidad++;
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
            target.next = null;
            capacidad--;
            return target.data;
        }

        return null;
    }
}

public class List_notail {
public static void exec(int size, String method, Operation operation) {
        long start = System.nanoTime();

        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }

        long finish = System.nanoTime();
        double timeElapsedUs = (finish - start) / 1000.0; // Tiempo promedio/total en microsegundos (µs)
        System.out.printf("Se ejecutó %-10s de %-6d elementos en: %10.2f µs\n", method, size, timeElapsedUs);
    }

    // Método auxiliar para precargar la lista con 'size' elementos antes de medir
    private static Double_LinkedList_tail<Integer> llenarLista(int size) {
        LinkedList_tail<Integer> lista = new LinkedList_tail<>();
        for (int i = 0; i < size; i++) {
            lista.pushBack(i);
        }
        return lista;
    }

    public static void main(String[] args) {
        final int start = 10;
        final int end = 100000; // Puedes ajustar a 1000000 según las pruebas del PDF

        for (int size = start; size <= end; size *= 10) {
            System.out.println("\n==================================================");
            System.out.println("   EVALUACIÓN DE COMPLEJIDAD PARA TAMAÑO N = " + size);
            System.out.println("==================================================");

            // 1. PushFront (Construye la lista desde cero)
            LinkedList_notail<Integer> listaPushFront = new LinkedList_notail<>();
            exec(size, "pushFront", listaPushFront::pushFront);

            // 2. PushBack (Construye la lista desde cero)
            LinkedList_notail<Integer> listaPushBack = new LinkedList_notail<>();
            exec(size, "pushBack", listaPushBack::pushBack);

            // 3. PopFront (Elimina N elementos de una lista ya poblada)
            LinkedList_notail<Integer> listaPopFront = llenarLista(size);
            exec(size, "popFront", (i) -> listaPopFront.popFront());

            // 4. PopBack (Elimina N elementos de una lista ya poblada)
            LinkedList_notail<Integer> listaPopBack = llenarLista(size);
            exec(size, "popBack", (i) -> listaPopBack.popBack());

            // 5. Find (Busca elementos dentro de la lista poblada)
            LinkedList_notail<Integer> listaFind = llenarLista(size);
            exec(size, "find", (i) -> listaFind.find(i));

            // 6. AddAfter (Inserta N elementos después de un nodo intermedio)
            LinkedList_notail<Integer> listaAddAfter = llenarLista(size);
            Nodo<Integer> targetAddAfter = listaAddAfter.find(size / 2); // Nodo ubicado en la mitad
            exec(size, "addAfter", (i) -> listaAddAfter.addAfter(targetAddAfter, i));

            // 7. AddBefore (Inserta N elementos antes de un nodo intermedio)
            LinkedList_notail<Integer> listaAddBefore = llenarLista(size);
            Nodo<Integer> targetAddBefore = listaAddBefore.find(size / 2); // Nodo ubicado en la mitad
            exec(size, "addBefore", (i) -> listaAddBefore.addBefore(targetAddBefore, i));

            // 8. Erase (Elimina N elementos pasando siempre el nodo cabeza actual)
            LinkedList_notail<Integer> listaErase = llenarLista(size);
            exec(size, "erase", (i) -> listaErase.erase(listaErase.head));
        }
    }
}