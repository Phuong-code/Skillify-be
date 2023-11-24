package com.fdmgroup.skillify.repository;

import com.fdmgroup.skillify.entity.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID>  {
    List<Quiz> findByAuthor_Id(@NonNull UUID id);

    @Override
    void deleteById(UUID uuid);

}
