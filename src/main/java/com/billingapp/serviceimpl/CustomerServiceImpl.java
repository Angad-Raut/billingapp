package com.billingapp.serviceimpl;

import com.billingapp.entity.CustomerDetails;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.customerDto.CustomerDataDto;
import com.billingapp.payload.customerDto.CustomerDetailsDto;
import com.billingapp.payload.customerDto.SearchCustomerDataDto;
import com.billingapp.repository.CustomerDetailsRepository;
import com.billingapp.service.CustomerService;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDetailsRepository customerRepository;

    @Override
    public Boolean addCustomer(CustomerDetailsDto dto) throws AlreadyExistException, InvalidDataException {
        CustomerDetails customerDetails=null;
        Boolean result= Constants.DI_ACTIVE_STATUS;
        if(dto.getCustomerId()==null){
            customerDetails = CustomerDetails.builder()
                    .customerEmail(dto.getCustomerEmail())
                    .customerMobile(dto.getCustomerMobile())
                    .customerName(dto.getCustomerName())
                    .village(dto.getVillage())
                    .status(Constants.ACTIVE_STATUS)
                    .insertedBy(dto.getRequestedBy())
                    .insertedById(dto.getRequestedById())
                    .insertedTime(new Date())
                    .updatedBy(dto.getRequestedBy())
                    .updatedById(dto.getRequestedById())
                    .updatedTime(new Date())
                    .build();
        }else{
            customerDetails = customerRepository.getById(dto.getCustomerId());
            if(!dto.getCustomerName().equals(customerDetails.getCustomerName())){
                customerDetails.setCustomerName(dto.getCustomerName());
            }
            if(!dto.getCustomerMobile().equals(customerDetails.getCustomerMobile())){
                customerDetails.setCustomerMobile(dto.getCustomerMobile());
            }
            if(!dto.getCustomerEmail().equals(customerDetails.getCustomerEmail())){
                customerDetails.setCustomerEmail(dto.getCustomerEmail());
            }
            if(!dto.getVillage().equals(customerDetails.getVillage())){
                customerDetails.setVillage(dto.getVillage());
            }
            customerDetails.setUpdatedBy(dto.getRequestedBy());
            customerDetails.setUpdatedById(dto.getRequestedById());
            customerDetails.setUpdatedTime(new Date());
        }
        try{
            if(customerRepository.save(customerDetails)!=null){
                return Constants.ACTIVE_STATUS;
            }else{
                return Constants.DI_ACTIVE_STATUS;
            }
        }catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public CustomerDataDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        CustomerDetails customerDetails = customerRepository.getById(dto.getEntityId());
        if(customerDetails==null){
            throw new ResourceNotFoundException(Constants.CUSTOMER_DETAILS_NOT_FOUND);
        }else{
            return CustomerDataDto.builder()
                    .customerId(customerDetails.getCustomerId())
                    .customerEmail(customerDetails.getCustomerEmail())
                    .customerMobile(customerDetails.getCustomerMobile())
                    .customerName(customerDetails.getCustomerName())
                    .village(customerDetails.getVillage())
                    .status(Constants.setStatus(customerDetails.getStatus()))
                    .build();
        }
    }

    @Override
    public Boolean updateStatus(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        Boolean result = customerRepository.getStatus(entityIdDto.getEntityId());
        if(result==null){
            throw new ResourceNotFoundException(Constants.CUSTOMER_DETAILS_NOT_FOUND);
        }else{
            Boolean status = null;
            if(result.equals(Constants.ACTIVE_STATUS)){
                status = Constants.DI_ACTIVE_STATUS;
            }else if(result.equals(Constants.DI_ACTIVE_STATUS)){
                status = Constants.ACTIVE_STATUS;
            }
            Integer count = customerRepository.updateStatus(entityIdDto.getEntityId(),status);
            if(status!=null && count==1){
                return Constants.ACTIVE_STATUS;
            }else{
                return Constants.DI_ACTIVE_STATUS;
            }
        }
    }

    @Override
    public List<CustomerDataDto> getAllCustomersByUserId(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        List<CustomerDetails> list = customerRepository.getAllCustomersByUserId(entityIdDto.getEntityId());
        if(!list.isEmpty()){
            return list.stream().map(data-> new CustomerDataDto(data.getCustomerId(),
                            data.getCustomerName(),data.getCustomerMobile(),data.getCustomerEmail(),
                            data.getVillage(),Constants.setStatus(data.getStatus())))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<CustomerDataDto>();
        }
    }

    @Override
    public Long saveCustomer(CustomerDetailsDto dto) throws AlreadyExistException, InvalidDataException {
        CustomerDetails customerDetails = CustomerDetails.builder()
                .customerName(dto.getCustomerName())
                .customerMobile(dto.getCustomerMobile())
                .customerEmail(dto.getCustomerEmail())
                .village(dto.getVillage())
                .status(Constants.ACTIVE_STATUS)
                .insertedTime(new Date())
                .insertedBy(dto.getRequestedBy())
                .insertedById(dto.getRequestedById())
                .updatedTime(new Date())
                .updatedBy(dto.getRequestedBy())
                .updatedById(dto.getRequestedById())
                .build();
        CustomerDetails details = customerRepository.save(customerDetails);
        if(details!=null && details.getCustomerId()!=null){
            return details.getCustomerId();
        }else{
            throw new InvalidDataException(Constants.CUSTOMER_DETAILS_NOT_SAVE);
        }
    }

    @Override
    public SearchCustomerDataDto getCustomerDetailsByMobile(EntityIdDto dto) throws ResourceNotFoundException {
        List<Object[]> list = customerRepository.getCustomerDetailsByMobile(dto.getEntityId());
        if(list!=null && !list.isEmpty()){
            for(Object[] data:list){
                Long mobile = Long.parseLong(data[3].toString());
                if(mobile.equals(dto.getEntityId())){
                    return SearchCustomerDataDto.builder()
                            .customerId(data[0]!=null?Long.parseLong(data[0].toString()):null)
                            .customerName(data[1]!=null?data[1].toString():null)
                            .customerEmail(data[2]!=null?data[2].toString():null)
                            .customerMobile(data[3]!=null?Long.parseLong(data[3].toString()):null)
                            .customerVillage(data[4]!=null?data[4].toString():null)
                            .build();
                }
                break;
            }
        }
        throw new ResourceNotFoundException(Constants.CUSTOMER_DETAILS_NOT_FOUND);
    }
}
