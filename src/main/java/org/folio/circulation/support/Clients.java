package org.folio.circulation.support;

import java.net.MalformedURLException;

import org.folio.circulation.support.http.client.VertxWebClientOkapiHttpClient;
import org.folio.circulation.support.http.server.WebContext;

import io.vertx.core.http.HttpClient;

public class Clients {
  private final CollectionResourceClient requestsStorageClient;
  private final CollectionResourceClient requestsBatchStorageClient;
  private final CollectionResourceClient cancellationReasonStorageClient;
  private final CollectionResourceClient itemsStorageClient;
  private final CollectionResourceClient holdingsStorageClient;
  private final CollectionResourceClient instancesStorageClient;
  private final CollectionResourceClient usersStorageClient;
  private final CollectionResourceClient addressTypesStorageClient;
  private final CollectionResourceClient loansStorageClient;
  private final CollectionResourceClient locationsStorageClient;
  private final CollectionResourceClient institutionsStorageClient;
  private final CollectionResourceClient campusesStorageClient;
  private final CollectionResourceClient librariesStorageClient;
  private final CollectionResourceClient materialTypesStorageClient;
  private final CollectionResourceClient loanTypesStorageClient;
  private final CollectionResourceClient proxiesForClient;
  private final CollectionResourceClient loanPoliciesStorageClient;
  private final CollectionResourceClient overdueFinesPoliciesPoliciesStorageClient;
  private final CollectionResourceClient lostItemPoliciesStorageClient;
  private final CollectionResourceClient fixedDueDateSchedulesStorageClient;
  private final CirculationRulesClient circulationLoanRulesClient;
  private final CirculationRulesClient circulationOverdueFinesRulesClient;
  private final CirculationRulesClient circulationLostItemRulesClient;
  private final CirculationRulesClient circulationRequestRulesClient;
  private final CirculationRulesClient circulationNoticeRulesClient;
  private final CollectionResourceClient circulationRulesStorageClient;
  private final CollectionResourceClient requestPoliciesStorageClient;
  private final CollectionResourceClient servicePointsStorageClient;
  private final CollectionResourceClient calendarStorageClient;
  private final CollectionResourceClient patronGroupsStorageClient;
  private final CollectionResourceClient patronNoticePolicesStorageClient;
  private final CollectionResourceClient patronNoticeClient;
  private final CollectionResourceClient configurationStorageClient;
  private final CollectionResourceClient scheduledNoticesStorageClient;
  private final CollectionResourceClient accountsStorageClient;
  private final CollectionResourceClient feeFineActionsStorageClient;
  private final CollectionResourceClient anonymizeStorageLoansClient;
  private final CollectionResourceClient patronActionSessionsStorageClient;
  private final CollectionResourceClient patronExpiredSessionsStorageClient;
  private final CollectionResourceClient userManualBlocksStorageClient;

  public static Clients create(WebContext context, HttpClient httpClient) {
    return new Clients(context.createHttpClient(httpClient), context);
  }

  private Clients(VertxWebClientOkapiHttpClient client, WebContext context) {
    try {
      requestsStorageClient = createRequestsStorageClient(client, context);
      requestsBatchStorageClient = createRequestsBatchStorageClient(client, context);
      cancellationReasonStorageClient = createCancellationReasonStorageClient(client, context);
      itemsStorageClient = createItemsStorageClient(client, context);
      holdingsStorageClient = createHoldingsStorageClient(client, context);
      instancesStorageClient = createInstanceStorageClient(client, context);
      usersStorageClient = createUsersStorageClient(client, context);
      addressTypesStorageClient = createAddressTypesStorageClient(client, context);
      loansStorageClient = createLoansStorageClient(client, context);
      overdueFinesPoliciesPoliciesStorageClient = createOverdueFinesPoliciesStorageClient(client, context);
      lostItemPoliciesStorageClient = createLostItemPoliciesStorageClient(client, context);
      locationsStorageClient = createLocationsStorageClient(client, context);
      anonymizeStorageLoansClient = createAnonymizeStorageLoansClient(client, context);
      institutionsStorageClient = createInstitutionsStorageClient(client, context);
      campusesStorageClient = createCampusesStorageClient(client, context);
      librariesStorageClient = createLibrariesStorageClient(client, context);
      materialTypesStorageClient = createMaterialTypesStorageClient(client, context);
      loanTypesStorageClient = createLoanTypesStorageClient(client, context);
      proxiesForClient = createProxyUsersStorageClient(client, context);
      circulationLoanRulesClient = createCirculationLoanRulesClient(client, context);
      circulationRequestRulesClient = createCirculationRequestRulesClient(client, context);
      circulationNoticeRulesClient = createCirculationNoticeRulesClient(client, context);
      circulationOverdueFinesRulesClient = createCirculationOverdueFinesRulesClient(client, context);
      circulationLostItemRulesClient = createCirculationLostItemRulesClient(client, context);
      circulationRulesStorageClient = createCirculationRulesStorageClient(client, context);
      loanPoliciesStorageClient = createLoanPoliciesStorageClient(client, context);
      requestPoliciesStorageClient = createRequestPoliciesStorageClient(client, context);
      fixedDueDateSchedulesStorageClient = createFixedDueDateSchedulesStorageClient(client, context);
      servicePointsStorageClient = createServicePointsStorageClient(client, context);
      patronGroupsStorageClient = createPatronGroupsStorageClient(client, context);
      calendarStorageClient = createCalendarStorageClient(client, context);
      patronNoticePolicesStorageClient = createPatronNoticePolicesStorageClient(client, context);
      patronNoticeClient = createPatronNoticeClient(client, context);
      configurationStorageClient = createConfigurationStorageClient(client, context);
      scheduledNoticesStorageClient = createScheduledNoticesStorageClient(client, context);
      accountsStorageClient = createAccountsStorageClient(client, context);
      feeFineActionsStorageClient = createFeeFineActionsStorageClient(client, context);
      patronActionSessionsStorageClient = createPatronActionSessionsStorageClient(client, context);
      patronExpiredSessionsStorageClient = createPatronExpiredSessionsStorageClient(client, context);
      userManualBlocksStorageClient = createUserManualBlocksStorageClient(client, context);
    }
    catch(MalformedURLException e) {
      throw new InvalidOkapiLocationException(context.getOkapiLocation(), e);
    }
  }

  public CollectionResourceClient requestsStorage() {
    return requestsStorageClient;
  }

  public CollectionResourceClient requestsBatchStorage() {
    return requestsBatchStorageClient;
  }

  public CollectionResourceClient cancellationReasonStorage() {
    return cancellationReasonStorageClient;
  }

  public CollectionResourceClient requestPoliciesStorage() {
    return requestPoliciesStorageClient;
  }

  public CollectionResourceClient itemsStorage() {
    return itemsStorageClient;
  }

  public CollectionResourceClient holdingsStorage() {
    return holdingsStorageClient;
  }

  public CollectionResourceClient instancesStorage() {
    return instancesStorageClient;
  }

  public CollectionResourceClient usersStorage() {
    return usersStorageClient;
  }

  public CollectionResourceClient addressTypesStorage() {
    return addressTypesStorageClient;
  }

  public CollectionResourceClient loansStorage() {
    return loansStorageClient;
  }

  public CollectionResourceClient anonymizeStorageLoansClient() {
    return anonymizeStorageLoansClient;
  }

  public CollectionResourceClient locationsStorage() {
    return locationsStorageClient;
  }

  public CollectionResourceClient institutionsStorage() {
    return institutionsStorageClient;
  }

  public CollectionResourceClient campusesStorage() {
    return campusesStorageClient;
  }

  public CollectionResourceClient librariesStorage() {
    return librariesStorageClient;
  }

  public CollectionResourceClient materialTypesStorage() {
    return materialTypesStorageClient;
  }

  public CollectionResourceClient loanTypesStorage() {
    return loanTypesStorageClient;
  }

  public CollectionResourceClient loanPoliciesStorage() {
    return loanPoliciesStorageClient;
  }

  public CollectionResourceClient overdueFinesPoliciesStorage() {
    return overdueFinesPoliciesPoliciesStorageClient;
  }

  public CollectionResourceClient lostItemPoliciesStorage() {
    return lostItemPoliciesStorageClient;
  }

  public CollectionResourceClient fixedDueDateSchedules() {
    return fixedDueDateSchedulesStorageClient;
  }

  public CollectionResourceClient servicePointsStorage() {
    return servicePointsStorageClient;
  }

  public CollectionResourceClient patronGroupsStorage() {
    return patronGroupsStorageClient;
  }

  public CollectionResourceClient calendarStorageClient() {
    return calendarStorageClient;
  }

  public CollectionResourceClient configurationStorageClient() {
    return configurationStorageClient;
  }

  public CollectionResourceClient userProxies() {
    return proxiesForClient;
  }

  public CirculationRulesClient circulationLoanRules() {
    return circulationLoanRulesClient;
  }

  public CirculationRulesClient circulationOverdueFineRules() {
    return circulationOverdueFinesRulesClient;
  }

  public CirculationRulesClient circulationLostItemRules() {
    return circulationLostItemRulesClient;
  }

  public CirculationRulesClient circulationRequestRules(){
    return circulationRequestRulesClient;
  }

  public CirculationRulesClient circulationNoticeRules(){
    return circulationNoticeRulesClient;
  }

  public CollectionResourceClient circulationRulesStorage() {
    return circulationRulesStorageClient;
  }

  public CollectionResourceClient patronNoticePolicesStorageClient() {
    return patronNoticePolicesStorageClient;
  }

  public CollectionResourceClient patronNoticeClient() {
    return patronNoticeClient;
  }

  public CollectionResourceClient scheduledNoticesStorageClient() {
    return scheduledNoticesStorageClient;
  }

  public CollectionResourceClient accountsStorageClient() {
    return accountsStorageClient;
  }

  public CollectionResourceClient feeFineActionsStorageClient() {
    return feeFineActionsStorageClient;
  }

  public CollectionResourceClient patronActionSessionsStorageClient() {
    return patronActionSessionsStorageClient;
  }

  public CollectionResourceClient patronExpiredSessionsStorageClient() {
    return patronExpiredSessionsStorageClient;
  }

  public CollectionResourceClient userManualBlocksStorageClient() {
    return userManualBlocksStorageClient;
  }

  private static CollectionResourceClient getCollectionResourceClient(
    VertxWebClientOkapiHttpClient client, WebContext context,
    String path)
    throws MalformedURLException {

    return new CollectionResourceClient(client, context.getOkapiBasedUrl(path));
  }

  private static CirculationRulesClient createCirculationLoanRulesClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context,
      "/circulation/rules/loan-policy");
  }

  private static CirculationRulesClient createCirculationOverdueFinesRulesClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context,
      "/circulation/rules/overdue-fine-policy");
  }

  private static CirculationRulesClient createCirculationLostItemRulesClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context,
      "/circulation/rules/lost-item-policy");
  }

  private static CirculationRulesClient createCirculationRequestRulesClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context,
      "/circulation/rules/request-policy");
  }

  private static CirculationRulesClient createCirculationNoticeRulesClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return new CirculationRulesClient(client, context,
      "/circulation/rules/notice-policy");
  }

  private static CollectionResourceClient createRequestsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/request-storage/requests");
  }

  private static CollectionResourceClient createRequestsBatchStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/request-storage-batch/requests");
  }

  private static CollectionResourceClient createCancellationReasonStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/cancellation-reason-storage/cancellation-reasons");
  }

  private static CollectionResourceClient createItemsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/item-storage/items");
  }

  private static CollectionResourceClient createHoldingsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/holdings-storage/holdings");
  }

  private static CollectionResourceClient createInstanceStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/instance-storage/instances");
  }

  private static CollectionResourceClient createUsersStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/users");
  }

  private static CollectionResourceClient createAddressTypesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/addresstypes");
  }

  private static CollectionResourceClient createLoansStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/loan-storage/loans");
  }

  private static CollectionResourceClient createAnonymizeStorageLoansClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/anonymize-storage-loans");
  }

  private static CollectionResourceClient createLocationsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/locations");
  }

  private static CollectionResourceClient createInstitutionsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/location-units/institutions");
  }

  private static CollectionResourceClient createCampusesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/location-units/campuses");
  }

  private static CollectionResourceClient createLibrariesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/location-units/libraries");
  }

  private CollectionResourceClient createProxyUsersStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/proxiesfor");
  }

  private CollectionResourceClient createMaterialTypesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/material-types");
  }

  private CollectionResourceClient createLoanTypesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/loan-types");
  }

  private CollectionResourceClient createLoanPoliciesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/loan-policy-storage/loan-policies");
  }

  private CollectionResourceClient createOverdueFinesPoliciesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
            "/overdue-fines-policies");
  }

  private CollectionResourceClient createLostItemPoliciesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
            "/lost-item-fees-policies");
  }

  private CollectionResourceClient createRequestPoliciesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/request-policy-storage/request-policies");
  }

  private CollectionResourceClient createFixedDueDateSchedulesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/fixed-due-date-schedule-storage/fixed-due-date-schedules");
  }


  private CollectionResourceClient createCirculationRulesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/circulation-rules-storage");
  }

  private CollectionResourceClient createServicePointsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
      throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/service-points");
  }

  private CollectionResourceClient createPatronGroupsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
      throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/groups");
  }

  private CollectionResourceClient createCalendarStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/calendar/periods");
  }

  private CollectionResourceClient createPatronNoticePolicesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/patron-notice-policy-storage/patron-notice-policies");
  }

  private CollectionResourceClient createPatronNoticeClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/patron-notice");
  }

  private CollectionResourceClient createConfigurationStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/configurations/entries");
  }

  private CollectionResourceClient createScheduledNoticesStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/scheduled-notice-storage/scheduled-notices");
  }
  private CollectionResourceClient createAccountsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/accounts");
  }

  private CollectionResourceClient createFeeFineActionsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
      throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/feefineactions");
  }

  private CollectionResourceClient createPatronActionSessionsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/patron-action-session-storage/patron-action-sessions");
  }

  private CollectionResourceClient createPatronExpiredSessionsStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context,
      "/patron-action-session-storage");
  }

  private static CollectionResourceClient createUserManualBlocksStorageClient(
    VertxWebClientOkapiHttpClient client, WebContext context)
    throws MalformedURLException {

    return getCollectionResourceClient(client, context, "/manualblocks");
  }
}
