import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { CreateGroupComponent } from './create/create-group/create-group.component';
import { CreatePostComponent } from './create/create-post/create-post.component';
import { HomeComponent } from './home/home.component';
import { AllGroupComponent } from './view/all-group/all-group.component';
import { PostComponent } from './view/post/post.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'groups', component: AllGroupComponent },
  { path: 'post/create', component: CreatePostComponent },
  { path: 'post/:id', component: PostComponent },
  { path: 'group/create', component: CreateGroupComponent },
];

@NgModule({
  imports: [BrowserModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
