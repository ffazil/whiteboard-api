package org.blanc.whiteboard.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import org.blanc.whiteboard.domain.Client;
import org.blanc.whiteboard.rest.resource.ClientResource;
import org.blanc.whiteboard.service.impl.ClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController(value = "idemClientController")
@RequestMapping("/admin")
public class ClientController {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private ClientDetailsServiceImpl clientDetailsServiceImpl;

    @RequestMapping(value = "/client", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResource> createClient(@RequestBody ClientResource clientResource) {
        Client client = assemblerResolver.resolveEntityAssembler(Client.class, ClientResource.class).toEntity(clientResource, Client.class);
        clientDetailsServiceImpl.addClientDetails(client);
        client = (Client)clientDetailsServiceImpl.loadClientByClientId(client.getClientId());
        if(client != null) {
            clientResource = assemblerResolver.resolveResourceAssembler(ClientResource.class, Client.class).toResource(client, ClientResource.class);
            return new ResponseEntity<ClientResource>(clientResource, HttpStatus.OK);
        }
        return new ResponseEntity<ClientResource>(new ClientResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/client", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResource> updateClient(@RequestBody ClientResource clientResource) {
        Client client = assemblerResolver.resolveEntityAssembler(Client.class, ClientResource.class).toEntity(clientResource, Client.class);
        clientDetailsServiceImpl.updateClientDetails(client);
        client = (Client)clientDetailsServiceImpl.loadClientByClientId(client.getClientId());
        if(client != null) {
            clientResource = assemblerResolver.resolveResourceAssembler(ClientResource.class, Client.class).toResource(client, ClientResource.class);
            return new ResponseEntity<ClientResource>(clientResource, HttpStatus.OK);
        }
        return new ResponseEntity<ClientResource>(new ClientResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/client/{clientId}/secret", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResource> updateClientSecret(@PathVariable("clientId") String clientId, @RequestParam("clientSecret") String clientSecret) {
        clientDetailsServiceImpl.updateClientSecret(clientId, clientSecret);
        Client client = (Client)clientDetailsServiceImpl.loadClientByClientId(clientId);
        if(client != null) {
            ClientResource clientResource = assemblerResolver.resolveResourceAssembler(ClientResource.class, Client.class).toResource(client, ClientResource.class);
            return new ResponseEntity<ClientResource>(clientResource, HttpStatus.OK);
        }
        return new ResponseEntity<ClientResource>(new ClientResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResource> findByClientId(@PathVariable("clientId") String clientId) {
        Client client = (Client)clientDetailsServiceImpl.loadClientByClientId(clientId);
        if(client != null) {
            ClientResource clientResource = assemblerResolver.resolveResourceAssembler(ClientResource.class, Client.class).toResource(client, ClientResource.class);
            return new ResponseEntity<ClientResource>(clientResource, HttpStatus.OK);
        }
        return new ResponseEntity<ClientResource>(new ClientResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ClientResource>> findClients() {
        List<Client> clients = new ArrayList<Client>();
        List<ClientDetails> clientDetails = clientDetailsServiceImpl.listClientDetails();
        if(clientDetails != null && clientDetails.size() > 0) {
            clientDetails.stream().forEach(client -> clients.add((Client)client));
            Set<ClientResource> clientResources = assemblerResolver.resolveResourceAssembler(ClientResource.class, Client.class).toResources(clients, ClientResource.class);
            return new ResponseEntity<Set<ClientResource>>(clientResources, HttpStatus.OK);
        }
        return new ResponseEntity<Set<ClientResource>>(Collections.<ClientResource>emptySet(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> removeByClientId(@PathVariable("clientId") String clientId) {
        clientDetailsServiceImpl.removeClientDetails(clientId);
        Client client = (Client)clientDetailsServiceImpl.loadClientByClientId(clientId);
        return new ResponseEntity<Boolean>(client == null ? true : false, HttpStatus.OK);
    }
}