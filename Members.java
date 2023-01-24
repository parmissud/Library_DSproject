import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Members {
        static Member root;

        void addNewMember(String personName){
            int index;
            if (root==null)
                root=new Member();
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null)
                    currMember.children[index]=new Member();
                currMember=currMember.children[index];
            }
            currMember.name=personName;
            currMember.endOfWord=true;
        }
        boolean IsAMember(String value){
            int index;
            Member currMember=root;
            for (int level = 0; level < value.length(); level++) {
                index=value.charAt(level)-'a';
                if (currMember.children[index]==null)
                    return false;
                currMember=currMember.children[index];
            }
            return currMember.endOfWord;
        }
        void arrive(String personName){
            int index;
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null){
                    System.out.println(personName+" is not a member!");
                    return;
                }
                currMember=currMember.children[index];
            }
            if (currMember.endOfWord){
                currMember.isInLib=true;
                currMember.arrives[currMember.countsOfArrives]=LocalDateTime.now();
                currMember.countsOfArrives++;
                System.out.println(personName+" arrives.");
                return;
            }
            System.out.println(personName+" is not a member!");
            return;
        }
        void exit(String personName){
            int index;
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null){
                    System.out.println(personName+" is not a member!");
                    return ;
                }
                currMember=currMember.children[index];
            }
            if (currMember.endOfWord){
                if (!currMember.isInLib){
                    System.out.println(personName+" has not been here!");
                    return;
                }
                currMember.isInLib=false;
                currMember.leaves[currMember.countsOfArrives-1]=LocalDateTime.now();
                Duration duration= Duration.between(currMember.arrives[currMember.countsOfArrives-1],currMember.leaves[currMember.countsOfArrives-1]);
                currMember.timesInLib[currMember.countsOfArrives-1]=duration;
                if (currMember.countsOfArrives>1){
                    currMember.timesInLib[currMember.countsOfArrives-1]=Duration.ofHours(currMember.timesInLib[currMember.countsOfArrives-1].toHours()+currMember.timesInLib[currMember.countsOfArrives-2].toHours());
                }
                System.out.println(personName+" leaves.");
                return;
            }
            System.out.println(personName+" is not a member!");
        }
        void isInLib(String personName){
            int index;
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null){
                    System.out.println(personName+" is not a member!");
                    return ;
                }
                currMember=currMember.children[index];
            }
            if (currMember.endOfWord){
                if (currMember.isInLib){
                    System.out.println(personName+" is here.");
                    return;
                }
                else System.out.println(personName+" is not here");
            }
            System.out.println(personName+" is not a member!");
            return;
        }
        void TotalTimeInLib(String personName,LocalDateTime startTime,LocalDateTime endTime){
            int index;
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null) {
                    System.out.println(personName+" is not a member!");
                    return;
                }
                currMember=currMember.children[index];
            }
            if (currMember.endOfWord){
                int indexOfStart=binarySearch(currMember.arrives,currMember.countsOfArrives,startTime);
                int indexOfEnd=binarySearch(currMember.leaves,currMember.countsOfArrives,endTime);
                System.out.println(currMember.timesInLib[indexOfEnd].toHours()-currMember.timesInLib[indexOfStart].toHours());
                return;

            }
            System.out.println(personName+" is not a member!");
            return;
        }
    int binarySearch(LocalDateTime[] arr, int countOfArrives, LocalDateTime x)
    {
        if( arr[0].isAfter(x)) {
            return 0;
        }
        if(x.isAfter(arr[countOfArrives-1])) {
            return countOfArrives-1;
        }
        int lo = 0;
        int hi = countOfArrives - 1;
        while (lo <= hi) {
            int mid = (hi + lo) / 2;
            if (arr[mid].isAfter(x)) {
                hi = mid - 1;
            } else if (x.isAfter(arr[mid])) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return Duration.between(arr[lo],x).toMinutes() < Duration.between(x,arr[hi]).toMinutes() ? lo: hi;

    }
        void allPersonCurrentBooks(String personName){
            int index;
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null){
                    System.out.println(personName+" is not a member!");
                    return;
                }
                currMember=currMember.children[index];
            }
            if (currMember.endOfWord){
                System.out.println(personName+" has borrowed these books currently:");
                for (int i = 0; i < currMember.countOfCurrentlyBorrowedBooks; i++) {
                    System.out.println(i+"- "+currMember.currentlyBorrowedBooks[i]);
                }
                return;
            }

            System.out.println(personName+" is not a member!");
            return;
        }
        void allPersonAllTimeBooks(String personName){
            int index;
            Member currMember=root;
            for (int level = 0; level < personName.length(); level++) {
                index=personName.charAt(level)-'a';
                if (currMember.children[index]==null){
                    System.out.println(personName+" is not a member!");
                    return;
                }
                currMember=currMember.children[index];
            }
            if (currMember.endOfWord){
                System.out.println(personName+" has borrowed these books up to now:");
                for (int i = 0; i < currMember.countOfBorrowedBooks; i++) {
                    System.out.println(i+"- "+currMember.borrowedBooks[i]);
                }
                return;
            }

            System.out.println(personName+" is not a member!");
            return;
        }

}
class Member{

    static final int ALPHABET =26;
    boolean endOfWord;
    Member[] children;
    String name;
    Book[] currentlyBorrowedBooks;
    Book[] borrowedBooks;
    int indexInCurrentlyBorrowedBy;
    int indexInBorrowedBy;
    int countOfCurrentlyBorrowedBooks;
    int countOfBorrowedBooks;
    boolean isInLib;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime[] arrives;
    LocalDateTime[] leaves;
    Duration[] timesInLib;
    int countsOfArrives;
    public Member() {
        endOfWord=false;
        children=new Member[ALPHABET];
        for (int i = 0; i < ALPHABET; i++) {
            children[i]=null;
        }
        indexInBorrowedBy=-1;
        indexInCurrentlyBorrowedBy=-1;
        borrowedBooks=new Book[100];
        currentlyBorrowedBooks=new Book[100];
        countOfCurrentlyBorrowedBooks=0;
        isInLib=false;
        arrives=new LocalDateTime[100];
        leaves=new LocalDateTime[100];
        countsOfArrives=0;
        countOfBorrowedBooks=0;
        timesInLib=new Duration[100];
    }
}
