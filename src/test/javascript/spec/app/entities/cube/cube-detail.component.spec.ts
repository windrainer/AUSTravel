/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AusTravelTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CubeDetailComponent } from '../../../../../../main/webapp/app/entities/cube/cube-detail.component';
import { CubeService } from '../../../../../../main/webapp/app/entities/cube/cube.service';
import { Cube } from '../../../../../../main/webapp/app/entities/cube/cube.model';

describe('Component Tests', () => {

    describe('Cube Management Detail Component', () => {
        let comp: CubeDetailComponent;
        let fixture: ComponentFixture<CubeDetailComponent>;
        let service: CubeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AusTravelTestModule],
                declarations: [CubeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CubeService,
                    JhiEventManager
                ]
            }).overrideTemplate(CubeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CubeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CubeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cube(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cube).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
