import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionStockSharedModule } from 'app/shared';
import {
  AchatComponent,
  AchatDetailComponent,
  AchatUpdateComponent,
  AchatDeletePopupComponent,
  AchatDeleteDialogComponent,
  achatRoute,
  achatPopupRoute
} from './';

const ENTITY_STATES = [...achatRoute, ...achatPopupRoute];

@NgModule({
  imports: [GestionStockSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AchatComponent, AchatDetailComponent, AchatUpdateComponent, AchatDeleteDialogComponent, AchatDeletePopupComponent],
  entryComponents: [AchatComponent, AchatUpdateComponent, AchatDeleteDialogComponent, AchatDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionStockAchatModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
