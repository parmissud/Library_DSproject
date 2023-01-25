public class Trie {
    public static void main(String[] args) {
        Trie trie=new Trie();
        trie.insert("abb");
        trie.insert("abbc");
        trie.insert("abc");
        trie.insert("bcd");
        System.out.println(trie.search("abb"));
        System.out.println(trie.search("affb"));
        StringBuilder str=new StringBuilder();
        trie.print(trie.root,str);


    }
    static final int ALPHABET =26;
    static Node root;
    static class Node{
        boolean endOfWord;
        Node[] children;
        int indexInArray;
        public Node() {
            endOfWord=false;
            indexInArray=0;
            children=new Node[ALPHABET];
            for (int i = 0; i < ALPHABET; i++) {
                children[i]=null;
            }
        }
    }
    void insert(String key){
        int index;
        if (root==null)
            root=new Node();
        Node currNode=root;
        for (int level = 0; level < key.length(); level++) {
            index=key.charAt(level)-'a';
            if (currNode.children[index]==null)
                currNode.children[index]=new Node();
            currNode=currNode.children[index];
        }
        currNode.endOfWord=true;
    }
    boolean search(String value){
        int index;
        Node currNode=root;
        for (int level = 0; level < value.length(); level++) {
            index=value.charAt(level)-'a';
            if (currNode.children[index]==null)
                return false;
            currNode=currNode.children[index];
        }
        return currNode.endOfWord;
    }
    void print(Node root,StringBuilder str){
        Node currNode = root;
        boolean isLeaf=true;
        for (int i = 0; i < 26; i++) {
            if (currNode.children[i] != null) {
                isLeaf=false;
                break;
            }
        }
        if (isLeaf){
            System.out.println(str);
            return;
        }
        for (int i = 0; i < 26; i++) {
            if (currNode.children[i] != null) {
                str.append((char) ('a'+i));
                print(currNode.children[i], str);
            }
        }
        str.append(" ");
    }
}
