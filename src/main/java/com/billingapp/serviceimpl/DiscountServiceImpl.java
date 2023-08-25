package com.billingapp.serviceimpl;

import com.billingapp.entity.DiscountSetting;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdAndValueDto;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.EntityIdWithTypeDto;
import com.billingapp.payload.discountDto.DiscountDataDto;
import com.billingapp.payload.discountDto.DiscountDropDownDto;
import com.billingapp.payload.discountDto.DiscountDto;
import com.billingapp.payload.discountDto.DiscountTypeDto;
import com.billingapp.repository.DiscountRepository;
import com.billingapp.service.DiscountService;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Boolean addDiscount(DiscountDto dto) throws AlreadyExistException, InvalidDataException {
        DiscountSetting discountSetting = null;
        if(dto.getId()==null){
             discountSetting = DiscountSetting.builder()
                     .discountType(dto.getDiscountType())
                     .discountValue(dto.getDiscountValue())
                     .insertedById(dto.getRequestedById())
                     .insertedBy(dto.getRequestedBy())
                     .insertedTime(new Date())
                     .updatedById(dto.getRequestedById())
                     .updatedBy(dto.getRequestedBy())
                     .updatedTime(new Date())
                     .build();
        }else{
             discountSetting = discountRepository.getById(dto.getId());
             if(!dto.getDiscountType().equals(discountSetting.getDiscountType())){
                 discountSetting.setDiscountType(dto.getDiscountType());
             }
             if(!dto.getDiscountValue().equals(discountSetting.getDiscountValue())){
                 discountSetting.setDiscountValue(dto.getDiscountValue());
             }
             discountSetting.setUpdatedTime(new Date());
             discountSetting.setUpdatedById(dto.getRequestedById());
             discountSetting.setUpdatedBy(dto.getRequestedBy());
        }
        try{
            if(discountRepository.save(discountSetting)!=null){
                return Constants.ACTIVE_STATUS;
            }else{
                return Constants.DI_ACTIVE_STATUS;
            }
        }catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public DiscountDataDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        DiscountSetting discountSetting = discountRepository.getById(dto.getEntityId());
        if(discountSetting==null){
             throw new ResourceNotFoundException(Constants.DISCOUNT_DETAILS_NOT_FOUND);
        }else{
            return DiscountDataDto.builder()
                    .id(discountSetting.getId())
                    .discountType(discountSetting.getDiscountType())
                    .discountValue(discountSetting.getDiscountValue())
                    .build();
        }
    }

    @Override
    public List<DiscountDataDto> getAllDiscountsByUserId(EntityIdWithTypeDto dto) throws ResourceNotFoundException {
        List<DiscountSetting> list = discountRepository.getAllDiscountsByUserId(dto.getEntityId());
        if(!list.isEmpty()){
            return list.stream().map(data-> new DiscountDataDto(data.getId(),data.getDiscountType(),
                    data.getDiscountValue())).collect(Collectors.toList());
        }else{
            return new ArrayList<DiscountDataDto>();
        }
    }

    @Override
    public List<DiscountTypeDto> getDiscountDropDown() {
        return Arrays.asList(new DiscountTypeDto[]{
                new DiscountTypeDto(Constants.PERCENTAGE, Constants.PERCENTAGE_VALUE),
                new DiscountTypeDto(Constants.RUPEES, Constants.RUPEES_VALUE)
        });
    }

    @Override
    public List<DiscountDropDownDto> getDiscountDropDownByType(EntityIdWithTypeDto dto) {
        List<Object[]> list = discountRepository.getDiscountDropDownByType(dto.getEntityId(),dto.getEntityType());
        if(!list.isEmpty()){
            return list.stream().map(objects ->
                    new DiscountDropDownDto(objects[0]!=null?Long.parseLong(objects[0].toString()):null,
                            objects[1]!=null?Double.parseDouble(objects[1].toString()):null))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<DiscountDropDownDto>();
        }
    }
}
