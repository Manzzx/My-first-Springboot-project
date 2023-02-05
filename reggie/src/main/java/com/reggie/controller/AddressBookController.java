package com.reggie.controller;


import com.reggie.common.R;
import com.reggie.entity.AddressBook;
import com.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook){
        R<String> r = addressBookService.add(addressBook);
        return r;
    }

    @GetMapping("/list")
    public R<List<AddressBook>> addressBookList(){
        R<List<AddressBook>> addressBookByUserId = addressBookService.getByUserId();
        return addressBookByUserId;
    }

    @GetMapping("/{id}")
    public R<AddressBook> addressBookOne(@PathVariable Long id){
        R<AddressBook> addressBookR = addressBookService.getById(id);
        return addressBookR;
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook){
        R<String> r = addressBookService.update(addressBook);
        return r;
    }

    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        return addressBookService.updateDefault(addressBook);
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        return addressBookService.getDefault();
    }
}
