package org.folio.circulation.resources;

import java.util.concurrent.CompletableFuture;

import org.folio.circulation.infrastructure.storage.ConfigurationRepository;
import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.domain.notice.schedule.ScheduledNotice;
import org.folio.circulation.infrastructure.storage.notices.ScheduledNoticesRepository;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.Result;
import org.folio.circulation.support.RouteRegistration;
import org.folio.circulation.support.http.client.PageLimit;
import org.folio.circulation.support.http.server.NoContentResponse;
import org.folio.circulation.support.http.server.WebContext;

import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class ScheduledNoticeProcessingResource extends Resource {
  private final String rootPath;

  ScheduledNoticeProcessingResource(String rootPath, HttpClient client) {
    super(client);
    this.rootPath = rootPath;
  }

  @Override
  public void register(Router router) {
    RouteRegistration routeRegistration = new RouteRegistration(rootPath, router);

    routeRegistration.create(this::process);
  }

  private void process(RoutingContext routingContext) {
    final WebContext context = new WebContext(routingContext);
    final Clients clients = Clients.create(context, client);

    final ScheduledNoticesRepository scheduledNoticesRepository =
      ScheduledNoticesRepository.using(clients);
    final ConfigurationRepository configurationRepository =
      new ConfigurationRepository(clients);

    configurationRepository.lookupSchedulerNoticesProcessingLimit()
      .thenCompose(r -> r.after(limit -> findNoticesToSend(scheduledNoticesRepository,
        limit)))
      .thenCompose(r -> r.after(notices -> handleNotices(clients, notices)))
      .thenApply(r -> r.toFixedValue(NoContentResponse::noContent))
      .thenAccept(context::writeResultToHttpResponse);
  }

  protected abstract CompletableFuture<Result<MultipleRecords<ScheduledNotice>>> findNoticesToSend(
    ScheduledNoticesRepository scheduledNoticesRepository, PageLimit pageLimit);

  protected abstract CompletableFuture<Result<MultipleRecords<ScheduledNotice>>> handleNotices(
    Clients clients, MultipleRecords<ScheduledNotice> noticesResult);
}
