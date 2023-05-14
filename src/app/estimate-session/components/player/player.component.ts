import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { Player } from '../../models/player.model';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PlayerComponent {

  @Input() player !: Player; 

}
