package com.billingapp.service;

import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.cropDto.CropDataDto;
import com.billingapp.payload.cropDto.CropDetailsDto;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.payload.cropDto.CropDropDownDto;

import java.util.List;

public interface CropService {
    Boolean addCrop(CropDetailsDto cropDetailsDto)throws InvalidDataException, AlreadyExistException;
    CropDataDto getCropDetailsById(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    Boolean updateStatus(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    List<CropDataDto> getAllCropsByUserId(EntityIdDto entityIdDto);
    String getCropName(Long cropId);
    List<CropDropDownDto> getCropDropDown(EntityIdDto dto);
}
