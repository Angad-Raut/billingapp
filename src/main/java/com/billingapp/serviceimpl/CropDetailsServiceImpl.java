package com.billingapp.serviceimpl;

import com.billingapp.entity.CropDetails;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.cropDto.CropDataDto;
import com.billingapp.payload.cropDto.CropDetailsDto;
import com.billingapp.payload.cropDto.CropDropDownDto;
import com.billingapp.repository.CropDetailsRepository;
import com.billingapp.service.CropService;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CropDetailsServiceImpl implements CropService {

    @Autowired
    private CropDetailsRepository cropRepository;

    @Override
    public Boolean addCrop(CropDetailsDto dto) throws InvalidDataException, AlreadyExistException {
        CropDetails cropDetails = null;
        Boolean result=Constants.DI_ACTIVE_STATUS;
        if(dto.getCropId()==null){
            cropDetails = CropDetails.builder()
                    .cropName(dto.getCropName())
                    .cropPrice(dto.getCropPrice())
                    .status(Constants.ACTIVE_STATUS)
                    .insertedBy(dto.getRequestedBy())
                    .insertedId(dto.getRequestedById())
                    .insertedTime(new Date())
                    .updatedId(dto.getRequestedById())
                    .updatedType(dto.getRequestedBy())
                    .updatedTime(new Date())
                    .build();
        }else{
             CropDetails cropDetails1=cropRepository.getCropById(dto.getCropId());
             if(!dto.getCropName().equals(cropDetails1.getCropName())){
                 cropDetails.setCropName(dto.getCropName());
             }
             if(!dto.getCropPrice().equals(cropDetails1.getCropPrice())){
                 cropDetails.setCropPrice(dto.getCropPrice());
             }
             cropDetails.setUpdatedId(dto.getRequestedById());
             cropDetails.setUpdatedType(dto.getRequestedBy());
             cropDetails.setUpdatedTime(new Date());
        }
        try{
            if(cropRepository.save(cropDetails)!=null){
                result = Constants.ACTIVE_STATUS;
            }
        }catch(Exception e){

        }
        return result;
    }

    @Override
    public CropDataDto getCropDetailsById(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        CropDetails cropDetails = cropRepository.getCropById(entityIdDto.getEntityId());
        if(cropDetails==null){
            throw new ResourceNotFoundException(Constants.CROP_DETAILS_NOT_FOUND);
        }else{
           return CropDataDto.builder()
                    .cropId(cropDetails.getCropId())
                    .cropName(cropDetails.getCropName())
                    .cropPrice(cropDetails.getCropPrice())
                    .status(Constants.setStatus(cropDetails.getStatus()))
                    .build();
        }
    }

    @Override
    public Boolean updateStatus(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        Boolean result = cropRepository.getStatus(entityIdDto.getEntityId());
        if(result==null){
            throw new ResourceNotFoundException(Constants.CROP_DETAILS_NOT_FOUND);
        }else {
            Boolean status = null;
            if(result.equals(Constants.ACTIVE_STATUS)){
                status = Constants.DI_ACTIVE_STATUS;
            }else if(result.equals(Constants.DI_ACTIVE_STATUS)){
                status = Constants.ACTIVE_STATUS;
            }
            Integer count = cropRepository.updateStatus(entityIdDto.getEntityId(),status);
            if(count==1){
                return Constants.ACTIVE_STATUS;
            }else{
                return Constants.DI_ACTIVE_STATUS;
            }
        }
    }

    @Override
    public List<CropDataDto> getAllCropsByUserId(EntityIdDto entityIdDto) {
        List<CropDetails> list = cropRepository.getAllCropDetailsByUserId(entityIdDto.getEntityId());
        return list.stream()
                .map(data -> new CropDataDto(data.getCropId(),
                        data.getCropName(),data.getCropPrice(),
                        Constants.setStatus(data.getStatus())))
                .collect(Collectors.toList());
    }

    @Override
    public String getCropName(Long cropId) {
        return cropRepository.getCropName(cropId);
    }

    @Override
    public List<CropDropDownDto> getCropDropDown(EntityIdDto dto) {
        List<CropDropDownDto> convertedList = new ArrayList<CropDropDownDto>();
        List<Object[]> list = cropRepository.getCropDropDown(dto.getEntityId());
        for(Object[] objects:list){
            CropDropDownDto dropDownDto = new CropDropDownDto();
            dropDownDto.setCropId(objects[0]!=null?Long.parseLong(objects[0].toString()):null);
            dropDownDto.setCropName(objects[1]!=null?objects[1].toString():null);
            dropDownDto.setCropPrice(objects[2]!=null?Double.parseDouble(objects[2].toString()):null);
            convertedList.add(dropDownDto);
        }
        return convertedList;
    }
}
