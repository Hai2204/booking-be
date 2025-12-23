package com.hainv.booking.controller;

import com.hainv.booking.entity.booking.Customer;
import com.hainv.booking.entity.booking.Partner;
import com.hainv.booking.repository.booking.CustomerRepository;
import com.hainv.booking.repository.booking.PartnerRepository;
import com.hainv.booking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CommonController {

    private final PartnerRepository partnerRepository;
    private final CustomerRepository customerRepository;

    @PostMapping("/partner")
    public ApiResponse<Partner> createPartner(@RequestBody Partner partner) throws Exception {
        return ApiResponse.success(partnerRepository.save(partner));
    }
    @PutMapping("/partner")
    public ApiResponse<Partner> updatePartner(@RequestBody Partner partner) throws Exception {
        return ApiResponse.success(partnerRepository.save(partner));
    }
    @DeleteMapping("/partner/{partnerId}")
    public ApiResponse<Boolean> deletePartner(@PathVariable Long partnerId) throws Exception {
        partnerRepository.deleteById(partnerId);
        return ApiResponse.success(Boolean.TRUE);
    }
    @GetMapping("/partners")
    public ApiResponse<List<Partner>> listPartners(@RequestParam(name = "id", required = false) Long id) throws Exception {
        if (id != null){
            return ApiResponse.success(partnerRepository.findAllById(Collections.singleton(id)));
        }
        return ApiResponse.success(partnerRepository.findAll());
    }

    @PostMapping("/customer")
    public ApiResponse<Customer> createCustomer(@RequestBody Customer request) throws Exception {
        Optional<Customer> customer = customerRepository.findByNationalId(request.getNationalId());

        if (customer.isPresent()){
           throw new Exception("Khách hàng đã tồn tại");
        }

        return ApiResponse.success(customerRepository.save(request));
    }
}
