import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MousePosition} from '../../models/MousePosition';

@Component({
  selector: 'app-dir-options',
  templateUrl: './dir-options.component.html',
  styleUrls: ['./dir-options.component.scss']
})
export class DirOptionsComponent implements OnInit {

    @Output()
    public reloadFiles: EventEmitter<any> = new EventEmitter<any>();

    @Output()
    public createDirFile: EventEmitter<any> = new EventEmitter<any>();

    @Input()
    public file: FileEntry;

    @Input()
    public currentDir: string;

    @Input()
    public mousePosition: MousePosition;

    constructor() { }

    ngOnInit() {
    }

    reloadFilesClicked() {
        this.reloadFiles.emit();
    }

    newDirFileClicked() {
        this.createDirFile.emit();
    }
}
