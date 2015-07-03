package org.blanc.whiteboard.rest.assembler.resource;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.Client;
import org.blanc.whiteboard.rest.resource.AuthorityResource;
import org.blanc.whiteboard.rest.resource.ClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClientResourceAssembler extends ResourceAssembler<ClientResource, Client> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public ClientResource toResource(Client entity, Class<ClientResource> resourceClass) {
        ClientResource client = null;
        try {
            client = resourceClass.newInstance();
            if (entity != null) {
                client.setUid(entity.getEntityId().getId());
                client.setScope(entity.getScope());
                client.setPassive(entity.isPassive());
                client.setResourceIds(entity.getResourceIds());
                client.setRegisteredRedirectUris(entity.getRegisteredRedirectUri());
                client.setRefreshTokenValiditySeconds(entity.getRefreshTokenValiditySeconds());
                client.setAccessTokenValiditySeconds(entity.getAccessTokenValiditySeconds());
                Map<String, Object> additionalInformation = entity.getAdditionalInformation();
                Map<String, String> additionalInformation2 = new HashMap<String, String>();
                Set<Map.Entry<String, Object>> entries = additionalInformation.entrySet();
                entries.forEach(e -> additionalInformation2.put(e.getKey(), (String)e.getValue()));
                client.setAdditionalInformation(additionalInformation2);
                client.setAuthorizedGrantTypes(entity.getAuthorizedGrantTypes());
                client.setAutoApproveScopes(entity.getAutoApproveScopes());
                client.setClientId(entity.getClientId());
                client.setClientSecret(entity.getClientSecret());
                Collection<GrantedAuthority> authorities = entity.getAuthorities();
                if(authorities != null && authorities.size() > 0) {
                    Set<Authority> authorityList = new HashSet<Authority>();
                    authorities.stream().forEach(a -> authorityList.add((Authority)a));
                    client.setAuthorities(assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResources(authorityList, AuthorityResource.class));
                }
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return client;
    }

    @Override
    public Set<ClientResource> toResources(Collection<Client> entities, Class<ClientResource> resourceClass) {
        Set<ClientResource> clients = new HashSet<ClientResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<Client> iterator = entities.iterator();
            if(iterator.hasNext()) {
                Client client = iterator.next();
                clients.add(toResource(client, ClientResource.class));
            }
        }
        return clients;
    }
}