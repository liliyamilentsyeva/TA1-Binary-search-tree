import java.io.*;
import java.util.*;

public class BST {

    static ArrayList<Integer> massiv = new ArrayList<>();
    static BSTItem root = null;
    static int maxLength = 0;
    static int minDepth = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BST tree1 = new BST();

        try (Scanner in = new Scanner(new File("in.txt"))) {
            while (in.hasNext())
                tree1.insertItem(in.nextInt());
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("out.txt"))) {
            if (root != null) {
                findHeight(root);
                findRoot(root);
                for (Integer integer : massiv)
                    deleteItem(integer);
                walkDirect(root, out);
            }
        }
    }

    private static class BSTItem {
        int info;
        BSTItem left;
        BSTItem right;
        BSTItem father;
        int length = 0;
        int depth = 0;
        int height = 0;

        BSTItem(int aInfo) {
            info = aInfo;
            left = null;
            right = null;
            father = null;
        }
    }

    public boolean insertItem(int aInfo) {
        if (root == null) {
            root = new BSTItem(aInfo);
            return true;
        }
        BSTItem r;
        BSTItem s0 = find(aInfo);
        if (s0.info == aInfo)
            return false;
        r = new BSTItem(aInfo);
        if (s0.info > aInfo)
            s0.left = r;
        else
            s0.right = r;
        r.father = s0;
        return true;
    }


    public static boolean deleteItem(int aInfo) {
        BSTItem p = find(aInfo);
        if (p == null || p.info != aInfo)
            return false;
        if (p.left == null && p.right == null) {
            if (p.father == null)
                root = null;
            else if (p.father.left == p)
                p.father.left = null;
            else
                p.father.right = null;
            return true;
        }
        if (p.left == null) {
            if (p.father == null)
                root = p.right;
            else {
                if (p.father.right == p)
                    p.father.right = p.right;
                else
                    p.father.left = p.right;
            }
            return true;
        }
        if (p.right == null) {
            if (p.father == null)
                root = p.left;
            else {
                if (p.father.right == p)
                    p.father.right = p.left;
                else
                    p.father.left = p.left;
            }
            return true;
        }

        BSTItem q = p.right;
        while (q.left != null)
            q = q.left;
        p.info = q.info;
        if (q.father == p)
            p.right = q.right;
        else
            q.father.left = q.right;
        return true;
    }


    private static BSTItem find(int aInfo) {
        BSTItem p = root, q = null;
        while (p != null) {
            if (p.info == aInfo)
                return p;
            q = p;
            if (p.info < aInfo)
                p = p.right;
            else
                p = p.left;
            if (p != null)
                q = p;
        }
        return q;
    }

    private static void walkDirect(BSTItem r, PrintWriter out2) {
        if (r != null) {
            out2.println(r.info);
            if (r.left != null) {
                walkDirect(r.left, out2);
            }
            if (r.right != null) {
                walkDirect(r.right, out2);
            }
        }
    }

    public static int findHeight(BSTItem r) {
        if (r == null)
            return -1;
        int left = findHeight(r.left), right = findHeight(r.right);
        r.length = left + right + 2;
        if (r.length > maxLength) {
            maxLength = r.length;
        }
        r.height = Math.max(left, right) + 1;
        return r.height;
    }

    public static void findRoot(BSTItem r) {
        if (r.length == maxLength) {
            r.depth = root.height - r.height;
            if (r.depth <= minDepth) {
                minDepth = r.depth;
                massiv.add(r.info);
            }
        }
        if (r.left != null) {
            findRoot(r.left);
        }
        if (r.right != null) {
            findRoot(r.right);
        }
    }

}