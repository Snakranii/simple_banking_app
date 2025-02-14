package com.example.assignment1.service;

import com.example.assignment1.model.Bank;
import com.example.assignment1.repository.BankRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.rmi.StubNotFoundException;
import java.util.UUID;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    public long uniqueId(){
        UUID uuid=UUID.randomUUID();
        String uuidString=uuid.toString().replace("-","");
        long num=Math.abs(uuidString.hashCode());
        while(String.valueOf(num).length()<10){
            num*=10;
        }
        return Long.parseLong(String.valueOf(num).substring(0,10));
    }

    public void save(Bank bank){
        long id = uniqueId();
        bank.setAccountNumber(id);
        bankRepository.save(bank);
    }

    public String withdraw(long accNo,long amount){
        try {
            Bank temp = bankRepository.findById(accNo).orElse(null);
            if(temp==null){
                return "Please Check Your Account Number!!!";
            }else if(amount<0){
              return "Amount can't be negative";
            } else if(temp.getBalance()<amount){
                Gson s1 = new Gson();
                String ans = s1.toJson(temp);
                return "Insufficient Balance....!!\n"+ ans;
            }else{
                temp.setBalance(temp.getBalance()-amount);
                bankRepository.save(temp);
                Gson s1 = new Gson();
                String ans = s1.toJson(temp);
                return "Successfully withdrawn.....\n"+ans;
            }
        }catch (Exception e){
            return "error...."+e.getMessage();
        }
    }

    public String deposite(long accNo,long amount){
        try {
            Bank temp = bankRepository.findById(accNo).orElse(null);
            if(temp==null){
                return "Please Check Your Account Number!!!";
            }else if(amount<0){
                return "Amount can't be negative";
            } else if(amount > 100000){
                return "Can't allow to deposite more than 1,00,000 Rs. At a Time.....!!\n";
            }else{
                temp.setBalance(temp.getBalance()+amount);
                bankRepository.save(temp);
                Gson s1 = new Gson();
                String ans = s1.toJson(temp);
                return "Successfully Deposite.....\n"+ans;
            }
        }catch (Exception e){
            return "error...."+e.getMessage();
        }
    }

    public String transfer(long accNo1,long accNo2,long amount){
        try {
            Bank temp = bankRepository.findById(accNo1).orElse(null);
            Bank temp2 = bankRepository.findById(accNo2).orElse(null);
            if(temp==null){
                return "Please Check Your Account Number which has withdrawn money!!!";
            }
            else if(temp2==null){
                return "Please Check Your Account Number which has deposite money!!!";
            }else if(amount<0){
                return "Amount can't be negative";
            }else if(amount>temp.getBalance()){
                Gson s1 = new Gson();
                String ans = s1.toJson(temp);
                return "Check your balance....\n"+ans;
            }
            else if(amount > 100000){
                return "Can't allow to transfer more than 1,00,000 Rs. At a Time.....!!\n";
            }else{
                temp.setBalance(temp.getBalance()-amount);
                temp2.setBalance(temp2.getBalance()+amount);
                bankRepository.save(temp);
                bankRepository.save(temp2);
                Gson s1 = new Gson();
                Gson s2 = new Gson();
                String ans = s1.toJson(temp);
                String ans2 = s2.toJson(temp2);
                return "Successfully Transfer.....\n"+ans+"\n"+ans2;
            }
        }catch (Exception e){
            return "error...."+e.getMessage();
        }
    }

    public String showBalance(long accNo){
        try {
            Bank temp = bankRepository.findById(accNo).orElse(null);
            if(temp==null){
                return "Check Your Account Number....!";
            }else{
                Gson s = new Gson();
                String ans = s.toJson(temp);
                return "Your Account details...\n"+ans;
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String getStudent(int id){
        try{
            RestTemplate temp = new RestTemplate();
            String response = temp.getForObject("http://localhost:8081/student/getbyid/"+id, String.class);
            return  response;
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
