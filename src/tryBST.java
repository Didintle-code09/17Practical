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

public class tryBST {
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
    tNode insertRec(tNode root, int key) {
        if (root == null) {
            root = new tNode(key);
            return root;
        }
        if (key < root.key) {
            root.left = insertRec(root.left, key);

        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }
    public void buildPerfectBST() {int start, int end} {
        if (start >end){
            return;
        }
        int mid = (start + end)/2;
        insert(mid);
        buildPerfectBST(start, mid -1);
        buildPerfectBST(mid +1, end);
    }
    tNode deleteRec(tNode root, int key) {
        if (root == null) {
            return root;
        }
        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }
    int minValue(tNode root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }
    public void delete(int key) {
        root = deleteRec(root, key);
    }
}
