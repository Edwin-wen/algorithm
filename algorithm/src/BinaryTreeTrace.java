import javax.print.attribute.standard.NumberUp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class BinaryTreeTrace {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){
            val=x;
        }
        @Override
        public String toString(){
            return "val: "+val;
        }
    }

    private void visit(TreeNode node) {

    }

    /**
     * 前序遍历递归
     *
     */
    public void frontTrace(TreeNode root) {
        if (root ==null) {
             return;
        }
        visit(root);
        frontTrace(root.left);
        frontTrace(root.right);
    }

    /**
     * 非递归
     * @param root
     */
    public void frontTrace2(TreeNode root) {
        if (root ==null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            visit(node);
            if (node.right != null) {
                stack.add(node.right);
            }
            if (root.left != null) {
                stack.add(node.left);
            }
        }
    }

    /**
     * 后序遍历
     */
    public void backTrace(TreeNode root) {
        if (root == null) {
            return;
        }
        backTrace(root.left);
        backTrace(root.right);
        visit(root);
    }

    /**
     * 非递归
     */
    public void backTrace2(TreeNode root) {
        if (root ==null) {
            return;
        }
        ArrayList<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            if (root.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }
        Collections.reverse(list);
    }

    /**
     * 中序遍历
     */
    public void midTrace(TreeNode root) {
        if (root == null) {
            return;
        }

        midTrace(root.left);
        visit(root);
        midTrace(root.right);
    }

    /**
     * 非递归
     * 思路：就是将左节点入栈前，把根结点先入栈，所以访问完左结点就可以指向右结点
     */
    public void midTrace2(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                //向左走的时候先把当前根结点入栈
                stack.add(cur);
                cur = cur.left;
            }

            // pop出来肯定是左结点
            TreeNode node = stack.pop();
            visit(node);
            // 指针指向右子树
            cur = cur.right;
        }
    }

}
