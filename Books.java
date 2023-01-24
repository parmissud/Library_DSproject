public class Books {
        static Book root;

        void addNewBook(String bookName,int count){
            if (count<=0){
                System.out.println("count of book is not acceptable.");
                return;
            }
            int index;
            if (root==null) {
                root = new Book();
                root.count=count;
            }
            Book currBook=root;
            for (int level = 0; level < bookName.length(); level++) {
                index=bookName.charAt(level)-'a';
                if (currBook.children[index]==null)
                    currBook.children[index]=new Book();
                currBook=currBook.children[index];
            }
            if (currBook.endOfWord==true ){
                System.out.println(bookName+" was available.");
                currBook.count+=count;
                return;
            }
            if (count<=0){
                System.out.println("count of book is not acceptable.");
            }
            currBook.count=count;
            currBook.isAvailable=true;
            currBook.name=bookName;
            currBook.endOfWord=true;
            System.out.println(bookName+" has been added!");
        }
        void hasBook(String bookName){
            int index;
            Book currBook=root;
            for (int level = 0; level < bookName.length(); level++) {
                index=bookName.charAt(level)-'a';
                if (currBook.children[index]==null){
                    System.out.println(bookName+" is not available at all.");
                    return;
                }
                currBook=currBook.children[index];
            }
            if (currBook.endOfWord && currBook.count==0){
                System.out.println(bookName+ "is not available at the moment.");
                return;
            }
            if (currBook.endOfWord){
                System.out.println(bookName +"is available.");
                return;
            }
            System.out.println(bookName+" is not available at all.");
            return;
        }
        void allPersonsHaveThisBook(String bookName){
            int index;
            Book currBook=root;
            for (int level = 0; level < bookName.length(); level++) {
                index=bookName.charAt(level)-'a';
                if (currBook.children[index]==null){
                    System.out.println(bookName+" is not available at all.");
                    return;
                }
                currBook=currBook.children[index];
            }
            if (currBook.endOfWord){
                System.out.println(bookName+" is currently borrowed by:");
                for (int i = 0; i < currBook.countOfMembersCurrentlyBorrowedThisBook; i++) {
                    System.out.println(i+" -"+currBook.currentlyBorrowedBy[i].name);
                }
                return;
            }
            System.out.println(bookName+" is not available at all.");
            return;
        }
        void  allPersonsBorrowedThisBook(String bookName){
        int index;
        Book currBook=root;
        for (int level = 0; level < bookName.length(); level++) {
            index=bookName.charAt(level)-'a';
            if (currBook.children[index]==null){
                System.out.println(bookName+" is not available at all.");
                return;
            }
            currBook=currBook.children[index];
        }
        if (currBook.endOfWord){
            System.out.println(bookName+" has borrowed by:");
            for (int i = 0; i < currBook.countOfMembersBorrowedThisBook; i++) {
                System.out.println(i+" -"+currBook.borrowedBy[i].name);
            }
            return;
        }
        System.out.println(bookName+" is not available at all.");
        return;
    }

}
class Book{
    static final int ALPHABET =26;
    boolean endOfWord;
    Book[] children;
    String name;
    boolean isAvailable;
    Member[] currentlyBorrowedBy;
    Member[] borrowedBy;
    int indexInCurrentlyBorrowed;
    int indexInBorrowed;
    int countOfMembersCurrentlyBorrowedThisBook;
    int countOfMembersBorrowedThisBook;
    int count;
    public Book() {
        endOfWord=false;
        children=new Book[ALPHABET];
        for (int i = 0; i < ALPHABET; i++) {
            children[i]=null;
        }
        isAvailable=false;
        currentlyBorrowedBy=new Member[100];
        borrowedBy=new Member[100];
        countOfMembersBorrowedThisBook=0;
        countOfMembersCurrentlyBorrowedThisBook=0;
        count=0;
        indexInCurrentlyBorrowed=-1;
        indexInBorrowed=-1;
    }
}