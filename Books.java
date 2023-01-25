public class Books {
    static Book root = new Book();

    void addNewBook(String bookName, int count) {
        int index;
        Book currBook = root;
        for (int level = 0; level < bookName.length(); level++) {
            index = bookName.charAt(level) - 'a';
            if (currBook.children[index] == null) currBook.children[index] = new Book();
            currBook = currBook.children[index];
        }
        currBook.count += count;
        currBook.isAvailable = true;
        currBook.name = bookName;
    }

    boolean isEmpty(Book root) {
        for (int i = 0; i < 26; i++)
            if (root.children[i] != null) return false;
        return true;
    }

    void remove(Book root, String bookName, int level) {
        if (root == null) return;
        if (level == bookName.length()) {
            if (isEmpty(root)) {
                root = null;
            }
            return;
        }
        int index = bookName.charAt(level) - 'a';
        remove(root.children[index], bookName, level + 1);
        if (isEmpty(root)) {
            root = null;
        }
    }

    boolean hasBook(String bookName) {
        int index;
        Book currBook = root;
        for (int level = 0; level < bookName.length(); level++) {
            index = bookName.charAt(level) - 'a';
            if (currBook.children[index] == null) {
                return false;
            }
            currBook = currBook.children[index];
        }
        return true;
    }

    void allPersonsHaveThisBook(String bookName) {
        int index;
        Book currBook = root;
        for (int level = 0; level < bookName.length(); level++) {
            index = bookName.charAt(level) - 'a';
            if (currBook.children[index] == null) {
                currBook.children[index] = new Book();
            }
            currBook = currBook.children[index];
        }
       StringBuilder st=new StringBuilder();
        print(currBook.currentlyBorrowedBy.root,st);

    }

    public static void print(Member root,StringBuilder memberName) {
        Member currMember = root;
        boolean is=false;
        for (int i = 0; i < 26; i++) {
            if (currMember.children[i] != null) {
                is = true;
                break;
            }
        }
        if (is)
            return;
        if (!is){
            System.out.println(memberName);
            return;
        }
        for (int i = 0; i < 26; i++) {
            while (currMember.children[i] != null) {
                memberName.append((char) ('a'+i));
                print(currMember.children[i], memberName);
            }
        }
    }
//        void  allPersonsBorrowedThisBook(String bookName){
//        int index;
//        Book currBook=root;
//        for (int level = 0; level < bookName.length(); level++) {
//            index=bookName.charAt(level)-'a';
//            if (currBook.children[index]==null){
//                System.out.println(bookName+" is not available at all.");
//                return;
//            }
//            currBook=currBook.children[index];
//        }
//        if (currBook.endOfWord){
//            System.out.println(bookName+" has borrowed by:");
//            for (int i = 0; i < currBook.countOfMembersBorrowedThisBook; i++) {
//                System.out.println(i+" -"+currBook.borrowedBy[i].name);
//            }
//            return;
//        }
//        System.out.println(bookName+" is not available at all.");
//        return;
//    }
}

class Book {
    static final int ALPHABET = 26;
    Book[] children;
    String name;
    boolean isAvailable;
    Members currentlyBorrowedBy;
    Members borrowedBy;
    int countOfMembersCurrentlyBorrowedThisBook;
    int countOfMembersBorrowedThisBook;
    int count;

    public Book() {
        children = new Book[ALPHABET];
        for (int i = 0; i < ALPHABET; i++) {
            children[i] = null;
        }
        isAvailable = false;
        currentlyBorrowedBy = new Members();
        borrowedBy = new Members();
        countOfMembersBorrowedThisBook = 0;
        countOfMembersCurrentlyBorrowedThisBook = 0;
        count = 0;
    }
}