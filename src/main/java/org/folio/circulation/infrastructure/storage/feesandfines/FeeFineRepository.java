package org.folio.circulation.infrastructure.storage.feesandfines;

import static org.folio.circulation.support.http.ResponseMapping.forwardOnFailure;
import static org.folio.circulation.support.http.ResponseMapping.mapUsingJson;
import static org.folio.circulation.support.http.client.PageLimit.limit;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.folio.circulation.domain.FeeFine;
import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.CollectionResourceClient;
import org.folio.circulation.support.Result;
import org.folio.circulation.support.http.client.CqlQuery;
import org.folio.circulation.support.http.client.Response;
import org.folio.circulation.support.http.client.ResponseInterpreter;

public class FeeFineRepository {
  private final CollectionResourceClient feeFineStorageClient;

  public FeeFineRepository(Clients clients) {
    feeFineStorageClient = clients.feeFineStorageClient();
  }

  public CompletableFuture<Result<FeeFine>> getFeeFine(String type, boolean automatic) {
    return getFeeFines(Collections.singleton(type), automatic)
      .thenApply(r -> r.map(col -> col.stream().findFirst().orElse(null)));
  }

  private CompletableFuture<Result<Collection<FeeFine>>> getFeeFines(
    Collection<String> types, boolean automatic) {

    final Result<CqlQuery> typeQuery = CqlQuery.exactMatchAny("feeFineType", types);
    final Result<CqlQuery> automaticQuery = CqlQuery.exactMatch("automatic", Boolean.toString(automatic));

    return typeQuery.combine(automaticQuery, CqlQuery::and)
      .after(q -> feeFineStorageClient.getMany(q, limit(types.size())))
      .thenApply(r -> r.next(this::mapResponseToFeeFines))
      .thenApply(r -> r.map(MultipleRecords::getRecords));
  }

  public CompletableFuture<Result<Collection<FeeFine>>> getAutomaticFeeFines(Collection<String> types) {
    return getFeeFines(types, true);
  }

  private Result<MultipleRecords<FeeFine>> mapResponseToFeeFines(Response response) {
    return MultipleRecords.from(response, FeeFine::from, "feefines");
  }

  public CompletableFuture<Result<FeeFine>> create(FeeFine feeFine) {
    final ResponseInterpreter<FeeFine> interpreter = new ResponseInterpreter<FeeFine>()
      .flatMapOn(201, mapUsingJson(FeeFine::from))
      .otherwise(forwardOnFailure());

    return feeFineStorageClient.post(feeFine.toJson())
      .thenApply(interpreter::flatMap);
  }
}
