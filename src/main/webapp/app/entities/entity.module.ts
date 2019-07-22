import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'categorie',
        loadChildren: './categorie/categorie.module#GestionStockCategorieModule'
      },
      {
        path: 'produit',
        loadChildren: './produit/produit.module#GestionStockProduitModule'
      },
      {
        path: 'achat',
        loadChildren: './achat/achat.module#GestionStockAchatModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionStockEntityModule {}
