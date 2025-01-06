import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { NewArticle } from 'src/app/interfaces/form/newArticle.interface';
import { Topic } from 'src/app/interfaces/topic.interface';
import { ArticleService } from 'src/app/services/article.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-article-new',
  imports: [
    AsyncPipe,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule],
  templateUrl: './article-new.component.html',
  styleUrl: './article-new.component.scss'
})
export class ArticleNewComponent implements OnInit {
  public $topics!: Observable<Topic[]>;
  public form = this.fb.group({
    title: ['', [Validators.required]],
    content: ['', [Validators.required]],
    topicId: ['', [Validators.required]]
  });
  public isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private articleService: ArticleService,
    private topicService: TopicService
  ) { }

  ngOnInit(): void {
    this.$topics = this.topicService.all();

  }

  public onSubmit(): void {
    if (this.form.valid) {
      const newArticle = this.form.getRawValue() as NewArticle;
      this.articleService.create(newArticle).subscribe({
        next: (article) => {
          this.router.navigate(['/articles', article.id]);
        },
        error: (error) => {
          console.error('Error creating article:', error);
        }
      });
    }
  }
}
