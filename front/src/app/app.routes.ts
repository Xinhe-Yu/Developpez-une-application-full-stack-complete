
import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { ArticleComponent } from './pages/article/article.component';
import { ArticleNewComponent } from './pages/article-new/article-new.component';
import { TopicsComponent } from './pages/topics/topics.component';
export const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  { path: 'topics', component: TopicsComponent },
  { path: 'articles/new', component: ArticleNewComponent },
  { path: 'articles/:id', component: ArticleComponent },
];
