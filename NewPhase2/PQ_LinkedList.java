
package project;


public class PQ_LinkedList <T>{
    
    
    // PQNode class
    class PQNode<T> {
        public T data;
        public float priority;
        public PQNode<T> next;

        public PQNode() {
                next = null;
        }

        public PQNode(T e, float p) {
                data = e;
                priority = p;
        }


        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public float getPriority() {
            return priority;
        }

        public PQNode<T> getNext() {
            return next;
        }

        public void setNext(PQNode<T> next) {
            this.next = next;
        }

    }
    
    
    
    private int size;
    private PQNode<T> head;


    //Constructor of PQ_LinkedList class
    public PQ_LinkedList() {
            head = null;
            size = 0;
    }

                public int length (){
            return size;
    }

    public boolean full () {
            return false;
    }

    public void enqueue(T e, float pty) {
            PQNode<T> tmp = new PQNode<T>(e, pty);
            if((size == 0) || (pty > head.priority)) {
                    tmp.next = head;
                    head = tmp;
            }
            else {
                    PQNode<T> p = head;
                    PQNode<T> q = null;
                    while((p != null) && (pty <= p.priority)) {
                            q = p;
                            p = p.next;
                    }
                    tmp.next = p;
                    q.next = tmp;
            }
            size++;
    }

    public PQ_Element<T> serve(){
            PQNode<T> node = head;
            PQ_Element<T> pqe=new PQ_Element<T>(node.data,node.priority);
            head = head.next;
            size--;
            return pqe;
    }
}