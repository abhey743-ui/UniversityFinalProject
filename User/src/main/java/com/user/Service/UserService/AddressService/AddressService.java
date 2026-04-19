package com.user.Service.UserService.AddressService;
import com.user.Dto.AddressDto.AddAddress;
import com.user.Dto.AddressDto.AddressDto;
import com.user.Dto.AddressDto.UpdateAddress;
import com.user.Dto.AuthDto.UserPrincipalAuth;
import com.user.Entity.Address;
import com.user.Entity.User;
import com.user.Repository.UserRepository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AddressService {

   private final  AddressRepository addressRepository;

   @Transactional
   @PostMapping("add/address")
   public AddAddress addAddress(AddAddress  addAddress){

               UserPrincipalAuth userPrincipalAuth = (UserPrincipalAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
               Long id = userPrincipalAuth.getId();

              Address newAddress = Address.builder().country(addAddress.getCountry()).city(addAddress.getCity())
                                 .nearByPlace(addAddress.getNearByPlace()).postcode(addAddress.getPostcode())
                      .userId(User.builder().id(id).build()).build();

              Address address =  addressRepository.save(newAddress);

       return AddAddress.builder().id(address.getId()).city(address.getCity()).nearByPlace(address.getNearByPlace())
               .country(address.getCountry()).postcode(address.getPostcode()).build();
   }

   @Transactional
   public UpdateAddress updateAddress(AddressDto addressDto) {

       UserPrincipalAuth userPrincipalAuth = (UserPrincipalAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Long id = userPrincipalAuth.getId();
       Address address = addressRepository.findByUserId_IdAndId(id,addressDto.getId());



       if(address != null){
                  if(addressDto.getCity() !=null){

                           address.setCity(addressDto.getCity());
                  }
                  if(addressDto.getPostcode() != null){

                           address.setPostcode(addressDto.getPostcode());
                  }
                  if(addressDto.getCountry() != null){
                           address.setCountry(addressDto.getCountry());
                  }
                  if(addressDto.getNearByPlace() != null){
                           address.setNearByPlace(addressDto.getNearByPlace());
                  }
                  if(addressDto.getId() !=null){
                         address.setId(addressDto.getId());
                  }

       }
       else{

            throw  new RuntimeException("Address not found");

       }

       return UpdateAddress.builder().postcode(address.getPostcode()).city(address.getCity()).country(address.getCountry())
               .nearByPlace(address.getNearByPlace()).id(address.getId()).build();
   }


   @Transactional
   public AddressDto deleteAddress(Long  id){
                   UserPrincipalAuth userPrincipalAuth = (UserPrincipalAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                   Address address  =  addressRepository.findByUserId_IdAndId(userPrincipalAuth.getId(),id);
                   addressRepository.deleteById(address.getId());
                   return AddressDto.builder().postcode(address.getPostcode()).city(address.getCity())
                           .nearByPlace(address.getNearByPlace()).country(address.getCountry()).id(address.getId()).build();
  }

  public List<AddressDto> getAllAddress(){
                 UserPrincipalAuth userPrincipalAuth = (UserPrincipalAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                 List<Address> address = addressRepository.findByUserId_Id(userPrincipalAuth.getId());
                 List<AddressDto> addressDtoList = new ArrayList<>();
                 for(Address address1:address){

                          AddressDto addressDto1 = AddressDto.builder().postcode(address1.getPostcode()).nearByPlace(address1.getNearByPlace())
                                  .country(address1.getCountry()).city(address1.getCity()).id(address1.getId()).build();


                          addressDtoList.add(addressDto1);
                 }
                 return addressDtoList;

                 }

  }


