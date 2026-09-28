public class Nodo<T> {
    // El tipo de dato ahora es parametrizado (T)
    private T data;
    private Nodo<T> next;

    // Constructor
    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }

    // Getters y Setters para encapsulamiento
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Nodo<T> getNext() {
        return next;
    }

    public void setNext(Nodo<T> next) {
        this.next = next;
    }
}