import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tester {
    public static void main(String [] args){
        Map<Long, ArrayList<String>> anagramTable = new HashMap<>();
        anagramTable.put(1L, new ArrayList<>());
        anagramTable.get(1L).add("Hello");
        System.out.println(anagramTable.get(1L).get(0));
    }
}
