public class NodoDoble<T> {
    private T data;
    private NodoDoble<T> next;
    private NodoDoble<T> prev; // Referencia al nodo anterior

    public NodoDoble(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    // Getters y Setters
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public NodoDoble<T> getNext() { return next; }
    public void setNext(NodoDoble<T> next) { this.next = next; }

    public NodoDoble<T> getPrev() { return prev; }
    public void setPrev(NodoDoble<T> prev) { this.prev = prev; }
}