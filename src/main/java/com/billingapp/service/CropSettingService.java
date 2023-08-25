package com.billingapp.service;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.cropDto.CropSettingDataDto;
import com.billingapp.payload.cropDto.CropSettingDto;

import java.util.List;

public interface CropSettingService {
    Boolean addCropSetting(CropSettingDto dto)throws AlreadyExistException, InvalidDataException;
    CropSettingDataDto getCropSettingById(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    Boolean updateStatus(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    List<CropSettingDataDto> getAllCropSettingByUserId(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    CropSettingDataDto getCropSettingByUserId(EntityIdDto dto)throws ResourceNotFoundException;
}
