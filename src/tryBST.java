import java.util.*;

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
    tNode root;

    boolean isBST() {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    boolean isBSTUtil(tNode node, int min, int max) {
        if (node == null) return true;
        if (node.key <= min || node.key >= max) return false;
        return isBSTUtil(node.left, min, node.key) && isBSTUtil(node.right, node.key, max);
    }

    tNode insertRec(tNode root, int key) {
        if (root == null) return new tNode(key);
        if (key < root.key) root.left = insertRec(root.left, key);
        else if (key > root.key) root.right = insertRec(root.right, key);
        return root;
    }

    public void insert(int key) {
        root = insertRec(root, key);
    }

    public void buildPerfectBST(int start, int end) {
        if (start > end) return;
        int mid = (start + end) / 2;
        insert(mid);
        buildPerfectBST(start, mid - 1);
        buildPerfectBST(mid + 1, end);
    }

    tNode deleteRec(tNode root, int key) {
        if (root == null) return root;
        if (key < root.key) root.left = deleteRec(root.left, key);
        else if (key > root.key) root.right = deleteRec(root.right, key);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    int minValue(tNode root) {
        while (root.left != null) root = root.left;
        return root.key;
    }

    public void delete(int key) {
        root = deleteRec(root, key);
    }

    public tNode removeEvensSafe(tNode node) {
        if (node == null) return null;

        node.left = removeEvensSafe(node.left);
        node.right = removeEvensSafe(node.right);

        if (node.key % 2 == 0) {
            return deleteRec(node, node.key);
        }

        return node;
    }

    public void removeEvens() {
        root = removeEvensSafe(root);
    }

    public long timePopulate(int n) {
        root = null;
        long start = System.currentTimeMillis();
        buildPerfectBST(1, (int)Math.pow(2, n) - 1);
        long end = System.currentTimeMillis();
        return (end - start);
    }

    public long timeRemoveEvens() {
        long start = System.currentTimeMillis();
        removeEvens();
        long end = System.currentTimeMillis();
        return (end - start);
    }

    public void generateTable(int n, int runs) {
        long[] populateTimes = new long[runs];
        long[] removeTimes = new long[runs];

        int numKeys = (int)Math.pow(2, n) - 1;

        for (int i = 0; i < runs; i++) {
            root = null;

            long startPop = System.currentTimeMillis();
            buildPerfectBST(1, numKeys);
            long endPop = System.currentTimeMillis();
            populateTimes[i] = endPop - startPop;

            long startRem = System.currentTimeMillis();
            removeEvens(); //
            long endRem = System.currentTimeMillis();
            removeTimes[i] = endRem - startRem;
        }

        double avgPop = 0, avgRem = 0;

        for (int i = 0; i < runs; i++) {
            avgPop += populateTimes[i];
            avgRem += removeTimes[i];
        }

        avgPop /= runs;
        avgRem /= runs;

        double stdPop = 0, stdRem = 0;

        for (int i = 0; i < runs; i++) {
            stdPop += Math.pow(populateTimes[i] - avgPop, 2);
            stdRem += Math.pow(removeTimes[i] - avgRem, 2);
        }

        stdPop = Math.sqrt(stdPop / runs);
        stdRem = Math.sqrt(stdRem / runs);

        System.out.println("---------------------------------------------------------------");
        System.out.println("Method                    Number of Keys   Avg Time (ms)   Std Dev");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("Populate tree             %d               %.2f           %.2f\n", numKeys, avgPop, stdPop);
        System.out.printf("Remove evens from tree    %d               %.2f           %.2f\n", numKeys, avgRem, stdRem);
        System.out.println("---------------------------------------------------------------");
    }

    public static void main(String[] args) {
        tryBST tree = new tryBST();
        int n = 22;
        int runs = 10;

        tree.generateTable(n, runs);

        System.out.println("Is BST? " + tree.isBST());
    }
}