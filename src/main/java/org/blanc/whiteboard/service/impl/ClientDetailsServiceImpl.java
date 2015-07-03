package org.blanc.whiteboard.service.impl;

import org.blanc.whiteboard.domain.Client;
import org.blanc.whiteboard.repository.jpa.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FFL
 * @since 16-03-2015
 */

@Service("clientDetailsServiceImpl")
@Transactional
public class ClientDetailsServiceImpl implements ClientDetailsService, ClientRegistrationService{

    @Autowired
    private ClientRepository clientRepository;

    public ClientDetailsServiceImpl(){

    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        Client client;
        try {
             client = clientRepository.findByClientId(clientId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }

/*        BaseClientDetails clientDetails = new BaseClientDetails(client);
        return clientDetails;*/
        return client;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        Client client = (Client) clientDetails;
        try{
            client = clientRepository.save(client);
        }
        catch (DuplicateKeyException e){
            throw new ClientAlreadyExistsException("Client already exists: " + client.getClientId());
        }

    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        Client client = (Client) clientDetails;
        try{
            client = clientRepository.save(client);
        }
        catch (Exception e){
            throw new NoSuchClientException("No client found with id: " + client.getClientId());
        }
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        Client client = clientRepository.findByClientId(clientId);
        if(client != null){
            client.setClientSecret(secret);
            client = clientRepository.save(client);
        }
        else{
            throw new NoSuchClientException("No client found with id: " + clientId);
        }

    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        if(clientRepository.findByClientId(clientId) != null)
            clientRepository.deleteByClientId(clientId);
        else
            throw new NoSuchClientException("No client found with id: " + clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<ClientDetails> clientDetails = new ArrayList<>(0);
        clientRepository.findAll()
                .forEach(client -> clientDetails.add(client));
        return clientDetails;
    }
}
