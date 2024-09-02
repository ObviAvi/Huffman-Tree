import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanTree {
    Node root;
    ////////////////////creates Huffman Tree based on frequencies/////////////////////
    HuffmanTree(int[] count){
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>();

        for(int i = 0; i < count.length; i++) if(count[i] > 0) nodeQueue.add(new Node(i, count[i]));
        nodeQueue.add(new Node(255, 1));

        while(nodeQueue.size() > 1){
            Node a = nodeQueue.poll();
            Node b = nodeQueue.poll();
            Node miniRoot = new Node(-1, a.frequency + b.frequency);
            miniRoot.left = a;
            miniRoot.right = b;
            nodeQueue.add(miniRoot);
        }

        root = nodeQueue.poll();
        write("Hamlet.code");
        TreePrinter.printTree(root);
    }
    ////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////Makes Huffman tree based on Hamlet.code////////////////////
    HuffmanTree(String codeFile){
        Scanner input = null;
        try{
            input = new Scanner(new File(codeFile));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        String chars = "";
        ArrayList<String> codes = new ArrayList<>();
        while(input.hasNext()){
            chars += (char)input.nextInt();
            codes.add(input.next());
        }

        makeTree(chars, codes);
    }
    void makeTree(String chars, ArrayList<String> codes){
        Node miniRoot = new Node(-1, -1);
        for(int i = 0; i < codes.size(); i++){
            Node rootCopy = miniRoot;
            String code = codes.get(i);
            while(code.length() > 0){
                if(code.charAt(0) == '0') {
                    if(rootCopy.left == null) rootCopy = rootCopy.left = new Node(-1, -1);
                    else rootCopy = rootCopy.left;
                }else{
                    if (rootCopy.right == null) rootCopy = rootCopy.right = new Node(-1, -1);
                    else rootCopy = rootCopy.right;
                }
                code = code.substring(1);
            }
            rootCopy.val = chars.charAt(i);
        }
        root = miniRoot;
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    ////////////////Writes the huffman tree into Hamlet.code////////////////////////////////
    void write(String fileName){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(fileName);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        writeHelper(root, "", pw);
        pw.close();
    }
    public void writeHelper(Node n, String currAscii, PrintWriter pw){
        if(n == null) return;

        if(n.left == null && n.right == null) pw.println(n.val + "\n" + currAscii);
        else{
            writeHelper(n.left, currAscii + "0", pw);
            writeHelper(n.right, currAscii + "1", pw);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////Puts the bits back into txt////////////////////////////
    void decode(BitInputStream in, String outFile){

        PrintWriter pw = null;
        try{
            pw = new PrintWriter(outFile);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }

        Node rootCopy = root;

        while(true) {
            int bit = in.readBit();
            if (bit == 0) {
                rootCopy = rootCopy.left;
                if (rootCopy.val != -1){
                    if(rootCopy.val == 255) break;
                    else if(rootCopy.val == 254) pw.println();
                    else pw.print((char) rootCopy.val);
                    rootCopy = root;
                }
            } else if (bit == 1) {
                rootCopy = rootCopy.right;
                if (rootCopy.val != -1) {
                    if(rootCopy.val == 255) break;
                    else if(rootCopy.val == 254) pw.println();
                    else pw.print((char) rootCopy.val);
                    rootCopy = root;
                }
            }
        }
        pw.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////Puts the text into Bits/////////////////////////////////////////////////
    void encode(BitOutputStream out, String inFile){
        Scanner input = null;
        try{
            input = new Scanner(new File(inFile));
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }

        while(input.hasNext()){
            char[] cArr = (input.nextLine() + (char)254).toCharArray();
            for(char c: cArr){
                String path = findLetter(c, "", root);
                while(path.length() > 0){
                    if(path.charAt(0) == '0') out.writeBit(0);
                    else out.writeBit(1);
                    path = path.substring(1);
                }
            }
        }

        String path = findLetter((char)255, "", root);
        while(path.length() > 0){
            if(path.charAt(0) == '0') out.writeBit(0);
            else out.writeBit(1);
            path = path.substring(1);
        }

        out.close();
    }
    String findLetter(char letter, String path, Node n){
        if(n == null) return "";
        if(n.val == (int)letter) return path;

        return findLetter(letter, path + "0", n.left) + findLetter(letter, path + "1", n.right);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

}
