/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AusTravelTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PageTourEntryDetailComponent } from '../../../../../../main/webapp/app/entities/page-tour-entry/page-tour-entry-detail.component';
import { PageTourEntryService } from '../../../../../../main/webapp/app/entities/page-tour-entry/page-tour-entry.service';
import { PageTourEntry } from '../../../../../../main/webapp/app/entities/page-tour-entry/page-tour-entry.model';

describe('Component Tests', () => {

    describe('PageTourEntry Management Detail Component', () => {
        let comp: PageTourEntryDetailComponent;
        let fixture: ComponentFixture<PageTourEntryDetailComponent>;
        let service: PageTourEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AusTravelTestModule],
                declarations: [PageTourEntryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PageTourEntryService,
                    JhiEventManager
                ]
            }).overrideTemplate(PageTourEntryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PageTourEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PageTourEntryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PageTourEntry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pageTourEntry).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
