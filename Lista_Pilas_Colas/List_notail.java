public class List_notail<T> {
    private Nodo<T> head;
    private int size;

    public List_notail() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void pushFront(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        nuevo.setNext(head);
        head = nuevo;
        size++;
    }


    public void pushBack(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        if (isEmpty()) {
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            while (actual.getNext() != null) {
                actual = actual.getNext();
            }
            actual.setNext(nuevo);
        }
        size++;
    }

    public T popFront() {
        if (isEmpty()) return null;

        T eliminado = head.getData();
        head = head.getNext();
        size--;
        return eliminado;
    }

    public T popBack() {
        if (isEmpty()) return null;

        if (head.getNext() == null) {
            T eliminado = head.getData();
            head = null;
            size--;
            return eliminado;
        }

        Nodo<T> actual = head;
        while (actual.getNext().getNext() != null) {
            actual = actual.getNext();
        }

        T eliminado = actual.getNext().getData();
        actual.setNext(null);
        size--;
        return eliminado;
    }

    public Nodo<T> find(T data) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (actual.getData() != null && actual.getData().equals(data)) {
                return actual;
            }
            actual = actual.getNext();
        }
        return null;
    }

    public void addAfter(Nodo<T> target, T data) {
        if (target == null) return;

        Nodo<T> nuevo = new Nodo<>(data);
        nuevo.setNext(target.getNext());
        target.setNext(nuevo);
        size++;
    }

    public void addBefore(Nodo<T> target, T data) {
        if (target == null || isEmpty()) return;

        if (target == head) {
            pushFront(data);
            return;
        }

        Nodo<T> actual = head;
        while (actual != null && actual.getNext() != target) {
            actual = actual.getNext();
        }

        if (actual != null) {
            Nodo<T> nuevo = new Nodo<>(data);
            nuevo.setNext(target);
            actual.setNext(nuevo);
            size++;
        }
    }
    
    public T erase(Nodo<T> target) {
        if (target == null || isEmpty()) return null;

        if (target == head) {
            return popFront();
        }

        Nodo<T> actual = head;
        while (actual != null && actual.getNext() != target) {
            actual = actual.getNext();
        }

        if (actual != null) {
            actual.setNext(target.getNext());
            target.setNext(null);
            size--;
            return target.getData();
        }

        return null;
    }
}