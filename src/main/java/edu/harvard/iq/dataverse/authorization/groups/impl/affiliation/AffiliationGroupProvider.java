package edu.harvard.iq.dataverse.authorization.groups.impl.affiliation;

import edu.harvard.iq.dataverse.DvObject;
import edu.harvard.iq.dataverse.authorization.RoleAssignee;
import edu.harvard.iq.dataverse.authorization.groups.GroupProvider;
import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
import edu.harvard.iq.dataverse.engine.command.DataverseRequest;

import java.util.*;


public class AffiliationGroupProvider implements GroupProvider<AffiliationGroup> {

    private AffiliationGroupServiceBean affiliationGroupService;

    public AffiliationGroupProvider(AffiliationGroupServiceBean affiliationGroupService) {
        this.affiliationGroupService = affiliationGroupService;
    }

    @Override
    public String getGroupProviderAlias() {
        return "aff";
    }

    @Override
    public String getGroupProviderInfo() {
        return "Groups users by their affiliation";
    }

    @Override
    public Set<AffiliationGroup> groupsFor(RoleAssignee ra, DvObject dvo) {
        return groupsFor(ra);
    }

    @Override
    public Set<AffiliationGroup> groupsFor(DataverseRequest req, DvObject dvo) {
        return groupsFor(req);
    }

    @Override
    public Set<AffiliationGroup> groupsFor(RoleAssignee ra) {
        if (ra instanceof AuthenticatedUser) {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) ra;
            return getAffiliationGroups(authenticatedUser);
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public Set<AffiliationGroup> groupsFor(DataverseRequest req) {
        AuthenticatedUser authenticatedUser = req.getAuthenticatedUser();
        return getAffiliationGroups(authenticatedUser);
    }

    @Override
    public AffiliationGroup get(String groupAlias) {
        return setProvider(affiliationGroupService.getByGroupName(groupAlias));
    }

    public AffiliationGroup get(Long id) {

        return setProvider(affiliationGroupService.get(id));
    }

    @Override
    public Set<AffiliationGroup> findGlobalGroups() {
        return updateProvider(new HashSet<>(affiliationGroupService.findAll()));
    }

    public void store(AffiliationGroup grp) {
        affiliationGroupService.store(grp);
    }

    public void deleteGroup(AffiliationGroup grp) {
        affiliationGroupService.deleteGroup(grp);
    }

    public boolean isAssigned(AffiliationGroup grp) {
        return affiliationGroupService.isAssigned(grp);
    }

    public List<AffiliationGroup> getAffiliationGroups() {
        return affiliationGroupService.findAll();
    }

    public Long getAffiliationGroupsCount() {
        return affiliationGroupService.getAffiliationGroupsCount();
    }

    private Set<AffiliationGroup> updateProvider(Set<AffiliationGroup> groups) {
        groups.forEach(g -> g.setGroupProvider(this));
        return groups;
    }

    private AffiliationGroup setProvider(AffiliationGroup g) {
        if (g != null) {
            g.setGroupProvider(this);
        }
        return g;
    }

    public AffiliationGroup findByAlias(String alias) {
        return affiliationGroupService.getByGroupName(alias);
    }

    private Set<AffiliationGroup> getAffiliationGroups(AuthenticatedUser authenticatedUser) {
        if (authenticatedUser != null && authenticatedUser.getEmailConfirmed() != null) {
            AffiliationGroup group = affiliationGroupService.getByDisplayName(authenticatedUser.getAffiliation());
            if (group != null) {
                Set<AffiliationGroup> set = new HashSet<>(Arrays.asList(group));
                return updateProvider(set);
            }
        }
        return Collections.emptySet();
    }
}
