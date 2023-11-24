package com.fdmgroup.skillify.repository;

import com.fdmgroup.skillify.entity.ClientQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientQuizRepository extends JpaRepository<ClientQuiz, UUID> {
    List<ClientQuiz> findByPlacement_Id(@NonNull UUID id);


}