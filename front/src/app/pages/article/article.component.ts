import { AsyncPipe, DatePipe } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { finalize, forkJoin, map, Observable, of, switchMap, tap, withLatestFrom } from 'rxjs';
import { Article } from 'src/app/interfaces/article.interface';
import { Comments } from 'src/app/interfaces/comments.interface';
import { NewComment } from 'src/app/interfaces/form/newComment.interface';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-article',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    AsyncPipe,
    DatePipe,
    ReactiveFormsModule
  ],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ArticleComponent implements OnInit {
  @ViewChild('commentInput') input!: ElementRef<HTMLInputElement>;
  public $articleData!: Observable<{ article: Article, comments: Comments }>;
  public form: FormGroup = this.fb.group({
    content: ['', [Validators.required]]
  })
  public isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.$articleData = this.route.params.pipe(
      map(params => Number(params['id'])),
      switchMap(id => forkJoin({
        article: this.articleService.detail(id),
        comments: this.articleService.getComments(id)
      }))
    );
  }

  public submit(): void {
    if (this.form.invalid) {
      return;
    }

    this.isSubmitting = true;
    const newComment = this.form.getRawValue() as NewComment;
    const articleId = this.route.snapshot.params['id'];
    this.articleService.createComment(articleId, newComment).pipe(
      switchMap(() => this.articleService.getComments(articleId)),
      withLatestFrom(this.$articleData),
      map(([newComments, currentData]) => ({
        ...currentData,
        comments: newComments
      })),
      tap(updatedData => {
        this.$articleData = of(updatedData);
      }),
      finalize(() => {
        this.isSubmitting = false;
        this.form.reset();
        setTimeout(() => {
          this.input.nativeElement.blur();
        }, 200);
        this.form.markAsUntouched();
        this.form.markAsPristine();
        this.cdr.markForCheck();
      })
    ).subscribe(() => {
      this.scrollToBottom();
    });
  }

  private scrollToBottom(): void {
    setTimeout(() => {
      window.scrollTo(0, document.body.scrollHeight);
    }, 100);
  }
}
