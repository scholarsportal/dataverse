package edu.harvard.iq.dataverse.authorization.groups.impl.affiliation;

import edu.harvard.iq.dataverse.authorization.RoleAssignee;
import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
import edu.harvard.iq.dataverse.mocks.MocksFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AffiliationGroupProviderTest {

    private AffiliationGroupServiceBean mockAffiliationGroupService;

    @Before
    public void setUp() {
        mockAffiliationGroupService = mock(AffiliationGroupServiceBean.class);
    }

    @Test
    public void testGetAffiliationGroups() {
        RoleAssignee ra = null;
        AffiliationGroupProvider provider = new AffiliationGroupProvider(mockAffiliationGroupService);
        Set<AffiliationGroup> set = provider.groupsFor(ra);
        assertEquals(Collections.emptySet(),set);

        AuthenticatedUser authenticatedUser = MocksFactory.makeAuthenticatedUser("jeff", "crowe");
        authenticatedUser.setAffiliation("Non-existent");
        Set<AffiliationGroup> affiliationGroups = provider.groupsFor(authenticatedUser);

        when(mockAffiliationGroupService.getByDisplayName(authenticatedUser.getAffiliation())).thenReturn(null);
        assertEquals(Collections.emptySet(),set);

        authenticatedUser.setAffiliation("University of Toronto");
        AffiliationGroup group = MocksFactory.makeAffiliationGroup();
        when(mockAffiliationGroupService.getByDisplayName(authenticatedUser.getAffiliation())).thenReturn(group);
        affiliationGroups = provider.groupsFor(authenticatedUser);
        assertEquals(Collections.emptySet(), affiliationGroups);

        long nowInMilliseconds = new Date().getTime();
        Timestamp emailConfirmed = new Timestamp(nowInMilliseconds);
        authenticatedUser.setEmailConfirmed(emailConfirmed);
        affiliationGroups = provider.groupsFor(authenticatedUser);

        Set<AffiliationGroup> set1 = new HashSet<>(Arrays.asList(group));
        assertEquals(set1, affiliationGroups);
        AffiliationGroup next = affiliationGroups.iterator().next();
        assertNotNull(next.getGroupProvider());
        next = affiliationGroups.iterator().next();
        assertNotNull(next.getGroupProvider());
    }
}
