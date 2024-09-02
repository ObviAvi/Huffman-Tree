# Huffman-Tree
Overview
This Java project implements the Huffman Compression Algorithm to efficiently compress and decompress text files. Huffman Compression is a lossless data compression algorithm that reduces the amount of space required to store text by using variable-length codes to represent characters based on their frequencies of occurrence.

## Key Concepts:

**Huffman Compression:**
In traditional text files, each character is represented by a fixed-length code, typically 8 bits. However, not all characters are used with the same frequency. Huffman Compression leverages this by assigning shorter codes to more frequently occurring characters and longer codes to less frequent ones. This creates a custom binary tree (Huffman Tree) that represents characters with varying bit lengths, ultimately saving space in the compressed file.

**Huffman Tree:**
The Huffman Tree is a binary tree where each leaf node represents a character from the input text, and the path from the root to the leaf node represents the binary code for that character. The tree is built by combining the least frequent characters and creating parent nodes that represent the sum of their frequencies. This process continues until a single root node remains, which represents the entire text.

## Constructors:

HuffmanTree(int[] count): Constructs a Huffman Tree based on the frequencies of characters in the input text. The count array contains the frequencies of each character.

HuffmanTree(String codeFile): Reconstructs a Huffman Tree from a previously saved code file (Hamlet.code). This allows the tree to be recreated and used for decoding without having the original frequency data.

## Methods

- void encode(BitOutputStream out, String inFile): Reads the input text file, converts each character into its corresponding Huffman code, and writes the bits to the output stream.

- void decode(BitInputStream in, String outFile): Reads the compressed bitstream and reconstructs the original text file using the Huffman Tree.

- void write(String fileName): Writes the Huffman Tree's structure and codes to a file, enabling the tree to be reused for decoding.

- void writeHelper(Node n, String currAscii, PrintWriter pw): A recursive helper method to traverse the Huffman Tree and generate the codes for each character.

- String findLetter(char letter, String path, Node n): A recursive method to find the Huffman code for a specific character by traversing the tree.

- void makeTree(String chars, ArrayList<String> codes): Reconstructs the Huffman Tree from a list of characters and their corresponding Huffman codes.

## Node:

A helper class used to represent the nodes in the Huffman Tree. Each node contains:

- int val: The ASCII value of the character represented by the node.
- int frequency: The frequency of the character in the input text.
- Node left: The left child of the node.
- Node right: The right child of the node.
