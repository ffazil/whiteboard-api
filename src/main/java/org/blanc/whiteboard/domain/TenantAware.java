package org.blanc.whiteboard.domain;

import java.util.Map;

/**
 * @author ffazil
 * @since 17/03/15
 * Extension to add tenant information.
 */
public interface TenantAware {
    Map<String, Object> getTenantInformation();
}
