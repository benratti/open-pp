import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { Observable } from 'rxjs';
import { Player } from '../../models/player.model';
import { PlayersService } from '../../services/players.service';
import { PlayerVoteStatus } from '../../models/player-vote-status.enum';
import { VoteStatus } from '../../models/vote-status.enum';
import { Issue } from '../../models/issue';

@Component({
  selector: 'app-poker-table',
  templateUrl: './poker-table.component.html',
  styleUrls: ['./poker-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PokerTableComponent implements OnInit {

  @Input() sessionName !: string
  @Input() players!: Player[];
  @Input() vote: VoteStatus = VoteStatus.NOT_STARTED;
  myChoice : string = 'No Choice';
  @Input() myId!: string;
  @Input() choiceTable: string[] = ['1', '2', '3']; //, '5', '8', '13', '21', '?'];
  @Input() issue !: Issue; 

  @Output() estimateEvent = new EventEmitter<Issue>();

  constructor() {}

  ngOnInit(): void {
    
  }

  getGridTemplate() {
    let MIN_ITEMS = 3;

    const distribution: PlayersDistribution = getPlayersDistribution(
      this.players.length
    );

    const centerItems: number = Math.max(
      MIN_ITEMS,
      distribution.top,
      distribution.bottom
    );

    const gritTempleColumns = {
      'grid-template-columns': `4fr ${centerItems}fr 4fr`,
    };
    console.log(gritTempleColumns);
    return gritTempleColumns;
  }

  getPlayers(type: 'top' | 'bottom' | 'left' | 'right'): Player[] {
    const distribution: PlayersDistribution = getPlayersDistribution(
      this.players.length
    );
    console.log(distribution);
    switch (type) {
      case 'top':
        return this.players.slice(0, distribution.top);

      case 'right':
        return this.players.slice(
          distribution.top,
          distribution.top + distribution.right
        );

      case 'bottom':
        return this.players
          .slice(
            distribution.top + distribution.right,
            distribution.top + distribution.right + distribution.bottom
          )
          .reverse();

      case 'left':
        return this.players.slice(
          distribution.top + distribution.right + distribution.bottom
        );
    }
  }


  onValidate() {
    console.log("validation de l'estimation du " + this.issue.title); 
    this.estimateEvent.emit({
      ...this.issue, 
      weigth : this.getConsensusResult()
    })
  }

  onStart(): void {
    this.players = this.players.map((player) => {
      this.myChoice = '-1';
      this.vote = VoteStatus.IN_PROGRESS;

      if (player.status === PlayerVoteStatus.OBSERVER) {
        return player;
      } else {
        return {
          ...player,
          status: PlayerVoteStatus.VOTE_IN_PROGRESS,
        };
      }
    });
  }

  onStop(): void {
    this.players = this.players.map((player) => {
      this.vote = VoteStatus.DONE;

      if (player.status === PlayerVoteStatus.OBSERVER) {
        return player;
      } else {
        return {
          ...player,
          status: PlayerVoteStatus.VOTE_REVELEAD,
        };
      }
    });
  }

  selectVote(choice: string): void {
    this.players = this.players.map((player) => {
      this.myChoice = choice;

      if (player.status === PlayerVoteStatus.OBSERVER) {
        return player;
      } else {
        return {
          ...player,
          status: PlayerVoteStatus.VOTE_COMPLETED,
          vote:
            player.id === this.myId
              ? choice
              : this.choiceTable[
                  Math.floor(Math.random() * this.choiceTable.length)
                ],
        };
      }
    });
  }

  getResultByChoice(choiceValue: string): number {
    return this.players
      .filter((player) => player.status === PlayerVoteStatus.VOTE_REVELEAD)
      .filter((player) => player.vote === choiceValue).length;
  }

  getConsensusResult(): string {
    const results = this.players
      .filter((p) => p.status !== PlayerVoteStatus.OBSERVER)
      .map((p) => p.vote);

    if (results.length > 0 && this.hasConsensus()) {
      return results[0]!;
    } else {
      throw new Error('No consensus');
    }
  }

  allVotesCompleted(): boolean {
    return this.players
      .filter((p) => p.status !== 'observer')
      .every((p) => p.status === 'vote_completed');
  }

  getPlayersByChoice(choice: string): Player[] {
    return this.players
      .filter((player) => player.status !== 'observer')
      .filter((player) => player.vote === choice);
  }

  isNotStarted(): boolean {
    return this.vote === 'not_started';
  }

  isInProgress(): boolean {
    return this.vote === 'in_progess';
  }

  isCompleted(): boolean {
    return this.isInProgress() === true && this.allVotesCompleted() === true;
  }
  isDone(): boolean {
    return this.vote === 'done';
  }

  hasConsensus(): boolean {
    const votes = this.players
      .filter((p) => p.status !== PlayerVoteStatus.OBSERVER)
      .map((p) => p.vote);

    return new Set(votes).size === 1;
  }
}


class PlayersDistribution {
  top!: number;
  bottom!: number;
  left!: number;
  right!: number;
}

function getPlayersDistribution(numberOfPlayers: number): PlayersDistribution {
  let TABLE_EDGE_NUMBER = 4;

  let MAX_TOP = 100;
  let MAX_BOTTOM = 100;
  let MAX_LEFT = 100;
  let MAX_RIGHT = 100;

  // 9 | 3 - 2 - 2 - 2

  // top = nb_player / table_edge_number + (nb_player % table_edge_number ? )
  // bottom = nb_player % table_edge

  return {
    top: Math.min(
      MAX_TOP,
      Math.floor(numberOfPlayers / TABLE_EDGE_NUMBER) +
        (numberOfPlayers % TABLE_EDGE_NUMBER >= 1 ? 1 : 0)
    ),
    right: Math.min(
      MAX_TOP,
      Math.floor(numberOfPlayers / TABLE_EDGE_NUMBER) +
        (numberOfPlayers % TABLE_EDGE_NUMBER >= 2 ? 1 : 0)
    ),
    bottom: Math.min(
      MAX_TOP,
      Math.floor(numberOfPlayers / TABLE_EDGE_NUMBER) +
        (numberOfPlayers % TABLE_EDGE_NUMBER >= 3 ? 1 : 0)
    ),
    left: Math.min(
      MAX_TOP,
      Math.floor(numberOfPlayers / TABLE_EDGE_NUMBER) +
        (numberOfPlayers % TABLE_EDGE_NUMBER >= 4 ? 1 : 0)
    ),

    //    top: Math.floor(
    //      1 + Math.max(0, numberOfPlayers - (TABLE_EDGE_NUMBER - 1)) / 2
    //    ),
    //    bottom: Math.floor(
    //      1 + Math.max(0, numberOfPlayers - TABLE_EDGE_NUMBER) / 2
    //    ),

    //    left: numberOfPlayers < 3 ? 0 : 1,
    //    right: numberOfPlayers < 4 ? 0 : 1,
  };
}
