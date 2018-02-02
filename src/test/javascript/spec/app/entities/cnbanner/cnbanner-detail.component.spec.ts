/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AusTravelTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CnbannerDetailComponent } from '../../../../../../main/webapp/app/entities/cnbanner/cnbanner-detail.component';
import { CnbannerService } from '../../../../../../main/webapp/app/entities/cnbanner/cnbanner.service';
import { Cnbanner } from '../../../../../../main/webapp/app/entities/cnbanner/cnbanner.model';

describe('Component Tests', () => {

    describe('Cnbanner Management Detail Component', () => {
        let comp: CnbannerDetailComponent;
        let fixture: ComponentFixture<CnbannerDetailComponent>;
        let service: CnbannerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AusTravelTestModule],
                declarations: [CnbannerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CnbannerService,
                    JhiEventManager
                ]
            }).overrideTemplate(CnbannerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CnbannerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CnbannerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cnbanner(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cnbanner).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
