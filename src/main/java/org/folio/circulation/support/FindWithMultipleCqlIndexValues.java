package org.folio.circulation.support;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.support.http.client.CqlQuery;

public interface FindWithMultipleCqlIndexValues<T> {
    CompletableFuture<Result<MultipleRecords<T>>> findByIds(
      Collection<String> ids);

    CompletableFuture<Result<MultipleRecords<T>>> findByIndexName(
      Collection<String> ids, String indexName);

    CompletableFuture<Result<MultipleRecords<T>>> findByIdIndexAndQuery(
      Collection<String> ids, String indexName, Result<CqlQuery> andQuery);
}
