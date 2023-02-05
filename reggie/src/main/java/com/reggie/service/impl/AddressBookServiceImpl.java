package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.entity.AddressBook;
import com.reggie.exception.BusinessException;
import com.reggie.exception.SystemException;
import com.reggie.mapper.AddressBookMapper;
import com.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址薄
     *
     * @param addressBook
     * @return
     */
    @Override
    public R<String> add(AddressBook addressBook) {
        try {
            addressBook.setUserId(BaseContext.getCurrentId());
            addressBookMapper.insert(addressBook);
        } catch (Exception e) {
            throw e;
        }
        return R.success("成功");
    }

    /**
     * 展示用户所有地址
     *
     * @return
     */
    @Override
    public R<List<AddressBook>> getByUserId() {
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        //查询当前用户所有地址
        lqw.eq(BaseContext.getCurrentId() != null, AddressBook::getUserId, BaseContext.getCurrentId());
        //按照更新时间降序排列
        lqw.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBooks = addressBookMapper.selectList(lqw);
        if (addressBooks.isEmpty()) {
            throw new BusinessException("您还没有地址,快去新增个地址吧");
        }
        return R.success(addressBooks);
    }

    /**
     * 修改地址回显
     *
     * @param id
     * @return
     */
    @Override
    public R<AddressBook> getById(Long id) {
        AddressBook addressBook = null;
        try {
            addressBook = addressBookMapper.selectById(id);
            if (addressBook == null) {
                throw new SystemException("修改地址异常");
            }
        } catch (Exception e) {
            throw e;
        }

        return R.success(addressBook);
    }

    /**
     * 修改地址
     *
     * @param addressBook
     * @return
     */
    @Override
    public R<String> update(AddressBook addressBook) {
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getId, addressBook.getId());
        int update = addressBookMapper.update(addressBook, lqw);
        if (update == 0) {
            throw new SystemException("地址更新失败");
        }
        return R.success("成功");
    }

    /**
     * 修改默认地址
     *
     * @param addressBook
     * @return
     */
    @Override
    @Transactional
    public R<AddressBook> updateDefault(AddressBook addressBook) {
        //先将该用户的所有地址设为0
        LambdaUpdateWrapper<AddressBook> luw = new LambdaUpdateWrapper();
        luw.set(AddressBook::getIsDefault, 0);
        luw.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        try {
            addressBookMapper.update(null, luw);//设置为0
            //将该地址设置为默认1
            addressBook.setIsDefault(1);
            addressBookMapper.updateById(addressBook);
        } catch (Exception e) {
            throw e;
        }
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId,BaseContext.getCurrentId())
                .eq(AddressBook::getIsDefault,1);
        AddressBook address = addressBookMapper.selectOne(lqw);

        return R.success(address);
    }

    /**
     * 获取默认地址
     */
    @Override
    public R<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId,BaseContext.getCurrentId())
                .eq(AddressBook::getIsDefault,1);
        AddressBook address = addressBookMapper.selectOne(lqw);
        return R.success(address);
    }


}
