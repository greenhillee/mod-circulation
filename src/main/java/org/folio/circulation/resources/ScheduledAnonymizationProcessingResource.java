package org.folio.circulation.resources;

import static org.folio.circulation.support.results.AsynchronousResultBindings.safelyInitialise;

import org.folio.circulation.domain.ConfigurationRepository;
import org.folio.circulation.domain.anonymization.LoanAnonymization;
import org.folio.circulation.domain.representations.anonymization.AnonymizeLoansRepresentation;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.RouteRegistration;
import org.folio.circulation.support.http.server.WebContext;
import org.folio.circulation.support.results.CommonFailures;

import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Perform automatic loan anonymization based on tenant settings for loan history.
 * This process is intended to run in short intervals.
 *
 */
public class ScheduledAnonymizationProcessingResource extends Resource {

  public ScheduledAnonymizationProcessingResource(HttpClient client) {
    super(client);
  }

  @Override
  public void register(Router router) {
    new RouteRegistration("/circulation/scheduled-anonymize-processing", router)
    .create(this::scheduledAnonymizeLoans);
  }

  private void scheduledAnonymizeLoans(RoutingContext routingContext) {
    final Clients clients = Clients.create(new WebContext(routingContext), client);

    ConfigurationRepository configurationRepository = new ConfigurationRepository(clients);

    LoanAnonymization loanAnonymization = new LoanAnonymization(clients);

    safelyInitialise(configurationRepository::loanHistoryConfiguration)
      .thenCompose(r -> r.after(config -> loanAnonymization
          .byCurrentTenant(config).anonymizeLoans()))
      .exceptionally(CommonFailures::failedDueToServerError)
      .thenApply(AnonymizeLoansRepresentation::from)
      .thenAccept(result -> result.writeTo(routingContext.response()));
  }
}
