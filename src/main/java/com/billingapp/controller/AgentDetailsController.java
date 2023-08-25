package com.billingapp.controller;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.agentDto.AgentDataDto;
import com.billingapp.payload.agentDto.AgentDetailsDto;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.service.AgentService;
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
@RequestMapping(value = "/api/agentDetails")
public class AgentDetailsController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/addAgent")
    public ResponseEntity<ResponseDto<Boolean>> addAgent(@RequestBody AgentDetailsDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = agentService.addAgent(dto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.CREATED);
        }catch(AlreadyExistException | InvalidDataException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getAgentById")
    public ResponseEntity<ResponseDto<AgentDataDto>> getAgentById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            AgentDataDto data = agentService.getById(entityIdDto);
            return new ResponseEntity<ResponseDto<AgentDataDto>>(new ResponseDto<AgentDataDto>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/updateAgentStatusById")
    public ResponseEntity<ResponseDto<Boolean>> updateAgentStatusById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = agentService.updateStatus(entityIdDto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getAllAgentDetailsByUserId")
    public ResponseEntity<ResponseDto<List<AgentDataDto>>> getAllAgentDetailsByUserId(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<AgentDataDto> data = agentService.getAllAgentsByUserId(entityIdDto);
            return new ResponseEntity<ResponseDto<List<AgentDataDto>>>(new ResponseDto<List<AgentDataDto>>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }
}
