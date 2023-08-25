package com.billingapp.serviceimpl;

import com.billingapp.entity.CropSetting;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.cropDto.CropSettingDataDto;
import com.billingapp.payload.cropDto.CropSettingDto;
import com.billingapp.repository.CropSettingRepository;
import com.billingapp.service.CropSettingService;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CropSettingServiceImpl implements CropSettingService {

    @Autowired
    private CropSettingRepository cropSettingRepo;

    @Override
    public Boolean addCropSetting(CropSettingDto dto) throws AlreadyExistException, InvalidDataException {
        CropSetting cropSetting = null;
        Boolean result = Constants.DI_ACTIVE_STATUS;
        if(dto.getId()==null){
             cropSetting = CropSetting.builder()
                     .hamali(dto.getHamali())
                     .warai(dto.getWarai())
                     .tollai(dto.getTollai())
                     .bhade(dto.getBhade())
                     .status(Constants.ACTIVE_STATUS)
                     .insertedBy(dto.getRequestedBy())
                     .insertedById(dto.getRequestedById())
                     .insertedTime(new Date())
                     .updatedBy(dto.getRequestedBy())
                     .updatedById(dto.getRequestedById())
                     .updatedTime(new Date())
                     .build();
        }else{
            cropSetting = cropSettingRepo.getById(dto.getId());
            if(!dto.getHamali().equals(cropSetting.getHamali())){
                cropSetting.setHamali(dto.getHamali());
            }
            if(!dto.getWarai().equals(cropSetting.getWarai())){
                cropSetting.setWarai(dto.getWarai());
            }
            if(!dto.getTollai().equals(cropSetting.getTollai())){
                cropSetting.setTollai(dto.getTollai());
            }
            if(cropSetting.getBhade()!=null && dto.getBhade()!=null) {
                if (!dto.getBhade().equals(cropSetting.getBhade())) {
                    cropSetting.setBhade(dto.getBhade());
                }
            }
            cropSetting.setUpdatedBy(dto.getRequestedBy());
            cropSetting.setUpdatedById(dto.getRequestedById());
            cropSetting.setUpdatedTime(new Date());
        }
        try{
            if(cropSettingRepo.save(cropSetting)!=null){
                result = Constants.ACTIVE_STATUS;
            }else{
                result = Constants.DI_ACTIVE_STATUS;
            }
        }catch(Exception e){
            throw new InvalidDataException(e.getMessage());
        }
        return result;
    }

    @Override
    public CropSettingDataDto getCropSettingById(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        CropSetting cropSetting = cropSettingRepo.getById(entityIdDto.getEntityId());
        if(cropSetting==null){
            throw new ResourceNotFoundException(Constants.CROP_SETTING_NOT_FOUND);
        }else{
            return CropSettingDataDto.builder()
                    .id(cropSetting.getId())
                    .warai(cropSetting.getWarai())
                    .hamali(cropSetting.getHamali())
                    .tollai(cropSetting.getTollai())
                    .bhade(cropSetting.getBhade())
                    .status(Constants.setStatus(cropSetting.getStatus()))
                    .build();
        }
    }

    @Override
    public Boolean updateStatus(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        Boolean result = cropSettingRepo.getStatus(entityIdDto.getEntityId());
        if(result==null){
            throw new ResourceNotFoundException(Constants.CROP_SETTING_NOT_FOUND);
        }else {
            Boolean status = null;
            if(result.equals(Constants.ACTIVE_STATUS)){
                status = Constants.DI_ACTIVE_STATUS;
            }else if(result.equals(Constants.DI_ACTIVE_STATUS)){
                status = Constants.ACTIVE_STATUS;
            }
            Integer count = cropSettingRepo.updateStatus(entityIdDto.getEntityId(),status);
            if(count==1){
                return Constants.ACTIVE_STATUS;
            }else{
                return Constants.DI_ACTIVE_STATUS;
            }
        }
    }

    @Override
    public List<CropSettingDataDto> getAllCropSettingByUserId(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        List<CropSetting> list = cropSettingRepo.getAllCropSettingByUserId(entityIdDto.getEntityId());
        if(!list.isEmpty()){
            return list.stream().map(data-> new CropSettingDataDto(data.getId()
                            ,data.getWarai(), data.getHamali(),data.getTollai(),data.getBhade(),
                             Constants.setStatus(data.getStatus())))
                    .collect(Collectors.toList());
        }else{
            throw new ResourceNotFoundException(Constants.CROP_SETTING_NOT_FOUND);
        }
    }

    @Override
    public CropSettingDataDto getCropSettingByUserId(EntityIdDto dto) throws ResourceNotFoundException {
        CropSetting cropSetting = cropSettingRepo.getByUserId(dto.getEntityId());
        if(cropSetting==null){
            return new CropSettingDataDto();
        }else{
            return CropSettingDataDto.builder()
                    .id(cropSetting.getId())
                    .hamali(cropSetting.getHamali())
                    .warai(cropSetting.getWarai())
                    .tollai(cropSetting.getTollai())
                    .bhade(cropSetting.getBhade())
                    .status(Constants.setStatus(cropSetting.getStatus()))
                    .build();
        }
    }
}
