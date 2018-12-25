import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-address-bar',
  templateUrl: './address-bar.component.html',
  styleUrls: ['./address-bar.component.scss']
})
export class AddressBarComponent implements OnInit {

    @Input()
    public currentDir: string;

    @Output()
    public pathInBarChange: EventEmitter<string> = new EventEmitter<string>();

    @Output()
    public reload: EventEmitter<any> = new EventEmitter<any>();

    @Output()
    public clickedForwardBackward: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor() { }

    ngOnInit() {
    }

    public pathInBarChanged() {
      this.pathInBarChange.emit(this.currentDir);
    }

    public clickedForward() {
      this.clickedForwardBackward.emit(true);
    }

    public clickedBackwards() {
      this.clickedForwardBackward.emit(false);
    }

    public reloadClicked() {
        this.reload.emit();
    }

}
