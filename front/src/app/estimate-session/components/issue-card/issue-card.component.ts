import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Issue } from '../../models/issue';

@Component({
  selector: 'app-issue-card',
  templateUrl: './issue-card.component.html',
  styleUrls: ['./issue-card.component.scss']
})
export class IssueCardComponent {

  @Input() issue !: Issue; 
  @Output() issueSelected = new EventEmitter<string>();

  onSelectedIssue() {
    this.issueSelected.emit(this.issue.id); 
  }


}
