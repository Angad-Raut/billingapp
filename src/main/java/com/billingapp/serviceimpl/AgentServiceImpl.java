package com.billingapp.serviceimpl;

import com.billingapp.entity.AgentDetails;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.agentDto.AgentDataDto;
import com.billingapp.payload.agentDto.AgentDetailsDto;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.repository.AgentDetailsRepository;
import com.billingapp.service.AgentService;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentDetailsRepository agentRepository;

    @Override
    public Boolean addAgent(AgentDetailsDto dto) throws AlreadyExistException, InvalidDataException {
        AgentDetails agentDetails=null;
        if(dto.getAgentId()==null){
            agentDetails = AgentDetails.builder()
                    .agentName(dto.getAgentName())
                    .agentEmail(dto.getAgentEmail())
                    .agentMobile(dto.getAgentMobile())
                    .agentAddress(dto.getAgentAddress())
                    .status(Constants.ACTIVE_STATUS)
                    .insertedBy(dto.getRequestedBy())
                    .insertedById(dto.getRequestedById())
                    .insertedTime(new Date())
                    .updatedTime(new Date())
                    .updatedBy(dto.getRequestedBy())
                    .updatedById(dto.getRequestedById())
                    .build();
        }else{
             agentDetails = agentRepository.getById(dto.getAgentId());
             if(!dto.getAgentName().equals(agentDetails.getAgentName())){
                 agentDetails.setAgentName(dto.getAgentName());
             }
            if(!dto.getAgentMobile().equals(agentDetails.getAgentMobile())){
                agentDetails.setAgentMobile(dto.getAgentMobile());
            }
            if(!dto.getAgentEmail().equals(agentDetails.getAgentEmail())){
                agentDetails.setAgentEmail(dto.getAgentEmail());
            }
            if(!dto.getAgentAddress().equals(agentDetails.getAgentAddress())){
                agentDetails.setAgentAddress(dto.getAgentAddress());
            }
            agentDetails.setUpdatedBy(dto.getRequestedBy());
            agentDetails.setUpdatedById(dto.getRequestedById());
            agentDetails.setUpdatedTime(new Date());
        }
        try{
            if(agentRepository.save(agentDetails)!=null){
                return Constants.ACTIVE_STATUS;
            }else{
                return Constants.DI_ACTIVE_STATUS;
            }
        }catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public AgentDataDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        AgentDetails agentDetails = agentRepository.getById(dto.getEntityId());
        if(agentDetails==null){
            throw new ResourceNotFoundException(Constants.AGENT_DETAILS_NOT_FOUND);
        }else{
            return AgentDataDto.builder()
                    .agentId(agentDetails.getAgentId())
                    .agentName(agentDetails.getAgentName())
                    .agentMobile(agentDetails.getAgentMobile())
                    .agentEmail(agentDetails.getAgentEmail())
                    .agentAddress(agentDetails.getAgentAddress())
                    .status(Constants.setStatus(agentDetails.getStatus()))
                    .build();
        }
    }

    @Override
    public Boolean updateStatus(EntityIdDto dto) throws ResourceNotFoundException {
        Boolean result = agentRepository.getStatus(dto.getEntityId());
        if(result==null){
            throw new ResourceNotFoundException(Constants.AGENT_DETAILS_NOT_FOUND);
        }else{
             Boolean status = null;
             if(result.equals(Constants.ACTIVE_STATUS)){
                 status = Constants.DI_ACTIVE_STATUS;
             }else if(result.equals(Constants.DI_ACTIVE_STATUS)){
                 status = Constants.ACTIVE_STATUS;
             }
             Integer count = agentRepository.updateStatus(dto.getEntityId(),status);
             if(status!=null && count==1){
                 return Constants.ACTIVE_STATUS;
             }else{
                 return Constants.DI_ACTIVE_STATUS;
             }
        }
    }

    @Override
    public List<AgentDataDto> getAllAgentsByUserId(EntityIdDto dto) throws ResourceNotFoundException {
        List<AgentDetails> list = agentRepository.getAllAgentsByUserId(dto.getEntityId());
        if(!list.isEmpty()){
            return list.stream().map(data-> new AgentDataDto(data.getAgentId(),
                       data.getAgentName(),data.getAgentMobile(),data.getAgentEmail(),
                       data.getAgentAddress(),Constants.setStatus(data.getStatus())))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<AgentDataDto>();
        }
    }
}
