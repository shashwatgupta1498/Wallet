package DataStore;
import java.util.*;
import entities.User;

public class DataStore {
    static HashMap<String, User> userList = new HashMap<String,User>();

    public static void enter(String id, User user){
        userList.put(id,user);
    }
    public static User get(String id){
        User p  = userList.get(id);
        return p;
    }
    public static void display(){
        for(Map.Entry<String,User> entry:userList.entrySet()){
            User x = entry.getValue();
            double bal = x.getBalance();
            System.out.println(entry.getKey()+"  "+bal);
        }
        return;
    }
    public static boolean check(String id){
        if(userList.containsKey(id)){
            return true;
        }
        return false;
    }
    public static List<User> getAllUsers(){
        ArrayList<User> arr = new ArrayList<User>();
        for(Map.Entry<String,User> entry:userList.entrySet()){
            arr.add(entry.getValue());
        }
        return arr;
    }
}
