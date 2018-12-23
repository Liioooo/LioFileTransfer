import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileRenameModalComponent } from './file-rename-modal.component';

describe('FileRenameModalComponent', () => {
  let component: FileRenameModalComponent;
  let fixture: ComponentFixture<FileRenameModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileRenameModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileRenameModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
