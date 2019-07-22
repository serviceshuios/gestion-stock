/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AchatService } from 'app/entities/achat/achat.service';
import { IAchat, Achat } from 'app/shared/model/achat.model';

describe('Service Tests', () => {
  describe('Achat Service', () => {
    let injector: TestBed;
    let service: AchatService;
    let httpMock: HttpTestingController;
    let elemDefault: IAchat;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AchatService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Achat(0, currentDate, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            achatDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Achat', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            achatDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            achatDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Achat(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Achat', async () => {
        const returnedFromService = Object.assign(
          {
            achatDate: currentDate.format(DATE_FORMAT),
            achatDescription: 'BBBBBB',
            achatQuantite: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            achatDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Achat', async () => {
        const returnedFromService = Object.assign(
          {
            achatDate: currentDate.format(DATE_FORMAT),
            achatDescription: 'BBBBBB',
            achatQuantite: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            achatDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Achat', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
