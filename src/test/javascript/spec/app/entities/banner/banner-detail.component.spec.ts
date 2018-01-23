/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AusTravelTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BannerDetailComponent } from '../../../../../../main/webapp/app/entities/banner/banner-detail.component';
import { BannerService } from '../../../../../../main/webapp/app/entities/banner/banner.service';
import { Banner } from '../../../../../../main/webapp/app/entities/banner/banner.model';

describe('Component Tests', () => {

    describe('Banner Management Detail Component', () => {
        let comp: BannerDetailComponent;
        let fixture: ComponentFixture<BannerDetailComponent>;
        let service: BannerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AusTravelTestModule],
                declarations: [BannerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BannerService,
                    JhiEventManager
                ]
            }).overrideTemplate(BannerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BannerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BannerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Banner(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.banner).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
