import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-file-entry',
  templateUrl: './file-entry.component.html',
  styleUrls: ['./file-entry.component.scss']
})
export class FileEntryComponent implements OnInit {

    @Output()
    public selectedFile: EventEmitter<any> = new EventEmitter();

    @Output()
    public showFileMenu: EventEmitter<any> = new EventEmitter();

    @Input()
    public file: FileEntry;

    private preventSingleClick = true;
    private singleClickTimeout;

    constructor() { }

    ngOnInit() {
    }

    singleClicked() {
        this.preventSingleClick = false;
        this.singleClickTimeout = setTimeout(() => {
            if (!this.preventSingleClick) {
              this.showFileMenu.emit();
            }
        }, 200);
    }

    doubleClicked() {
        this.preventSingleClick = true;
        clearTimeout(this.singleClickTimeout);
        this.selectedFile.emit();
    }

}
