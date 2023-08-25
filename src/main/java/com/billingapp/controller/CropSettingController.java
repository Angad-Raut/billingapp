package com.billingapp.controller;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.payload.cropDto.CropSettingDataDto;
import com.billingapp.payload.cropDto.CropSettingDto;
import com.billingapp.service.CropSettingService;
import com.billingapp.util.ErrorHandlerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cropSetting")
public class CropSettingController {

    @Autowired
    private CropSettingService cropSettingService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/addCropSetting")
    public ResponseEntity<ResponseDto<Boolean>> addCropSetting(@RequestBody CropSettingDto cropSettingDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = cropSettingService.addCropSetting(cropSettingDto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.CREATED);
        }catch(AlreadyExistException | InvalidDataException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getCropSettingByUserId")
    public ResponseEntity<ResponseDto<CropSettingDataDto>> getCropSettingByUserId(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            CropSettingDataDto data = cropSettingService.getCropSettingByUserId(entityIdDto);
            return new ResponseEntity<ResponseDto<CropSettingDataDto>>(new ResponseDto<CropSettingDataDto>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getCropSettingById")
    public ResponseEntity<ResponseDto<CropSettingDataDto>> getCropSettingById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            CropSettingDataDto data = cropSettingService.getCropSettingById(entityIdDto);
            return new ResponseEntity<ResponseDto<CropSettingDataDto>>(new ResponseDto<CropSettingDataDto>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/updateCropSettingStatusById")
    public ResponseEntity<ResponseDto<Boolean>> updateCropSettingStatusById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = cropSettingService.updateStatus(entityIdDto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getAllCropSettingDetailsByUserId")
    public ResponseEntity<ResponseDto<List<CropSettingDataDto>>> getAllCropSettingDetailsByUserId(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<CropSettingDataDto> data = cropSettingService.getAllCropSettingByUserId(entityIdDto);
            return new ResponseEntity<ResponseDto<List<CropSettingDataDto>>>(new ResponseDto<List<CropSettingDataDto>>(data,null), HttpStatus.OK);
        }catch(Exception e){
            return errorHandler.handleError(e);
        }
    }
}
