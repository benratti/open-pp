import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueCardComponent } from './issue-card.component';

describe('IssueCardComponent', () => {
  let component: IssueCardComponent;
  let fixture: ComponentFixture<IssueCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IssueCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IssueCardComponent);
    component = fixture.componentInstance;

  
  });

  it('should create', () => {
    component.issue = {
      id : "aaaa",
      title : "my-title", 
      isVoting : false,
      description : "a little description" 
    }

    fixture.detectChanges();

    expect(component).toBeTruthy();
  });
});
