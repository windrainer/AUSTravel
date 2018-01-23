import { BaseEntity } from './../../shared';

export class Banner implements BaseEntity {
    constructor(
        public id?: number,
        public background?: string,
        public logo?: string,
        public text1?: string,
        public text2?: string,
        public url1?: string,
        public url2?: string,
        public css1?: string,
        public css2?: string,
        public name?: string,
        public activated?: boolean,
    ) {
        this.activated = false;
    }
}
