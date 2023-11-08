import java.io.FileWriter;
import java.io.IOException;

public class AVLTree extends RuntimeException {
    public Node root;
    public FileWriter fileWriter;

    public AVLTree(FileWriter fw) {
        this.fileWriter = fw;
        this.root = null;
    }

    public int biggestSubtree(Node rootNode)
    {
        if (rootNode == null)
            return 0;
        if (rootNode.biggestSubtree != 0)
            return rootNode.biggestSubtree;
        if (rootNode.left == null && rootNode.right == null)
            return rootNode.biggestSubtree = 1;

        // Calculate size excluding the current node
        int withoutCurrentNode = biggestSubtree(rootNode.left) + biggestSubtree(rootNode.right);

        // Calculate size including the current node
        int withCurrentNode = 1;
        if (rootNode.left != null)
        {
            withCurrentNode += (biggestSubtree(rootNode.left.left) + biggestSubtree(rootNode.left.right));
        }
        if (rootNode.right != null)
        {
            withCurrentNode += (biggestSubtree(rootNode.right.left) + biggestSubtree(rootNode.right.right));
        }

        // Maximum of two sizes is biggestSubtree, store it for future uses.
        return rootNode.biggestSubtree = Math.max(withoutCurrentNode, withCurrentNode);
    }
    public void intelTarget(float lowerGMS, float higherGMS, Node rootNode) throws IOException {
        if (rootNode.GMS <= higherGMS && rootNode.GMS >= lowerGMS) {
            String strGMS = Float.toString(rootNode.GMS);
            fileWriter.write("Target Analysis Result: " + rootNode.name + " " + strGMS +"\n");
        } else if (rootNode.GMS > higherGMS) {
            intelTarget(lowerGMS, higherGMS, rootNode.left);

        } else if (rootNode.GMS < lowerGMS) {
            intelTarget(lowerGMS, higherGMS, rootNode.right);
        }

    }
    public int findDepth(Node rootNode, float wantedGMS) {
        int depth = 0;
        while (rootNode.GMS != wantedGMS){
            if (rootNode.GMS < wantedGMS) {
                rootNode = rootNode.right;

            } else if (rootNode.GMS > wantedGMS) {
                rootNode = rootNode.left;
            } 
            depth++;
        } return depth;
    }

    public void printCurrentLevel(Node root, int level) throws IOException {
        if (root == null)
            return;
        if (level == 1) {
            fileWriter.write(" " + root.name + " " + root.GMS);
        }
        else if (level > 1) {
            printCurrentLevel(root.left, level - 1);
            printCurrentLevel(root.right, level - 1);
        }
    }
    public void printLevelOrder(int depth) throws IOException {
        printCurrentLevel(root, depth + 1);
    }
    public Node remove(float deletedGMS, Node rootNode) throws IOException {
        if (rootNode == null) return rootNode;
        if (deletedGMS < rootNode.GMS) rootNode.left = remove(deletedGMS, rootNode.left);
        else if (deletedGMS > rootNode.GMS) rootNode.right = remove(deletedGMS, rootNode.right);
        else if (rootNode.left != null && rootNode.right != null) {
            fileWriter.write(rootNode.name + " left the family, replaced by " + findMin(rootNode.right).name+ "\n");
            rootNode.GMS = findMin(rootNode.right).GMS;
            rootNode.name = findMin(rootNode.right).name;
            rootNode.right = remove(deletedGMS, rootNode.right);
        } else {
            if (rootNode.left != null) {
               fileWriter.write(rootNode.name + " left the family, replaced by " + rootNode.left.name+ "\n");
                return rootNode.left;
            }
            else if (rootNode.right != null) {
                fileWriter.write(rootNode.name + " left the family, replaced by " + rootNode.right.name+ "\n");
                return rootNode.right;
            } else {
                fileWriter.write(rootNode.name + " left the family, replaced by nobody"+ "\n");
                return null;
            }
        }
        return balance(rootNode);
    } public void remove(float deletedGMS) throws IOException { root = remove(deletedGMS, root);}

    public int height(Node node) {
        return node == null ? -1 : node.h;
    }
    public Node findMin(Node node) {
        if (node == null) return node;
        while (node.left != null) node = node.left;
        return node;
    }
    public Node insert(Node newNode, Node rootNode) throws IOException {
        if (rootNode == null) {
            return new Node(newNode.GMS, newNode.name);
        }
        fileWriter.write(rootNode.name + " welcomed " + newNode.name + "\n");
        if (newNode.GMS < rootNode.GMS) {

            rootNode.left = insert(newNode, rootNode.left);

        }
        else if (newNode.GMS > rootNode.GMS) {
            rootNode.right = insert(newNode, rootNode.right);
        }
        else ; // duplicate
        return balance(rootNode);
    }
    public void insert(float GMS, String name) throws IOException {
        Node newNode = new Node(GMS, name);
        root = insert(newNode, root);
    }
    public Node balance(Node rootNode) {
        if (rootNode == null) return rootNode;
        if (height(rootNode.left) - height(rootNode.right) > 1) {
            if (height(rootNode.left.left) >= height(rootNode.left.right)) rootNode = rightRotation(rootNode);
            else rootNode = leftRightRotation(rootNode);
        }
        if (height(rootNode.right) - height(rootNode.left) > 1) {
            if (height(rootNode.right.right) >= height(rootNode.right.left)) rootNode = leftRotation(rootNode);
            else rootNode = rightLeftRotation(rootNode);
        }
        rootNode.h = Math.max(height(rootNode.left), height(rootNode.right)) + 1;
        return rootNode;
    }
    public Node rightRotation(Node node2) {
        Node node1 = node2.left;
        node2.left = node1.right;
        node1.right = node2;
        node2.h = Math.max(height(node2.left), height(node2.right)) + 1;
        node1.h = Math.max(height(node1.left), node2.h) + 1;
        return node1;
    }

    public Node leftRotation(Node node1) {
        Node node2 = node1.right;
        node1.right = node2.left;
        node2.left = node1;
        node1.h = Math.max(height(node1.left), height(node1.right)) + 1;
        node2.h = Math.max(height(node2.right), node1.h) + 1;
        return node2;
    }

    public Node leftRightRotation(Node node3) {
        node3.left = leftRotation(node3.left);
        return rightRotation(node3);
    }

    public Node rightLeftRotation(Node node1) {
        node1.right = rightRotation(node1.right);
        return leftRotation(node1);
    }
}