package org.folio.circulation.domain;

import static org.folio.circulation.support.JsonPropertyFetcher.getBooleanProperty;
import static org.folio.circulation.support.JsonPropertyFetcher.getProperty;

import io.vertx.core.json.JsonObject;

public class AutomatedPatronBlock {
  private final String patronBlockConditionId;
  private final boolean blockBorrowing;
  private final boolean blockRenewal;
  private final boolean blockRequest;
  private final String message;

  public AutomatedPatronBlock(String patronBlockConditionId, boolean blockBorrowing,
    boolean blockRenewal, boolean blockRequest, String message) {

    this.patronBlockConditionId = patronBlockConditionId;
    this.blockBorrowing = blockBorrowing;
    this.blockRenewal = blockRenewal;
    this.blockRequest = blockRequest;
    this.message = message;
  }

  public static AutomatedPatronBlock from(JsonObject representation) {
    return new AutomatedPatronBlock(
      getProperty(representation, "patronBlockConditionId"),
      getBooleanProperty(representation, "blockBorrowing"),
      getBooleanProperty(representation, "blockRenewal"),
      getBooleanProperty(representation, "blockRequest"),
      getProperty(representation, "message")
    );
  }

  public String getPatronBlockConditionId() {
    return patronBlockConditionId;
  }

  public boolean isBlockBorrowing() {
    return blockBorrowing;
  }

  public boolean isBlockRenewal() {
    return blockRenewal;
  }

  public boolean isBlockRequest() {
    return blockRequest;
  }

  public String getMessage() {
    return message;
  }
}
