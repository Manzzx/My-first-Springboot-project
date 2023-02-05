package com.reggie.service;

import com.reggie.common.R;
import com.reggie.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    R<String> add(AddressBook addressBook);

    R<List<AddressBook>> getByUserId();

    R<AddressBook> getById(Long id);

    R<String> update(AddressBook addressBook);

    R<AddressBook> updateDefault(AddressBook addressBook);

    R<AddressBook> getDefault();

}
