import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-file-rename-modal',
  templateUrl: './file-rename-modal.component.html',
  styleUrls: ['./file-rename-modal.component.scss']
})
export class FileRenameModalComponent implements OnInit {

    @Output()
    public closingModal: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input()
    public file: FileEntry;

    @Input()
    public currentDir: string;

    public newName: string;
    public errorRenamingFile = false;

    constructor(private filesService: FileService) { }

    ngOnInit() {
      this.newName = this.file.file;
    }

    closeModalSave() {
      let toRename = (this.currentDir + '/' + this.file.file).replace('//', '/');
      let _newName = (this.currentDir + '/' + this.newName).replace('//', '/');
      this.filesService.rename(toRename, _newName).subscribe(data => {
        if(data['error'] === 'null') {
            this.errorRenamingFile = false;
            this.closingModal.emit(true);
        } else {
          this.errorRenamingFile = true;
        }
      });
    }

    closeModalClickedOutside(event: MouseEvent) {
      if(event.target == document.getElementById('modal')) {
          this.closingModal.emit(false);
          this.errorRenamingFile = false;
      }
    }

    closeModalCancel() {
        this.closingModal.emit(false);
        this.errorRenamingFile = false;
    }

}
