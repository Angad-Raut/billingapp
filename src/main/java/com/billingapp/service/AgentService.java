package com.billingapp.service;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.agentDto.AgentDataDto;
import com.billingapp.payload.agentDto.AgentDetailsDto;
import com.billingapp.payload.commonDto.EntityIdDto;

import java.util.List;

public interface AgentService {
    Boolean addAgent(AgentDetailsDto dto)throws AlreadyExistException, InvalidDataException;
    AgentDataDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updateStatus(EntityIdDto dto)throws ResourceNotFoundException;
    List<AgentDataDto> getAllAgentsByUserId(EntityIdDto dto)throws ResourceNotFoundException;
}
