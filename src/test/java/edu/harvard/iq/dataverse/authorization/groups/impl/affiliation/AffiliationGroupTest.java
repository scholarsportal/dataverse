package edu.harvard.iq.dataverse.authorization.groups.impl.affiliation;

import org.junit.Assert;
import org.junit.Test;

public class AffiliationGroupTest {

    @Test
    public void testSortByRearrangingAccentsBasedOnProperCanonicalOrder() {
        AffiliationGroup group1 = new AffiliationGroup();
        AffiliationGroup group2 = new AffiliationGroup();
        group1.setDisplayName("École de technologie supérieure");
        group2.setDisplayName("École nationale d’administration publique");
        Assert.assertEquals(-10, group1.compare(group2));
        group1.setDisplayName("Université du Québec à Trois-Rivières");
        group2.setDisplayName("Université du Québec en Abitibi-Témiscamingue");
        Assert.assertEquals(-4, group1.compare(group2));
        Assert.assertEquals(4, group2.compare(group1));
    }
}
