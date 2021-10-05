import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreationFournisseurComponent } from './creation-article.component';

describe('CreationArticleComponent', () => {
  let component: CreationFournisseurComponent;
  let fixture: ComponentFixture<CreationFournisseurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreationFournisseurComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreationFournisseurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
