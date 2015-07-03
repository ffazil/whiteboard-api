package org.blanc.whiteboard.service.impl;

import com.tracebucket.tron.ddd.domain.EntityId;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.repository.jpa.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("authorityServiceImpl")
@Transactional
public class AuthorityServiceImpl {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    public Authority update(Authority authority) {
        return authorityRepository.save(authority);
    }

    public Authority findOne(String uid) {
        return authorityRepository.findOne(new EntityId(uid));
    }

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    public boolean delete(String uid) {
        authorityRepository.delete(new EntityId(uid));
        return authorityRepository.findOne(new EntityId(uid)) == null ? true : false;
    }

    public boolean deleteAll() {
        authorityRepository.deleteAll();
        return authorityRepository.findAll().size() == 0 ? true : false;
    }

}