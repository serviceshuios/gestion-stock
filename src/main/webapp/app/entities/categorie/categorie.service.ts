import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategorie } from 'app/shared/model/categorie.model';

type EntityResponseType = HttpResponse<ICategorie>;
type EntityArrayResponseType = HttpResponse<ICategorie[]>;

@Injectable({ providedIn: 'root' })
export class CategorieService {
  public resourceUrl = SERVER_API_URL + 'api/categories';

  constructor(protected http: HttpClient) {}

  create(categorie: ICategorie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categorie);
    return this.http
      .post<ICategorie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(categorie: ICategorie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categorie);
    return this.http
      .put<ICategorie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICategorie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICategorie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(categorie: ICategorie): ICategorie {
    const copy: ICategorie = Object.assign({}, categorie, {
      dateCreation: categorie.dateCreation != null && categorie.dateCreation.isValid() ? categorie.dateCreation.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation != null ? moment(res.body.dateCreation) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((categorie: ICategorie) => {
        categorie.dateCreation = categorie.dateCreation != null ? moment(categorie.dateCreation) : null;
      });
    }
    return res;
  }
}
