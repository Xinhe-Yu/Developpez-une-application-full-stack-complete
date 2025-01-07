import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { forkJoin, map, Observable } from 'rxjs';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicService } from 'src/app/services/topic.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-topics',
  imports: [
    AsyncPipe,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent implements OnInit {
  public $topicData!: Observable<{ topics: Topic[], subscriptions: Number[] }>;

  constructor(
    private topicService: TopicService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.$topicData = forkJoin({
      topics: this.topicService.all(),
      subscriptions: this.userService.getSubscriptions()
    }).pipe(
      map(({ topics, subscriptions }) => ({
        topics,
        subscriptions: subscriptions.map(sub => sub.id)
      }))
    );
  }

  public subscribe(topic: Topic): void {
    this.topicService.subscribe(topic.id).subscribe(() => {
      this.$topicData = this.$topicData.pipe(
        map(({ topics, subscriptions }) => ({
          topics,
          subscriptions: [...subscriptions, topic.id]
        }))
      );
    });
  }

  public unsubscribe(topic: Topic): void {
    this.topicService.unsubscribe(topic.id).subscribe(() => {
      this.$topicData = this.$topicData.pipe(
        map(({ topics, subscriptions }) => ({
          topics,
          subscriptions: subscriptions.filter(sub => sub !== topic.id)
        }))
      );
    });
  }
}
