import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { RegisterComponent } from './features/sign-in/pages/register.component';
import { LoginComponent } from './features/login/pages/login.component';
import { ArticlesComponent } from './features/articles/pages/articles-page.component';
import { ArticleComponent } from './features/article/pages/article-page.component';
import { CreateArticleComponent } from './features/create-article/pages/create-article.component';
// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path:'articles', component: ArticlesComponent},
   {
    path: 'articles/new',
    component: CreateArticleComponent
  },
  {
    path: 'articles/:id',
    component: ArticleComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
