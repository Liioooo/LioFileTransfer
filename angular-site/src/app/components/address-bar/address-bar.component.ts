import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-address-bar',
  templateUrl: './address-bar.component.html',
  styleUrls: ['./address-bar.component.scss']
})
export class AddressBarComponent {

    @Input()
    public currentDir: string;

    @Output()
    public pathInBarChange = new EventEmitter<string>();

    @Output()
    public reload = new EventEmitter<any>();

    @Output()
    public clickedForwardBackward = new EventEmitter<boolean>();

    constructor() { }

    public pathInBarChanged(): void {
      this.pathInBarChange.emit(this.currentDir);
    }

    public clickedForward(): void {
      this.clickedForwardBackward.emit(true);
    }

    public clickedBackwards(): void {
      this.clickedForwardBackward.emit(false);
    }

    public reloadClicked(): void {
        this.reload.emit();
    }

}
