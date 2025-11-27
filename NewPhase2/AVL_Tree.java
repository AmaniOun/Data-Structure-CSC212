
package project;


public class AVL_Tree<K extends Comparable<K>, T> {
    
    class AVLNode<K extends Comparable<K>, T> {
        K key;
        T data;
        int height;
        AVLNode<K, T> left, right;
        
        AVLNode(K k, T d) {
            key = k;
            data = d;
            height = 1;
            left = right = null;
        }
    }
    
    private AVLNode<K, T> root;
    private AVLNode<K, T> current;
    private int size;
    
    public AVL_Tree() {
        root = current = null;
        size = 0;
    }
    
    public boolean empty() {
        return root == null;
    }
    
    public int size() {
        return size;
    }
    
    public T retrieve() {
        return current.data;
    }
    
    public boolean findKey(K key) {
        AVLNode<K, T> node = findNode(root, key);
        if (node == null)
            return false;
        current = node;
        return true;
    }
    
    private AVLNode<K, T> findNode(AVLNode<K, T> node, K key) {
        if (node == null)
            return null;
        
        int cmp = key.compareTo(node.key);
        if (cmp == 0)
            return node;
        else if (cmp < 0)
            return findNode(node.left, key);
        else
            return findNode(node.right, key);
    }
    
    public boolean insert(K key, T data) {
        if (findKey(key))
            return false; 
        
        root = insertNode(root, key, data);
        size++;
        return true;
    }
    
    private AVLNode<K, T> insertNode(AVLNode<K, T> node, K key, T data) {
        if (node == null) {
            current = new AVLNode<>(key, data);
            return current;
        }
        
        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = insertNode(node.left, key, data);
        else if (cmp > 0)
            node.right = insertNode(node.right, key, data);
        else
            return node; 
        
        node.height = 1 + Math.max(height(node.left), height(node.right));
        
        int balance = getBalance(node);
        
        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rotateRight(node);
        
        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return rotateLeft(node);
        
        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    public boolean update(K key, T data) {
        if (!findKey(key))
            return false;
        current.data = data;
        return true;
    }
    
    public boolean remove(K key) {
        if (!findKey(key))
            return false;
        
        root = removeNode(root, key);
        size--;
        return true;
    }
    
    private AVLNode<K, T> removeNode(AVLNode<K, T> node, K key) {
        if (node == null)
            return node;
        
        int cmp = key.compareTo(node.key);
        
        if (cmp < 0)
            node.left = removeNode(node.left, key);
        else if (cmp > 0)
            node.right = removeNode(node.right, key);
        else {
            // Node with one child or no child
            if (node.left == null || node.right == null) {
                AVLNode<K, T> temp = (node.left != null) ? node.left : node.right;
                
                if (temp == null) {
                    temp = node;
                    node = null;
                } else
                    node = temp;
            } else {
                // Node with two children
                AVLNode<K, T> temp = minValueNode(node.right);
                node.key = temp.key;
                node.data = temp.data;
                node.right = removeNode(node.right, temp.key);
            }
        }
        
        if (node == null)
            return node;
        
        node.height = 1 + Math.max(height(node.left), height(node.right));
        
        int balance = getBalance(node);
        
        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);
        
        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);
        
        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    private AVLNode<K, T> minValueNode(AVLNode<K, T> node) {
        AVLNode<K, T> current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }
    
    private int height(AVLNode<K, T> node) {
        if (node == null)
            return 0;
        return node.height;
    }
    
    private int getBalance(AVLNode<K, T> node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }
    
    private AVLNode<K, T> rotateRight(AVLNode<K, T> y) {
        AVLNode<K, T> x = y.left;
        AVLNode<K, T> T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        
        return x;
    }
    
    private AVLNode<K, T> rotateLeft(AVLNode<K, T> x) {
        AVLNode<K, T> y = x.right;
        AVLNode<K, T> T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        
        return y;
    }
    
    public void inOrder(Visitor<T> visitor) {
        inOrderRec(root, visitor);
    }
    
    private void inOrderRec(AVLNode<K, T> node, Visitor<T> visitor) {
        if (node != null) {
            inOrderRec(node.left, visitor);
            visitor.visit(node.data);
            inOrderRec(node.right, visitor);
        }
    }
    
    // Range query
    public LinkedList<T> rangeSearch(K min, K max) {
        LinkedList<T> result = new LinkedList<>();
        rangeSearchRec(root, min, max, result);
        return result;
    }
    
    private void rangeSearchRec(AVLNode<K, T> node, K min, K max, LinkedList<T> result) {
        if (node == null)
            return;
        
        if (min.compareTo(node.key) < 0)
            rangeSearchRec(node.left, min, max, result);
        
        if (min.compareTo(node.key) <= 0 && max.compareTo(node.key) >= 0) {
            if (result.empty())
                result.insert(node.data);
            else {
                result.findFirst();
                result.insert(node.data);
            }
        }
        
        if (max.compareTo(node.key) > 0)
            rangeSearchRec(node.right, min, max, result);
    }
    
    // Get all elements as LinkedList 
    public LinkedList<T> toLinkedList() {
        LinkedList<T> list = new LinkedList<>();
        toLinkedListRec(root, list);
        return list;
    }
    
    private void toLinkedListRec(AVLNode<K, T> node, LinkedList<T> list) {
        if (node != null) {
            toLinkedListRec(node.left, list);
            if (list.empty())
                list.insert(node.data);
            else {
                list.findFirst();
                list.insert(node.data);
            }
            toLinkedListRec(node.right, list);
        }
    }
    
    public void print() {
        printRec(root);
    }
    
    private void printRec(AVLNode<K, T> node) {
        if (node != null) {
            printRec(node.left);
            System.out.println(node.data);
            printRec(node.right);
        }
    }
    

    public interface Visitor<T> {
        void visit(T data);
    }
}
