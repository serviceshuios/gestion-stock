import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProduit } from 'app/shared/model/produit.model';
import { AccountService } from 'app/core';
import { ProduitService } from './produit.service';

@Component({
  selector: 'jhi-produit',
  templateUrl: './produit.component.html'
})
export class ProduitComponent implements OnInit, OnDestroy {
  produits: IProduit[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected produitService: ProduitService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.produitService
      .query()
      .pipe(
        filter((res: HttpResponse<IProduit[]>) => res.ok),
        map((res: HttpResponse<IProduit[]>) => res.body)
      )
      .subscribe(
        (res: IProduit[]) => {
          this.produits = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProduits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProduit) {
    return item.id;
  }

  registerChangeInProduits() {
    this.eventSubscriber = this.eventManager.subscribe('produitListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
