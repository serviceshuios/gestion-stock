import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAchat, Achat } from 'app/shared/model/achat.model';
import { AchatService } from './achat.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit';

@Component({
  selector: 'jhi-achat-update',
  templateUrl: './achat-update.component.html'
})
export class AchatUpdateComponent implements OnInit {
  isSaving: boolean;

  produits: IProduit[];
  achatDateDp: any;

  editForm = this.fb.group({
    id: [],
    achatDate: [null, [Validators.required]],
    achatDescription: [null, [Validators.required]],
    achatQuantite: [null, [Validators.required]],
    produits: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected achatService: AchatService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ achat }) => {
      this.updateForm(achat);
    });
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(achat: IAchat) {
    this.editForm.patchValue({
      id: achat.id,
      achatDate: achat.achatDate,
      achatDescription: achat.achatDescription,
      achatQuantite: achat.achatQuantite,
      produits: achat.produits
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const achat = this.createFromForm();
    if (achat.id !== undefined) {
      this.subscribeToSaveResponse(this.achatService.update(achat));
    } else {
      this.subscribeToSaveResponse(this.achatService.create(achat));
    }
  }

  private createFromForm(): IAchat {
    return {
      ...new Achat(),
      id: this.editForm.get(['id']).value,
      achatDate: this.editForm.get(['achatDate']).value,
      achatDescription: this.editForm.get(['achatDescription']).value,
      achatQuantite: this.editForm.get(['achatQuantite']).value,
      produits: this.editForm.get(['produits']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAchat>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
