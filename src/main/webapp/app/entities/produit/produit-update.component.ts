import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduit, Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie';
import { IAchat } from 'app/shared/model/achat.model';
import { AchatService } from 'app/entities/achat';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html'
})
export class ProduitUpdateComponent implements OnInit {
  isSaving: boolean;

  categories: ICategorie[];

  achats: IAchat[];

  editForm = this.fb.group({
    id: [],
    produitNom: [null, [Validators.required]],
    produitDescription: [null, [Validators.required]],
    produitPrix: [null, [Validators.required]],
    produitStock: [null, [Validators.required]],
    owner: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected produitService: ProduitService,
    protected categorieService: CategorieService,
    protected achatService: AchatService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);
    });
    this.categorieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategorie[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategorie[]>) => response.body)
      )
      .subscribe((res: ICategorie[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.achatService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAchat[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAchat[]>) => response.body)
      )
      .subscribe((res: IAchat[]) => (this.achats = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(produit: IProduit) {
    this.editForm.patchValue({
      id: produit.id,
      produitNom: produit.produitNom,
      produitDescription: produit.produitDescription,
      produitPrix: produit.produitPrix,
      produitStock: produit.produitStock,
      owner: produit.owner
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id']).value,
      produitNom: this.editForm.get(['produitNom']).value,
      produitDescription: this.editForm.get(['produitDescription']).value,
      produitPrix: this.editForm.get(['produitPrix']).value,
      produitStock: this.editForm.get(['produitStock']).value,
      owner: this.editForm.get(['owner']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>) {
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

  trackCategorieById(index: number, item: ICategorie) {
    return item.id;
  }

  trackAchatById(index: number, item: IAchat) {
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
