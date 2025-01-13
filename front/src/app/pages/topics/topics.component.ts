import { AsyncPipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { BehaviorSubject, catchError, EMPTY, finalize, forkJoin, map, Observable, of } from 'rxjs';
import { Topic } from 'src/app/interfaces/topic.interface';
import { ToastService } from 'src/app/services/toast.service';
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
  public $topicData!: Observable<{ topics: Topic[], subscriptions: Number[] }>;
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  constructor(
    private topicService: TopicService,
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadTopicData();
  }

  private loadTopicData(): void {
    console.log('TopicsComponent: Loading topic data');
    this.loadingSubject.next(true);
    this.$topicData = forkJoin({
      topics: this.topicService.all(),
      subscriptions: this.topicService.getSubscriptions()
    }).pipe(
      map(({ topics, subscriptions }) => {
        console.log('TopicsComponent: Processed topic data', { topics, subscriptions });
        return {
          topics,
          subscriptions: subscriptions.map(sub => sub.id)
        };
      }),
      catchError(error => {
        console.error('TopicsComponent: Error loading topic data', error);
        this.toastService.showError('Failed to load topics. Please try again.');
        return of({ topics: [], subscriptions: [] });
      }),
      finalize(() => {
        console.log('TopicsComponent: Finished loading topic data');
        this.loadingSubject.next(false);
      })
    );
    this.cdr.detectChanges();
  }

  public subscribe(topic: Topic): void {

    this.loadingSubject.next(true);
    this.topicService.subscribe(topic.id).pipe(
      catchError(error => {
        console.error(`TopicsComponent: Error subscribing to topic ${topic.id}`, error);
        this.toastService.showError('Failed to subscribe to topic. Please try again.');
        return EMPTY;
      }),
      finalize(() => {
        console.log(`TopicsComponent: Finished subscribing to topic ${topic.id}`);
        this.loadingSubject.next(false);
      })
    ).subscribe(() => {
      console.log(`TopicsComponent: Successfully subscribed to topic ${topic.id}`);
      this.loadTopicData();
    });
  }
}
