import java.util.Iterator;

public class Node implements Iterable<Node> {
    private int data;
    private Node next;

    public Node(int data){
        this.data = data;
    }

    public Node(int data, Node next){
        this.data = data;
        this.next = next;
    }

    @Override
    public Iterator<Node> iterator() {
        return new NodeIterator(this);
    }

    private static class NodeIterator implements Iterator<Node> {
        private Node node;
        private NodeIterator(Node node){
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return this.node != null;
        }

        @Override
        public Node next() {
            Node temp = this.node;
            this.node = this.node.next;
            return temp;
        }
    }
}