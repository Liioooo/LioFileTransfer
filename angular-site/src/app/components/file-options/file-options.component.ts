import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MousePosition} from '../../models/MousePosition';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-file-options',
  templateUrl: './file-options.component.html',
  styleUrls: ['./file-options.component.scss']
})
export class FileOptionsComponent {

    @Output()
    public openDir = new EventEmitter<FileEntry>();

    @Output()
    public showRenameModal = new EventEmitter<FileEntry>();

    @Output()
    public showDeleteModal = new EventEmitter<FileEntry>();

    @Output()
    public copy = new EventEmitter<FileEntry>();

    @Output()
    public cut = new EventEmitter<FileEntry>();

    @Input()
    public file: FileEntry;

    @Input()
    public currentDir: string;

    @Input()
    public mousePosition: MousePosition;

    constructor(private files: FileService) { }

    public openDirClick() {
        this.openDir.emit(this.file);
    }

    public downloadFileClick() {
      this.files.downloadFile((this.currentDir + '/' + this.file.file).replace('//', '/'));
    }

    public renameFileClick() {
        this.showRenameModal.emit(this.file);
    }

    public deleteFileClick() {
        this.showDeleteModal.emit(this.file);
    }

    public copyClick() {
        this.copy.emit(this.file);
    }

    public cutClick() {
        this.cut.emit(this.file);

    }

}
