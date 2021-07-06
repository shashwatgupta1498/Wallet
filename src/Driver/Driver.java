//package com.company;
package Driver;
import java.io.*;
import java.util.*;
import DataStore.DataStore;
import Services.WalletService;


public class Driver {
    public static void main(String[] args){
        DataStore datastore = new DataStore();
        Scanner sc = new Scanner(System.in);
        String ip = "";
        while(!ip.equalsIgnoreCase("quit")){
            ip = sc.nextLine();
        String[] ipArray = ip.split(" ");
        String comm = ipArray[0];
        switch(comm){
            case "CreateWallet":
                String id = ipArray[1];
                double amount = Double.parseDouble(ipArray[2]);
                String x = WalletService.createWallet(id, amount);
                System.out.println(x);
                break;
            case "Overview":
                WalletService.Overview();
                break;
            case "TransferMoney":
                String idFrom = ipArray[1];
                String idTo = ipArray[2];
                double amt = Double.parseDouble(ipArray[3]);
                WalletService.transfer(idFrom, idTo, amt);
                break;
            case "Statement":
                String idForStatement = ipArray[1];
                WalletService.Statement(idForStatement);
                break;
            case "offer2":
                WalletService.TriggerOffer2();
                break;
            case "FixedDeposit":
                String FDid = ipArray[1];
                double FDamount = Double.parseDouble(ipArray[2]);
                WalletService.FD(FDid,FDamount);
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
        }

    }
}
