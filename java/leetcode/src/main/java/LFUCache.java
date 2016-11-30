import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 2016/11/30 19:03
 */
public class LFUCache {

    static class Entry {
        Integer value;
        Integer key;
        Entry next;
        Entry pre;
        Level level;

        @Override
        public String toString() {
            return "Entry{" +
                    "value=" + value +
                    ", key=" + key +
                    '}';
        }
    }

    static class Level {
        Level pre;
        Level next;
        Entry first;
        Entry last;
        int count;
    }

    Map<Integer,Entry> cache;
    Level head;
    int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
    }

    public int get(int key) {
        return -1;
    }

    public void set(int key, int value) {
         if (get(key) == -1) {
             if (cache.size() <= capacity) {
                 // insert to level 1
                 Entry entry = new Entry();
                 entry.key = key;
                 entry.value = value;
                 cache.put(key, entry);
                 if (head == null || head.count > 1) {
                     Level level = new Level();
                     level.count = 1;
                     level.next = head;
                     if (head != null) {
                         head.pre = level;
                     }
                     head = level;
                 }
                 if (head.last == null) {
                     // head 下没有entry
                     head.last = entry;
                     head.first = entry;
                     entry.level = head;
                 } else {
                     entry.pre = head.last;
                     head.last.next = entry;
                     head.last = entry;
                     entry.level = head;
                 }

             } else {
                 // remove and insert
                 Entry toDel = head.first;
                 if (toDel.next != null) {
                     head.first = toDel.next;
                     head.first.pre = null;
                 } else {
                    remodeHead(head);
                 }
                 cache.remove(toDel.key);
                 toDel = null;
             }
         } else {
             //replace
         }
    }


    private void remodeHead(Level head) {
        if (head.next != null) {
            this.head = head.next;
        }
        head = null;
    }

    void printlevels () {
        Level node = head;
        while (node!=null) {
            System.out.println(node);

            Entry e = head.first;
            while (e !=null) {
                System.out.print(e);
                System.out.print("       ");
                e = e.next;
            }
            System.out.println();
            node = head.next;
        }
    }


    public static void main(String[] args) {
        LFUCache cache = new LFUCache( 2 /* capacity */ );

        cache.set(1, 1);
        cache.printlevels();
        cache.set(2, 2);
        cache.printlevels();
        cache.set(3, 3);
        cache.printlevels();
        cache.set(4, 4);
        cache.printlevels();




    }
}