package edu.harvard.iq.dataverse.authorization.groups.impl.affiliation;

import edu.harvard.iq.dataverse.RoleAssigneeServiceBean;
import edu.harvard.iq.dataverse.actionlogging.ActionLogRecord;
import edu.harvard.iq.dataverse.actionlogging.ActionLogServiceBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Named
@Stateless
public class AffiliationGroupServiceBean {

    private static final Logger logger = Logger.getLogger(AffiliationGroupServiceBean.class.getName());

    @PersistenceContext(unitName = "VDCNet-ejbPU")
    protected EntityManager em;

    @EJB
    private ActionLogServiceBean actionLogSvc;

    @EJB
    private RoleAssigneeServiceBean roleAssigneeSvc;

    public AffiliationGroup get(long id) {
        return em.find(AffiliationGroup.class, id);
    }

    public AffiliationGroup getByGroupName(String alias) {
        try {
            TypedQuery<AffiliationGroup> namedQuery = em.createNamedQuery("AffiliationGroup.findByPersistedGroupAlias", AffiliationGroup.class);
            namedQuery.setParameter("persistedGroupAlias", alias);
            return namedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AffiliationGroup getByDisplayName(String displayname) {
        try {
            TypedQuery<AffiliationGroup> namedQuery = em.createNamedQuery("AffiliationGroup.findByDisplayName", AffiliationGroup.class);
            namedQuery.setParameter("displayName", displayname);
            return namedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AffiliationGroup find(String userEmail) {
        String topLevelDomain = null;
        String domain = userEmail.substring(userEmail.indexOf("@")+1).trim();
        long count = domain.chars().filter(ch -> ch == '.').count();
        if (count > 1) {
            int secondLastIndex = StringUtils.lastIndexOf(domain, '.', domain.lastIndexOf(".") - 1);
            topLevelDomain = domain.substring(secondLastIndex + 1);
        } else {
            topLevelDomain = domain;
        }
        AffiliationGroup group = matchByTopLevelEmailDomain(topLevelDomain);
        if (group != null) {
            String emaildomain = group.getEmaildomain();
            List<String> domainValues = Arrays.asList(emaildomain.split("\\s*,\\s*"));
            if (domainValues.contains(topLevelDomain))
                return group;
        }
        return null;
    }

    public List<AffiliationGroup> findAll() {
        return em.createNamedQuery("AffiliationGroup.findAll", AffiliationGroup.class).getResultList();
    }

    public AffiliationGroup store(AffiliationGroup grp) {
        ActionLogRecord alr = new ActionLogRecord(ActionLogRecord.ActionType.GlobalGroups, "affCreate");
        alr.setInfo(grp.getDisplayName());
        AffiliationGroup existing = getByDisplayName(grp.getDisplayName());
        if (existing == null) {
            em.persist(grp);
            actionLogSvc.log(alr);
            return grp;
        } else {
            existing.setDescription(grp.getDescription());
            existing.setPersistedGroupAlias(grp.getPersistedGroupAlias());
            existing.setEmaildomain(grp.getEmaildomain());
            actionLogSvc.log(alr.setActionSubType("affUpdate"));
            return existing;
        }
    }

    /**
     * Deletes the group - if it has no assignments.
     *
     * @param grp the group to be deleted
     * @throws IllegalArgumentException if the group has assignments
     * @see RoleAssigneeServiceBean#getAssignmentsFor(java.lang.String)
     */
    public void deleteGroup(AffiliationGroup grp) {
        ActionLogRecord alr = new ActionLogRecord(ActionLogRecord.ActionType.GlobalGroups, "affDelete");
        String identifier = grp.getIdentifier();
        alr.setInfo(identifier);
        if (isAssigned(grp)) {
            String failReason = "Group " + grp.getAlias() + " has assignments and thus can't be deleted.";
            alr.setActionResult(ActionLogRecord.Result.BadRequest);
            alr.setInfo(alr.getInfo() + "// " + failReason);
            actionLogSvc.log(alr);
            throw new IllegalArgumentException(failReason);
        } else {
            if (!em.contains(grp)) {
                grp = em.merge(grp);
            }
            em.remove(grp);
            actionLogSvc.log(alr);
        }
    }

    public boolean isAssigned(AffiliationGroup grp) {
        return !roleAssigneeSvc.getAssignmentsFor(grp.getIdentifier()).isEmpty();
    }

    public Long getAffiliationGroupsCount() {
        String qstr = "SELECT count(ag)";
        qstr += " FROM AffiliationGroup ag";
        Query query = em.createQuery(qstr);
        return (Long) query.getSingleResult();
    }

    public AffiliationGroup find(Object pk) {
        return (AffiliationGroup) em.find(AffiliationGroup.class, pk);
    }

    private AffiliationGroup matchByTopLevelEmailDomain(String emaildomain) {
        try {
            TypedQuery<AffiliationGroup> namedQuery = em.createNamedQuery("AffiliationGroup.findByEmailDomain", AffiliationGroup.class);
            namedQuery.setParameter("emailDomain", "%" + emaildomain.toUpperCase() + "%");
            return namedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
