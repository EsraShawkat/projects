package com.example.backend.repositories;

import com.example.backend.exeptions.NotAcceptableException;
import com.example.backend.models.Custom_User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class UserRepositoryJpa implements UserRepository{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Custom_User save(Custom_User user) {
        return entityManager.merge(user);
    }

    @Override
    public Custom_User findByQuery(String query, Object... params) {
        try{
        TypedQuery<Custom_User> typedQuery = entityManager.createNamedQuery(query, Custom_User.class);

        for (int i = 0; i < params.length; i++) {
            typedQuery.setParameter(i + 1, params[i]);
         }
        return typedQuery.getSingleResult();
        }catch (NoResultException e){
            System.out.print("geen gebruikers met deze query gevonden");
        }
        return null;
    }
}
