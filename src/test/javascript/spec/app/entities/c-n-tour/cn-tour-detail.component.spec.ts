/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AusTravelTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CNTourDetailComponent } from '../../../../../../main/webapp/app/entities/c-n-tour/cn-tour-detail.component';
import { CNTourService } from '../../../../../../main/webapp/app/entities/c-n-tour/cn-tour.service';
import { CNTour } from '../../../../../../main/webapp/app/entities/c-n-tour/cn-tour.model';

describe('Component Tests', () => {

    describe('CNTour Management Detail Component', () => {
        let comp: CNTourDetailComponent;
        let fixture: ComponentFixture<CNTourDetailComponent>;
        let service: CNTourService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AusTravelTestModule],
                declarations: [CNTourDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CNTourService,
                    JhiEventManager
                ]
            }).overrideTemplate(CNTourDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CNTourDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CNTourService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CNTour(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cNTour).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
