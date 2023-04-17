import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RegisterComponent } from './auth/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
<<<<<<< HEAD:frontend - angular/src/app/app.module.ts
import { HomeComponent } from './home/home.component';
import { TokenInterceptor } from './token-interceptor';
import { VoteButtonComponent } from './home/vote-button/vote-button.component';
import { PostBoxComponent } from './home/post-box/post-box.component';
import { GroupSidebarComponent } from './home/group-sidebar/group-sidebar.component';
import { SidebarComponent } from './home/sidebar/sidebar.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CreateGroupComponent } from './create/create-group/create-group.component';
import { CreatePostComponent } from './create/create-post/create-post.component';
import { DialogueBoxComponent } from './common/dialogue-box/dialogue-box.component';
import { AllGroupComponent } from './view/all-group/all-group.component';
import { AllPostComponent } from './view/all-post/all-post.component';
import { PostComponent } from './view/post/post.component';
import { GroupComponent } from './view/group/group.component';
import { EditorModule, TINYMCE_SCRIPT_SRC } from '@tinymce/tinymce-angular';
import { CommentComponent } from './create/create-comment/create-comment.component';
import { ProfileComponent } from './view/profile/profile.component';
=======
>>>>>>> parent of 053d69ea (complete frontend):frontend/jblog_frontend/src/app/app.module.ts

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
<<<<<<< HEAD:frontend - angular/src/app/app.module.ts
    HomeComponent,
    VoteButtonComponent,
    PostBoxComponent,
    GroupSidebarComponent,
    SidebarComponent,
    CreateGroupComponent,
    CreatePostComponent,
    DialogueBoxComponent,
    AllGroupComponent,
    AllPostComponent,
    PostComponent,
    GroupComponent,
    CommentComponent,
    ProfileComponent,
=======
>>>>>>> parent of 053d69ea (complete frontend):frontend/jblog_frontend/src/app/app.module.ts
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
