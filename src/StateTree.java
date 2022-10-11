import java.util.ArrayList;

public class StateTree {
    // the current state of the game is used as the root of the tree
    private Node root;

    public StateTree(State currentState) {
        this.root = new Node();
        this.root.setState(currentState);
        this.root.setChildren(new ArrayList<>());
    }

    public void expand() {
        Node currentNode = this.root;

        for (int i = 6; i < 12; i++) {
            if (currentNode.getState().getBoard()[i] != 0) {
                State s = currentNode.getState().makeMove(i);
                this.root.getChildren().add(new Node(s, this.root));
            }
        }

        ArrayList<Node> currentChildren = (ArrayList<Node>) currentNode.getChildren();
        for (int i = 0; i < 3; i++) {
            for (Node child : currentChildren) {
                for (int j = 6; j < 12; j++) {
                    if (child.getState().getBoard()[j] != 0) {
                        State s = child.getState().makeMove(j);
                        child.setChildren(new ArrayList<>());
                        child.getChildren().add(new Node(s, child));
                    }
                }
            }
        }
    }

        private void expand2(StateTree tree){
            int childCounter = this.root.getChildren().size();
            if(childCounter == 0){
                System.out.println("mach nix");
            }else{
                for(int i = 0; i< childCounter; i++){
                    StateTree childTree = new StateTree(this.getRoot().getParent().getState());
                    expand2(childTree);
                }
            }
        }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public static void main(String[] args) {
        State s = new State();
        StateTree t = new StateTree(s);
        t.expand();
        for (Node child : t.getRoot().getChildren()) {
            System.out.println(child);
        }
    }
}
