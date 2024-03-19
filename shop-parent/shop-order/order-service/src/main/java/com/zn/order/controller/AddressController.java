package com.zn.order.controller;

import com.zn.core.controller.BaseController;
import com.zn.order.service.AddressService;
import com.zn.po.Address;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 张男
 * @date: 2024/2/13---11:08
 */
@RestController
@RequestMapping("/address")
public class AddressController extends BaseController<AddressService, Address> {

    @RequestMapping("/list/{username}")
    public List<Address> list(@PathVariable String username) {
        System.err.println(username);
        Address address = new Address();
        address.setUsername(username);
        return service.list(address);
    }
}
