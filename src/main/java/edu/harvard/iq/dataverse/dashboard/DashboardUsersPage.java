package edu.harvard.iq.dataverse.dashboard;

import edu.harvard.iq.dataverse.*;
import edu.harvard.iq.dataverse.api.Admin;
import edu.harvard.iq.dataverse.authorization.AuthenticationProvider;
import edu.harvard.iq.dataverse.authorization.AuthenticationServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.GroupServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.impl.affiliation.AffiliationGroup;
import edu.harvard.iq.dataverse.authorization.groups.impl.affiliation.AffiliationGroupProvider;
import edu.harvard.iq.dataverse.authorization.groups.impl.affiliation.AffiliationGroupServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.impl.affiliation.AffiliationServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroup;
import edu.harvard.iq.dataverse.authorization.groups.impl.ipaddress.IpGroupProvider;
import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
import edu.harvard.iq.dataverse.engine.command.impl.GrantSuperuserStatusCommand;
import edu.harvard.iq.dataverse.engine.command.impl.RevokeAllRolesCommand;
import edu.harvard.iq.dataverse.engine.command.impl.RevokeSuperuserStatusCommand;
import edu.harvard.iq.dataverse.mydata.Pager;
import edu.harvard.iq.dataverse.userdata.UserListMaker;
import edu.harvard.iq.dataverse.userdata.UserListResult;
import edu.harvard.iq.dataverse.util.BundleUtil;
import edu.harvard.iq.dataverse.util.JsfHelper;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ViewScoped
@Named("DashboardUsersPage")
public class DashboardUsersPage implements java.io.Serializable {
  
    @EJB
    AuthenticationServiceBean authenticationService;
    @EJB
    UserServiceBean userService;
    @Inject
    DataverseSession session;
    @Inject
    PermissionsWrapper permissionsWrapper;
    @EJB
    EjbDataverseEngine commandEngine;
    @Inject
    DataverseRequestServiceBean dvRequestService;
    @Inject
    GroupServiceBean groupSvc;


    @Inject
    AffiliationServiceBean affiliationServiceBean;
    @Inject
    AffiliationGroupServiceBean affiliationGroupServiceBean;

    private String alias = "";
    private String description = "";
    private String displayname = "";
    private String emaildomain = "";
    private UIInput emaildomainField;

    public enum PageMode {
        CREATE, EDIT
    }

    public PageMode getPageMode() {
        return pageMode;
    }

    public void setPageMode(PageMode pageMode) {
        this.pageMode = pageMode;
    }

    private PageMode pageMode =  PageMode.CREATE;

    private static final Logger logger = Logger.getLogger(DashboardUsersPage.class.getCanonicalName());

    private AuthenticatedUser authUser = null;
    private Integer selectedPage = 1;
    private UserListMaker userListMaker = null;

    private Pager pager;
    private List<AuthenticatedUser> userList;
    private List<AffiliationGroup> affiliationGroups = new ArrayList<AffiliationGroup>();

    private String searchTerm;
    private AffiliationGroupProvider affGroupProvider;
    private IpGroupProvider ipGroupProvider;

    @PostConstruct
    public void setup() {
        affGroupProvider = groupSvc.getAffiliationGroupProvider();
        ipGroupProvider = groupSvc.getIpGroupProvider();
    }

    public String init() {

        if ((session.getUser() != null) && (session.getUser().isAuthenticated()) && (session.getUser().isSuperuser())) {
           authUser = (AuthenticatedUser) session.getUser();
            userListMaker = new UserListMaker(userService);
            runUserSearch();
        } else {
            return permissionsWrapper.notAuthorized();
            // redirect to login OR give some type ‘you must be logged in message'
        }

        return null;
    }
    
    public boolean runUserSearchWithPage(Integer pageNumber){
        System.err.println("runUserSearchWithPage");
        setSelectedPage(pageNumber);
        runUserSearch();
        return true;
    }
    
    public boolean runUserSearch(){

        logger.fine("Run the search!");


        /**
         * (1) Determine the number of users returned by the count        
         */
        UserListResult userListResult = userListMaker.runUserSearch(searchTerm, UserListMaker.ITEMS_PER_PAGE, getSelectedPage(), null);
        if (userListResult==null){
            try {
                throw new Exception("userListResult should not be null!");
            } catch (Exception ex) {
                Logger.getLogger(DashboardUsersPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setSelectedPage(userListResult.getSelectedPageNumber());        

        this.userList = userListResult.getUserList();
        this.pager = userListResult.getPager();
        
        return true;
        
    }


    
    public String getListUsersAPIPath() {
        //return "ok";
        return Admin.listUsersFullAPIPath;
    }

    /** 
     * Number of total users
     * @return 
     */
    public String getUserCount() {

        return NumberFormat.getInstance().format(userService.getTotalUserCount());
    }

    /** 
     * Number of total Superusers
     * @return 
     */
    public Long getSuperUserCount() {
        
        return userService.getSuperUserCount();
    }

    public List<AuthenticatedUser> getUserList() {
        return this.userList;
    }

    public List<AffiliationGroup> getAffiliationGroups() {
        affiliationGroups = affGroupProvider.getAffiliationGroups();
        affiliationGroups.sort(AffiliationGroup::compare);
        return affiliationGroups;
    }

    public void setAffiliationGroups(List<AffiliationGroup> affiliationGroups) {
        this.affiliationGroups = affiliationGroups;
    }

    public List<String> getAffiliationList() {
        ResourceBundle bundle = BundleUtil.getResourceBundle("affiliation", new Locale("en"));
        List<String> values = affiliationServiceBean.getValues(bundle);
        if (affiliationGroups.isEmpty()) {
            affiliationGroups = affGroupProvider.getAffiliationGroups();
        }
        List<String> existing = affiliationGroups.stream().map(a -> a.getDisplayName()).collect(Collectors.toList());
        values.removeAll(existing);
        values.sort((String o1, String o2) -> {
            o1 = Normalizer.normalize(o1, Normalizer.Form.NFD);
            o2 = Normalizer.normalize(o2, Normalizer.Form.NFD);
            return o1.compareTo(o2);
        });
        return values;
    }
    /**
     * Pager for when user list exceeds the number of display rows
     * (default: UserListMaker.ITEMS_PER_PAGE)
     * 
     * @return 
     */
    public Pager getPager() {
        return this.pager;
    }

    public void setSelectedPage(Integer pgNum){
        if ((pgNum == null)||(pgNum < 1)){
            this.selectedPage = 1;
        }
        selectedPage = pgNum;
    }

    public Integer getSelectedPage(){
        if ((selectedPage == null)||(selectedPage < 1)){
            setSelectedPage(null);            
        }
        return selectedPage;
    }
    
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
    /* 
       Methods for toggling the supeuser status of a selected user. 
       Our normal two step approach is used: first showing the "are you sure?" 
       popup, then finalizing the toggled value. 
    */

    AuthenticatedUser selectedUserDetached = null; // Note: This is NOT the persisted object!!!!  Don't try to save it, etc.
    AuthenticatedUser selectedUserPersistent = null;  // This is called on the fly and updated
    AffiliationGroup selectedAffGroupDetached = null;

    public void setSelectedUserDetached(AuthenticatedUser user) {
        this.selectedUserDetached = user;
    }
    
    public AuthenticatedUser getSelectedUserDetached() {
        return this.selectedUserDetached;
    }
    
    
    public void setUserToToggleSuperuserStatus(AuthenticatedUser user) {
        selectedUserDetached = user; 
    }

    public void setGroupForDeletion(AffiliationGroup group) {
        selectedAffGroupDetached = group;
        selectedAffGroupDetached.setGroupProvider(affGroupProvider);
    }

    public boolean isAssigned(AffiliationGroup group) {
        group.setGroupProvider(affGroupProvider);
        return affGroupProvider.isAssigned(group);
    }

    public void setGroupForEdit(AffiliationGroup group) {
        setPageMode(PageMode.EDIT);
        selectedAffGroupDetached = group;
        selectedAffGroupDetached.setGroupProvider(affGroupProvider);
        AffiliationGroup affiliationGroup = affGroupProvider.get(group.getId());
        alias = affiliationGroup.getPersistedGroupAlias();
        description = affiliationGroup.getDescription();
        displayname = affiliationGroup.getDisplayName();
        emaildomain = affiliationGroup.getEmaildomain();
        setDisplayname(displayname);
    }


    public void saveSuperuserStatus() {

        // Retrieve the persistent version for saving to db
        logger.fine("Get persisent AuthenticatedUser for id: " + selectedUserDetached.getId());
        selectedUserPersistent = userService.find(selectedUserDetached.getId());

        if (selectedUserPersistent != null) {
            logger.fine("Toggling user's " + selectedUserDetached.getIdentifier() + " superuser status; (current status: " + selectedUserDetached.isSuperuser() + ")");
            logger.fine("Attempting to save user " + selectedUserDetached.getIdentifier());

            logger.fine("selectedUserPersistent info: " + selectedUserPersistent.getId() + " set to: " + selectedUserDetached.isSuperuser());
            selectedUserPersistent.setSuperuser(selectedUserDetached.isSuperuser());

            // Using the new commands for granting and revoking the superuser status: 
            try {
                if (!selectedUserPersistent.isSuperuser()) {
                    // We are revoking the status:
                    commandEngine.submit(new RevokeSuperuserStatusCommand(selectedUserPersistent, dvRequestService.getDataverseRequest()));
                } else {
                    // granting the status:
                    commandEngine.submit(new GrantSuperuserStatusCommand(selectedUserPersistent, dvRequestService.getDataverseRequest()));
                }
            } catch (Exception ex) {
                logger.warning("Failed to permanently toggle the superuser status for user " + selectedUserDetached.getIdentifier() + ": " + ex.getMessage());
            }
        } else {
            logger.warning("selectedUserPersistent is null.  AuthenticatedUser not found for id: " + selectedUserDetached.getId());
        }

    }
    
    public void cancelSuperuserStatusChange(){
        selectedUserDetached.setSuperuser(!selectedUserDetached.isSuperuser());        
        selectedUserPersistent = null;
    }
    
    // Methods for the removeAllRoles for a user : 
    
    public void removeUserRoles() {
        logger.fine("Get persisent AuthenticatedUser for id: " + selectedUserDetached.getId());
        selectedUserPersistent = userService.find(selectedUserDetached.getId());
        
        selectedUserDetached.setRoles(null); // for display
        try {
            commandEngine.submit(new RevokeAllRolesCommand(selectedUserPersistent, dvRequestService.getDataverseRequest()));
        } catch (Exception ex) {
            // error message to show on the page:
            JsfHelper.addErrorMessage(BundleUtil.getStringFromBundle("dashboard.list_users.removeAll.message.failure", Arrays.asList(selectedUserPersistent.getUserIdentifier())));
            return;
        }
        // success message: 
        JsfHelper.addSuccessMessage(BundleUtil.getStringFromBundle("dashboard.list_users.removeAll.message.success", Arrays.asList(selectedUserPersistent.getUserIdentifier()))); 
    }

    public void deleteAffiliationGroup() {
        try {
            logger.fine("deleteAffiliationGroup: affiliationGroup id: " + selectedAffGroupDetached.getId());
            affGroupProvider.deleteGroup(selectedAffGroupDetached);
        } catch (Exception ex) {
            JsfHelper.addErrorMessage(BundleUtil.getStringFromBundle("dashboard.card.groups.tbl_header.group.delete.message.failure", Arrays.asList(selectedAffGroupDetached.getDisplayName())));
            return;
        }
        JsfHelper.addSuccessMessage(BundleUtil.getStringFromBundle("dashboard.card.groups.tbl_header.group.delete.message.success", Arrays.asList(selectedAffGroupDetached.getDisplayName())));
    }

    public void save() {
        AffiliationGroup group = new AffiliationGroup();
        group.setPersistedGroupAlias(alias);
        group.setDescription(description);
        group.setDisplayName(displayname);
        if (StringUtils.isNotBlank(emaildomain)) {
            emaildomain = StringUtils.stripStart(emaildomain.trim(), "@");
            group.setEmaildomain(emaildomain);
        }
        affGroupProvider.store(group);
    }

    public void edit() {
        pageMode = PageMode.EDIT;
    }

    public String getConfirmRemoveRolesMessage() {
        if (selectedUserDetached != null) {
            return BundleUtil.getStringFromBundle("dashboard.list_users.tbl_header.roles.removeAll.confirmationText", Arrays.asList(selectedUserDetached.getUserIdentifier()));
        } 
        return BundleUtil.getStringFromBundle("dashboard.list_users.tbl_header.roles.removeAll.confirmationText");
    }

    public String getConfirmRemoveAffiliationGroupMessage() {
        if (selectedAffGroupDetached != null) {
            return BundleUtil.getStringFromBundle("dashboard.card.groups.tbl_header.group.delete.confirmationText", Arrays.asList(selectedAffGroupDetached.getDisplayName()));
        }
        return BundleUtil.getStringFromBundle("dashboard.card.groups.tbl_header.group.delete.confirmationText");
    }

    public String getAuthProviderFriendlyName(String authProviderId){
        
        return AuthenticationProvider.getFriendlyName(authProviderId);
    }

    public Long getAffiliationsCount() {
        return affGroupProvider.getAffiliationGroupsCount();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void validateAlias(FacesContext context, UIComponent component, Object value) {
        Object oldValue = ((UIInput) component).getValue();
        if (oldValue == null || value.toString().equalsIgnoreCase(oldValue.toString()) ) {
            return;
        }
        String alias = (String) value;
        AffiliationGroup affiliationGroup = affGroupProvider.findByAlias(alias);
        if (affiliationGroup != null && affiliationGroup.getPersistedGroupAlias().toLowerCase().equalsIgnoreCase(alias.toLowerCase())) {
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Affiliation Group.", null);
            context.addMessage(component.getClientId(context), message);
        } else {
            IpGroup ipGroup = ipGroupProvider.get(alias);
            if (ipGroup != null && ipGroup.getPersistedGroupAlias().toLowerCase().equalsIgnoreCase(alias.toLowerCase())) {
                ((UIInput) component).setValid(false);
                FacesMessage message = new FacesMessage("Aliases must be unique across Affiliation Groups & IP Groups.");
                context.addMessage(component.getClientId(context), message);
            }
        }
    }

    public void validateEmailDomain(FacesContext context, UIComponent component, Object value) {
        Object oldValue = ((UIInput) component).getValue();
        if (oldValue == null || value.toString().equalsIgnoreCase(oldValue.toString()) ) {
            return;
        }
        String emaildomain = ((String) value).trim();
        emaildomain = StringUtils.stripStart(emaildomain, "@");
        if (!isValidEmailDomain(emaildomain)) {
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Non-affiliated email.", null);
            context.addMessage(component.getClientId(context), message);
        }
        if (emailDomainExists(emaildomain)) {
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email domain exists.", null);
            context.addMessage(component.getClientId(context), message);
        }
    }

    private boolean isValidEmailDomain(String emailStr) {
        Pattern VALID_EMAIL_DOMAIN_REGEX = Pattern.compile("^[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_DOMAIN_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private boolean emailDomainExists(String emaildomain) {
        AffiliationGroup emailDomain = affiliationGroupServiceBean.getByEmailDomain(emaildomain);
        return (emailDomain != null);
    }

    public void reset() {
        alias = "";
        displayname = "";
        description = "";
        emaildomain = "";
        setPageMode(PageMode.CREATE);
    }

    public String getEmaildomain() {
        return emaildomain;
    }

    public void setEmaildomain(String emaildomain) {
        this.emaildomain = emaildomain;
    }

    public UIInput getEmaildomainField() {
        return emaildomainField;
    }

    public void setEmaildomainField(UIInput emaildomainField) {
        this.emaildomainField = emaildomainField;
    }
}