import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';
import {MousePosition} from '../../models/MousePosition';
import {Clipboard} from '../../models/Clipboard';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-dir-options',
  templateUrl: './dir-options.component.html',
  styleUrls: ['./dir-options.component.scss']
})
export class DirOptionsComponent implements OnDestroy{

    @Output()
    public reloadFiles = new EventEmitter<any>();

    @Output()
    public createDirFile = new EventEmitter<any>();

    @Output()
    public pastedFile = new EventEmitter<boolean>();

    @Input()
    public file: FileEntry;

    @Input()
    public currentDir: string;

    @Input()
    public mousePosition: MousePosition;

    @Input()
    public clipboard: Clipboard;

    private hasPastedFile = false;

    constructor(private files: FileService) { }

    ngOnDestroy(): void {
        this.pastedFile.emit(this.hasPastedFile);
    }

    public reloadFilesClicked(): void {
        this.reloadFiles.emit();
    }

    public newDirFileClicked(): void {
        this.createDirFile.emit(this.hasPastedFile);
    }

    public pasteFile(): void {
        if (this.clipboard.file === null) return;
        this.hasPastedFile = true;
        if(this.clipboard.copyCut == 'copy') {
            this.files.copy(this.clipboard.file, this.currentDir).subscribe(data => {
                this.clipboard.file = null;
            });
        } else {
            this.files.move(this.clipboard.file, this.currentDir).subscribe(data => {
                this.clipboard.file = null;
            });
        }
    }
}
