import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICategorie, Categorie } from 'app/shared/model/categorie.model';
import { CategorieService } from './categorie.service';

@Component({
  selector: 'jhi-categorie-update',
  templateUrl: './categorie-update.component.html'
})
export class CategorieUpdateComponent implements OnInit {
  isSaving: boolean;
  dateCreationDp: any;

  editForm = this.fb.group({
    id: [],
    categorieName: [null, [Validators.required]],
    categorieDescription: [null, [Validators.required]],
    dateCreation: [null, [Validators.required]]
  });

  constructor(protected categorieService: CategorieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ categorie }) => {
      this.updateForm(categorie);
    });
  }

  updateForm(categorie: ICategorie) {
    this.editForm.patchValue({
      id: categorie.id,
      categorieName: categorie.categorieName,
      categorieDescription: categorie.categorieDescription,
      dateCreation: categorie.dateCreation
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const categorie = this.createFromForm();
    if (categorie.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieService.update(categorie));
    } else {
      this.subscribeToSaveResponse(this.categorieService.create(categorie));
    }
  }

  private createFromForm(): ICategorie {
    return {
      ...new Categorie(),
      id: this.editForm.get(['id']).value,
      categorieName: this.editForm.get(['categorieName']).value,
      categorieDescription: this.editForm.get(['categorieDescription']).value,
      dateCreation: this.editForm.get(['dateCreation']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorie>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
