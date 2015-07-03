package org.blanc.whiteboard.rest.assembler.entity;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.EntityId;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.Client;
import org.blanc.whiteboard.rest.resource.AuthorityResource;
import org.blanc.whiteboard.rest.resource.ClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class ClientEntityAssembler extends EntityAssembler<Client, ClientResource> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public Client toEntity(ClientResource resource, Class<Client> entityClass) {
        Client client = null;
        if(resource != null) {
            client = new Client();
            if (resource.getUid() != null) {
                client.setEntityId(new EntityId(resource.getUid()));
            }
            client.setScope(resource.getScope());
            client.setPassive(resource.isPassive());
            client.setResourceIds(resource.getResourceIds());
            client.setRegisteredRedirectUri(resource.getRegisteredRedirectUris());
            client.setRefreshTokenValiditySeconds(resource.getRefreshTokenValiditySeconds());
            client.setAccessTokenValiditySeconds(resource.getAccessTokenValiditySeconds());
            client.setAdditionalInformation(resource.getAdditionalInformation());
            client.setAuthorizedGrantTypes(resource.getAuthorizedGrantTypes());
            client.setAutoApproveScopes(resource.getAutoApproveScopes());
            client.setClientId(resource.getClientId());
            client.setClientSecret(resource.getClientSecret());
            client.setAuthorities(assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntities(resource.getAuthorities(), Authority.class));
        }
        return client;
    }

    @Override
    public Set<Client> toEntities(Collection<ClientResource> resources, Class<Client> entityClass) {
        Set<Client> clients = new HashSet<Client>();
        if(resources != null) {
            Iterator<ClientResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                ClientResource clientResource = iterator.next();
                clients.add(toEntity(clientResource, Client.class));
            }
        }
        return clients;
    }
}