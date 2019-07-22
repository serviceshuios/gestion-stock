/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestionStockTestModule } from '../../../test.module';
import { ProduitComponent } from 'app/entities/produit/produit.component';
import { ProduitService } from 'app/entities/produit/produit.service';
import { Produit } from 'app/shared/model/produit.model';

describe('Component Tests', () => {
  describe('Produit Management Component', () => {
    let comp: ProduitComponent;
    let fixture: ComponentFixture<ProduitComponent>;
    let service: ProduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionStockTestModule],
        declarations: [ProduitComponent],
        providers: []
      })
        .overrideTemplate(ProduitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProduitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Produit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.produits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
