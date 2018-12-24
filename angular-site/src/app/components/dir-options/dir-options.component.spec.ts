import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DirOptionsComponent } from './dir-options.component';

describe('DirOptionsComponent', () => {
  let component: DirOptionsComponent;
  let fixture: ComponentFixture<DirOptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DirOptionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DirOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
