/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AusTravelTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CNPageTourEntryDetailComponent } from '../../../../../../main/webapp/app/entities/c-n-page-tour-entry/cn-page-tour-entry-detail.component';
import { CNPageTourEntryService } from '../../../../../../main/webapp/app/entities/c-n-page-tour-entry/cn-page-tour-entry.service';
import { CNPageTourEntry } from '../../../../../../main/webapp/app/entities/c-n-page-tour-entry/cn-page-tour-entry.model';

describe('Component Tests', () => {

    describe('CNPageTourEntry Management Detail Component', () => {
        let comp: CNPageTourEntryDetailComponent;
        let fixture: ComponentFixture<CNPageTourEntryDetailComponent>;
        let service: CNPageTourEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AusTravelTestModule],
                declarations: [CNPageTourEntryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CNPageTourEntryService,
                    JhiEventManager
                ]
            }).overrideTemplate(CNPageTourEntryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CNPageTourEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CNPageTourEntryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CNPageTourEntry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cNPageTourEntry).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
