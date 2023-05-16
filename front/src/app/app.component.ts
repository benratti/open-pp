import { Component, OnDestroy, OnInit } from '@angular/core';
import { Player } from './estimate-session/models/player.model';
import { PlayerVoteStatus } from './estimate-session/models/player-vote-status.enum';
import { VoteStatus } from './estimate-session/models/vote-status.enum';
import { Issue } from './estimate-session/models/issue';
import { ReplaySubject, Subject } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {
  issue$: ReplaySubject<Issue> = new ReplaySubject<Issue>();
  issues$: ReplaySubject<Issue[]> = new ReplaySubject<Issue[]>();

  estimateName: string = "Session d'estimation FranceConnect du 15 mai 2023";

  players: Player[] = [
    { alias: 'Jaalib Korror', id: '1', status: PlayerVoteStatus.OBSERVER },
    { alias: 'Crev Elson', id: '2', status: PlayerVoteStatus.NONE },
    { alias: 'Ike Kravhenn', id: '3', status: PlayerVoteStatus.NONE },
    { alias: 'Neala Omega', id: '4', status: PlayerVoteStatus.OBSERVER },
    { alias: 'Bulsar Velos', id: '5', status: PlayerVoteStatus.NONE },
    { alias: 'Braganti Werjua', id: '6', status: PlayerVoteStatus.NONE },
    { alias: 'Lori Dremine', id: '7', status: PlayerVoteStatus.NONE },
    { alias: 'Otara Draav', id: '8', status: PlayerVoteStatus.NONE },
    { alias: 'Penta Axfow', id: '9', status: PlayerVoteStatus.NONE },
    { alias: 'Karva Elatar', id: '10', status: PlayerVoteStatus.OBSERVER },
    { alias: 'Janekah Sheotah', id: '11', status: PlayerVoteStatus.OBSERVER },
  ];

  vote: VoteStatus = VoteStatus.NOT_STARTED;

  private issues: Issue[] = [
    {
      id: '1',
      title: 'ETQ User, je veux manger des pommes',
      description: 'Une petite description en fait jamais de mal',
      weigth: 'XL',
      isVoting: false,
    },
    {
      id: '2',
      title: 'ETQ User, je veux éplucher des pommes',
      description: 'Il me faut un économe pour cela',
      isVoting: false,
    },
    {
      id: '3',
      title: 'ETQ User, je veux cuisiner des tartes aux pommes',
      description:
        "J'aimerais pouvoir enfourner mar tarte au four pour la faire cuire. ",
      isVoting: false,
    },
    {
      id: '4',
      title: 'ETQ User, je veux decouper tartes aux pommes',
      description: "J'ai envie de pouvoir partager ma tarte avec mes amis",
      isVoting: false,
    },
  ];

  title = 'open-pp';

  ngOnInit(): void {
    this.issue$.next(this.issues[0]);
    this.issues$.next(this.issues);
  }

  onSelectedIssue(issue: Issue) {
    console.log(issue);
    this.issue$.next(issue);
  }

  onEstimate($event: Issue) {
    console.log("event receveid"); 
    console.log($event); 
    this.issues = this.issues
    .map((issue) => {
      switch (issue.id) {
        case $event.id:
          return {
            ...issue,
            weigth: $event.weigth,
            isVoting: false,
          };
        default:
          return issue;
      }
    });

    this.issues$.next(this.issues); 
  }

  ngOnDestroy(): void {
    this.issue$.complete();
  }
}
