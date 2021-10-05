package com.example.gestionAchat.service;
import com.example.gestionAchat.repository.AclSidRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {


    @Autowired
    AclSidRepository aclSidRepository;

    private final Log logger = LogFactory.getLog(getClass());

    private final AclService aclService;
    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
    private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();
    private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();
    private PermissionFactory permissionFactory = new DefaultPermissionFactory();

    public CustomPermissionEvaluator(AclService aclService) {
        this.aclService = aclService;
    }

    /**
     * Determines whether the user has the given permission(s) on the domain object using
     * the ACL configuration. If the domain object is null, returns false (this can always
     * be overridden using a null check in the expression itself).
     */
    public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {


        if (domainObject == null) {
            return false;
        }

        ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy
                .getObjectIdentity(domainObject);

        return checkPermission(authentication, objectIdentity, permission);
    }

    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        ObjectIdentity objectIdentity = objectIdentityGenerator.createObjectIdentity(
                targetId, targetType);

        return checkPermission(authentication, objectIdentity, permission);
    }

    private boolean checkPermission(Authentication authentication, ObjectIdentity oid, Object permission) {

         List<Sid> sids = sidRetrievalStrategy.getSids(authentication);

         for(int s=0; s<sids.size(); s++)
             System.out.println(" sid ==> "+ s + ": "+ sids.get(s));
        List<Permission> requiredPermission = resolvePermission(permission);

        System.out.println("permission size"+requiredPermission.size() );

        final boolean debug = logger.isDebugEnabled();

        if (debug) {
            logger.debug("Checking permission '" + permission + "' for object '" + oid
                    + "'");
        }

        try {
            // Lookup only ACLs for SIDs we're interested in
             Acl acl = aclService.readAclById(oid, sids);

                if (acl.isGranted(requiredPermission, sids, false)) {
                    if (debug) {
                        logger.debug("Access is granted");
                    }

            return true;
           }

//                if (debug) {
//                    logger.debug("Returning false - ACLs returned, but insufficient permissions for this principal");
//                }

        }
        catch (NotFoundException nfe) {
            if (debug) {
                logger.debug("Returning false - no ACLs apply for this principal");
            }
        }

        return false;

    }

    List<Permission> resolvePermission(Object permission) {
        if (permission instanceof Integer) {
            return Arrays.asList(permissionFactory.buildFromMask((Integer) permission));
        }

        if (permission instanceof Permission) {
            return Arrays.asList((Permission) permission);
        }

        if (permission instanceof Permission[]) {
            return Arrays.asList((Permission[]) permission);
        }

        if (permission instanceof String) {
            String permString = (String) permission;
            Permission p;

            try {
                p = permissionFactory.buildFromName(permString);
            }
            catch (IllegalArgumentException notfound) {
                p = permissionFactory.buildFromName(permString.toUpperCase(Locale.ENGLISH));
            }

            if (p != null) {
                return Arrays.asList(p);
            }

        }
        throw new IllegalArgumentException("Unsupported permission: " + permission);
    }

    public void setObjectIdentityRetrievalStrategy(
            ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
        this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
    }

    public void setObjectIdentityGenerator(ObjectIdentityGenerator objectIdentityGenerator) {
        this.objectIdentityGenerator = objectIdentityGenerator;
    }

    public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy) {
        this.sidRetrievalStrategy = sidRetrievalStrategy;
    }

    public void setPermissionFactory(PermissionFactory permissionFactory) {
        this.permissionFactory = permissionFactory;
    }
}

