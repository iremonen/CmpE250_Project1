import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class Main{
    public static void main(String[]args) throws IOException {

        FileWriter fileWriter = new FileWriter("output.txt", true);
        File file = new File("/Users/iremonen/IdeaProjects/Project1/src/small5.txt");
        Scanner sc = new Scanner(file);
        AVLTree  fam = new AVLTree(fileWriter);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] inputs = line.split(" ");
            if (inputs.length == 2) {
                fam.root = new Node(Float.parseFloat(inputs[1]), inputs[0]);
            }
            if (inputs[0].equals("MEMBER_IN")) {
                fam.insert(Float.parseFloat(inputs[2]), inputs[1]);
            }
            else if (inputs[0].equals("MEMBER_OUT")) {
                fam.remove(Float.parseFloat(inputs[2]));
            }
            else if (inputs[0].equals("INTEL_TARGET")) {
                float lowerGMS = Math.min(Float.parseFloat(inputs[2]), Float.parseFloat(inputs[4]));
                float higherGMS = Math.max(Float.parseFloat(inputs[2]), Float.parseFloat(inputs[4]));
                fam.intelTarget(lowerGMS, higherGMS, fam.root);
            } else if (inputs[0].equals("INTEL_RANK")) {
                fileWriter.write("Rank Analysis Result:");
                float wantedGMS = Float.parseFloat(inputs[2]);
                int wantedRank = fam.findDepth(fam.root, wantedGMS);
                fam.printLevelOrder(wantedRank);
                fileWriter.write("\n");
            } else if (inputs[0].equals("INTEL_DIVIDE")) {
                fileWriter.write("Division Analysis Result: " + fam.biggestSubtree(fam.root));
            }
        }
        sc.close();
        fileWriter.close();
        }}