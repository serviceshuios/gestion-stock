import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICategorie } from 'app/shared/model/categorie.model';
import { AccountService } from 'app/core';
import { CategorieService } from './categorie.service';

@Component({
  selector: 'jhi-categorie',
  templateUrl: './categorie.component.html'
})
export class CategorieComponent implements OnInit, OnDestroy {
  categories: ICategorie[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected categorieService: CategorieService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.categorieService
      .query()
      .pipe(
        filter((res: HttpResponse<ICategorie[]>) => res.ok),
        map((res: HttpResponse<ICategorie[]>) => res.body)
      )
      .subscribe(
        (res: ICategorie[]) => {
          this.categories = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICategorie) {
    return item.id;
  }

  registerChangeInCategories() {
    this.eventSubscriber = this.eventManager.subscribe('categorieListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
