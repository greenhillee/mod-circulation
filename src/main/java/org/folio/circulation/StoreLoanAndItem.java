package org.folio.circulation;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.folio.circulation.infrastructure.storage.inventory.ItemRepository.noLocationMaterialTypeAndLoanTypeInstance;
import static org.folio.circulation.support.Result.succeeded;
import static org.folio.circulation.support.ResultBinding.mapResult;

import org.folio.circulation.domain.Item;
import org.folio.circulation.domain.Loan;
import org.folio.circulation.infrastructure.storage.loans.LoanRepository;
import org.folio.circulation.resources.context.RenewalContext;
import org.folio.circulation.support.Clients;
import org.folio.circulation.infrastructure.storage.inventory.ItemRepository;
import org.folio.circulation.support.Result;
import org.folio.circulation.support.http.client.Response;

import java.util.concurrent.CompletableFuture;

public class StoreLoanAndItem {
  private final LoanRepository loanRepository;
  private final ItemRepository itemRepository;

  public StoreLoanAndItem(LoanRepository loanRepository, ItemRepository itemRepository) {
    this.loanRepository = loanRepository;
    this.itemRepository = itemRepository;
  }

  public StoreLoanAndItem(Clients clients) {
    this(new LoanRepository(clients), noLocationMaterialTypeAndLoanTypeInstance(clients));
  }

  public CompletableFuture<Result<RenewalContext>> updateLoanAndItemInStorage(
    RenewalContext relatedRecords) {

    return updateLoanAndItemInStorage(relatedRecords.getLoan())
      .thenApply(mapResult(relatedRecords::withLoan));
  }

  public CompletableFuture<Result<Loan>> updateLoanAndItemInStorage(Loan loan) {
    if (loan == null || loan.getItem() == null) {
      return completedFuture(succeeded(null));
    }

    //TODO: What should happen if updating the item fails?
    return updateItem(loan.getItem())
      .thenComposeAsync(response -> loanRepository.updateLoan(loan));
  }

  private CompletableFuture<Result<Response>> updateItem(Item item) {
    if (!item.hasChanged()) {
      return completedFuture(succeeded(null));
    }

    return itemRepository.updateItem(item);
  }
}
