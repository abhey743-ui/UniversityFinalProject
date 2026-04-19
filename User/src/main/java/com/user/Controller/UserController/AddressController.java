package com.user.Controller.UserController;

import com.user.Dto.AddressDto.AddAddress;
import com.user.Dto.AddressDto.AddressDto;
import com.user.Dto.AddressDto.UpdateAddress;
import com.user.Service.UserService.AddressService.AddressService;
import jakarta.ws.rs.GET;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("add/address")
    public ResponseEntity<AddAddress> AddAddress(@RequestBody  AddAddress address){

         AddAddress addAddress = addressService.addAddress(address);
         return ResponseEntity.status(HttpStatus.CREATED).body(addAddress);

    }

    @PostMapping("/update/address")
    public ResponseEntity<UpdateAddress> updateAddress(@RequestBody AddressDto addressDto){
                 UpdateAddress updateAddress1 = addressService.updateAddress(addressDto);
                 return ResponseEntity.status(HttpStatus.OK).body(updateAddress1);
    }

    @DeleteMapping("/delete/address/{id}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable("id") Long  id) {
        AddressDto addressDto = addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.OK).body(addressDto);
    }

    @GetMapping("/get/all/address")
    public ResponseEntity<List<AddressDto>> getAllAddress(){
                     List<AddressDto>  addressDto = addressService.getAllAddress();
                     return ResponseEntity.status(HttpStatus.OK).body(addressDto);
    }


}
