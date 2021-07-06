package Services;
import DataStore.DataStore;
import entities.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class WalletService {
    static double minbal = 0.0001;
    public static void checkFD(String id){
        User fd = DataStore.get(id);
        if(!fd.getFixedDepositFlag()){
            return;
        }
        double bal = fd.getBalance();
        double fdbal = fd.getFixedDepositAmount();
        if(bal<fdbal){
            fd.unsetFixedDepositFlag();
            fd.setFixedDepositAmount(0);
            fd.unsetFixedDepositTransactionCount();
            fd.updateTransactionLog("Fd closed, balance too low");
            return;
        }
        else{
            fd.setFixedDepositTransactionCount();
            int ct = fd.getFixedDepositTransactionCount();
            if(ct%2==0){
                double balance = fd.getBalance();
                fd.setBalance(balance+10);
                fd.updateTransactionLog("FD interest FKrupees 10 added");
            }
        }
    }
    public static void FD(String id, double amt){
        if(!DataStore.check(id)){
            System.out.println(id+": this username does not exist");
            return;
        }
        User fdUser = DataStore.get(id);
        double bal = fdUser.getBalance();
        if(bal<amt){
            System.out.println("Balance too low for FD");
            return;
        }
        fdUser.setFixedDepositFlag();
        fdUser.setFixedDepositAmount(amt);
        fdUser.updateTransactionLog("FD of "+amt+" done.");
        System.out.println("Fixed Deposit made successfully, you made a good investment");
    }
    public static String createWallet(String id , double amt){
        if(DataStore.check(id)){
            return "Username already exists, please use another username";
        }
        if(amt<minbal){
            return "Cannot create as opening balance is too low";
        }
        else{
            User newUser = new User(id,amt);
            DataStore.enter(id,newUser);
            return id+" "+amt;
        }
    }
    public static void transfer(String id1, String id2, double amt){
        if(!DataStore.check(id1)){
            System.out.println(id1+" username does not exist");
            return;
        }
        if(!DataStore.check(id2)){
            System.out.println(id2+" username does not exist");
            return;
        }
        if(amt<=0){
            System.out.println("Invalid amount entered");
            return;
        }
        User x = DataStore.get(id1);
        User y = DataStore.get(id2);
        double bal1 = x.getBalance();
        double bal2 = y.getBalance();
        if(bal1<amt){
            System.out.println("Insufficient Balance");
        }
        else{
            x.setBalance(bal1-amt);
            y.setBalance(bal2+amt);
            String stat = "Amount "+amt+" transferred from "+id1+" to " + id2;
            x.updateTransactionLog(stat);
            y.updateTransactionLog(stat);
            System.out.println("Transaction Done");
            x.setTransactionCount();
            y.setTransactionCount();
            checkForOffer1(x,y);
            checkFD(id1);
            checkFD(id2);
        }
    }
    public static void Statement(String id){
        if(!DataStore.check(id)){
            System.out.println(id+" username does not exist");
            return;
        }
        User x = DataStore.get(id);
        System.out.println(id+"'s Statement");
        for(String i:x.transactionLog){
            System.out.println(i);
        }
    }
    public static void Overview(){
        DataStore.display();
    }
    public static void checkForOffer1(User x , User y){
        double v1 = x.getBalance();
        double v2 = y.getBalance();
        if(v1==v2){
            x.setBalance(v1+10);
            y.setBalance(v2+10);
            x.setTransactionCount();
            y.setTransactionCount();
            x.updateTransactionLog("10 fkrupees added as you won offer 1");
            y.updateTransactionLog("10 fkrupees added as you won offer 1");
            System.out.println("Offer 1 applied");
        }

        //return;
    }
    public static void TriggerOffer2(){
        List<User> allUsersList = DataStore.getAllUsers();
        if(allUsersList.size()==1){
            User First = allUsersList.get(0);
            double x = First.getBalance();
            First.setBalance(x+10);
            First.updateTransactionLog("Offer 2 applied FKrupees 10 added");
            System.out.println("Offer 2 success");
            return;
        }
        Collections.sort(allUsersList, new UserSort());
        if(allUsersList.size()==2) {
            User First = allUsersList.get(0);
            User Second = allUsersList.get(1);
            double x = First.getBalance();
            double y = Second.getBalance();
            First.setBalance(x + 10);
            First.updateTransactionLog("Offer 2 applied FKrupees 10 added");
            Second.setBalance(y + 5);
            Second.updateTransactionLog("Offer 2 applied FKrupees 5 added");
            System.out.println("Offer 2 success");
            return;
        }
        User First = allUsersList.get(0);
        User Second = allUsersList.get(1);
        User Third = allUsersList.get(2);
        double x = First.getBalance();
        double y = Second.getBalance();
        double z = Third.getBalance();
        First.setBalance(x+10);
        First.updateTransactionLog("Offer 2 applied FKrupees 10 added");
        Second.setBalance(y+5);
        Second.updateTransactionLog("Offer 2 applied FKrupees 5 added");
        Third.setBalance(z+2);
        Third.updateTransactionLog("Offer 2 applied FKrupees 2 added");
        System.out.println("Offer 2 success");
    }
}

class UserSort implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        if(u1.getTransactionCount() != u2.getTransactionCount()) {
            return u2.getTransactionCount() - u1.getTransactionCount();
        }
        else {
            if(u1.getBalance() != u2.getBalance()) {
                if(u2.getBalance()>u1.getBalance())
                    return 1;
                else {
                    return -1;
                }
            }
            else {
                if(u2.getTimestamp().compareTo(u1.getTimestamp())>0) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        }
    }
}