package com.billingapp.repository;

import com.billingapp.entity.AgentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AgentDetailsRepository extends JpaRepository<AgentDetails,Long> {

    @Query(value = "select * from agent_details where agent_id=:agentId",nativeQuery = true)
    AgentDetails getById(@Param("agentId")Long agentId);

    @Query(value = "select status from agent_details where agent_id=:agentId",nativeQuery = true)
    Boolean getStatus(@Param("agentId")Long agentId);

    @Transactional
    @Modifying
    @Query(value = "update agent_details set status=:status where agent_id=:agentId",nativeQuery = true)
    Integer updateStatus(@Param("agentId")Long agentId,@Param("status")Boolean status);

    @Query(value = "select * from agent_details where inserted_by_id=:userId",nativeQuery = true)
    List<AgentDetails> getAllAgentsByUserId(@Param("userId")Long userId);
}
