class Member {
    static final int ALPHABET = 26;
    Member[] children;
    String name;
    Books currentlyBorrowedBooks;
    Books borrowedBooks;
    int countOfCurrentlyBorrowedBooks;
    int countOfBorrowedBooks;
    boolean isInLib;
    long[] arrives;
    long[] leaves;
    long[] timesInLib;
    int countsOfArrives;

    public Member() {
        children = new Member[ALPHABET];
        for (int i = 0; i < ALPHABET; i++) {
            children[i] = null;
        }
        borrowedBooks = new Books();
        currentlyBorrowedBooks = new Books();
        countOfCurrentlyBorrowedBooks = 0;
        isInLib = false;
        arrives = new long[100];
        leaves = new long[100];
        countsOfArrives = 0;
        countOfBorrowedBooks = 0;
        timesInLib = new long[100];
    }
}

public class Members {
    static Member root = new Member();

    void arrive(String personName, long arriveTime) {
        int index;
        Member currMember = root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                currMember.children[index] = new Member();
            }
            currMember = currMember.children[index];
        }
        currMember.isInLib = true;
        currMember.arrives[currMember.countsOfArrives] = arriveTime;
        currMember.countsOfArrives++;
    }

    void exit(String personName, long exitTime) {
        int index;
        Member currMember = root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                currMember.children[index] = new Member();
            }
            currMember = currMember.children[index];
        }
        currMember.isInLib = false;
        currMember.leaves[currMember.countsOfArrives - 1] = exitTime;
        currMember.timesInLib[currMember.countsOfArrives - 1] = exitTime - currMember.arrives[currMember.countsOfArrives - 1];
        if (currMember.countsOfArrives > 1) {
            currMember.timesInLib[currMember.countsOfArrives - 1] += currMember.timesInLib[currMember.countsOfArrives - 2];

        }
    }

    void isInLib(String personName) {
        int index;
        Member currMember = root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                currMember.children[index] = new Member();
            }
            currMember = currMember.children[index];
        }

        if (currMember.isInLib) {
            System.out.println("YES");
            return;
        }
        System.out.println("NO");
    }

    void TotalTimeInLib(String personName, long startTime, long endTime) {
        int index;
        Member currMember = root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                currMember.children[index] = new Member();
            }
            currMember = currMember.children[index];
        }
        boolean wasInLib = false;
        //////arrive ali 0
        //exit ali 1
        //arrive ali 3
        //exit ali 6
        //totalTimeInLib ali 1 4
        if (currMember.isInLib) {
            exit(personName, endTime);
            wasInLib = true;
        }
        int indexOfEnd = binarySearch(currMember.leaves, currMember.countsOfArrives, endTime);
        System.out.println(currMember.timesInLib[indexOfEnd]);
        if (wasInLib) {
            arrive(personName, endTime);
        }
    }

    int binarySearch(long[] arr, int countOfArrives, long x) {
        if (x < arr[0]) {
            return 0;
        }
        if (countOfArrives == 0) return 0;
        if (x > arr[countOfArrives - 1]) {
            return countOfArrives - 1;
        }
        int low = 0;
        int high = countOfArrives - 1;
        while (low < high) {
            int mid = (high + low) / 2;
            if (x < arr[mid]) {
                high = mid - 1;
            } else if (x > arr[mid]) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return (arr[low] - x) < (x - arr[high]) ? low : high;

    }

    boolean isEmpty(Member root) {
        for (int i = 0; i < 26; i++)
            if (root.children[i] != null) return false;
        return true;
    }

    void remove(Member root, String personName, int level) {
        if (root == null) return;
        if (level == personName.length()) {
            if (isEmpty(root)) {
                root = null;
            }
            return;
        }
        int index = personName.charAt(level) - 'a';
        remove(root.children[index], personName, level + 1);
        if (isEmpty(root)) {
            root = null;
        }
    }

    void addNewMember(String personName) {
        int index;
        if (root == null) root = new Member();
        Member currMember = root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) currMember.children[index] = new Member();
            currMember = currMember.children[index];
        }
        currMember.name = personName;
    }

    boolean IsAMember(String value) {
        int index;
        Member currMember = root;
        for (int level = 0; level < value.length(); level++) {
            index = value.charAt(level) - 'a';
            if (currMember.children[index] == null) return false;
            currMember = currMember.children[index];
        }
        return true;
    }

    void allPersonCurrentBooks(String personName) {
        int index;
        Member currMember = root;
        for (int level = 0; level < personName.length(); level++) {
            index = personName.charAt(level) - 'a';
            if (currMember.children[index] == null) {
                return;
            }
            currMember = currMember.children[index];
        }
        StringBuilder st=new StringBuilder();
            print(currMember.currentlyBorrowedBooks.root,st);
    }

    public static void print(Book root,StringBuilder bookName) {
        Book currBook = root;
        boolean is=false;
        for (int i = 0; i < 26; i++) {
            if (currBook.children[i] != null) {
                is = true;
                break;
            }
        }
        if (is)
            return;
        if (!is){
            System.out.println(bookName);
            return;
        }

        for (int i = 0; i < 26; i++) {
            while (currBook.children[i] != null) {
                bookName.append((char) ('a'+i));
                print(currBook.children[i], bookName);
            }
        }
    }

        void allPersonAllTimeBooks(String personName){
            int index;
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null){
                    return;
                }
                currMember=currMember.children[index];
            }
            System.out.println(currMember.countOfBorrowedBooks);
        }


}

