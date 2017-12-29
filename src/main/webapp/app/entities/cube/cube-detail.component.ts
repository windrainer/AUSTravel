import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Cube } from './cube.model';
import { CubeService } from './cube.service';

@Component({
    selector: 'jhi-cube-detail',
    templateUrl: './cube-detail.component.html'
})
export class CubeDetailComponent implements OnInit, OnDestroy {

    cube: Cube;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cubeService: CubeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCubes();
    }

    load(id) {
        this.cubeService.find(id).subscribe((cube) => {
            this.cube = cube;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCubes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cubeListModification',
            (response) => this.load(this.cube.id)
        );
    }
}
