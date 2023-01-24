import java.time.LocalDateTime;

public class Library {
    Members members;
    Books books;

    public Library() {
        members=new Members();
        books=new Books();
    }
    void borrowBook(String personName,String bookName) {
        int index;
        Member currMember = members.root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null){
                System.out.println(personName+" is not a member!");
                return;
            }
            currMember = currMember.children[index];
        }
        if (!currMember.endOfWord){
            System.out.println(personName+" is not a member!");
            return;
        }
        else {
            Book currBook=books.root;
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
            if (currBook.endOfWord && currBook.isAvailable){
                currMember.currentlyBorrowedBooks[currMember.countOfCurrentlyBorrowedBooks]=currBook;
                currBook.indexInCurrentlyBorrowed=currMember.countOfCurrentlyBorrowedBooks;
                currMember.countOfCurrentlyBorrowedBooks++;
                currMember.borrowedBooks[currMember.countOfBorrowedBooks]=currBook;
                currBook.indexInBorrowed=currMember.countOfBorrowedBooks;
                currMember.countOfBorrowedBooks++;
                currBook.currentlyBorrowedBy[currBook.countOfMembersCurrentlyBorrowedThisBook]=currMember;
                currMember.indexInCurrentlyBorrowedBy=currBook.countOfMembersCurrentlyBorrowedThisBook;
                currBook.countOfMembersCurrentlyBorrowedThisBook++;
                currBook.borrowedBy[currBook.countOfMembersBorrowedThisBook]=currMember;
                currMember.indexInBorrowedBy=currBook.countOfMembersBorrowedThisBook;
                currBook.countOfMembersBorrowedThisBook++;
                currBook.count--;
                if (currBook.count==0)
                    currBook.isAvailable=false;
                return;
            }
            System.out.println(bookName+" is not available at all.");
            return;
        }
    }
    void returnBook(String bookName,String personName){
        int index;
        Member currMember = members.root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null){
                System.out.println(personName+" is not a member!");
                return;
            }
            currMember = currMember.children[index];
        }
        if (!currMember.endOfWord){
            System.out.println(personName+" is not a member!");
            return;
        }
        else {
            Book currBook=books.root;
            for (int level = 0; level < bookName.length(); level++) {
                index=bookName.charAt(level)-'a';
                if (currBook.children[index]==null){
                    System.out.println(bookName+" is not available at all.");
                    return;
                }
                currBook=currBook.children[index];
            }
            if (currBook.endOfWord ){
                currMember.currentlyBorrowedBooks[currBook.indexInCurrentlyBorrowed]=null;
                currBook.indexInCurrentlyBorrowed=-1;
                currMember.countOfCurrentlyBorrowedBooks--;
                currMember.borrowedBooks[currBook.indexInBorrowed]=null;
                currBook.indexInBorrowed=-1;
                currMember.countOfBorrowedBooks--;
                currBook.currentlyBorrowedBy[currMember.indexInCurrentlyBorrowedBy]=null;
                currMember.indexInCurrentlyBorrowedBy=-1;
                currBook.countOfMembersCurrentlyBorrowedThisBook--;
                currBook.borrowedBy[currMember.indexInBorrowedBy]=null;
                currMember.indexInBorrowedBy=-1;
                currBook.countOfMembersBorrowedThisBook--;
                currBook.count++;
                if (currBook.count>0)
                    currBook.isAvailable=true;
                return;
            }
            System.out.println(bookName+" is not available at all.");
            return;
        }
    }
    void shouldBring(String personName,String bookName){
        int index;
        Book currBook=books.root;
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
            Member currMember=members.root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null){
                    System.out.println(personName+" is not a member!");
                    return;
                }
                currMember=currMember.children[index];
            }
            if (currMember.endOfWord){
                if (currBook.isAvailable && currMember.isInLib){
                    borrowBook(personName,bookName);
                    return;
                }

            }
            System.out.println(personName+" is not a member!");
            return;
        }
        System.out.println(bookName+" is not available at all.");
        return;
    }
}
