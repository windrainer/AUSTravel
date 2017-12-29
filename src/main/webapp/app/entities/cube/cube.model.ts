import { BaseEntity } from './../../shared';

export class Cube implements BaseEntity {
    constructor(
        public id?: number,
        public imgUrl?: string,
        public colWidth?: string,
        public subTitle?: string,
        public heading?: string,
        public content?: string,
        public style?: string,
    ) {
    }
}
