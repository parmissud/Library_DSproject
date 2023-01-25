import java.util.Scanner;

public class Library {
    static Members members = new Members();
    static Books books = new Books();

    public Library() {
        members = new Members();
        books = new Books();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String order = sc.nextLine();
            switch (order.split(" ")[0]) {
                case "arrive": {
                    String personName = order.split(" ")[1];
                    long arriveTime = Long.parseLong(order.split(" ")[2]);
                    members.arrive(personName, arriveTime);
                    break;
                }
                case "exit": {
                    String personName = order.split(" ")[1];
                    long exitTime = Long.parseLong(order.split(" ")[2]);
                    members.exit(personName, exitTime);
                    break;
                }
                case "isInLib": {
                    String personName = order.split(" ")[1];
                    members.isInLib(personName);
                    break;
                }
                case "totalTimeInLib": {
                    String personName = order.split(" ")[1];
                    long start = Long.parseLong(order.split(" ")[2]);
                    long end = Long.parseLong(order.split(" ")[3]);
                    members.TotalTimeInLib(personName, start, end);
                    break;
                }
                case "addNewBook": {
                    String bookName = order.split(" ")[1];
                    int count = Integer.parseInt(order.split(" ")[2]);
                    books.addNewBook(bookName, count);
                    break;
                }
                case "shouldBring": {
                    String personName = order.split(" ")[1];
                    String bookName = order.split(" ")[2];
                    shouldBring(personName, bookName);
                    break;
                }
                case "returnBook": {
                    String personName = order.split(" ")[1];
                    String bookName = order.split(" ")[2];
                    returnBook(bookName, personName);
                    break;
                }
                case "allPersonCurrentBook": {
                    String personName = order.split(" ")[1];
                    members.allPersonCurrentBooks(personName);
                    break;
                }
                case "allPersonHave": {
                    String bookName = order.split(" ")[1];
                    books.allPersonsHaveThisBook(bookName);
                    break;
                }
                default: {
                    System.out.println("order not found,try again");
                    break;
                }
            }
        }
    }

    static void borrowBook(String personName, String bookName) {
        int index;
        Member currMember = Members.root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                return;
            }
            currMember = currMember.children[index];
        }
        Book currBook = Books.root;
        for (int level = 0; level < bookName.length(); level++) {
            index = bookName.charAt(level) - 'a';
            if (currBook.children[index] == null) {
                return;
            }
            currBook = currBook.children[index];
        }
        if (currBook.count == 0) {
            return;
        }
        if (currBook.isAvailable) {
            currMember.currentlyBorrowedBooks.addNewBook(bookName, 1);
            currMember.countOfCurrentlyBorrowedBooks++;
            currMember.borrowedBooks.addNewBook(bookName, 1);
            currMember.countOfBorrowedBooks++;
            currBook.currentlyBorrowedBy.addNewMember(personName);
            currBook.countOfMembersCurrentlyBorrowedThisBook++;
            currBook.borrowedBy.addNewMember(personName);
            currBook.countOfMembersBorrowedThisBook++;
            currBook.count--;
            if (currBook.count == 0) currBook.isAvailable = false;
        }
    }

    static void returnBook(String bookName, String personName) {
        int index;
        Member currMember = Members.root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                return;
            }
            currMember = currMember.children[index];
        }
        Book currBook = Books.root;
        for (int level = 0; level < bookName.length(); level++) {
            index = bookName.charAt(level) - 'a';
            if (currBook.children[index] == null) {
                return;
            }
            currBook = currBook.children[index];
        }

        currMember.currentlyBorrowedBooks.remove(Books.root, bookName, 0);
        currMember.countOfCurrentlyBorrowedBooks--;
        currBook.currentlyBorrowedBy.remove(Members.root, personName, 0);
        currBook.countOfMembersCurrentlyBorrowedThisBook--;
        currBook.count++;
        if (currBook.count > 0) currBook.isAvailable = true;
    }

    static void shouldBring(String personName, String bookName) {
        int index;
        Book currBook = Books.root;
        for (int level = 0; level < bookName.length(); level++) {
            index = bookName.charAt(level) - 'a';
            if (currBook.children[index] == null) {
                return;
            }
            currBook = currBook.children[index];
        }
        if (currBook.count == 0) {
            return;
        }
        Member currMember = Members.root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                return;
            }
            currMember = currMember.children[index];
        }
        if (currBook.isAvailable && currMember.isInLib) {
            borrowBook(personName, bookName);
        }
    }
}
