  import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { Issue } from '../../models/issue';

@Component({
  selector: 'app-issues-board',
  templateUrl: './issues-board.component.html',
  styleUrls: ['./issues-board.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class IssuesBoardComponent {

  @Input() issues !: Issue[]; 
  @Output() selectedIssueEvent = new EventEmitter<Issue>();


  onSelectedIssue(selectedIssueId : string) {
    
    this.issues = this.issues
      .map( issue => {
          const modifiedIssue = {
            ...issue, 
            isVoting : issue.id === selectedIssueId
          }; 
          return modifiedIssue; 
      })
     ; 

    const currentIssue = this.issues.find( issue => issue.id === selectedIssueId); 
    this.selectedIssueEvent.emit(currentIssue); 
    
    }
  

}
