import java.util.ArrayList;
import java.util.List;

public class Node {
    private State state;
    private Node parent;
    private List<Node> children;
    private int depth;

    public Node() {};

    public Node(State state) {
        this.state = state;
    }

    public Node(State state, Node parent) {
        this.state = state;
        this.parent = parent;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public int getDepth() {
        int i = 0;

        Node parentNode = this.parent;
        while (parentNode != null) {
            parentNode = parentNode.getParent();
            i++;
        }

        return i;
    }

    @Override
    public String toString() {
        return this.state.toString() + "Tiefe: " + this.getDepth() + "\n";
    }
}
