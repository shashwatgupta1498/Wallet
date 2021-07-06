package entities;
import java.util.*;
import java.time.Instant;
import java.sql.Timestamp;

public class User {
    boolean FixedDepositFlag = false;
    double FixedDepositAmount = 0;
    int FixedDepositTransactionCount = 0;
    String id;
    double balance;
    int TransactionCount=0;
    Timestamp ts;
    public ArrayList<String> transactionLog;
    public Timestamp getTimestamp(){
        return ts;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String id, double balance){
        this.transactionLog = new ArrayList<String>();
        this.id = id;
        this.balance = balance;
        this.ts = Timestamp.from(Instant.now());
    }
    public void updateTransactionLog(String stat){
        transactionLog.add(stat);
    }
    public int getTransactionCount(){
        return TransactionCount;
    }
    public void setTransactionCount(){
        this.TransactionCount = this.TransactionCount+1;
    }
    public void setFixedDepositFlag(){
        this.FixedDepositFlag = true;
    }
    public void unsetFixedDepositFlag(){
        this.FixedDepositFlag = false;
    }
    public void setFixedDepositAmount(double amt){
        this.FixedDepositAmount = amt;
    }
    public boolean getFixedDepositFlag(){
        return this.FixedDepositFlag;
    }
    public double getFixedDepositAmount(){
        return this.FixedDepositAmount;
    }
    public void setFixedDepositTransactionCount(){
        this.FixedDepositTransactionCount = this.FixedDepositTransactionCount+1;
    }
    public void unsetFixedDepositTransactionCount(){
        this.FixedDepositTransactionCount = 0;
    }
    public int getFixedDepositTransactionCount(){
        return this.FixedDepositTransactionCount;
    }
}
