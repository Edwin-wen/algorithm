package interview;

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
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
        return 1 + Math.max(left, right);
    }

    public int isBalance(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = isBalance(root.left);
        int right = isBalance(root.right);
        if (left == -1 || right == -1 || Math.abs(left, right) > 1) {
            return -1;
        }
        return 1 + Math.max(left, right);
    }

    public int isBinarySort(TreeNode root) {

    }

    private void midTravesal(TreeNode root) {
        midTravesal(root.left);

    }

    TreeAL.TreeNode KthNode(TreeAL.TreeNode pRoot, int k) {

    }

    public boolean VerifySquenceOfBST(int [] sequence) {

    }


    /**
     * goujian
     */
    public TreeAL.TreeNode reConstructBinaryTree(int[] pre, int[] in) {

    }

    String Serialize(TreeAL.TreeNode root) {

    }

    TreeAL.TreeNode Deserialize(String str) {

    }


    /**
     * specil strcut
     */
    public void Mirror(TreeAL.TreeNode root) {

    }

    boolean isSymmetrical(TreeAL.TreeNode pRoot) {

    }

    public boolean HasSubtree(TreeAL.TreeNode root1, TreeAL.TreeNode root2) {

    }

    /**
     * specil op
     */
    private void collectAllPaths(TreeAL.TreeNode root, int target) {

    }

    public TreeAL.TreeNode Convert(TreeAL.TreeNode pRootOfTree) {

    }


    /**
     * 遍历
     */

}
