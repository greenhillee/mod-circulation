package org.folio.circulation.infrastructure.storage;

import static java.util.function.Function.identity;
import static org.folio.circulation.domain.OpeningDay.createClosedDay;
import static org.folio.circulation.domain.OpeningDay.fromJsonByDefaultKey;
import static org.folio.circulation.support.ValidationErrorFailure.failedValidation;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.folio.circulation.AdjacentOpeningDays;
import org.folio.circulation.domain.MultipleRecords;
import org.folio.circulation.domain.OpeningDay;
import org.folio.circulation.support.Clients;
import org.folio.circulation.support.CollectionResourceClient;
import org.folio.circulation.support.FetchSingleRecord;
import org.folio.circulation.support.Result;
import org.folio.circulation.support.http.client.Response;
import org.folio.circulation.support.http.server.ValidationError;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class CalendarRepository {

  private static final String OPENING_PERIODS = "openingPeriods";
  private static final String OPENING_DAYS = "openingDays";
  private static final String PATH_PARAM_WITH_QUERY = "%s/calculateopening?requestedDate=%s";

  private final CollectionResourceClient calendarClient;
  private final ConfigurationRepository configurationRepository;

  public CalendarRepository(Clients clients) {
    this.calendarClient = clients.calendarStorageClient();
    this.configurationRepository = new ConfigurationRepository(clients);
  }

  public CompletableFuture<Result<AdjacentOpeningDays>> lookupOpeningDays(LocalDate requestedDate, String servicePointId) {
    String path = String.format(PATH_PARAM_WITH_QUERY, servicePointId, requestedDate);

    //TODO: Validation error should have parameters
    return FetchSingleRecord.<AdjacentOpeningDays>forRecord(OPENING_PERIODS)
      .using(calendarClient)
      .mapTo(this::convertToOpeningDays)
      .whenNotFound(failedValidation(
        new ValidationError("Calendar open periods are not found", Collections.emptyMap())))
      .fetch(path);
  }

  public CompletableFuture<Result<Collection<OpeningDay>>> fetchOpeningDaysBetweenDates(
    String servicePointId, DateTime startDate, DateTime endDate, boolean includeClosedDays) {

    String params = String.format(
      "servicePointId=%s&startDate=%s&endDate=%s&includeClosedDays=%s&limit=%d",
      servicePointId, startDate.toLocalDate(), endDate.toLocalDate().plusDays(1),
      includeClosedDays, 10000);

    return calendarClient.getManyWithRawQueryStringParameters(params)
      .thenCombineAsync(configurationRepository.findTimeZoneConfiguration(),
        Result.combined(this::getOpeningDaysFromOpeningPeriods));
  }

  private Result<Collection<OpeningDay>> getOpeningDaysFromOpeningPeriods(
    Response periodsResponse, DateTimeZone zone) {

    return MultipleRecords.from(periodsResponse, openingPeriod ->
        getOpeningDayFromOpeningPeriod(openingPeriod, zone), OPENING_PERIODS)
      .next(r -> Result.succeeded(r.toKeys(identity())));
  }

  private OpeningDay getOpeningDayFromOpeningPeriod(
    JsonObject openingPeriod, DateTimeZone zone) {

    return OpeningDay.fromOpeningPeriodJson(openingPeriod, zone);
  }

  private AdjacentOpeningDays convertToOpeningDays(JsonObject jsonObject) {
    if (jsonObject.isEmpty()) {
      return buildClosedOpeningDays();
    }
    JsonArray openingDaysJson = jsonObject.getJsonArray(OPENING_DAYS);
    if (openingDaysJson.isEmpty()) {
      return buildClosedOpeningDays();
    }
    OpeningDay previousDate = fromJsonByDefaultKey(openingDaysJson.getJsonObject(0));
    OpeningDay requestedDate = fromJsonByDefaultKey(openingDaysJson.getJsonObject(1));
    OpeningDay nextDate = fromJsonByDefaultKey(openingDaysJson.getJsonObject(2));

    return new AdjacentOpeningDays(previousDate, requestedDate, nextDate);
  }

  private AdjacentOpeningDays buildClosedOpeningDays() {
    OpeningDay closedDay = createClosedDay();
    return new AdjacentOpeningDays(closedDay, closedDay, closedDay);
  }
}
