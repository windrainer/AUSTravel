import {
    Component,
    OnDestroy,
    AfterViewInit,
    EventEmitter,
    Input,
    Output
} from '@angular/core';

import 'tinymce';
import 'tinymce/themes/modern';

import 'tinymce/plugins/table';
import 'tinymce/plugins/link';
import 'tinymce/plugins/paste';

declare var tinymce: any;

@Component({
    selector: 'jhi-tinymce-editor',
    template: `<textarea name="content" class="form-control" value="{{content}}" id="{{elementId}}"></textarea>`
})
export class TinymceComponent implements AfterViewInit, OnDestroy {
    @Input() elementId: string;
    @Input() content: string;
    @Output() onEditorKeyup: EventEmitter<any> = new EventEmitter<any>();

    editor;

    ngAfterViewInit() {
        tinymce.init({
            selector: '#' + this.elementId,
            plugins: ['link', 'paste', 'table'],
            skin_url: '../../../content/tinymce/skins/lightgray',
            setup: (editor) => {
                this.editor = editor;
                editor.on('keyup', () => {
                    const content = editor.getContent();
                    this.onEditorKeyup.emit(content);
                });
            },
        });
    }

    ngOnDestroy() {
        tinymce.remove(this.editor);
    }
}
