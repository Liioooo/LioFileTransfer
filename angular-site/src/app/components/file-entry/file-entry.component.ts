import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MousePosition} from '../../models/MousePosition';

@Component({
  selector: 'app-file-entry',
  templateUrl: './file-entry.component.html',
  styleUrls: ['./file-entry.component.scss']
})
export class FileEntryComponent implements OnInit {

    @Output()
    public selectedFile: EventEmitter<any> = new EventEmitter();

    @Output()
    public showFileMenu: EventEmitter<MousePosition> = new EventEmitter<MousePosition>();

    @Input()
    public file: FileEntry;

    private preventSingleClick = true;
    private singleClickTimeout;

    constructor() { }

    ngOnInit() {
    }

    singleClicked(mouseEvent: MouseEvent) {
        this.preventSingleClick = false;
        this.singleClickTimeout = setTimeout(() => {
            if (!this.preventSingleClick) {
                this.showFileMenu.emit({
                    x: mouseEvent.clientX,
                    y: mouseEvent.clientY
                });
            }
        }, 200);
    }

    doubleClicked() {
        this.preventSingleClick = true;
        clearTimeout(this.singleClickTimeout);
        this.selectedFile.emit();
    }

}
