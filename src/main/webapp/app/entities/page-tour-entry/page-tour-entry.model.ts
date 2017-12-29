import { BaseEntity } from './../../shared';

export class PageTourEntry implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public heading?: string,
        public subTitle?: string,
        public content?: string,
        public imgUrl1?: string,
        public imgUrl2?: string,
        public cssStyle?: string,
        public createBy?: string,
        public createTime?: any,
        public updateBy?: string,
        public updateTime?: any,
        public colWidth?: string,
        public tour?: BaseEntity,
    ) {
    }
}
