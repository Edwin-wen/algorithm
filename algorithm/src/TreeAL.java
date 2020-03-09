import sun.jvm.hotspot.debugger.windbg.x86.WindbgX86ThreadFactory;

import java.util.*;

public class TreeAL {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 树的深度
     */
    public int TreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
        return 1 +  Math.max(left, right);
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
            return 1 +  Math.max(left, right);
        }
    }

    /**
     * 判断是否为平衡二叉树
     * 当前层：根结点，左右子树返回数量是-1，或者深度差>1，就返回-1给上一层
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        return isBalance(root) != -1;
    }

    /**
     * 重建二叉树
     * 就是先用前序和后序确定根的位置
     * 再二分中序和前后序的元素，递归
     *
     * @param pre
     * @param in
     * @return
     */
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (pre[0] == in[i]) {
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return root;
    }

    public class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    /**
     * 获取中序遍历下个结点
     * 思路：分情况讨论：
     * @param pNode
     * @return
     */
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }

        TreeLinkNode next = null;
        // 1.当前结点有右子树
        if(pNode.right != null) {
            TreeLinkNode node = pNode.right;
            while (node.left != null) {
                node = node.left;
            }
            next = node;
        } else {
            TreeLinkNode father = pNode.next;
            // 2.父节点的左结点是当前结点
            if (father != null && father.left == pNode) {
                next = father;
            } else {
                // 3. 父节点的右结点是当前结点的
                TreeLinkNode curNode = pNode;
                while (father != null && father.right == curNode) {
                    curNode = father;
                    father = father.next;
                }
                next = father;
            }
        }
        return next;
    }

    /**
     * 判断是否是镜像二叉树
     * @param pRoot
     * @return
     */
    boolean isSymmetrical(TreeNode pRoot)
    {
        return pRoot == null || isSameLR(pRoot.left ,pRoot.right);
    }

    private boolean isSameLR(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        }

        if (left.val != right.val) {
            return false;
        } else {
            return isSameLR(left.left, right.right) && isSameLR(left.right, right.left);
        }
    }

    /**
     * 按之字型打印二叉树
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> PrintZhi(TreeNode pRoot) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        queue.addLast(pRoot);
        boolean isReversal = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> cengList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.removeFirst();
                if (node == null) {
                    continue;
                }
                if (isReversal) {
                    cengList.add(node.val);
                } else {
                    cengList.add(0, node.val);
                }
                queue.addLast(node.left);
                queue.addLast(node.right);
            }
            if (cengList.size() != 0) {
                result.add(cengList);
            }
            isReversal = !isReversal;
        }
        return result;
    }

    /**
     * 按层输出二叉树
     * @param pRoot
     * @return
     */
    ArrayList<ArrayList<Integer> > PrintCeng(TreeNode pRoot) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        queue.addLast(pRoot);
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> cengList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.removeFirst();
                if (node == null) {
                    continue;
                }
                cengList.add(node.val);
                queue.addLast(node.left);
                queue.addLast(node.right);
            }
            if (cengList.size() != 0) {
                result.add(cengList);
            }
        }
        return result;
    }

    /**
     * 序列化二叉树
     * 思路：反序列化时需要个全局变量标志当前走到了字符数组的什么位置了，因为前序遍历都是向左不断加进去的
     * @param root
     * @return
     */
    String Serialize(TreeNode root) {
        if (root == null) {
            return "#";
        }
        return root.val + "!" + Serialize(root.left) + "!" + Serialize(root.right);
    }

    private int index = -1;
    TreeNode Deserialize(String str) {
        String[] strings = str.split("!");
        index++;
        if (index >= strings.length) {
            return null;
        }

        String s = strings[index];
        TreeNode node = null;
        if (!s.equals("#")) {
            node = new TreeNode(Integer.parseInt(s));
            node.left = Deserialize(str);
            node.right = Deserialize(str);
        }
        return node;
    }

    /**
     * 二叉搜索树第k个结点
     * @param pRoot
     * @param k
     * @return
     */
    private int kIndex = 0;
    TreeNode KthNode(TreeNode pRoot, int k)
    {
        if (pRoot == null || k <= 0) {
            return null;
        }
        TreeNode node = KthNode(pRoot.left, k);
        if (node != null) {
            return node;
        }
        kIndex++;
        if (k == kIndex) {
            return pRoot;
        }
        node = KthNode(pRoot.right, k);
        if (node != null) {
            return node;
        }
        return null;
    }


    /**
     * 树的子结构
     * 思路，外层递归找到相同的根，在递归两个根的是不是有子结构（子结构就是当前结点是不是相等，再判断左右结点）
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        // root1为空，说明这条路走完了
        if (root1 == null || root2 == null) {
            return false;
        }
        if (root1.val == root2.val) {
            if (hasSub(root1, root2)) {
                return true;
            }
        }
        return HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    public boolean hasSub(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }
        if (root1.val == root2.val) {
            return hasSub(root1.left, root2.left) && hasSub(root1.right, root2.right);
        }
        return false;
    }

    /**
     * 二叉树的镜像
     * @param root
     */
    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        Mirror(root.left);
        Mirror(root.right);
    }

    /**
     * 按层打印二叉树
     *
     */
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.removeFirst();
            result.add(node.val);
            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
        }
        return result;
    }

    /**
     * 判断某个序列是不是二叉搜索树的后续遍历
     * @param sequence
     * @return
     */
    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence == null || sequence.length == 0) {
            return false;
        }
        return isSquenctBST(sequence, 0, sequence.length - 1);
    }

    public boolean isSquenctBST(int[] sequence, int start, int root) {
        if (start >= root) {
            return true;
        }
        int dataRoot = sequence[root];
        //先找到分界点
        int split = start;
        while (split < root) {
            if (sequence[split] > dataRoot) {
                break;
            }
            split++;
        }

        // 在右子树找有没有比根结点小的
        for (int j = split; j < root; j++){
            if (sequence[j] < dataRoot) {
                return false;
            }
        }
        // 递归左右子树
        return isSquenctBST(sequence, start, split - 1) && isSquenctBST(sequence, split, root - 1);
    }


    /**
     * 二叉树和为某一路经的
     * @param root
     * @param target
     * @return
     */
    private ArrayList<ArrayList<Integer>> allPath = new ArrayList<>();
    private ArrayList<Integer> onePath = new ArrayList();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        collectAllPaths(root, target);
        return allPath;
    }

    private void collectAllPaths(TreeNode root,int target) {
        if (root == null) {
            return;
        }
        // 遍历时每个结点都加进去
        onePath.add(root.val);
        target -= root.val;
        //到达叶子结点
        if (target == 0 && root.left == null && root.right == null) {
            allPath.add(new ArrayList<>(onePath));
        }
        collectAllPaths(root.left, target);
        collectAllPaths(root.right, target);
        // 递归回去时把根结点移除
        onePath.remove(onePath.size() - 1);
    }


    /**
     * 二叉树转双向链表
     * 思路：设置一个左子树的尾部指针，到达根结点，就可以直接处理尾部指针和根节点的转换，这时再把右子树变成双向链表，再处理右子树和根节点的转换
     * @param pRootOfTree
     * @return
     */
    private TreeNode tailNode = null;
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }

        if (pRootOfTree.left == null && pRootOfTree.right == null) {
            // tail结点就是左链中最后一个结点
            tailNode = pRootOfTree;
            // 这里返回的就是最左结点，当做双向链表的链头
            return pRootOfTree;
        }

        TreeNode left = Convert(pRootOfTree.left);
        if (left != null) {
            tailNode.right = pRootOfTree;
            pRootOfTree.left = tailNode;
        }

        // 把链表结尾偏移到根
        tailNode = pRootOfTree;

        // 将右子树也构建成双向链表，再连接根结点
        TreeNode right = Convert(pRootOfTree.right);
        if (right != null) {
            pRootOfTree.right = right;
            right.left = pRootOfTree;
        }
        return left != null ? left : pRootOfTree;
    }
}
