package edu.harvard.iq.dataverse.authorization.groups.impl.affiliation;

import edu.harvard.iq.dataverse.DataverseSession;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroup;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroupsServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.ip.IpAddress;
import edu.harvard.iq.dataverse.mocks.MocksFactory;
import edu.harvard.iq.dataverse.util.BundleUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BundleUtil.class, FacesContext.class})
public class AffiliationServiceBeanTest {

    private Locale enLocale;
    private Locale frLocale;
    private ResourceBundle bundle;
    private ResourceBundle bundle_fr;

    private Set<IpGroup> ipgroups;
    private HttpServletRequest mockHttpServletRequest;
    private IpGroupsServiceBean mockIpGroupsService;
    private DataverseSession mockDataverseSession;
    private AffiliationServiceBean bean;

    @Mock
    private FacesContext mockFacesContext;

    @Mock
    private ExternalContext mockExternalContext;

    @Before
    public void setUp() {
        mockDataverseSession = mock(DataverseSession.class);
        mockIpGroupsService = mock(IpGroupsServiceBean.class);
        mockHttpServletRequest = mock(HttpServletRequest.class);

        bean = new AffiliationServiceBean();
        bean.session = mockDataverseSession;
        bean.ipGroupsService = mockIpGroupsService;

        enLocale = new Locale("en");
        bundle = ResourceBundle.getBundle("properties/affiliation", enLocale);
        frLocale = new Locale("fr");
        bundle_fr = ResourceBundle.getBundle("properties/affiliation", frLocale);

        IpGroup ipGroup = MocksFactory.makeIpGroup();
        ipgroups = new HashSet<>();
        ipgroups.add(ipGroup);
    }

    @Test
    public void testGetAlias() {
        AffiliationServiceBean bean = new AffiliationServiceBean();
        String alias = bean.getAlias("OTHER");
        assertEquals("", alias);

        PowerMockito.mockStatic(BundleUtil.class);
        when(BundleUtil.getResourceBundle("affiliation", new Locale("en"))).thenReturn(bundle);

        alias = bean.getAlias("York University");
        assertEquals("york", alias);

        alias = bean.getAlias("Non-existent University");
        assertEquals("", alias);
    }

    @Test
    public void testGetAffiliationFromIPAddress() {

        // Mock static methods
        PowerMockito.mockStatic(BundleUtil.class);
        when(BundleUtil.getResourceBundle("affiliation", new Locale("en"))).thenReturn(bundle);

        PowerMockito.mockStatic(FacesContext.class);
        when(FacesContext.getCurrentInstance()).thenReturn(mockFacesContext);
        when(mockFacesContext.getExternalContext()).thenReturn(mockExternalContext);
        when(mockExternalContext.getRequest()).thenReturn(mockHttpServletRequest);

        String affiliation = bean.getAffiliationFromIPAddress();
        assertEquals("", affiliation);

        when(mockHttpServletRequest.getRemoteAddr()).thenReturn("142.150.192.190");
        when(mockIpGroupsService.findAllIncludingIp(IpAddress.valueOf("142.150.192.190"))).thenReturn(ipgroups);

        affiliation = bean.getAffiliationFromIPAddress();
        assertEquals("University of Toronto", affiliation);

        when(mockHttpServletRequest.getRemoteAddr()).thenReturn("999.150.192.190");
        when(mockIpGroupsService.findAllIncludingIp(IpAddress.valueOf("999.150.192.190"))).thenReturn(new HashSet<>());
        affiliation = bean.getAffiliationFromIPAddress();
        assertEquals("", affiliation);
    }

    @Test
    public void testGetLocalizedAffiliation() {
        when(mockDataverseSession.getLocaleCode()).thenReturn("fr");

        PowerMockito.mockStatic(BundleUtil.class);
        when(BundleUtil.getResourceBundle("affiliation", new Locale("en"))).thenReturn(bundle);
        when(BundleUtil.getResourceBundle("affiliation", new Locale("fr"))).thenReturn(bundle_fr);

        String affiliation = "York University";
        String localizedAffiliation = bean.getLocalizedAffiliation(affiliation);
        assertEquals("Université York", localizedAffiliation);

        affiliation = "University of Toronto";
        localizedAffiliation = bean.getLocalizedAffiliation(affiliation);
        assertEquals(affiliation, localizedAffiliation);
    }
}