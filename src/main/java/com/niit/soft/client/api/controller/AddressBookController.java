package com.niit.soft.client.api.controller;

import com.niit.soft.client.api.annotation.ControllerWebLog;
import com.niit.soft.client.api.common.ResponseResult;
import com.niit.soft.client.api.domain.dto.PageDto;
import com.niit.soft.client.api.domain.model.AddressBook;
import com.niit.soft.client.api.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/6/2
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "address_book")
@Api(tags = "通讯录接口")
public class AddressBookController {
    @Resource
    private AddressBookService addressBookService;

    @PostMapping(value = "/list/userId")
    @ControllerWebLog(name = "getAddressByUserId", isSaved = true)
    @ApiOperation(value = "根据id查询所有的通讯录好友", notes = "")
    public List<AddressBook> getAddressByUserId(@RequestBody PageDto pageDto) {
        log.info("访问/list/userId接口");
        log.info("进入/list/userId接口："+pageDto + "**1**");
        return addressBookService.getAddressBookByUserId(pageDto.getField().toString());
    }
    @PostMapping(value = "/list/phoneNumber")
    @ControllerWebLog(name = "getAddressByUserId", isSaved = true)
    @ApiOperation(value = "根据手机号查询所有的通讯录好友", notes = "")
    public List<AddressBook> findAddressBookByPhoneNumber(@RequestBody PageDto pageDto) {
        log.info("访问/list/phoneNumber接口");
        log.info("进入/list/phoneNumber接口："+pageDto + "**1**");
        return addressBookService.findAddressBookByPhoneNumber(pageDto.getField().toString());
    }
    @PostMapping(value = "/list/remark")
    @ControllerWebLog(name = "getAddressByUserId", isSaved = true)
    @ApiOperation(value = "根据remark模糊通讯录好友", notes = "")
    public List<AddressBook> findAddressBookByRemark(@RequestBody PageDto pageDto) {
        log.info("访问/list/remark接口");
        log.info("进入/list/remark接口："+pageDto + "**1**");
        return addressBookService.findAddressBookByRemarkContaning(pageDto.getField().toString());
    }

    @PostMapping
    @ControllerWebLog(name = "insertAddressBook", isSaved = true)
    @ApiOperation(value = "新增通讯录好友", notes = "")
    public ResponseResult insertAddressBook(@RequestBody AddressBook addressBook) {
        log.info("访问addressBook新增接口");
        log.info("进入addressBook新增接口："+addressBook + "**1**");
        addressBookService.insertAddressBook(addressBook);
        return ResponseResult.success();
    }

    @PutMapping(value = "/id")
    @ControllerWebLog(name = "updateAddressBook", isSaved = true)
    @ApiOperation(value = "修改通讯录好友信息", notes = "")
    public ResponseResult updateAddressBook(@RequestBody AddressBook addressBook) {
        log.info("访问AddressBook/id 修改接口");
        log.info("进入AddressBook/id 修改接口："+addressBook + "**1**");
        addressBookService.updateAddressBookById(addressBook);
        return ResponseResult.success();
    }

    @DeleteMapping(value = "/id/{id}")
    @ControllerWebLog(name = "deleteAddressBookById", isSaved = true)
    @ApiOperation(value = "根据id删除通讯录好友信息", notes = "")
    public ResponseResult deleteAddressBookById(@PathVariable int id) {
        log.info("访问AddressBook/id 删除接口");
        log.info("进入AddressBook/id 删除接口："+id + "**1**");
        addressBookService.deleteAddressBookById(id);
        return ResponseResult.success();
    }
    /**
     * 查询所有好友
     * @param pageDto
     * @return
     */
    @ApiOperation(value = "查询所有",notes = "请求参数为当前页和页面条数")
    @ControllerWebLog(name = "findAllByPage", isSaved = true)
    @PostMapping("/all")
    ResponseResult findAllByPage(@RequestBody PageDto pageDto){
        log.info("访问AddressBook/all 查询所有");
        log.info("进入AddressBook/all 查询所有："+pageDto + "**1**");
        return  ResponseResult.success(addressBookService.findAllByPage(pageDto));
    }


}
