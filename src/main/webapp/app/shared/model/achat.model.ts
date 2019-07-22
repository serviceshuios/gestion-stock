import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';

export interface IAchat {
  id?: number;
  achatDate?: Moment;
  achatDescription?: string;
  achatQuantite?: number;
  produits?: IProduit[];
}

export class Achat implements IAchat {
  constructor(
    public id?: number,
    public achatDate?: Moment,
    public achatDescription?: string,
    public achatQuantite?: number,
    public produits?: IProduit[]
  ) {}
}
