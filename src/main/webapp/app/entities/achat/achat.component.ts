import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAchat } from 'app/shared/model/achat.model';
import { AccountService } from 'app/core';
import { AchatService } from './achat.service';

@Component({
  selector: 'jhi-achat',
  templateUrl: './achat.component.html'
})
export class AchatComponent implements OnInit, OnDestroy {
  achats: IAchat[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected achatService: AchatService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.achatService
      .query()
      .pipe(
        filter((res: HttpResponse<IAchat[]>) => res.ok),
        map((res: HttpResponse<IAchat[]>) => res.body)
      )
      .subscribe(
        (res: IAchat[]) => {
          this.achats = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAchats();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAchat) {
    return item.id;
  }

  registerChangeInAchats() {
    this.eventSubscriber = this.eventManager.subscribe('achatListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
