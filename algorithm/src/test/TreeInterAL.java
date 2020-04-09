import java.util.ArrayList;
import java.util.Arrays;

public class TreeInterAL {

    public class TreeNode {
        int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     *
     * 类型
     */
    public int TreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(TreeDepth(root.left), TreeDepth(root.right));
    }

    public int isBalance(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        } else {
            return  1 + Math.max(left, right);
        }
    }

    private int pre = Integer.MIN_VALUE;
    public boolean isBinarySort(TreeNode root) {
        if (root == null) {
            return true;
        }
        isBinarySort(root.left);
        if (pre == Integer.MIN_VALUE) {
            pre = root.val;
        } else if (pre > root.val){
            return false;
        } else {
            return isBinarySort(root.right);
        }
        return true;
    }

    private int count = 0;
    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null || k <= 0) {
            return null;
        }
        TreeNode left = KthNode(pRoot.left, k);
        if (left != null) {
            return left;
        }
        count++;
        if (count == k) {
            return pRoot;
        }
        TreeNode right = KthNode(pRoot.right, k);
        if (right != null) {
            return right;
        }
        return null;
    }

    private TreeNode kNode = null;
    void getKNode(TreeNode root, int k) {
        if (root == null || k <= 0) {
            return;
        }
        getKNode(root.left, k);
        count++;
        if (count == k) {
            kNode = root;
        }
        if (kNode == null) {
            getKNode(root.right, k);
        }
    }

    public boolean VerifySquenceOfBST(int [] sequence) {
        int length = sequence.length;
        if (length == 0) {
            return true;
        }
        int root = sequence[length - 1];
        int index = 0;
        while (index < length && root > sequence[index]) {
            index++;
        }
        int splitIndex = index;
        while (index < length) {
            index++;
            if (root > sequence[index]) {
                return false;
            }
        }
        return VerifySquenceOfBST(Arrays.copyOfRange(sequence, 0, splitIndex)) && VerifySquenceOfBST(Arrays.copyOfRange(sequence, splitIndex, length - 1));
    }


    /**
     * goujian
     */
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        int preLength = pre.length;
        int inLength  = in.length;
        if (preLength == 0 || inLength == 0) {
            return null;
        }
        if (preLength != inLength) {
            throw new RuntimeException();
        }
        int root = pre[0];
        int index = 0;
        TreeNode rootNode = new TreeNode(root);
        while (index < inLength && root != in[index]) {
            index++;
        }
        rootNode.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, index), Arrays.copyOfRange(in, 0, index));
        rootNode.right = reConstructBinaryTree(Arrays.copyOfRange(pre, index, preLength), Arrays.copyOfRange(in, index + 1, inLength));
        return rootNode;
    }

    String Serialize(TreeNode root) {
        return null;
    }

    TreeNode Deserialize(String str) {
        return null;
    }


    /**
     * specil strcut
     */
    public void Mirror(TreeNode root) {
        if (root == null || root.left == null || root.right == null) {
            return;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        Mirror(root.left);
        Mirror(root.right);
    }

    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null || pRoot.left == null || pRoot.right == null) {
            return false;
        }
        return isMirror(pRoot.left, pRoot.right);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left.val == right.val) {
            return isMirror(left.left, right.right) && isMirror(left.right, right.left);
        } else {
            return false;
        }
    }

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 != null) {
            return false;
        }
        if (root2 == null) {
            return true;
        }
        if (root1.val == root2.val) {
            return hasSub(root1, root2);
        } else {
            return HasSubtree(root1.left, root2) && HasSubtree(root1.right, root2);
        }
    }

    private boolean hasSub(TreeNode root1, TreeNode root2) {
        if (root1.val == root2.val) {
            return hasSub(root1.left, root2.left) && hasSub(root2.right, root2.right);
        } else {
            return false;
        }
    }

    /**
     * specil op
     */
    private ArrayList<Integer> path = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> allPath = new ArrayList<>();
    private void collectAllPaths(TreeNode root, int target) {
        if (root == null) {
            return;
        }
        path.add(root.val);
        target -= root.val;
        if (root.left == null && root.right == null && target == 0) {
            allPath.add(new ArrayList<>(path));
        }
        collectAllPaths(root.left, target);
        collectAllPaths(root.right, target);
        path.remove(path.size() - 1);
    }

    private TreeNode tailNode = null;
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }
        if (pRootOfTree.left == null && pRootOfTree.right == null) {
            tailNode = pRootOfTree;
            return pRootOfTree;
        }
        TreeNode left = Convert(pRootOfTree.left);
        if (left != null) {
            pRootOfTree.left = tailNode;
            tailNode.right = pRootOfTree;
        }

        tailNode = pRootOfTree;

        TreeNode right = Convert(pRootOfTree.right);
        if (right != null) {
            right.left = pRootOfTree;
            pRootOfTree.right = right;
        }
        return left == null ? pRootOfTree : left;
    }


    /**
     * 遍历
     */

}
