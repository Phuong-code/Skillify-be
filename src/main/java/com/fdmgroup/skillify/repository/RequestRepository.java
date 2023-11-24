package com.fdmgroup.skillify.repository;

import java.util.List;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.Request;
import com.fdmgroup.skillify.enums.RequestType;


@Repository
public interface RequestRepository extends JpaRepository<Request, UUID>{

	/**
	 * 
	 * @param sender_email sender's email
	 * @return A list of requests sent by given sender email
	 */
	@Query("FROM Request r WHERE r.sender.email = :sender_email AND r.type != 2 ORDER BY r.createdDate DESC")
	List<Request> findRequestBySenderEmail(@Param("sender_email") String sender_email);
	
	@Query("FROM Request r WHERE r.sender.email = :sender_email AND r.type = 0")
	Request findAuthenticationRequestBySenderEmail(@Param("sender_email") String sender_email);
	
	/**
	 * 
	 * @param receiver_email receiver's email
	 * @return A list of requests received by given receiver email
	 */
	@Query("FROM Request r WHERE r.receiver.email = :receiver_email ORDER BY r.createdDate DESC")
	List<Request> findRequestByReceiverEmail(@Param("receiver_email") String receiver_email);
	

	 /**
	 * 
	 * @param type RequestType
	 * @return A list of Request objects by given type
	 */
	@Query("FROM Request r WHERE r.type = :type")
	List<Request> findRequestByType(@Param("type") RequestType type);

}
