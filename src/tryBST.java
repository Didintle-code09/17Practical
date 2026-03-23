public class tryBST {
    class tNode {
        int key;
        tNode left;
        tNode right;
        tNode(int item) {
            key = item;
            left = null;
            right = null;
        }
    }
    boolean isBST() {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    boolean isBSTUtil(tNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        if (node.key <= min || node.key >= max) {
            return false;
        }
        return isBSTUtil(node.left, min, node.key) && isBSTUtil(node.right, node.key, max);
    }
}
