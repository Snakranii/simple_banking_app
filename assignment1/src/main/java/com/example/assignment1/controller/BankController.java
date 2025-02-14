package com.example.assignment1.controller;

import com.example.assignment1.model.Bank;
import com.example.assignment1.service.BankService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bank")
public class BankController {
    @Autowired
    BankService bankService;

    @PostMapping("save")
    public void save(@RequestBody Bank bank){
        bankService.save(bank);
    }

    @PutMapping("withdraw")
    public String withdraw(@PathParam(value = "accNo")long accNo,@PathParam(value = "amount")long amount){
        return bankService.withdraw(accNo,amount);
    }

    @PutMapping("deposite")
    public String deposite(@PathParam(value = "accNo")long accNo,@PathParam(value = "amount")long amount){
        return bankService.deposite(accNo,amount);
    }

    @PutMapping("transfer")
    public String deposite(@PathParam(value = "accNo1")long accNo1,@PathParam(value = "accNo2")long accNo2,@PathParam(value = "amount")long amount){
        return bankService.transfer(accNo1,accNo2,amount);
    }

    @GetMapping("show")
    public String show(@PathParam(value = "accNo")long accNo){
        return bankService.showBalance(accNo);
    }

    @GetMapping("getstud/{rno}")
    public String getStud(@PathVariable(value = "rno")int id){
        return bankService.getStudent(id);
    }
}
