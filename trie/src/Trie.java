import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class Trie {

    private static class Node {

        private static final int ASCII_TABLE_SIZE = 128;

        private boolean isOutput;
        private final Node[] children;
        private int childCount;

        public Node(boolean isOutput) {

            this.isOutput = isOutput;
            this.children = new Node[ASCII_TABLE_SIZE];
            this.childCount = 0;
        }

        public boolean isLeaf() {
            return childCount == 0;
        }

        public boolean hasChild(char symbol) {
            return null != children[symbol];
        }

        public Optional<Node> getChild(char symbol) {

            if (hasChild(symbol)) {
                return Optional.of(children[symbol]);
            }
            return Optional.empty();
        }

        public Node addChild(char symbol) {

            if (hasChild(symbol)) {
                return children[symbol];
            }
            var child = new Node(isOutput);
            children[symbol] = child;
            childCount++;
            return child;
        }

        public void deleteChild(char symbol) {
            if (hasChild(symbol)) {
                children[symbol] = null;
                childCount--;
            }
        }

        public void assignToOutput() {
            this.isOutput = true;
        }

    }

    private final Node root;

    public Trie() {
        this.root = new Node(false);
    }

    public void addWord(String word) {

        var curr = root;
        for (int i = 0; i < word.length(); i++) {
            curr = curr.addChild(word.charAt(i));
            if (i == word.length() - 1) {
                curr.assignToOutput();
            }
        }
    }

    public boolean contians(String word) {

        var curr = root;
        for (int i = 0; i < word.length(); i++) {
            var optionalCurr = curr.getChild(word.charAt(i));
            if (optionalCurr.isEmpty()) {
                return false;
            }
            curr = optionalCurr.get();
        }
        return curr.isOutput;
    }

    /**
     * @param word String to be deleted.
     * @return true if word was successfully deleted. False if word does not exist.
     */
    public boolean delete(String word) {

        // Node symbol will be needed to be remembered to updated child tables on child deletion.
        record NodeWithSymbol(Node node, char symbol) {}

        // Find requested word's nodes to be potentially deleted.
        Deque<NodeWithSymbol> stack = new LinkedList<>();
        var currTraversed = root;

        for (int i = 0; i < word.length(); i++) {
            char currChar = word.charAt(i);
            var optionalCurr = currTraversed.getChild(currChar);
            if (optionalCurr.isEmpty()) {
                return false;
            }
            currTraversed = optionalCurr.get();
            stack.add(new NodeWithSymbol(currTraversed, currChar));
        }

        // If last node won't be able to be deleted, isOutput needs to be set to false.
        stack.peekLast().node.isOutput = false;

        // keep removing nodes from the trie if they are leaves. Update parent child list of deleted child.
        NodeWithSymbol curr;
        Character prevSymbol = null;
        while (!stack.isEmpty()) {

            curr = stack.pollLast();

            if (prevSymbol != null) {
                curr.node.deleteChild(prevSymbol);
            }
            if (curr.node.isLeaf() && !curr.node.isOutput) {
                prevSymbol = curr.symbol;
            } else {
                return true;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        var text = "Hello I am under the water und";

        String[] words = text.split(" ");

        var trie = new Trie();

        for (String word : words) {
            trie.addWord(word);
        }

//        trie.delete("under");
        trie.delete("und");

        System.out.println(trie.contians("under"));
        System.out.println(trie.contians("und"));
    }

}