import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { BehaviorSubject, forkJoin, map } from 'rxjs';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicService } from 'src/app/services/topic.service';

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
  private topicDataSubject = new BehaviorSubject<{ topics: Topic[], subscriptions: number[] } | null>(null);
  public $topicData = this.topicDataSubject.asObservable();

  constructor(
    private topicService: TopicService,
  ) { }

  ngOnInit(): void {
    forkJoin({
      topics: this.topicService.all(),
      subscriptions: this.topicService.getSubscriptions()
    }).pipe(
      map(({ topics, subscriptions }) => ({
        topics,
        subscriptions: subscriptions.map(sub => sub.id)
      }))
    ).subscribe(data => {
      this.topicDataSubject.next(data);
    });
  }

  ngOnDestroy(): void {
    this.topicDataSubject.complete();
  }

  public subscribe(topic: Topic): void {
    this.topicService.subscribe(topic.id).subscribe(() => {
      const currentData = this.topicDataSubject.getValue();
      if (currentData) {
        this.topicDataSubject.next({
          ...currentData,
          subscriptions: [...currentData.subscriptions, topic.id]
        });
      }
    });
  }
}
