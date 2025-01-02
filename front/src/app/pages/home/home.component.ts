import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
@Component({
    selector: 'app-home',
    imports: [MatButtonModule],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {
  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }
}
// export class HomeComponent implements OnInit {
//   constructor() { }

//   ngOnInit(): void { }


// }
