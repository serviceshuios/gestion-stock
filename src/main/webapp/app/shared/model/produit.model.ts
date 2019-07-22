import { ICategorie } from 'app/shared/model/categorie.model';
import { IAchat } from 'app/shared/model/achat.model';

export interface IProduit {
  id?: number;
  produitNom?: string;
  produitDescription?: string;
  produitPrix?: number;
  produitStock?: number;
  owner?: ICategorie;
  owners?: IAchat[];
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public produitNom?: string,
    public produitDescription?: string,
    public produitPrix?: number,
    public produitStock?: number,
    public owner?: ICategorie,
    public owners?: IAchat[]
  ) {}
}
