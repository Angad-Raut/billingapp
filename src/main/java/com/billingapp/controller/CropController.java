package com.billingapp.controller;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.payload.cropDto.CropDataDto;
import com.billingapp.payload.cropDto.CropDetailsDto;
import com.billingapp.payload.cropDto.CropDropDownDto;
import com.billingapp.service.CropService;
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
@RequestMapping(value = "/api/cropDetails")
public class CropController {

    @Autowired
    private CropService cropService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/addCrop")
    public ResponseEntity<ResponseDto<Boolean>> addCrop(@RequestBody CropDetailsDto cropDetailsDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = cropService.addCrop(cropDetailsDto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.CREATED);
        }catch(AlreadyExistException | InvalidDataException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getCropById")
    public ResponseEntity<ResponseDto<CropDataDto>> getCropById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            CropDataDto data = cropService.getCropDetailsById(entityIdDto);
            return new ResponseEntity<ResponseDto<CropDataDto>>(new ResponseDto<CropDataDto>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/updateCropStatusById")
    public ResponseEntity<ResponseDto<Boolean>> updateCropStatusById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = cropService.updateStatus(entityIdDto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getAllCropDetailsByUserId")
    public ResponseEntity<ResponseDto<List<CropDataDto>>> getAllCropDetailsByUserId(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<CropDataDto> data = cropService.getAllCropsByUserId(entityIdDto);
            return new ResponseEntity<ResponseDto<List<CropDataDto>>>(new ResponseDto<List<CropDataDto>>(data,null), HttpStatus.OK);
        }catch(Exception e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getCropDropDownByUserId")
    public ResponseEntity<ResponseDto<List<CropDropDownDto>>> getCropDropDownByUserId(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<CropDropDownDto> data = cropService.getCropDropDown(entityIdDto);
            return new ResponseEntity<ResponseDto<List<CropDropDownDto>>>(new ResponseDto<List<CropDropDownDto>>(data,null), HttpStatus.OK);
        }catch(Exception e){
            return errorHandler.handleError(e);
        }
    }
}
