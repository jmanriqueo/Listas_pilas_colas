import java.util.*;
import java.time.Instant;
import java.time.Duration;

@FunctionalInterface
interface Operation {
    void apply(int value);
}

// 1. Clase Nodo para Lista Doblemente Enlazada
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

// 2. Implementación de Lista Doblemente Enlazada CON Cola (Tail)
class Double_LinkedList_tail<T> {
    Nodo<T> head;
    Nodo<T> tail;
    private int size;

    public Double_LinkedList_tail() {
        this.head = null;
        this.tail = null;
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

    // Insertar al final - O(1) ¡Optimizado con tail!
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

    // Eliminar el primer elemento - O(1)
    public T popFront() {
        if (isEmpty()) return null;

        T eliminado = head.data;
        if (head == tail) { // Único elemento en la lista
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return eliminado;
    }

    // Eliminar el último elemento - O(1) ¡Optimizado con tail y prev!
    public T popBack() {
        if (isEmpty()) return null;

        T eliminado = tail.data;
        if (head == tail) { // Único elemento en la lista
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return eliminado;
    }

    // Buscar la referencia de un elemento - O(N)
    public Nodo<T> find( data) {
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

        // Si el objetivo es la cola, equivale a hacer pushBack
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

    // Insertar antes del nodo indicado - O(1)
    public void addBefore(Nodo<T> target, T data) {
        if (target == null || isEmpty()) return;

        // Si el objetivo es la cabeza, equivale a hacer pushFront
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

    // Eliminar un nodo dada su referencia - O(1)
    public T erase(Nodo<T> target) {
        if (target == null || isEmpty()) return null;

        if (target == head) {
            return popFront();
        }
        if (target == tail) {
            return popBack();
        }

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
        long start = System.nanoTime();

        // Si la operación es 'find', la ejecutamos pocas veces o de forma controlada
        // para evitar que el benchmark de O(N) dentro de un bucle de N elementos se vuelva O(N^2)
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }

        long finish = System.nanoTime();
        // Conversión correcta de Nanosegundos a Milisegundos (ms)
        double timeElapsedMs = (finish - start) / 1_000_000.0;
        System.out.printf("Se ejecutó %-10s con %-9d elementos en: %10.4f ms\n", method, size, timeElapsedMs);
    }

    private static Double_LinkedList_tail<Integer> llenarLista(long size) {
        Double_LinkedList_tail<Integer> lista = new Double_LinkedList_tail<>();
        for (int i = 0; i < size; i++) {
            lista.pushBack(i);
        }
        return lista;
    }

    public static void main(String[] args) {
        final long start = 100;
        final long end = 1000000; // Se recomienda probar hasta 10^6 primero para evitar OOM / congelamientos

        for (long size = start; size <= end; size *= 10) {
            System.out.println("\n--- Tamaño del contenedor: " + size + " ---");

            
            Double_LinkedList_tail<Integer> lista = new Double_LinkedList_tail<>();
            exec(size, "pushFront", lista::pushFront);

            
            Double_LinkedList_tail<Integer> listaBack = new Double_LinkedList_tail<>();
            exec(size, "pushBack", listaBack::pushBack);

            
            Double_LinkedList_tail<Integer> listaPopFront = llenarLista(size);
            exec(size, "popFront", (i) -> listaPopFront.popFront());

            
            Double_LinkedList_tail<Integer> listaPopBack = llenarLista(size);
            exec(size, "popBack", (i) -> listaPopBack.popBack());


            Double_LinkedList_tail<Integer> listaFind = llenarLista(size);
            exec(size, "find", (i) -> listaFind.find(i));

            Double_LinkedList_tail<Integer> listaAddAfter = llenarLista(size);
            Nodo<Integer> targetAddAfter = listaAddAfter.find((int) (size / 2));
            exec(size, "addAfter", (i) -> listaAddAfter.addAfter(targetAddAfter, i));


            Double_LinkedList_tail<Integer> listaAddBefore = llenarLista(size);
            Nodo<Integer> targetAddBefore = listaAddBefore.find((int) (size / 2));
            exec(size, "addBefore", (i) -> listaAddBefore.addBefore(targetAddBefore, i));


            Double_LinkedList_tail<Integer> listaErase = llenarLista(size);
            exec(size, "erase", (i) -> listaErase.erase(listaErase.head));
        }
    }
}