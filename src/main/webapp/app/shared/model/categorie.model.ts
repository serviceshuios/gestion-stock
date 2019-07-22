import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';

export interface ICategorie {
  id?: number;
  categorieName?: string;
  categorieDescription?: string;
  dateCreation?: Moment;
  produits?: IProduit[];
}

export class Categorie implements ICategorie {
  constructor(
    public id?: number,
    public categorieName?: string,
    public categorieDescription?: string,
    public dateCreation?: Moment,
    public produits?: IProduit[]
  ) {}
}
