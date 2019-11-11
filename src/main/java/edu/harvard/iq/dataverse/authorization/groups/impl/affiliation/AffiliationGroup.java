package edu.harvard.iq.dataverse.authorization.groups.impl.affiliation;

import edu.harvard.iq.dataverse.authorization.groups.GroupProvider;
import edu.harvard.iq.dataverse.authorization.groups.impl.PersistedGlobalGroup;
import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
import edu.harvard.iq.dataverse.engine.command.DataverseRequest;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import java.text.Normalizer;

@NamedQueries({
        @NamedQuery(name = "AffiliationGroup.findAll",
                query = "SELECT g FROM AffiliationGroup g"),
        @NamedQuery(name = "AffiliationGroup.findByPersistedGroupAlias",
                query = "SELECT g FROM AffiliationGroup g WHERE g.persistedGroupAlias=:persistedGroupAlias"),
        @NamedQuery(name = "AffiliationGroup.findByDisplayName",
                query = "SELECT g from AffiliationGroup g where g.displayName=:displayName"),
        @NamedQuery(name = "AffiliationGroup.findByEmailDomain",
                query = "SELECT g from AffiliationGroup g where UPPER(g.emaildomain) like :emailDomain"),
        @NamedQuery(name = "AffiliationGroup.getCount",
                query = "SELECT COUNT(g) from AffiliationGroup g")
})
@Entity
public class AffiliationGroup extends PersistedGlobalGroup {

    @Transient
    private AffiliationGroupProvider provider;

    public AffiliationGroup() {
    }

    public AffiliationGroup(AffiliationGroupProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean contains(DataverseRequest rq) {
        AuthenticatedUser authenticatedUser = rq.getAuthenticatedUser();
        String userAffiliation = authenticatedUser.getAffiliation();
        String displayName = getDisplayName();
        return StringUtils.isNotBlank(userAffiliation) && userAffiliation.equalsIgnoreCase(displayName);
    }

    @Override
    public boolean isEditable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GroupProvider getGroupProvider() {
        return provider;
    }

    public void setGroupProvider(AffiliationGroupProvider prv) {
        provider = prv;
    }

    public int compare(AffiliationGroup group) {
        String s1 = Normalizer.normalize(this.getDisplayName(), Normalizer.Form.NFD);
        String s2 = Normalizer.normalize(group.getDisplayName(), Normalizer.Form.NFD);
        return s1.compareTo(s2);
    }

    @Override
    public String toString() {
        return "AffiliationGroup{" +
                "provider=" + provider +
                "alias=" + getPersistedGroupAlias() +
                '}';
    }
}
