import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-create-modal',
  templateUrl: './create-modal.component.html',
  styleUrls: ['./create-modal.component.scss']
})
export class CreateModalComponent {


    @Output()
    public closingModal = new EventEmitter<boolean>();

    @Input()
    public currentDir: string;

    public name: string;
    public errorCreating = false;

    constructor(private filesService: FileService) { }

    public closeModalSave(): void {
        let _name = (this.currentDir + '/' + this.name).replace('//', '/');
        this.filesService.create(_name).subscribe(data => {
            if(data['error'] === 'null') {
                this.errorCreating = false;
                this.closingModal.emit(true);
            } else {
                this.errorCreating = true;
            }
        });
    }

    public closeModalClickedOutside(event: MouseEvent): void {
        if(event.target == document.getElementById('modal')) {
            this.closingModal.emit(false);
            this.errorCreating = false;
        }
    }

    public closeModalCancel(): void {
        this.closingModal.emit(false);
        this.errorCreating = false;
    }

}
