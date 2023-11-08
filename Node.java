public class Node {
    public float GMS;
    public String name;
    public int biggestSubtree;
    public int h;
    public Node left;
    public Node right;

    public Node () {
        left = null;
        right = null;
        h = 0;
        biggestSubtree = 0;
    }
    public Node(float GMS, String name){
        left = null;
        right = null;
        this.GMS = GMS;
        this.name = name;
        h = 0;
    }

}