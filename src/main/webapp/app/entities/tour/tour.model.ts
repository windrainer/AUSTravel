import { BaseEntity } from './../../shared';

export class Tour implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public content?: string,
        public price?: number,
        public discount?: number,
        public imgUrl1?: string,
        public imgUrl2?: string,
        public createBy?: string,
        public createTime?: any,
        public updateBy?: string,
        public updateTime?: any,
    ) {
    }
}
