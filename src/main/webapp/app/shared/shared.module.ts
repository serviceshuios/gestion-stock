import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GestionStockSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [GestionStockSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [GestionStockSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionStockSharedModule {
  static forRoot() {
    return {
      ngModule: GestionStockSharedModule
    };
  }
}
